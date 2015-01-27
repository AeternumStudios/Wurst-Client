package tk.wurst_client;

import net.minecraft.client.gui.ServerListEntryNormal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.darkstorm.minecraft.gui.theme.wurst.WurstTheme;

import tk.wurst_client.command.ChatMessenger;
import tk.wurst_client.command.CommandManager;
import tk.wurst_client.files.FileManager;
import tk.wurst_client.gui.GuiManager;
import tk.wurst_client.module.ModuleManager;
import tk.wurst_client.options.Options;
import tk.wurst_client.update.Updater;

public class Client
{
	private static final Logger logger = LogManager.getLogger();
	public final String CLIENT_NAME = "Wurst";
	public final String CLIENT_VERSION = "1.4.2";
	public String currentServerIP = "127.0.0.1:25565";
	public ServerListEntryNormal lastServer;
	public boolean startupMessageDisabled = false;
	
	public ModuleManager moduleManager;
	public GuiManager guiManager;
	public CommandManager commandManager;
	public FileManager fileManager;
	public Updater updater;
	public ChatMessenger chat;
	public Options options;
	
	public static final Client Wurst = new Client();
	
	public void startClient()
	{
		this.moduleManager = new ModuleManager();
		this.guiManager = new GuiManager();
		this.commandManager = new CommandManager();
		this.fileManager = new FileManager();
		this.updater = new Updater();
		this.chat = new ChatMessenger();
		this.options = new Options();
		
		this.guiManager.setTheme(new WurstTheme());
		this.guiManager.setup();
		this.fileManager.init();
		this.updater.check("Wurst 1.8", this.CLIENT_VERSION);
		logger.info("[Wurst Updater] " + (this.updater.updateAvailable ? "Found update: " + this.updater.latestVersion : "No update found."));
	}
}
