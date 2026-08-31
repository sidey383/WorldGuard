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

import java.io.File;

/**
 * Resolves per-world WorldGuard directories against the server's world
 * container, so that a world's data sits inside the world directory.
 */
public class BukkitWorldFileManager extends WorldFileManager {

    @Override
    public File baseWorldDirectory() {
        return Bukkit.getWorldContainer();
    }

    @Override
    public boolean isWorldDirectory(File world) {
        return new File(world, "level.dat").isFile();
    }

}
