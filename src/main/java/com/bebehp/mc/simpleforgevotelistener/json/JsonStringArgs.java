package com.bebehp.mc.simpleforgevotelistener.json;

import java.text.DecimalFormat;

import net.minecraft.entity.player.EntityPlayerMP;

public enum JsonStringArgs {
	HOLDITEM {
		@Override
		public String parseString(final String name, final EntityPlayerMP player) {
			return player.getHeldItem().toString();
		}
	},
	POSX {
		@Override
		public String parseString(final String name, final EntityPlayerMP player) {
			return new DecimalFormat("0.##").format(player.posX);
		}
	},
	POSY {
		@Override
		public String parseString(final String name, final EntityPlayerMP player) {
			return new DecimalFormat("0.##").format(player.posY);
		}
	},
	POSZ {
		@Override
		public String parseString(final String name, final EntityPlayerMP player) {
			return new DecimalFormat("0.##").format(player.posZ);
		}
	},
	;

	public abstract String parseString(String name, EntityPlayerMP player);
}
