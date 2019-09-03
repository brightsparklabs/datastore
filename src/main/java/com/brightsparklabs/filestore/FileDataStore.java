/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */
package com.brightsparklabs.filestore;

import com.google.common.io.ByteSource;

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
    @Override
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
}
