package com.bebehp.mc.simpleforgevotelistener;

import java.io.File;
import java.util.Map;

import com.bebehp.mc.simpleforgevotelistener.handler.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.handler.JsonHandler;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SimpleForgeVoteListener {

	@Instance(Reference.MODID)
	public static SimpleForgeVoteListener instance;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		final File configDir = new File(event.getModConfigurationDirectory(), Reference.MODID);
		configDir.mkdirs();
		ConfigurationHandler.init(new File(configDir, Reference.MODID + ".cfg"));
		JsonHandler.init(configDir);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {

	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {

	}


	@NetworkCheckHandler
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		return true;
	}
}
