package com.bebehp.mc.simpleforgevotelistener.vote;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.bebehp.mc.simpleforgevotelistener.ChatUtil;
import com.bebehp.mc.simpleforgevotelistener.ConfigurationHandler;
import com.bebehp.mc.simpleforgevotelistener.setting.JsonSetting;
import com.bebehp.mc.simpleforgevotelistener.setting.Setting.Offline_GlobalChat;
import com.bebehp.mc.simpleforgevotelistener.vote.data.VoteDataIO;

public class OfflineVoteEvent extends AbstractVoteEvent {

	public OfflineVoteEvent(final VoteDataIO voteDataIO, final String name, final UUID uuid) {
		super(voteDataIO, name, uuid);
	}

	@Override
	public void onVote() {
		super.onVote();

		sendOfflineChat();
		if (ConfigurationHandler.offlineVoteEnable) {
			int offlineVoteCount = Integer.parseInt(this.data.getVote_offline());
			this.data.setVote_offline(offlineVoteCount++);
		}
		this.voteDataIO.save(this.data);
	}

	private void sendOfflineChat() {
		if (JsonSetting.voteJson.offline_global_chat != null) {
			final List list = JsonSetting.voteJson.offline_global_chat;
			final Iterator<Offline_GlobalChat> it = list.iterator();
			while (it.hasNext()) {
				final Offline_GlobalChat chat = it.next();
				final String message = pattern.matcher(chat.str).replaceAll(this.name);
				final boolean isJson = Boolean.valueOf(chat.json);
				if(isJson)
					ChatUtil.sendServerChat(ChatUtil.byJson(message));
				else
					ChatUtil.sendServerChat(ChatUtil.byText(message));
			}
		}
	}

}
