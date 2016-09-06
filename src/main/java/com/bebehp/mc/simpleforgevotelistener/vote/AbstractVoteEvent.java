package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.bebehp.mc.simpleforgevotelistener.json.JsonStringArgs;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public abstract class AbstractVoteEvent implements IVote {

	public static final File csvFile = new File(Minecraft.getMinecraft().mcDataDir, "offlinevote.csv");
	public static LinkedHashMap<String, Integer> map;

	public static final Pattern pattern = Pattern.compile("%[cbhdoxefgatn]", Pattern.CASE_INSENSITIVE);

	public AbstractVoteEvent() {
		if (ConfigurationHandler.offlineVoteEnable)
			loadCSV();
	}

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

	public void loadCSV() {
		BufferedReader br = null;
		try {
			if (!csvFile.exists()) {
				csvFile.createNewFile();
				return;
			}

			final FileReader fr = new FileReader(csvFile);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				final String[] array = line.split(",");
				map.put(array[0], Integer.parseInt(array[1]));
			}
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(br);
		}
	}

}
