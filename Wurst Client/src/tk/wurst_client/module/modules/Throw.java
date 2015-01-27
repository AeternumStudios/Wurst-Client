package tk.wurst_client.module.modules;

import net.minecraft.client.Minecraft;
import tk.wurst_client.Client;
import tk.wurst_client.module.Category;
import tk.wurst_client.module.Module;

public class Throw extends Module
{
	public Throw()
	{
		super
		(
			"Throw",
			"Uses an item multiple times.\n"
			+ "This can cause a lot of lag and even crash a server.\n"
			+ "Works best with snowballs or eggs.\n"
			+ "Type .throw <amount> to change the amount of uses per click.",
			0,
			Category.MISC
		);
	}
	
	public String getRenderName()
	{
		return this.getName() + " [" + Client.Wurst.options.throwAmount + "]";
	}
	
	public void onUpdate()
	{
		if(!this.getToggled())
			return;
		if((Minecraft.getMinecraft().rightClickDelayTimer == 4 || Client.Wurst.moduleManager.getModuleFromClass(FastPlace.class).getToggled()) && Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed)
		{
			if(Minecraft.getMinecraft().objectMouseOver == null || Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem() == null)
				return;
			for(int i = 0; i < Client.Wurst.options.throwAmount - 1; i++)
			{
				Minecraft.getMinecraft().rightClickMouse();
			}
		}
	}
}
