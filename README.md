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

        export BINTRAY_USER=<user>
        export BINTRAY_KEY=<key>
        ./gradlew bintrayUpload

# Licenses

Refer to the `LICENSE` file for details.
