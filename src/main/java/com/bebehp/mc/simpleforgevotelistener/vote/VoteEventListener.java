package com.bebehp.mc.simpleforgevotelistener.vote;

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
import com.bebehp.mc.simpleforgevotelistener.vote.data.VoteDataIO;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.server.FMLServerHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class VoteEventListener {
	public static final VoteEventListener INSTANCE = new VoteEventListener();
	public static final File dataDir = new File(SimpleForgeVoteListener.getModDataDir(), "player");

	private VoteEventListener() {
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		reward(name);
	}

	public void reward(final String name) {
		UUID uuid = getUUID(name);
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

	@Deprecated
	public static boolean checkOnline(final String username) {
		for(final String line : MinecraftServer.getServer().getAllUsernames()) {
			if (line == username)
				return true;
		}
		return false;
	}

	public static UUID getUUID(final String name) {
		final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		if (player == null)
			return null;
		return player.getGameProfile().getId();
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
