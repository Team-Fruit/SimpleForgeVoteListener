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

public class VoteListener {
	public static final VoteListener INSTANCE = new VoteListener();
	public static final File dataDir = new File(SimpleForgeVoteListener.getModDataDir(), "player");

	private VoteListener() {
	}

	@SubscribeEvent
	public void onVoteEvent(final VotifierEvent event) {
		final Vote vote = event.getVote();
		final String name = vote.getUsername();
		reward(name);
	}

	public void reward(final String name) {
		final UUID uuid = getUUID(name);
		final VoteDataIO voteDataIO = new VoteDataIO(dataDir, uuid.toString() + ".json");
		if (checkOnline(name)) {
			final VoteOnline online = new VoteOnline(voteDataIO, name, uuid);
		} else {
			final VoteOffline offline = new VoteOffline(voteDataIO, name, uuid);
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
