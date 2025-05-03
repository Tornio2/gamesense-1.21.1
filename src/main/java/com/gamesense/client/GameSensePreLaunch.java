package com.gamesense.client;

import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameSensePreLaunch implements PreLaunchEntrypoint {
    private static final Logger LOGGER = LogManager.getLogger(GameSense.MODNAME);

    @Override
    public void onPreLaunch() {
        LOGGER.info("GameSense mixins initialized");
    }
}