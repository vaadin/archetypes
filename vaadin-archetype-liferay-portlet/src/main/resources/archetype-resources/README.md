# Project Base for a Vaadin Portlet application

This project can be used as a starting point to create a Vaadin Portlet application running in the Liferay container.
It has the necessary dependencies and files to help you get started.

To build and deploy the Vaadin Porlet, do the following:
1. Add the following property into the in Liferay's Tomcat `setenv.sh` (`{liferay.home}/tomcat-<version>/bin`) before starting Liferay:

`-Dvaadin.portlet.static.resources.mapping=/o/vaadin-portlet-static/`

2. Build project with `mvn install`

   _Note_: Currently you'll need a local build of the Portlet add-on, from the feature/liferay branch. To install a snapshot build to your local Maven repository,
   execute git clone --single-branch --branch feature/liferay https://github.com/vaadin/portlet.git; cd portlet; mvn install -DskipTests=true

3. Start Liferay by using `{liferay.home}/tomcat-<version>/bin/startup.sh` (`startup.bat`) of the Liferay's bundle
   or by downloading and running the Docker image.

4. Copy the portlet WAR into `{liferay.home}/deploy` to deploy it. Note that the static resources bundle Portlet should be deployed first.

Some useful links:
- [Installing Liferay](https://learn.liferay.com/dxp/latest/en/installation-and-upgrades/installing-liferay.html)
- [Vaadin Portlet](https://github.com/vaadin/portlet)
- [Documentation](https://vaadin.com/docs/v14/flow/integrations/portlet)
- [Tutorials](https://github.com/vaadin/addressbook-portlet/)