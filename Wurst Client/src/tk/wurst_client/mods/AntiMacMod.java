/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import java.util.HashSet;

import tk.wurst_client.WurstClient;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;

@Info(category = Category.MISC,
	description = "Makes other mods bypass Mineplex AntiCheat or blocks them\n"
		+ "if they can't.",
	name = "AntiMAC")
public class AntiMacMod extends Mod
{
	private HashSet<Mod> blockedMods;
	
	@Override
	public void onEnable()
	{
		if(WurstClient.INSTANCE.mods.yesCheatMod.isEnabled())
			WurstClient.INSTANCE.mods.yesCheatMod.setEnabled(false);
		if(blockedMods == null)
		{
			blockedMods = new HashSet<>();
			// add mods that down't work with YesCheat+
			for(Mod mod : WurstClient.INSTANCE.mods.getAllMods())
				if(!mod.getClass().getAnnotation(Mod.Info.class)
					.noCheatCompatible())
					blockedMods.add(mod);
			
			// remove mods that work with MAC
			// TODO: More efficient method to do this
			blockedMods.remove(WurstClient.INSTANCE.mods.antiFireMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.antiPotionMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.fastBowMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.glideMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.multiAuraMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.noSlowdownMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.regenMod);
			blockedMods.remove(WurstClient.INSTANCE.mods.spiderMod);
			
			// block AntiChatFilter because Mineplex disables special characters
			blockedMods.add(WurstClient.INSTANCE.mods.antiChatFilterMod);
		}
		for(Mod mod : blockedMods)
			mod.setBlocked(true);
	}
	
	@Override
	public void onDisable()
	{
		for(Mod mod : blockedMods)
			mod.setBlocked(false);
	}
}
