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

	public static final Pattern EXCLUSION_FORMAT = Pattern.compile("%[cbhdoxefgatn]", Pattern.CASE_INSENSITIVE);

	private final String rawText;
	private final String[] args;
	private final EntityPlayerMP player;

	public SettingFormat(final String name, final String rawText, final String[] argArray) {
		this.rawText = rawText;
		this.args = argArray;
		this.player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
	}

	public SettingFormat(final String name, final String rawText, final String rawArgs) {
		this(name, rawText, StringUtils.split(rawArgs, ","));
	}

	public String parseArgs() {
		if (!StringUtils.containsIgnoreCase(this.rawText, "%s"))
			return this.rawText;

		final List<String> list = new LinkedList<String>();
		for (final String line : this.args) {
			final String enumName = StringUtils.upperCase(StringUtils.deleteWhitespace(line));
			if (EnumUtils.isValidEnum(EnumSettingArgs.class, enumName)) {
				list.add(getArgs(EnumSettingArgs.valueOf(enumName)));
			} else if (StringUtils.startsWithIgnoreCase(line, "random")) {
				final String[] values = StringUtils.split(StringUtils.removeStartIgnoreCase(line, "random"), "-");
				if (values.length >= 2)
					getStringRandomNumber(values[0], values[1], "1");
				else if (values.length == 1 && StringUtils.isNumeric(values[0]))
					list.add(values[0]);
				else
					list.add("1");
			} else {
				if (line != null)
					list.add(line);
				else
					list.add("");
			}
		}
		final String text = EXCLUSION_FORMAT.matcher(this.rawText).replaceAll("");
		return String.format(text, list);
	}

	private String getArgs(final EnumSettingArgs a) {
		return a.parseString(this.player);
	}

	public static String getStringRandomNumber(final String startInclusive, final String endExclusive) {
		return getStringRandomNumber(startInclusive, endExclusive, "0");
	}

	public static String getStringRandomNumber(final String startInclusive, final String endExclusive, final String defaultString) {
		if (startInclusive == null || endExclusive == null)
			return defaultString;

		try {
			final int intStartInclusive = Integer.parseInt(startInclusive);
			final int intEndExclusive = Integer.parseInt(endExclusive);

			if (intStartInclusive == intEndExclusive)
				return startInclusive;

			final int randomNumber = RandomUtils.nextInt(intStartInclusive, intEndExclusive);
			return String.valueOf(randomNumber);
		} catch (final NumberFormatException nfe) {
			return defaultString;
		} catch (final IllegalArgumentException iae) {
			return defaultString;
		}
	}

}
