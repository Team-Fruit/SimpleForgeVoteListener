package com.bebehp.mc.simpleforgevotelistener.vote;

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

public class VoteDataIO {

	protected File loadFile;

	public VoteDataIO(final File file) {
		this.loadFile = file;
	}

	public VoteDataIO(final File dataDir, final String fileName) {
		this(new File(dataDir, fileName));
	}

	public Data load() {
		JsonReader jr = null;
		try {
			final InputStreamReader isr = new InputStreamReader(new FileInputStream(this.loadFile));
			jr= new JsonReader(isr);
			final Data data = new Gson().fromJson(jr, Data.class);
			return data;
		} catch (final FileNotFoundException e) {
			return new Data(null, null, null, null);
		} finally {
			IOUtils.closeQuietly(jr);
		}
	}

	public void save(final Data data) {
		JsonWriter jw = null;
		try {
			final BufferedWriter bw = new BufferedWriter(new FileWriter(this.loadFile));
			jw = new JsonWriter(bw);
			new Gson().toJson(data, Data.class, jw);
		} catch (final IOException e) {
			Reference.logger.info(e);
		} finally {
			IOUtils.closeQuietly(jw);
		}
	}
}
