package com.brightsparklabs.datastore

import com.google.common.base.Charsets
import com.google.common.io.ByteSource
import com.google.common.io.Files
import spock.lang.Specification

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Unit tests for {@link FileDataStore}.
 */
class FileDataStoreTest extends Specification {
    def "put and get 3 directory levels"() {
        setup:
        int expectedLevels = 3
        String expectedContents = "Hello World"
        ByteSource sourceData = ByteSource.wrap(expectedContents.bytes)

        Path baseDir = createTempDir()
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(baseDir).levels(expectedLevels).build())

        when:
        String uuid = fileDataStore.put(sourceData)
        ByteSource getResult = fileDataStore.get(uuid)

        then:
        Path expectedFile = getExpectedFile(fileDataStore, uuid)
        getExpectedDirectoryLevels(expectedFile, baseDir) == expectedLevels
        checkExpectedFile(expectedFile, expectedContents, getResult)
    }

    def "put and get 5 directory levels"() {
        setup:
        int expectedLevels = 5
        String expectedContents = "Hello World"
        ByteSource sourceData = ByteSource.wrap(expectedContents.bytes)
        Path baseDir = createTempDir()
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(baseDir).levels(expectedLevels).build())

        when:
        String uuid = fileDataStore.put(sourceData)
        ByteSource getResult = fileDataStore.get(uuid)

        then:
        Path expectedFile = getExpectedFile(fileDataStore, uuid)
        getExpectedDirectoryLevels(expectedFile, baseDir) == expectedLevels
        checkExpectedFile(expectedFile, expectedContents, getResult)
    }

    def "get FileNotFound"() {
        setup:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).build())

        when:
        fileDataStore.get(UUID.randomUUID().toString())

        then:
        thrown FileNotFoundException
    }

    def "getPath default 3 directory levels"() {
        setup:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).build())

        expect:
        fileDataStore.getPath(uuid).toString() == path

        where:
        uuid                                   | path
        "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14"
        "7AA4145F-53A5-4F8F-B30A-125EFE697DD2" | "/7A/A4/14"
    }

    def "getPath 5 directory levels"() {
        setup:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).levels(5).build())

        expect:
        fileDataStore.getPath(uuid).toString() == path

        where:
        uuid                                   | path
        "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53"
        "7AA4145F-53A5-4F8F-B30A-125EFE697DD2" | "/7A/A4/14/5F/53"
    }

    def "getPath invalid UUID"() {
        setup:
        final uuid = "not-a-uuid"
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).build())

        when:
        fileDataStore.getPath(uuid)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == String.format("Provided id [%s] is not a valid UUID", uuid)
    }

    def "ImmutableFileDataStoreConfiguration no base directory specified"() {
        when:
        ImmutableFileDataStoreConfiguration.builder().build()

        then:
        IllegalStateException ex = thrown()
        ex.message == "Cannot build Configuration, some of required attributes are not set [baseDirectory]"
    }

    def "ImmutableFileDataStoreConfiguration exceeds max levels"() {
        when:
        ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).levels(18).build()

        then:
        IllegalStateException ex = thrown()
        ex.message == "Number of directory levels must be less than or equal to 16"
    }

    // -------------------------------------------------------------------------
    // PRIVATE METHODS
    // -------------------------------------------------------------------------

    private void checkExpectedFile(Path expectedFile, String expectedContents, ByteSource getResult) {
        java.nio.file.Files.exists(expectedFile)
        getFileContents(expectedFile) == expectedContents
        new String(getResult.read()) == expectedContents
    }

    private int getExpectedDirectoryLevels(Path path, Path baseDir) {
        return path.parent.toString().replace(baseDir.toString() + "/", "").split("/").size()
    }

    private Path getExpectedFile(fileDataStore, String uuid) {
        Path dirPath = fileDataStore.getPath(uuid)
        return Paths.get(dirPath.toString(), uuid)
    }

    private String getFileContents(Path file) {
        return Files.asCharSource(file.toFile(), Charsets.UTF_8).read()
    }

    private Path createTempDir() {
        return java.nio.file.Files.createTempDirectory("FileDataStoreTest")
    }
}
