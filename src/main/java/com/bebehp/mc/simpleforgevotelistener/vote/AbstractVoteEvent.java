package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.SimpleForgeVoteListener;
import com.bebehp.mc.simpleforgevotelistener.setting.SettingAgrs;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public abstract class AbstractVoteEvent implements IVote {

	public static final File dataDir = new File(SimpleForgeVoteListener.getModDataDir(), "player");
	public static final Pattern pattern = Pattern.compile("%[cbhdoxefgatn]", Pattern.CASE_INSENSITIVE);

	protected LinkedHashMap<String, Data> map;

	public AbstractVoteEvent() {
	}

	public void loadJson(final String name) {
		final File userFile = new File(dataDir, getUUID(name).toString() + ".json");

	}

	@Override
	public String parseArgs(final String name, final String raw, final String args) {
		if (!raw.contains("%s"))
			return raw;

		final String[] astring = args.split(",");
		final List<String> list = new LinkedList<String>();
		final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		for (final String line : astring) {
			if (StringUtils.equalsIgnoreCase(line, "username")) {
				list.add(name);
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "holditem")) {
				list.add(getArgs(SettingAgrs.HOLDITEM, name, player));
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "posx")) {
				list.add(getArgs(SettingAgrs.POSX, name, player));
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "posy")) {
				list.add(getArgs(SettingAgrs.POSY, name, player));
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "posz")) {
				list.add(getArgs(SettingAgrs.POSZ, name, player));
				continue;
			} else if (!StringUtils.isBlank(line)) {
				list.add(line);
			}
		}
		final String text = pattern.matcher(raw).replaceAll("");
		return String.format(text, list);
	}

	public String getArgs(final SettingAgrs a, final String name, final EntityPlayerMP player) {
		return a.parseString(name, player);
	}

	public UUID getUUID(final String name) {
		final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		return player.getGameProfile().getId();
	}
}
