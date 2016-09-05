package com.bebehp.mc.simpleforgevotelistener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import net.minecraft.client.Minecraft;

public class VoteOffline {

	public static final File csvFile = new File(Minecraft.getMinecraft().mcDataDir, "offlinevote.csv");

	private LinkedHashMap<String, Integer> map;

	public VoteOffline() {
		BufferedReader br = null;
		try {
			final FileReader fr = new FileReader(csvFile);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				final String[] array = line.split(",");
				this.map.put(array[0], Integer.parseInt(array[1]));
			}
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(br);
		}
	}

	private void save(final LinkedHashMap<String, Integer> map) {
		if (map.isEmpty())
			return;

		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(csvFile, false);
			pw = new PrintWriter(new BufferedWriter(fw));

			final Set<Map.Entry<String, Integer>> set = map.entrySet();
			final Iterator<Map.Entry<String, Integer>> it = set.iterator();

			while (it.hasNext()) {
				final Map.Entry<String, Integer> entry = it.next();
				pw.println(entry.getKey() + "," + entry.getValue());
			}
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			pw.close();
		}
	}

	private void postSave(final String key, final int value) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			fw = new FileWriter(csvFile, true);
			pw = new PrintWriter(new BufferedWriter(fw));
			pw.println(key + "," + value);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			pw.close();
		}
	}

	public void onVote(final String name) {
		if (this.map.containsKey(name)) {
			int count = this.map.get(name);
			this.map.put(name, count++);
			save(this.map);
		} else {
			this.map.put(name, 1);
			postSave(name, 1);
		}
	}


}
