package com.bebehp.mc.simpleforgevotelistener;

import java.io.File;
import java.util.Map;

import com.bebehp.mc.simpleforgevotelistener.setting.JsonSettingLoader;
import com.bebehp.mc.simpleforgevotelistener.vote.VoteListener;
import com.bebehp.mc.simpleforgevotelistener.vote.VoteOffline;
import com.bebehp.mc.simpleforgevotelistener.vote.VoteOnline;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkCheckHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class SimpleForgeVoteListener {

	public final VoteOnline online = new VoteOnline();
	public final VoteOffline offline = new VoteOffline();
	public final VoteListener listener = new VoteListener(this.online, this.offline);

	@Instance(Reference.MODID)
	public static SimpleForgeVoteListener instance;

	@EventHandler
	public void preInit(final FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		JsonSettingLoader.load(getModDataDir());
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this.listener);
	}

	@EventHandler
	public void serverLoad(final FMLServerStartingEvent event) {

	}


	@NetworkCheckHandler
	public boolean checkModList(final Map<String, String> versions, final Side side) {
		return true;
	}

	public static File getModDataDir() {
		final File mcDataDir = Minecraft.getMinecraft().mcDataDir;
		return new File(mcDataDir, "vote");
	}
}
