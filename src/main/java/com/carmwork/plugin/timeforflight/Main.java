package com.carmwork.plugin.timeforflight;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;

	public static Main getInstance() {
		return Main.instance;
	}

	@Override
	public void onEnable() {
		instance = this;


	}

}