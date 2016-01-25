/*
 * Copyright � 2014 - 2015 Alexander01998 and contributors
 * All rights reserved.
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package tk.wurst_client.mods;

import net.minecraft.entity.EntityLivingBase;

import org.darkstorm.minecraft.gui.component.BoundedRangeComponent.ValueDisplay;

import tk.wurst_client.events.listeners.UpdateListener;
import tk.wurst_client.mods.Mod.Category;
import tk.wurst_client.mods.Mod.Info;
import tk.wurst_client.navigator.NavigatorItem;
import tk.wurst_client.navigator.settings.SliderSetting;
import tk.wurst_client.utils.EntityUtils;

@Info(category = Category.COMBAT,
	description = "Automatically attacks everything in your range.",
	name = "Killaura")
public class KillauraMod extends Mod implements UpdateListener
{
	public float normalSpeed = 20F;
	public float normalRange = 5F;
	public float yesCheatSpeed = 12F;
	public float yesCheatRange = 4.25F;
	public int fov = 360;
	public float realSpeed;
	public float realRange;
	
	@Override
	public void initSettings()
	{
		settings.add(new SliderSetting("Speed", normalSpeed, 2, 20, 0.1,
			ValueDisplay.DECIMAL));
		settings.add(new SliderSetting("Range", normalRange, 1, 6, 0.05,
			ValueDisplay.DECIMAL));
		settings.add(new SliderSetting("FOV", fov, 30, 360, 10,
			ValueDisplay.DEGREES));
	}
	
	@Override
	public NavigatorItem[] getSeeAlso()
	{
		return new NavigatorItem[]{wurst.special.targetSpf,
			wurst.mods.killauraLegitMod, wurst.mods.multiAuraMod,
			wurst.mods.clickAuraMod, wurst.mods.triggerBotMod,
			wurst.mods.criticalsMod};
	}
	
	@Override
	public void updateSliders()
	{
		normalSpeed = (float)((SliderSetting)settings.get(0)).getValue();
		yesCheatSpeed = Math.min(normalSpeed, 12F);
		normalRange = (float)((SliderSetting)settings.get(1)).getValue();
		yesCheatRange = Math.min(normalRange, 4.25F);
		fov = (int)((SliderSetting)settings.get(2)).getValue();
	}
	
	@Override
	public void onEnable()
	{
		// TODO: Clean up this mess!
		if(wurst.mods.killauraLegitMod.isEnabled())
			wurst.mods.killauraLegitMod.setEnabled(false);
		if(wurst.mods.multiAuraMod.isEnabled())
			wurst.mods.multiAuraMod.setEnabled(false);
		if(wurst.mods.clickAuraMod.isEnabled())
			wurst.mods.clickAuraMod.setEnabled(false);
		if(wurst.mods.triggerBotMod.isEnabled())
			wurst.mods.triggerBotMod.setEnabled(false);
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onUpdate()
	{
		if(wurst.mods.yesCheatMod.isActive())
		{
			realSpeed = yesCheatSpeed;
			realRange = yesCheatRange;
		}else
		{
			realSpeed = normalSpeed;
			realRange = normalRange;
		}
		updateMS();
		EntityLivingBase en = EntityUtils.getClosestEntity(true, true);
		if(hasTimePassedS(realSpeed) && en != null)
			if(mc.thePlayer.getDistanceToEntity(en) <= realRange)
			{
				if(wurst.mods.autoSwordMod.isActive())
					AutoSwordMod.setSlot();
				CriticalsMod.doCritical();
				wurst.mods.blockHitMod.doBlock();
				EntityUtils.faceEntityPacket(en);
				mc.thePlayer.swingItem();
				mc.playerController.attackEntity(mc.thePlayer, en);
				updateLastMS();
			}
	}
	
	@Override
	public void onDisable()
	{
		wurst.events.remove(UpdateListener.class, this);
	}
}
