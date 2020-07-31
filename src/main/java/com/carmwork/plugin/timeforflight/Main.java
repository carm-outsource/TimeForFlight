package com.carmwork.plugin.timeforflight;

import com.carmwork.plugin.timeforflight.commands.ToggleFlyCommand;
import com.carmwork.plugin.timeforflight.managers.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class Main extends JavaPlugin {

	private static Main instance;

	public static Main getInstance() {
		return Main.instance;
	}

	@Override
	public void onEnable() {
		instance = this;

		logInfo("加载配置文件中");
		ConfigManager.loadConfig();

		logInfo("注册指令");
		Main.getInstance().getCommand("togglefly").setExecutor(new ToggleFlyCommand());

	}

	public static void logInfo(String message) {
		Main.getInstance().getLogger().log(Level.INFO, message);
	}

}