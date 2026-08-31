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

package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.config.WorldFileManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Resolves per-world WorldGuard directories through the Bukkit API, so that a
 * world's data sits inside the directory the server stores that world in.
 *
 * <p>{@link World#getWorldFolder()} is asked rather than joining the world
 * container with the world name: since MC 26.1 the nether and the end of a
 * level are stored under {@code <level>/dimensions/<namespace>/<dimension>}
 * and no longer have a top-level {@code <container>/<level>_nether} directory
 * of their own. A directory built from the world name would be outside every
 * world, and the server clears such leftovers on startup.</p>
 */
public class BukkitWorldFileManager extends WorldFileManager {

    @Override
    protected File worldDirectory(String id) {
        World world = Bukkit.getWorld(id);
        if (world != null) {
            return world.getWorldFolder();
        }

        // The world is not loaded, so the server cannot tell us where it is.
        // Fall back to the pre-26.1 layout, which is also what a freshly
        // created world of that name would get.
        return new File(Bukkit.getWorldContainer(), id);
    }

    @Override
    protected Collection<String> worldNames() {
        List<World> worlds = Bukkit.getWorlds();
        List<String> names = new ArrayList<>(worlds.size());
        for (World world : worlds) {
            names.add(world.getName());
        }
        return names;
    }

}
