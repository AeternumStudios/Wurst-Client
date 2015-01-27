package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;
import tk.wurst_client.module.modules.Throw;
import tk.wurst_client.utils.MiscUtils;

public class ThrowMod extends Command
{

	private static String[] commandHelp =
	{
		"Changes the speed of Throw or toggles it.",
		".throw",
		".throw amount <amount>",
	};
	
	public ThrowMod()
	{
		super("throw", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			Client.Wurst.moduleManager.getModuleFromClass(Throw.class).toggleModule();
			Client.Wurst.chat.message("Throw turned " + (Client.Wurst.moduleManager.getModuleFromClass(Throw.class).getToggled() == true ? "on" : "off") + ".");
		}else if(args[0].equalsIgnoreCase("amount") && MiscUtils.isInteger(args[1]))
		{
			if(Integer.valueOf(args[1]) < 1)
			{
				Client.Wurst.chat.error("Throw amount must be at least 1.");
				return;
			}
			Client.Wurst.options.throwAmount = Integer.valueOf(args[1]);
			Client.Wurst.fileManager.saveOptions();
			Client.Wurst.chat.message("Throw amount set to " + args[1] + ".");
		}else
			commandError();
	}
}
