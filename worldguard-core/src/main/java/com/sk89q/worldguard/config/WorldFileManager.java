/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.config;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Resolves the directory that holds WorldGuard's per-world data.
 *
 * <p>Upstream WorldGuard keeps that data next to the plugin, in
 * {@code plugins/WorldGuard/worlds/<world>}. This fork puts it inside the
 * world's own directory on disk, so that regions and per-world configuration
 * travel with the world when the world directory is mounted or moved
 * independently of the plugin directory.</p>
 */
public abstract class WorldFileManager {

    /**
     * Name of the directory created inside every world directory.
     */
    public static final String WG_DIR_NAME = "worldguard";

    /**
     * Get the WorldGuard data directory of a single world.
     *
     * <p>The directory is not created here; callers create it on demand.</p>
     *
     * @param id the world name
     * @return the directory holding that world's WorldGuard data
     */
    public File getDirectory(String id) {
        checkNotNull(id);
        return new File(worldDirectory(id), WG_DIR_NAME);
    }

    /**
     * Get the directory that the given world stores its own data in.
     *
     * @param id the world name
     * @return that world's directory on disk
     */
    protected abstract File worldDirectory(String id);

    /**
     * Get the names of the worlds whose directories can be resolved.
     *
     * @return world names
     */
    protected abstract Collection<String> worldNames();

    /**
     * Get the WorldGuard data directory of every known world, keyed by world
     * name.
     *
     * <p>Unlike {@link #getDirectory(String)} this only reports worlds the
     * platform actually knows about. The directories are not guaranteed to
     * exist.</p>
     *
     * @return world name to WorldGuard data directory
     */
    public Map<String, File> getAllDirectories() {
        Map<String, File> results = new HashMap<>();
        for (String name : worldNames()) {
            results.put(name, getDirectory(name));
        }
        return results;
    }

}
