package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.apache.commons.io.IOUtils;

import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

public class VoteListener {

	private final VoteOnline voteOnline;
	private final VoteOffline voteOffline;

	public static final File csvFile = new File(Minecraft.getMinecraft().mcDataDir, "offlinevote.csv");
	public static LinkedHashMap<String, Integer> map;

	public VoteListener(final VoteOnline voteOline, final VoteOffline voteOffline) {
		this.voteOnline = voteOline;
		this.voteOffline = voteOffline;

		loadCSV();
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		if (checkOnline(name)) {
			this.voteOnline.onVote(name);
		} else {
			this.voteOffline.onVote(name);
		}
	}

	public boolean checkOnline(final String username) {
		for(final String line :  MinecraftServer.getServer().getAllUsernames()) {
			if (line == username)
				return true;
		}
		return false;
	}

	private void loadCSV() {
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
