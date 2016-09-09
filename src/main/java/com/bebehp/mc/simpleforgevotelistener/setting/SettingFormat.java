package com.bebehp.mc.simpleforgevotelistener.setting;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class SettingFormat {

	public static final Pattern pattern = Pattern.compile("%[cbhdoxefgatn]", Pattern.CASE_INSENSITIVE);

	private final String name;
	private final String rawText;
	private final String[] args;
	private final EntityPlayerMP player;

	public SettingFormat(final String name, final String rawText, final String[] argArray, final EntityPlayerMP player) {
		this.name = name;
		this.rawText = rawText;
		this.args = argArray;
		this.player = player;
	}

	public SettingFormat (final String name, final String rawText, final String[] argArray) {
		this(name, rawText, argArray, MinecraftServer.getServer().getConfigurationManager().func_152612_a(name));
	}

	public SettingFormat(final String name, final String rawText, final String rawArgs) {
		this(name, rawText, rawArgs.split(","));
	}

	public String parseArgs() {
		if (!this.rawText.contains("%s"))
			return this.rawText;

		final List<String> list = new LinkedList<String>();
		for (final String line : this.args) {
			final String enumName = StringUtils.deleteWhitespace(line).toUpperCase();
			if (EnumUtils.isValidEnum(SettingArgs.class, enumName)) {
				list.add(getArgs(SettingArgs.valueOf(enumName)));
			} else if (line.contains("random")) {
				final String[] value = StringUtils.remove(line, "random").split("-");
				try {
					list.add(String.valueOf(RandomUtils.nextInt(Integer.parseInt(value[0]), Integer.parseInt(value[0]))));
				} catch (final NumberFormatException e) {
					list.add("1");
				}
			} else {
				list.add(line);
			}
		}
		final String text = pattern.matcher(this.rawText).replaceAll("");
		return String.format(text, list);
	}

	private String getArgs(final SettingArgs a) {
		return a.parseString(this.name, this.player);
	}
}
