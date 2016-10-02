package com.bebehp.mc.simpleforgevotelistener.setting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.bebehp.mc.simpleforgevotelistener.handler.ConfigurationHandler;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;

public class JsonSetting {
	public static final String DEFAULT_FILENAME = "EventConfig.json";

	public static Setting voteJson;

	public static void load(final File configDir) {
		final File jsonFile = new File(configDir, ConfigurationHandler.jsonFileName);
		if (!jsonFile.exists())
			if (copyJson(jsonFile))
				voteJson = loadJson(jsonFile);
	}

	public static Setting loadJson(final File jsonFile) {
		JsonReader jsr = null;
		try {
			jsr = new JsonReader(new InputStreamReader(new FileInputStream(jsonFile)));
			final Setting voteJson = new Gson().fromJson(jsr, Setting.class);
			return voteJson;
		} catch (final JsonParseException e) {
			Reference.logger.error("The {} file in {} cannot be parsed as valid JSON. It will be ignored", ConfigurationHandler.jsonFileName, jsonFile);
		} catch (final FileNotFoundException e) {
			Reference.logger.error("The {} file not found. Setting is ignored",  ConfigurationHandler.jsonFileName);
		} finally {
			IOUtils.closeQuietly(jsr);
		}
		return new Setting();
	}

	public static boolean copyJson(final File destFile) {
		final ModContainer c = Loader.instance().getIndexedModList().get(Reference.MODID);
		final File runFile = c.getSource();
		if (runFile.isFile()) {
			JarFile jar = null;
			try {
				jar = new JarFile(runFile);
				final ZipEntry ze = jar.getEntry(DEFAULT_FILENAME);
				if (ze != null) {
					final InputStream is = jar.getInputStream(ze);
					FileUtils.copyInputStreamToFile(is, destFile);
					return true;
				} else {
					Reference.logger.warn("Sample json file not found.");
				}
			} catch (final IOException e) {
				Reference.logger.error(e);
			} finally {
				IOUtils.closeQuietly(jar);
			}
		} else {
			// dev
			try {
				final File file = new File(runFile, DEFAULT_FILENAME);
				FileUtils.copyFile(file, destFile);
				return true;
			} catch (final IOException e) {
				Reference.logger.error(e);
			}
		}
		return false;
	}

}
