package com.sk89q.worldguard.config;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class WorldFileManager {

    private static final String WG_DIR_NAME = "worldguard";

    public File getDirectory(String id) {
        checkNotNull(id);
        return new File(baseWorldDirectory(), id + File.separator + WG_DIR_NAME);
    }

    public abstract File baseWorldDirectory();

    public abstract boolean isWorldDirectory(File world);

    public Map<String, File> getAllDirectories() {
        File base = baseWorldDirectory();
        File[] files = base.listFiles();
        if (files == null)
            return Collections.emptyMap();

        Map<String, File> results = new HashMap<>();
        for (File world : files) {
            if (isWorldDirectory(world)) {
                results.put(world.getName(), new File(world, WG_DIR_NAME));
            }
        }
        return results;
    }

}
