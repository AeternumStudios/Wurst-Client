package tk.wurst_client.command.commands;

import tk.wurst_client.Client;
import tk.wurst_client.command.Command;

public class FastBreakMod extends Command
{

	private static String[] commandHelp =
	{
		"Changes the settings of FastBreak.",
		".fastbreak mode <normal | instant>"
	};
	
	public FastBreakMod()
	{
		super("fastbreak", commandHelp);
	}
	
	public void onEnable(String input, String[] args)
	{
		if(args == null)
		{
			commandError();
		}else if(args[0].toLowerCase().equals("mode"))
		{//0=normal, 1=instant
			if(args[1].toLowerCase().equals("normal"))
			{
				Client.Wurst.options.fastbreakMode = 0;
			}else if(args[1].toLowerCase().equals("instant"))
			{
				Client.Wurst.options.fastbreakMode = 1;
			}else
			{
				commandError();
				return;
			}
			Client.Wurst.fileManager.saveOptions();
			Client.Wurst.chat.message("FastBreak mode set to \"" + args[1] + "\".");
		}
	}
}
