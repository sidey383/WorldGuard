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

package com.sk89q.worldguard.protection.managers.storage.file;

import com.sk89q.worldguard.config.WorldFileManager;
import com.sk89q.worldguard.protection.managers.storage.RegionDatabase;
import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
import com.sk89q.worldguard.protection.managers.storage.StorageException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Stores region data in a {world_dir}/worldguard/{filename} pattern on disk
 * using {@link YamlRegionFile}.
 *
 * <p>The location of a world's directory is resolved by a
 * {@link WorldFileManager} rather than being a fixed root directory, so that
 * region data lives inside the world it belongs to.</p>
 */
public class DirectoryYamlDriver implements RegionDriver {

    private final WorldFileManager fileManager;
    private final String filename;

    /**
     * Create a new instance.
     *
     * @param fileManager resolves the data directory of a world
     * @param filename the filename (i.e. "regions.yml")
     */
    public DirectoryYamlDriver(WorldFileManager fileManager, String filename) {
        checkNotNull(fileManager);
        checkNotNull(filename);
        this.fileManager = fileManager;
        this.filename = filename;
    }

    /**
     * Get the path for the given ID.
     *
     * @param id the ID
     * @return the file path
     */
    private File getPath(String id) {
        checkNotNull(id);

        File f = new File(fileManager.getDirectory(id), filename);
        try {
            f.getCanonicalPath();
            return f;
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid file path for the world's regions file");
        }
    }

    @Override
    public RegionDatabase get(String id) {
        checkNotNull(id);

        File file = getPath(id);

        return new YamlRegionFile(id, file);
    }

    @Override
    public List<RegionDatabase> getAll() throws StorageException {
        List<RegionDatabase> stores = new ArrayList<>();

        for (Map.Entry<String, File> entry : fileManager.getAllDirectories().entrySet()) {
            File dir = entry.getValue();
            File file = new File(dir, filename);
            if (dir.isDirectory() && file.isFile()) {
                stores.add(new YamlRegionFile(entry.getKey(), file));
            }
        }

        return stores;
    }

}
