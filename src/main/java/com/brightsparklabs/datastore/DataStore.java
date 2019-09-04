/*
 * Created by brightSPARK Labs
 * www.brightsparklabs.com
 */

package com.brightsparklabs.datastore;

import com.google.common.io.ByteSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Stores and retrieves data from a backing store.
 *
 * @author brightSPARK Labs
 */
public interface DataStore
{
    // -------------------------------------------------------------------------
    // CONSTANTS
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // CLASS VARIABLES
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    /**
     * Returns the data in the file corresponding to the specified id.
     *
     * @param id
     *         Unique identifier of the file to retrieve.
     *
     * @return The data in the file corresponding to the specified id.
     *
     * @throws FileNotFoundException
     *         If not data can be found for the specified id.
     */
    ByteSource get(String id) throws FileNotFoundException;

    /**
     * Stores the data from the specified source into the store.
     *
     * @param source
     *         Source to read data from.
     *
     * @return Unique identifier for retrieving the data via {@link #get(String)}.
     */
    String put(ByteSource source);

    /**
     * Stores the data from the specified file into the store.
     *
     * @param file
     *         File to store.
     *
     * @return Unique identifier for retrieving the data via {@link #get(String)}.
     */
    String put(Path file) throws IOException;

    /**
     * Stores the data from the specified file into the store.
     *
     * @param file
     *         File to store.
     *
     * @return Unique identifier for retrieving the data via {@link #get(String)}.
     */
    String put(File file) throws IOException;
}
