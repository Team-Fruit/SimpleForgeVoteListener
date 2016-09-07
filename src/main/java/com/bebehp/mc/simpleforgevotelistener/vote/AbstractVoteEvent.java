package com.bebehp.mc.simpleforgevotelistener.vote;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.SimpleForgeVoteListener;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public abstract class AbstractVoteEvent implements IVote {

	public static final File dataDir = new File(SimpleForgeVoteListener.getModDataDir(), "player");

	protected String name;
	protected LinkedHashMap<String, Data> map;

	public AbstractVoteEvent(final String name) {
		this.name = name;
	}

	@Override
	public void onVote() {
		final UUID uuid = getUUID(this.name);
		final VoteDataIO voteData = new VoteDataIO(dataDir, uuid.toString() + ".json");
		final Data data = voteData.load();

	}

	private UUID getUUID(final String name) {
		final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(name);
		return player.getGameProfile().getId();
	}
}
