package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bebehp.mc.simpleforgevotelistener.ChatUtil;
import com.bebehp.mc.simpleforgevotelistener.handler.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.player.VoterPlayer;
import com.bebehp.mc.simpleforgevotelistener.setting.JsonSetting;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.C_PrivateChat;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.Commands;
import com.bebehp.mc.simpleforgevotelistener.setting.SettingFormat;

import net.minecraft.server.MinecraftServer;

public class OnlineVoteEvent extends AbstractVoteEvent {

	public OnlineVoteEvent(final VoterPlayer voterPlayer) {
		super(voterPlayer);
	}

	@Override
	public void onVote() {
		int voteCount = Integer.parseInt(this.data.getVote());
		this.data.setVote(++voteCount);
		if (this.data.getUuid() == null)
			this.data.setUuid(this.uuid.toString());

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
		this.playerData.save(this.data);
	}

	private void sendCumulativeCountChat() {
		if (JsonSetting.voteJson.on_cumulative_rewards_private_chat != null) {
			final List list = JsonSetting.voteJson.on_cumulative_rewards_private_chat;
			final Iterator<C_PrivateChat> it = list.iterator();
			while (it.hasNext()) {
				final C_PrivateChat chat = it.next();
				final String formatArgs = chat.args.replaceAll("count", this.data.getVote_offline());
				final SettingFormat format = new SettingFormat(this.name, chat.str, formatArgs);
				final boolean isJson = Boolean.valueOf(chat.json);
				if(isJson)
					ChatUtil.sendPlayerChat(this.player, ChatUtil.byJson(format.parseArgs()));
				else
					ChatUtil.sendPlayerChat(this.player, ChatUtil.byText(format.parseArgs()));
			}
		}
	}

	private void optionalExecuteCommand() {
		if (JsonSetting.voteJson.commands != null) {
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
