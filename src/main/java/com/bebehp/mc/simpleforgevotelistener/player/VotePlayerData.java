package com.bebehp.mc.simpleforgevotelistener.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.IOUtils;

import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class VotePlayerData {

	private final File loadFile;

	public VotePlayerData(final File file) {
		this.loadFile = file;
	}

	public VotePlayerData(final File dataDir, final String fileName) {
		this(new File(dataDir, fileName));
	}

	public PlayerData load() {
		JsonReader jr = null;
		try {
			final InputStreamReader isr = new InputStreamReader(new FileInputStream(this.loadFile));
			jr= new JsonReader(isr);
			final PlayerData data = new Gson().fromJson(jr, PlayerData.class);
			return data;
		} catch (final FileNotFoundException e) {
			return new PlayerData(null, 0, 0);
		} finally {
			IOUtils.closeQuietly(jr);
		}
	}

	public void save(final PlayerData data) {
		if (this.loadFile.exists())
			createFile();

		JsonWriter jw = null;
		try {
			jw = new JsonWriter(new BufferedWriter(new FileWriter(this.loadFile)));
			new Gson().toJson(data, PlayerData.class, jw);
		} catch (final IOException e) {
			Reference.logger.info(e);
		} finally {
			IOUtils.closeQuietly(jw);
		}
	}

	public boolean exists() {
		return this.loadFile.exists();
	}

	public void build(final String uuid) {
		save(new PlayerData(uuid, 0, 0));
	}

	private void createFile() {
		try {
			this.loadFile.createNewFile();
		} catch (final IOException e) {
			Reference.logger.error(e);
		}
	}
}
