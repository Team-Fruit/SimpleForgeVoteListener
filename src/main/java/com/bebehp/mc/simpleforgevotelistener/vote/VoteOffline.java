package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.Reference;

public class VoteOffline extends AbstractVoteEvent {


	public VoteOffline() {
		super();
	}

	private void saveAll(final LinkedHashMap<String, Integer> map) {
		if (map.isEmpty())
			return;

		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(csvFile, false)));

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
			pw = new PrintWriter(new BufferedWriter(new FileWriter(csvFile, true)));
			pw.println(key + "," + value);
		} catch (final IOException e) {
			Reference.logger.error(e);
		} finally {
			pw.close();
		}
	}

	private void deleteUser(final String key) {
		try {
			final BufferedReader br = new BufferedReader(new FileReader(csvFile));

			final List<String> list = new LinkedList<String>();
			String line1;
			while ((line1 = br.readLine()) != null) {
				if (!line1.contains(key))
					list.add(line1);
			}
			br.close();

			final PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(csvFile, false)));
			for (final String line2 : list) {
				pw.println(line2);
			}
			pw.close();
		} catch (final IOException e) {
			Reference.logger.error(e);
		}
	}

	public int collectVote(final String name) {
		if (map.containsKey(name)) {
			deleteUser(name);
			return map.get(name);
		} else {
			return 0;
		}
	}

	@Override
	public void onVote(final String name) {
		if (ConfigurationHandler.offlineVoteEnable) {
			if (map.containsKey(name)) {
				final int count = map.get(name);
				map.put(name, count+1);
				saveAll(map);
			} else {
				map.put(name, 1);
				postSave(name, 1);
			}
		}
	}
}
