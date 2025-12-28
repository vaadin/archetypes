import java.net.HttpURLConnection
import java.net.URL

def log(String msg) {
    println "[verify] ${msg}"
    System.out.flush()
}

def testServer(File baseDir, List<String> command, String mode, int port, int maxWaitSeconds) {
    def serverUrl = "http://localhost:${port}/"

    log "========================================"
    log "Testing ${mode}"
    log "========================================"
    log "Command: ${command.join(' ')}"
    log "Server URL: ${serverUrl}"
    log ""

    log "Launching server..."
    def processBuilder = new ProcessBuilder(command)
    processBuilder.directory(baseDir)
    processBuilder.redirectErrorStream(true)

    def process = processBuilder.start()
    log "Process started (PID: ${process.pid()})"

    def outputBuffer = new StringBuilder()
    Thread.start {
        try {
            process.inputStream.eachLine { line ->
                outputBuffer.append(line).append("\n")
                if (line.contains("Started") || line.contains("Exception") ||
                    line.contains("Error") || line.contains("FAILED") ||
                    line.contains("Tomcat started") || line.contains("Application run failed")) {
                    log "Server: ${line}"
                    System.out.flush()
                }
            }
        } catch (Exception e) {
            // Stream closed
        }
    }

    try {
        def serverReady = false
        def startTime = System.currentTimeMillis()
        def attempt = 0

        log ""
        log "Polling for server readiness..."

        while (!serverReady && (System.currentTimeMillis() - startTime) < maxWaitSeconds * 1000) {
            attempt++
            def elapsed = (System.currentTimeMillis() - startTime) / 1000

            try {
                Thread.sleep(3000)

                log "Attempt ${attempt}: Connecting to ${serverUrl} (${elapsed.intValue()}s elapsed)..."

                def connection = (HttpURLConnection) new URL(serverUrl).openConnection()
                connection.setConnectTimeout(5000)
                connection.setReadTimeout(10000)
                connection.setRequestMethod("GET")

                def responseCode = connection.getResponseCode()
                log "Attempt ${attempt}: Response code = ${responseCode}"

                if (responseCode == 200) {
                    serverReady = true
                    log ""
                    log "SUCCESS: ${mode} returned HTTP 200!"
                }
                connection.disconnect()
            } catch (java.net.ConnectException e) {
                log "Attempt ${attempt}: Connection refused (server starting...)"
            } catch (java.net.SocketTimeoutException e) {
                log "Attempt ${attempt}: Connection timeout (server busy...)"
            } catch (Exception e) {
                log "Attempt ${attempt}: ${e.class.simpleName}: ${e.message}"
            }
        }

        if (!serverReady) {
            log ""
            log "FAILURE: ${mode} did not respond within ${maxWaitSeconds} seconds"
            log ""
            log "Last server output:"
            log outputBuffer.toString().split("\n").takeRight(30).join("\n")
            throw new RuntimeException("${mode} did not start within ${maxWaitSeconds} seconds")
        }

        log ""

    } finally {
        log "Stopping server..."
        process.destroy()

        def stopped = process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)
        if (!stopped && process.isAlive()) {
            log "Force killing process..."
            process.destroyForcibly()
            process.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)
        }

        log "Server stopped."
        log ""
    }
}

// Configuration
def baseDir = new File(basedir, "project/basic")
def port = 8080
def maxWaitSeconds = 60

log "========================================"
log "Starting Spring Boot integration tests"
log "========================================"
log "Base directory: ${baseDir}"
log "Max wait time: ${maxWaitSeconds} seconds per test"
log ""

// Test 1: Development mode (spring-boot:run)
testServer(baseDir, ["mvn", "spring-boot:run"], "Development mode", port, maxWaitSeconds)

// Test 2: Production mode (java -jar)
def targetDir = new File(baseDir, "target")
def jarFiles = targetDir.listFiles({ dir, name -> name.endsWith(".jar") && !name.contains("original") } as FilenameFilter)

if (!jarFiles || jarFiles.length == 0) {
    throw new RuntimeException("No JAR file found in ${targetDir}")
}

def jarFile = jarFiles[0]
log "Found JAR file: ${jarFile.name}"

testServer(baseDir, ["java", "-jar", jarFile.absolutePath], "Production mode", port, maxWaitSeconds)

log "========================================"
log "All integration tests PASSED"
log "========================================"

return true
