package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import com.bebehp.mc.simpleforgevotelistener.ChatUtil;
import com.bebehp.mc.simpleforgevotelistener.setting.JsonSetting;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.Commands;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.GlobalChat;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.PrivateChat;
import com.bebehp.mc.simpleforgevotelistener.setting.SettingFormat;
import com.bebehp.mc.simpleforgevotelistener.vote.data.Data;
import com.bebehp.mc.simpleforgevotelistener.vote.data.VoteDataIO;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public abstract class AbstractVoteEvent implements IVoteEvent {

	public static final Pattern pattern = Pattern.compile("%s");

	protected VoteDataIO voteDataIO;
	protected String name;
	protected UUID uuid;
	protected Data data;

	public AbstractVoteEvent(final VoteDataIO voteDataIO, final String name, final UUID uuid) {
		this.name = name;
		this.uuid = uuid;
		this.voteDataIO = voteDataIO;
		this.data = voteDataIO.load();
	}

	@Override
	public void onVote() {
		if (this.data.getUuid() != this.uuid.toString())
			return;

		int voteCount = Integer.parseInt(this.data.getVote());
		this.data.setVote(voteCount++);
	}

	@Override
	public void executeCommand() {
		if (JsonSetting.voteJson.commands != null) {
			final MinecraftServer server = MinecraftServer.getServer();
			final List list = JsonSetting.voteJson.commands;
			final Iterator<Commands> it = list.iterator();
			while (it.hasNext()) {
				final Commands cmd = it.next();
				final SettingFormat format = new SettingFormat(this.name, cmd.str, cmd.args);
				server.getCommandManager().executeCommand(server, format.parseArgs());
			}
		}
	}

	@Override
	public void sendChat() {
		if (JsonSetting.voteJson.global_chat != null) {
			final List list = JsonSetting.voteJson.global_chat;
			final Iterator<GlobalChat> it = list.iterator();
			while (it.hasNext()) {
				final GlobalChat chat = it.next();
				final SettingFormat format = new SettingFormat(this.name, chat.str, chat.args);
				final boolean isJson = Boolean.valueOf(chat.json);
				if(isJson)
					ChatUtil.sendServerChat(ChatUtil.byJson(format.parseArgs()));
				else
					ChatUtil.sendServerChat(ChatUtil.byText(format.parseArgs()));
			}
		}
	}

	@Override
	public void sendPrivateChat() {
		if (JsonSetting.voteJson.private_chat != null) {
			final List list = JsonSetting.voteJson.private_chat;
			final Iterator<PrivateChat> it = list.iterator();
			while (it.hasNext()) {
				final PrivateChat chat = it.next();
				final SettingFormat format = new SettingFormat(this.name, chat.str, chat.args);
				final EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.name);
				final boolean isJson = Boolean.valueOf(chat.json);
				if(isJson)
					player.addChatComponentMessage(ChatUtil.byJson(format.parseArgs()));
				else
					player.addChatComponentMessage(ChatUtil.byText(format.parseArgs()));
			}
		}
	}

}
