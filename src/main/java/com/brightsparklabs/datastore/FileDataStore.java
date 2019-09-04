/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */
package com.brightsparklabs.datastore;

import com.google.common.io.ByteSource;
import org.immutables.value.Value;

import java.nio.file.Path;

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

    // -------------------------------------------------------------------------
    // CLASS VARIABLES
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // INSTANCE VARIABLES
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // CONSTRUCTION
    // -------------------------------------------------------------------------

    /**
     * Default constructor.
     */
    public FileDataStore()
    {
        this(ImmutableFileDataStoreConfiguration.builder().build());
    }

    /**
     * Creates a new instance from the supplied configuration.
     *
     * @param configuration
     *         Configuration for the store.
     */
    public FileDataStore(final Configuration configuration)
    {
        // TODO
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
    public ByteSource get(final String id)
    {
        // TODO
        return null;
    }

    /**
     * Stores the data from the specified source into the store.
     *
     * @param source
     *         Source to read data from.
     *
     * @return Unique identifier for retrieving the data via {@link #get(String)}.
     */
    public String put(final ByteSource source)
    {
        // TODO
        return null;
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
        // TODO
        return null;
    }

    // -------------------------------------------------------------------------
    // PRIVATE METHODS
    // -------------------------------------------------------------------------

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
    }
}
