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
    def "put and get all directory levels"() {
        setup:
        String expectedContents = "Hello World"
        ByteSource sourceData = ByteSource.wrap(expectedContents.bytes)

        when:
        Path baseDir = createTempDir()
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(baseDir).levels(level).build())
        String uuid = fileDataStore.put(sourceData)
        ByteSource getResult = fileDataStore.get(uuid)

        then:
        Path expectedFile = fileDataStore.getPath(uuid)
        getExpectedDirectoryLevels(expectedFile, baseDir) == level
        checkExpectedFile(expectedFile, expectedContents, getResult)

        where:
        level << [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16]
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

    def "getPath all directory levels"() {
        when:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).levels(level).build())

        then:
        fileDataStore.getPath(uuid).toString() == path

        where:
        level || uuid                                   | path
        1     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        2     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        3     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        4     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        5     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        6     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        7     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        8     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        9     || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        10    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        11    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/12/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        12    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/12/5e/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        13    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/12/5e/fe/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        14    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/12/5e/fe/69/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        15    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/12/5e/fe/69/7d/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
        16    || "7aa4145f-53a5-4f8f-b30a-125efe697dd2" | "/7a/a4/14/5f/53/a5/4f/8f/b3/0a/12/5e/fe/69/7d/d2/7aa4145f-53a5-4f8f-b30a-125efe697dd2"
    }

    def "put null check"() {
        setup:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).build())

        when:
        fileDataStore.put(null)

        then:
        thrown(NullPointerException)
    }

    def "get invalid inputs"() {
        setup:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).build())

        when:
        fileDataStore.get(id)

        then:
        thrown(expectedException)

        where:
        id                                          || expectedException
        "7xx4145x-53a5-4f8f-b30a-125efe697dd2"      || IllegalArgumentException
        "7aa4145f"                                  || IllegalArgumentException
        "7aa4145f-53a5-4f8f-b30a-125efe697dd2-b30a" || IllegalArgumentException
        "  "                                        || IllegalArgumentException
        ""                                          || IllegalArgumentException
        null                                        || NullPointerException
    }

    def "getPath invalid inputs"() {
        setup:
        FileDataStore fileDataStore = new FileDataStore(
                ImmutableFileDataStoreConfiguration.builder().baseDirectory(Paths.get("/")).build())

        when:
        fileDataStore.getPath(id)

        then:
        thrown(expectedException)

        where:
        id                                          || expectedException
        "7aa4145f-53a5-4f8f-b30a-125efe697dd2.zip"  || IllegalArgumentException
        "7xx4145x-53a5-4f8f-b30a-125efe697dd2"      || IllegalArgumentException
        "7aa4145f"                                  || IllegalArgumentException
        "7aa4145f-53a5-4f8f-b30a-125efe697dd2-b30a" || IllegalArgumentException
        "  "                                        || IllegalArgumentException
        ""                                          || IllegalArgumentException
        null                                        || NullPointerException
    }

    def "ImmutableFileDataStoreConfiguration no base directory specified"() {
        when:
        ImmutableFileDataStoreConfiguration.builder().build()

        then:
        IllegalStateException ex = thrown()
        ex.message == "Cannot build Configuration, some of required attributes are not set [baseDirectory]"
    }

    def "ImmutableFileDataStoreConfiguration invalid levels"() {
        when:
        ImmutableFileDataStoreConfiguration.builder()
                .baseDirectory(Paths.get("/"))
                .levels(levels)
                .build()

        then:
        IllegalStateException ex = thrown()
        ex.message == "Number of directory levels must be between [0-16]"

        where:
        levels << [Integer.MIN_VALUE, -1, 0, 17, Integer.MAX_VALUE]
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
        return path.getParent().toString().replace(baseDir.toString() + "/", "").split("/").size()
    }

    private Path getExpectedFile(fileDataStore, String uuid) {
        Path dirPath = fileDataStore.getPath(uuid)
        return Paths.get(dirPath.toString(), uuid)
    }

    private String getFileContents(Path file) {
        return Files.asCharSource(file.toFile(), Charsets.UTF_8).read()
    }

    private Path createTempDir() {
        final Path tempDir = java.nio.file.Files.createTempDirectory("FileDataStoreTest")
        tempDir.toFile().deleteOnExit()
        return tempDir
    }
}
