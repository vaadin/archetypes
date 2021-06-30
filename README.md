

## Vaadin Archetypes

Root project for Vaadin archetypes

### Building

Run the following command providing the version of vaadin to use in order to build and install the archetype in your local maven repository:

```
mvn clean install "-Dvaadin.version=20.0.3"
```

### Using the archetype

Go to the folder you want to create your new project and run:

```
mvn archetype:generate -DarchetypeCatalog=local
```

Answer the questions to configure your application, and finally go to the new application folder and run `mvn` to start the application.
