package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.File;
import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.SimpleForgeVoteListener;
import com.bebehp.mc.simpleforgevotelistener.vote.data.VoteDataIO;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
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
		final VoteDataIO voteDataIO = new VoteDataIO(dataDir, name + ".json");
		if (checkOnline(name)) {
			final UUID uuid = getUUID(name);
			final OnlineVoteEvent online = new OnlineVoteEvent(voteDataIO, name, uuid);
		} else {
			final OfflineVoteEvent offline = new OfflineVoteEvent(voteDataIO, name, null);
		}
	}

	public static boolean checkOnline(final String username) {
		for(final String line :  MinecraftServer.getServer().getAllUsernames()) {
			if (line == username)
				return true;
		}
		return false;
	}

	public static UUID getUUID(final String name) {
		final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		return player.getGameProfile().getId();
	}

}
