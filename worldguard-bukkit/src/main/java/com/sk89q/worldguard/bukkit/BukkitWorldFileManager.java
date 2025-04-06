package com.sk89q.worldguard.bukkit;

import com.sk89q.worldguard.config.WorldFileManager;
import org.bukkit.Bukkit;

import java.io.File;

public class BukkitWorldFileManager extends WorldFileManager {
    @Override
    public File baseWorldDirectory() {
        return Bukkit.getWorldContainer();
    }

    @Override
    public boolean isWorldDirectory(File world) {
        File level = new File(world, "level.dat");
        return level.isFile();
    }
}
