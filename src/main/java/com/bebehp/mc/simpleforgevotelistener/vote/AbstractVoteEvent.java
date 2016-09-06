package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.json.JsonStringArgs;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

// TODO
public abstract class AbstractVoteEvent implements IVote {

	public static final Pattern pattern = Pattern.compile("%[cbhdoxefgatn]", Pattern.CASE_INSENSITIVE);

	@Override
	public String parse(final String name, final String raw, final String args) {
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
				list.add(getArgs(JsonStringArgs.HOLDITEM, name, player));
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "posx")) {
				list.add(getArgs(JsonStringArgs.POSX, name, player));
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "posy")) {
				list.add(getArgs(JsonStringArgs.POSY, name, player));
				continue;
			} else if (StringUtils.equalsIgnoreCase(line, "posz")) {
				list.add(getArgs(JsonStringArgs.POSZ, name, player));
				continue;
			} else if (!StringUtils.isBlank(line)) {
				list.add(line);
			}
		}
		final String text = pattern.matcher(raw).replaceAll("");
		return String.format(text, list);
	}

	public String getArgs(final JsonStringArgs a, final String name, final EntityPlayerMP player) {
		return a.parseString(name, player);
	}
}
