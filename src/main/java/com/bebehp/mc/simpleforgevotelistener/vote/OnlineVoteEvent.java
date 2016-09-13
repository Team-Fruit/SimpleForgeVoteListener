package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.ChatUtil;
import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.setting.JsonSetting;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.C_PrivateChat;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.Commands;
import com.bebehp.mc.simpleforgevotelistener.setting.SettingFormat;
import com.bebehp.mc.simpleforgevotelistener.vote.data.VoteDataIO;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class OnlineVoteEvent extends AbstractVoteEvent {

	public OnlineVoteEvent(final VoteDataIO voteDataIO, final String name, final UUID uuid) {
		super(voteDataIO, name, uuid);
	}

	@Override
	public void onVote() {
		int voteCount = Integer.parseInt(this.data.getVote());
		this.data.setVote(voteCount++);

		executeCommand();
		sendChat();
		sendPrivateChat();
		final int offlineVoteTimes = Integer.parseInt(this.data.getVote_offline());
		if (offlineVoteTimes > 0) {
			if (ConfigurationHandler.offlineVoteEnable) {
				sendCumulativeCountChat();
				for (int i =0; i < offlineVoteTimes; i++)
					optionalExecuteCommand();
			}
			this.data.setVote_offline(0);
		}
		this.voteDataIO.save(this.data);
	}

	private void sendCumulativeCountChat() {
		if (JsonSetting.voteJson.on_cumulative_rewards_private_chat != null) {
			final List list = JsonSetting.voteJson.on_cumulative_rewards_private_chat;
			final Iterator<C_PrivateChat> it = list.iterator();
			while (it.hasNext()) {
				final C_PrivateChat chat = it.next();
				final String formatArgs = chat.args.replaceAll("count", this.data.getVote_offline());
				final SettingFormat format = new SettingFormat(this.name, chat.str, formatArgs);
				final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.name);
				final boolean isJson = Boolean.valueOf(chat.json);
				if(isJson)
					player.addChatComponentMessage(ChatUtil.byJson(format.parseArgs()));
				else
					player.addChatComponentMessage(ChatUtil.byText(format.parseArgs()));
			}
		}
	}

	private void optionalExecuteCommand() {
		if (JsonSetting.voteJson.commands == null) {
			final MinecraftServer server = MinecraftServer.getServer();
			final List list = JsonSetting.voteJson.commands;
			final Iterator<Commands> it = list.iterator();
			while (it.hasNext()) {
				final Commands cmd = it.next();
				if(!StringUtils.equalsIgnoreCase(cmd.option, "non_cumulative")) {
					final SettingFormat format = new SettingFormat(this.name, cmd.str, cmd.args);
					server.getCommandManager().executeCommand(server, format.parseArgs());
				}
			}
		}
	}

}
