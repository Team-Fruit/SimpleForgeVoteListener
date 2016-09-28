package com.bebehp.mc.simpleforgevotelistener;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.bebehp.mc.simpleforgevotelistener.setting.JsonSetting;
import com.bebehp.mc.simpleforgevotelistener.vote.VoteEventListener;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SimpleForgeVoteListener {

	@Instance(Reference.MODID)
	public static SimpleForgeVoteListener instance;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		Reference.logger.fatal(getModDataDir());
		if (getModDataDir().exists())
			getModDataDir().mkdirs();
		JsonSetting.load(getModDataDir());
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(VoteEventListener.INSTANCE);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {
		event.registerServerCommand(SFVLCommand.INSTANCE);
	}

	@NetworkCheckHandler
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		return true;
	}

	public static File getModDataDir() {
		final File file = FMLServerHandler.instance().getSavesDirectory();
		try {
			return new File(file.getCanonicalFile(), "vote");
		} catch (final IOException e) {
			Reference.logger.debug("Could not canonize path!", e);
		}
		return new File(file, "vote");
	}

}
