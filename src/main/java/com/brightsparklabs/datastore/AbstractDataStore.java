/*
 * Maintained by brightSPARK Labs.
 * www.brightsparklabs.com
 *
 * Refer to LICENSE at repository root for license details.
 */

package com.brightsparklabs.datastore;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Convenience class for implementing {@link DataStore}.
 *
 * @author brightSPARK Labs
 */
public abstract class AbstractDataStore implements DataStore {
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

    @Override
    public String put(final Path file) throws IOException {
        return put(Files.asByteSource(file.toFile()));
    }

    @Override
    public String put(final File file) throws IOException {
        return put(Files.asByteSource(file));
    }

    // -------------------------------------------------------------------------
    // PUBLIC METHODS
    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------
    // PRIVATE METHODS
    // -------------------------------------------------------------------------
}
