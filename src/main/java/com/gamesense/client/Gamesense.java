package com.gamesense.client;

import com.gamesense.api.config.LoadConfig;
import com.gamesense.api.util.font.CFontRenderer;
import com.gamesense.api.util.misc.VersionChecker;
import com.gamesense.api.util.render.CapeUtil;
import com.gamesense.client.clickgui.GameSenseGUI;
import com.gamesense.client.command.CommandManager;
import com.gamesense.client.manager.ManagerLoader;
import com.gamesense.client.module.ModuleManager;
import me.zero.alpine.bus.EventBus;
import me.zero.alpine.bus.EventManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.client.MinecraftClient;

import java.awt.*;

public class GameSense implements ModInitializer {

	public static final String MODNAME = "GameSense";
	public static final String MODID = "gamesense";
	public static final String MODVER = "d2.3.1";
	/**
	 * Official release starts with a "v", dev versions start with a "d" to bypass version check
	 */

	public static final Logger LOGGER = LogManager.getLogger(MODNAME);
	public static final EventBus EVENT_BUS = new EventManager();

	public static GameSense INSTANCE;

	public GameSense() {
		INSTANCE = this;
	}

	@Override
	public void onInitialize() {
		// Set window title (will be done via mixin since direct access is not possible)
		LOGGER.info("Starting up " + MODNAME + " " + MODVER + "!");
		startClient();
		LOGGER.info("Finished initialization for " + MODNAME + " " + MODVER + "!");
	}

	public CFontRenderer cFontRenderer;
	public GameSenseGUI gameSenseGUI;

	private void startClient() {
		VersionChecker.init();
		LOGGER.info("Version checked!");

		cFontRenderer = new CFontRenderer(new Font("Verdana", Font.PLAIN, 18), true, true);
		LOGGER.info("Custom font initialized!");

		ModuleManager.init();
		LOGGER.info("Modules initialized!");

		CommandManager.init();
		LOGGER.info("Commands initialized!");

		ManagerLoader.init();
		LOGGER.info("Managers initialized!");

		gameSenseGUI = new GameSenseGUI();
		LOGGER.info("GUI initialized!");

		CapeUtil.init();
		LOGGER.info("Capes initialized!");

		LoadConfig.init();
		LOGGER.info("Config initialized!");
	}
}