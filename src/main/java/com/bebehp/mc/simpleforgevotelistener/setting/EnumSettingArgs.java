package com.bebehp.mc.simpleforgevotelistener.setting;

import java.text.DecimalFormat;

import net.minecraft.entity.player.EntityPlayerMP;

public enum EnumSettingArgs {
	USERNAME {
		@Override
		public String parseString(final EntityPlayerMP player) {
			return player.getCommandSenderName();
		}
	},
	HOLDITEM {
		@Override
		public String parseString(final EntityPlayerMP player) {
			return player.getHeldItem().getDisplayName();
		}
	},
	POSX {
		@Override
		public String parseString(final EntityPlayerMP player) {
			return new DecimalFormat("0.##").format(player.posX);
		}
	},
	POSY {
		@Override
		public String parseString(final EntityPlayerMP player) {
			return new DecimalFormat("0.##").format(player.posY);
		}
	},
	POSZ {
		@Override
		public String parseString(final EntityPlayerMP player) {
			return new DecimalFormat("0.##").format(player.posZ);
		}
	},
	;

	public abstract String parseString(EntityPlayerMP player);
}
