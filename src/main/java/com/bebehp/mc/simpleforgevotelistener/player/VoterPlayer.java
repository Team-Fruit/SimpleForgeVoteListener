package com.bebehp.mc.simpleforgevotelistener.player;

import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.SimpleForgeVoteListener;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class VoterPlayer implements ICommandSender {

	private final String name;
	private final UUID uuid;
	private final EntityPlayerMP player;
	private final VoteDataIO dataIO;

	public VoterPlayer(final String name, final UUID uuid, final EntityPlayerMP entityPlayerMP, final VoteDataIO voteDataIO) {
		this.name = name;
		this.uuid = uuid;
		this.player = entityPlayerMP;
		this.dataIO = voteDataIO;
	}

	public VoterPlayer(final String name, final UUID uuid) {
		this(name, uuid, MinecraftServer.getServer().getConfigurationManager().func_152612_a(name),
				new VoteDataIO(SimpleForgeVoteListener.DATA_DIR, uuid.toString() + ".json"));
	}

	/**
	 * 通常はgetCommandSenderName()と同様です。
	 * @return Username
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return UUID
	 */
	public UUID getUUID() {
		return this.uuid;
	}

	/**
	 * 初期化時の値のため、Entityが存在していない場合でもnull以外が返される場合があります。
	 * @return EntityPlayerMP
	 */
	public EntityPlayerMP getEntityPlayerMP() {
		return this.player;
	}

	/**
	 * @return VoteDataIO
	 */
	public VoteDataIO getVoteDataIO() {
		return this.dataIO;
	}

	/**
	 * 現在Playerが存在するかをチェックします。
	 * @return Playerが存在する場合はtrue
	 */
	public boolean isOnline() {
		for (final String userName : MinecraftServer.getServer().getAllUsernames()) {
			if (this.name == userName)
				return true;
		}
		return false;
	}

	@Override
	public String getCommandSenderName() {
		if (this.player == null)
			return null;
		return this.player.getCommandSenderName();
	}

	@Override
	public IChatComponent func_145748_c_() {
		if (this.player == null)
			return null;
		return this.player.func_145748_c_();
	}

	@Override
	public void addChatMessage(final IChatComponent p_145747_1_) {
		if (this.player == null)
			return;
		this.player.addChatMessage(p_145747_1_);
	}

	@Override
	public boolean canCommandSenderUseCommand(final int p_70003_1_, final String p_70003_2_) {
		if (this.player == null)
			return false;
		return this.player.canCommandSenderUseCommand(p_70003_1_, p_70003_2_);
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		if (this.player == null)
			return null;
		return this.player.getPlayerCoordinates();
	}

	@Override
	public World getEntityWorld() {
		if (this.player == null)
			return null;
		return this.player.getEntityWorld();
	}

}
