/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */
package com.brightsparklabs.datastore;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.immutables.value.Value;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import static com.google.common.base.Preconditions.*;

/**
 * A {@link DataStore} which stores data in files.
 *
 * @author brightSPARK Labs
 */
public class FileDataStore extends AbstractDataStore
{
    // -------------------------------------------------------------------------
    // CONSTANTS
    // -------------------------------------------------------------------------

    /** The number of characters used to represent a directory level */
    private static final int DIR_LEVEL_CHARS = 2;

    /** The regex used to verify the format of a UUID */
    private static final String UUID_REGEX
            = "(?i)^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$";

    // -------------------------------------------------------------------------
    // INSTANCE VARIABLES
    // -------------------------------------------------------------------------

    /** The supplied configuration */
    private final Configuration configuration;

    // -------------------------------------------------------------------------
    // CONSTRUCTION
    // -------------------------------------------------------------------------

    /**
     * Creates a new instance from the supplied configuration.
     *
     * @param configuration
     *         Configuration for the store.
     */
    public FileDataStore(final Configuration configuration)
    {
        this.configuration = configuration;
    }

    // -------------------------------------------------------------------------
    // IMPLEMENTATION: DataStore
    // -------------------------------------------------------------------------

    /**
     * Returns the data in the file corresponding to the specified id.
     *
     * @param id
     *         Unique identifier of the file to retrieve.
     *
     * @return The data in the file corresponding to the specified id.
     */
    @Override
    public ByteSource get(final String id) throws FileNotFoundException
    {
        Objects.requireNonNull(id);
        checkArgument(!id.isEmpty(), "Id must not be empty");

        // Get file path from UUID
        final Path filePath = getPath(id);

        if (!java.nio.file.Files.exists(filePath))
        {
            throw new FileNotFoundException(String.format("The file at [%s] could not be found",
                    filePath.toString()));
        }

        return Files.asByteSource(filePath.toFile());
    }

    /**
     * Stores the data from the specified source into the store.
     *
     * @param source
     *         Source to read data from.
     *
     * @return Unique identifier for retrieving the data via {@link #get(String)}.
     */
    public String put(final ByteSource source) throws IOException
    {
        Objects.requireNonNull(source);

        // Generate UUID and associated path
        final String uuid = UUID.randomUUID().toString();
        final Path destinationFile = getPath(uuid);

        // Create all parent directories
        java.nio.file.Files.createDirectories(destinationFile.getParent());

        // Write data to destination file
        final ByteSink sink = Files.asByteSink(destinationFile.toFile());
        source.copyTo(sink);
        return uuid;
    }

    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    /**
     * Returns the file corresponding to the specified id.
     *
     * @param id
     *         Unique identifier of the file to retrieve.
     *
     * @return The file corresponding to the specified id.
     */
    public Path getPath(String id)
    {
        Objects.requireNonNull(id);
        checkArgument(!id.isEmpty(), "Id must not be empty");

        if (!id.matches(UUID_REGEX))
        {
            throw new IllegalArgumentException(String.format("Provided id [%s] is not a valid UUID",
                    id));
        }

        // Get configuration
        final int levels = this.configuration.getLevels();
        final Path baseDirectory = this.configuration.getBaseDirectory();

        // Normalise UUID
        String normalisedId = id.replaceAll("-", "");

        // Compute directory path from normalised UUID
        final String[] dirs = new String[levels + 1];
        for (int i = 0; i < levels; i++)
        {
            dirs[i] = normalisedId.substring(0, DIR_LEVEL_CHARS);
            normalisedId = normalisedId.substring(DIR_LEVEL_CHARS);
        }

        dirs[levels] = id;

        return Paths.get(baseDirectory.toString(), dirs);
    }

    // -------------------------------------------------------------------------
    // INNER CLASSES
    // -------------------------------------------------------------------------

    @Value.Immutable
    @Value.Style(typeImmutable = "ImmutableFileDataStore*")
    public static abstract class Configuration
    {
        // ---------------------------------------------------------------------
        // CONSTANTS
        // ---------------------------------------------------------------------

        /** Default number of directory levels to use for nesting files */
        private static final int DEFAULT_LEVELS = 3;

        /** The maximum number of directory levels possible */
        private static final int MAX_LEVELS = 16;

        // ---------------------------------------------------------------------
        // PUBLIC METHODS
        // ---------------------------------------------------------------------

        /**
         * Returns the number of directory levels to use for nesting files. Defaults to {@link
         * #DEFAULT_LEVELS}.
         *
         * @return The number of directory levels to use for nesting files.
         */
        @Value.Default
        int getLevels()
        {
            return DEFAULT_LEVELS;
        }

        /**
         * Returns the base directory used for computing the final path.
         *
         * @return the base directory used for computing the final path.
         */
        abstract Path getBaseDirectory();

        /**
         * Ensures that the configured number of levels is less than or equal to {@link
         * #MAX_LEVELS}.
         */
        @Value.Check
        protected void checkDirLevel()
        {
            Preconditions.checkState(getLevels() <= MAX_LEVELS,
                    "Number of directory levels must be less than or equal to " + MAX_LEVELS);
        }
    }
}
