import java.net.HttpURLConnection
import java.net.URL

def log(String msg) {
    println "[verify] ${msg}"
    System.out.flush()
}

def testJettyGoal(File baseDir, String goal, String mode, int port, int maxWaitSeconds) {
    def serverUrl = "http://localhost:${port}/"

    log "========================================"
    log "Testing ${mode}"
    log "========================================"
    log "Goal: mvn ${goal}"
    log "Server URL: ${serverUrl}"
    log ""

    log "Launching Jetty server..."
    def processBuilder = new ProcessBuilder("mvn", goal)
    processBuilder.directory(baseDir)
    processBuilder.redirectErrorStream(true)

    def process = processBuilder.start()
    log "Jetty process started (PID: ${process.pid()})"

    def outputBuffer = new StringBuilder()
    Thread.start {
        try {
            process.inputStream.eachLine { line ->
                outputBuffer.append(line).append("\n")
                if (line.contains("Started") || line.contains("Exception") ||
                    line.contains("Error") || line.contains("FAILED")) {
                    log "Jetty: ${line}"
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
            log "Last Jetty output:"
            log outputBuffer.toString().split("\n").takeRight(30).join("\n")
            throw new RuntimeException("${mode} did not start within ${maxWaitSeconds} seconds")
        }

        log ""

    } finally {
        log "Stopping Jetty server..."
        process.destroy()

        def stopped = process.waitFor(10, java.util.concurrent.TimeUnit.SECONDS)
        if (!stopped && process.isAlive()) {
            log "Force killing Jetty process..."
            process.destroyForcibly()
            process.waitFor(5, java.util.concurrent.TimeUnit.SECONDS)
        }

        log "Jetty server stopped."
        log ""
    }
}

// Configuration
def baseDir = new File(basedir, "project/basic")
def port = 8080
def maxWaitSeconds = 20

log "========================================"
log "Starting Jetty integration tests"
log "========================================"
log "Base directory: ${baseDir}"
log "Max wait time: ${maxWaitSeconds} seconds per test"
log ""

// Test 1: Development mode (jetty:run)
testJettyGoal(baseDir, "jetty:run", "Development mode", port, maxWaitSeconds)

// Test 2: Production mode (jetty:run-war)
testJettyGoal(baseDir, "jetty:run-war", "Production mode", port, maxWaitSeconds)

log "========================================"
log "All integration tests PASSED"
log "========================================"

return true
