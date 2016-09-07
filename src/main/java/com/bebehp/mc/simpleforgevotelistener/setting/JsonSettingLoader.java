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

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.relauncher.FMLInjectionData;

public class JsonSettingLoader {
	public static Setting voteJson;

	public static void load(final File configDir) {
		if (!configDir.exists())
			configDir.mkdirs();
		final File jsonFile = new File(configDir, ConfigurationHandler.jsonFileName);
		if (!jsonFile.exists())
			if (!copyJson(jsonFile))
				return;
		voteJson = loadJson(jsonFile);
	}

	public static Setting loadJson(final File jsonFile) {
		final File mcDir = (File) FMLInjectionData.data()[6];
		InputStreamReader isr;
		try {
			isr = new InputStreamReader(new FileInputStream(jsonFile));
			final JsonReader jsr = new JsonReader(isr);
			final Setting voteJson = new Gson().fromJson(jsr, Setting.class);
			return voteJson;
		} catch (final FileNotFoundException e) {
			Reference.logger.error(e);
			return null;
		}

	}

	public static boolean copyJson(final File destFile) {
		final ModContainer c = Loader.instance().getIndexedModList().get(Reference.MODID);
		final File runFile = c.getSource();
		if (runFile.isFile()) {
			JarFile jar = null;
			try {
				jar = new JarFile(runFile);
				final ZipEntry ze = jar.getEntry("VoteEvent.json");
				if (ze != null) {
					final InputStream is = jar.getInputStream(ze);
					FileUtils.copyInputStreamToFile(is, destFile);
					return true;
				} else {
					Reference.logger.warn("Sample json file not found.");
					return false;
				}
			} catch (final IOException e) {
				Reference.logger.error(e);
				return false;
			} finally {
				IOUtils.closeQuietly(jar);
			}
		} else {
			// dev
			try {
				final File file = new File(runFile, "VoteEvent.json");
				FileUtils.copyFile(file, destFile);
				return true;
			} catch (final IOException e) {
				Reference.logger.error(e);
				return false;
			}
		}
	}

}