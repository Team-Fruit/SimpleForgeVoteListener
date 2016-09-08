package com.bebehp.mc.simpleforgevotelistener;
import java.io.File;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class ConfigurationHandler {
	public static final ConfigurationHandler INSTANCE = new ConfigurationHandler();
	public static final String VERSION = "1";
	public static Configuration configuration;

	public static final String JSON_DAFAULT_FILENAME = "EventConfig.json";
	public static String jsonFileName = JSON_DAFAULT_FILENAME;
	public static Property propJsonFileName = null;

	public static final boolean OFFLINEVOTE_ENABLE_DEFAULT = true;
	public static boolean offlineVoteEnable = OFFLINEVOTE_ENABLE_DEFAULT;
	public static Property propOfflineVoteEnable = null;

	public static void init(final File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile, VERSION);
			loadConfiguration();
		}
	}

	public static void loadConfiguration() {
		propJsonFileName = configuration.get("SimpleForgeVoteListener", "Filename",
				JSON_DAFAULT_FILENAME, "Voting event configration json filename.");
		jsonFileName = propJsonFileName.getString();

		propOfflineVoteEnable = configuration.get("SimpleForgeVoteListener", "isOfflineVoteEnabled",
				OFFLINEVOTE_ENABLE_DEFAULT, "When the player is offline, the vote is recorded, it will be reward later.");
		offlineVoteEnable = propJsonFileName.getBoolean();

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	private ConfigurationHandler() {
	}

	@SubscribeEvent
	public void onConfigurationChangedEvent(final ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.modID.equalsIgnoreCase(Reference.MODID)) {
			loadConfiguration();
		}
	}
}