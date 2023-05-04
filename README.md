# Datastore

[ ![Download](https://api.bintray.com/packages/brightsparklabs/maven/datastore/images/download.svg) ](https://bintray.com/brightsparklabs/maven/datastore/_latestVersion)

A standardised means to store large amounts of files into a store.

# Usage

```java
final FileDataStore fileDataStore = new FileDataStore(
        ImmutableFileDataStoreConfiguration
            .builder()
            .baseDirectory(Paths.get("/")
            .build());

final String id = fileDataStore.put(data);
final ByteSource retrievedData = fileDataStore.get(id);
final Path backingFile = fileDataStore.getPath(id);
```

# Development

- Publish new versions via:

```bash
# Set env vars.
export ORG_GRADLE_PROJECT_signingKey=<secrets.PGP_SIGNING_KEY>
export ORG_GRADLE_PROJECT_signingPassword=<secrets.PGP_SIGNING_PASSWORD>
export ORG_GRADLE_PROJECT_sonatypeUsername=<secrets.MAVEN_CENTRAL_USERNAME>
export ORG_GRADLE_PROJECT_sonatypePassword=<secrets.MAVEN_CENTRAL_PASSWORD>
# Run the publishToMavenCentral gradle task
./gradlew publishToMavenCentral
```

# Licenses

Refer to the `LICENSE` file for details.
