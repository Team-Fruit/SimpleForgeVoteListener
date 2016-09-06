package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.bebehp.mc.simpleforgevotelistener.Reference;

public class VoteOffline implements IVoteEvent {


	public VoteOffline() {
		BufferedReader br = null;
		try {
			if (!VoteListener.csvFile.exists()) {
				VoteListener.csvFile.createNewFile();
				return;
			}

			final FileReader fr = new FileReader(VoteListener.csvFile);
			br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				final String[] array = line.split(",");
				VoteListener.map.put(array[0], Integer.parseInt(array[1]));
			}
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			IOUtils.closeQuietly(br);
		}
	}

	private void saveAll(final LinkedHashMap<String, Integer> map) {
		if (map.isEmpty())
			return;

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(VoteListener.csvFile, false)));

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
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(VoteListener.csvFile, true)));
			pw.println(key + "," + value);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			pw.close();
		}
	}

	private void deleteUser(final String key) {
		try {
			final BufferedReader br = new BufferedReader(new FileReader(VoteListener.csvFile));

			final List<String> list = new ArrayList<String>();
			String line1;
			while ((line1 = br.readLine()) != null) {
				if (!line1.contains(key))
					list.add(line1);
			}
			br.close();

			final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(VoteListener.csvFile, false)));
			for (final String line2 : list) {
				pw.println(line2);
			}
			pw.close();
		} catch (final IOException e) {
			Reference.logger.error(e);
		}
	}

	public int collectVote(final String name) {
		if (VoteListener.map.containsKey(name)) {
			deleteUser(name);
			return VoteListener.map.get(name);
		} else {
			return 0;
		}
	}

	@Override
	public void onVote(final String name) {
		if (VoteListener.map.containsKey(name)) {
			final int count = VoteListener.map.get(name);
			VoteListener.map.put(name, count+1);
			saveAll(VoteListener.map);
		} else {
			VoteListener.map.put(name, 1);
			postSave(name, 1);
		}
	}
}
