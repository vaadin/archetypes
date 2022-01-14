# Project Base for a Vaadin Portlet's static resources bundle

This project can be used as a starting point to create a static bundle Portlet sharing the 
commonly used frontend resources for other Vaadin portlets.
It has the necessary dependencies and files to help you get started.

To build and deploy the static resource bundle, do the following:
1. Add the following property into the in Liferay's Tomcat `setenv.sh` (`{liferay.home}/tomcat-<version>/bin`) before starting Liferay:

`-Dvaadin.portlet.static.resources.mapping=/o/vaadin-portlet-static/`

2. Build project with `mvn install`

    _Note_: Currently you'll need a local build of the Portlet add-on, from the feature/liferay branch. To install a snapshot build to your local Maven repository, 
execute git clone --single-branch --branch feature/liferay https://github.com/vaadin/portlet.git; cd portlet; mvn install -DskipTests=true

3. Start Liferay by using `{liferay.home}/tomcat-<version>/bin/startup.sh` (`startup.bat`) of the Liferay's bundle 
or by downloading and running the Docker image.

4. Copy the `vaadin-portlet-static.war` into `{liferay.home}/deploy` to deploy it.

Some useful links:
- [Installing Liferay](https://learn.liferay.com/dxp/latest/en/installation-and-upgrades/installing-liferay.html)
- [Vaadin Portlet](https://github.com/vaadin/portlet/tree/feature/liferay)
- [Documentation](https://github.com/vaadin/flow-and-components-documentation/tree/master/documentation/portlet-support)
- [Tutorials](https://github.com/vaadin/addressbook-portlet/)