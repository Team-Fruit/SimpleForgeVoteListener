package com.bebehp.mc.simpleforgevotelistener.handler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

import com.bebehp.mc.simpleforgevotelistener.Reference;
import com.bebehp.mc.simpleforgevotelistener.SimpleForgeVoteListener;
import com.bebehp.mc.simpleforgevotelistener.player.UUIDUtil;
import com.bebehp.mc.simpleforgevotelistener.player.VoteDataIO;
import com.bebehp.mc.simpleforgevotelistener.player.VoterPlayer;
import com.bebehp.mc.simpleforgevotelistener.vote.DummyVoteEvent;
import com.bebehp.mc.simpleforgevotelistener.vote.IVoteEvent;
import com.bebehp.mc.simpleforgevotelistener.vote.OfflineVoteEvent;
import com.bebehp.mc.simpleforgevotelistener.vote.OnlineVoteEvent;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.server.FMLServerHandler;

public class VoteHandler {
	public static final VoteHandler INSTANCE = new VoteHandler();
	public static final File dataDir = new File(SimpleForgeVoteListener.getModDataDir(), "player");

	public static Map<String, VoterPlayer> player = new HashMap<String, VoterPlayer>();

	private VoteHandler() {
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		UUID uuid = UUIDUtil.getUUIDfromUsername(name);
		IVoteEvent voteEvent;
		if (uuid != null) {
			voteEvent = new OnlineVoteEvent(new VoteDataIO(dataDir, uuid + ".json"), name, uuid);
		} else {
			final BiMap<String, String> map = HashBiMap.create(readUserNameCache());
			if (map.containsValue(name)) {
				uuid = UUID.fromString(map.inverse().get(name));
				voteEvent = new OfflineVoteEvent(new VoteDataIO(dataDir, uuid + ".json"), name, uuid);
			} else {
				voteEvent = new DummyVoteEvent(name);
			}
		}
		voteEvent.onVote();
	}

	public static Map<String, String> readUserNameCache() {
		JsonReader jr = null;
		try {
			final File file = new File(FMLServerHandler.instance().getSavesDirectory(), "usernamecache.json");
			final BufferedReader br = new BufferedReader(new FileReader(file));
			jr = new JsonReader(br);
			final Map<String, String> map = new HashMap<String, String>();
			return new Gson().fromJson(jr, map.getClass());
		} catch (final FileNotFoundException fnfe) {
			Reference.logger.error("The usernamecache.json file not found.");
		} catch (final JsonSyntaxException jse) {
			Reference.logger.error(jse);
		} finally {
			IOUtils.closeQuietly(jr);
		}
		return Collections.emptyMap();
	}
}
