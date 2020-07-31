package com.carmwork.plugin.timeforflight.managers;

import com.carmwork.plugin.timeforflight.Main;
import com.carmwork.plugin.timeforflight.utils.MessageParser;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class ConfigManager {


	private static FileConfiguration configuration;

	public static void loadConfig() {

		Main.getInstance().saveDefaultConfig();
		Main.getInstance().reloadConfig();

		configuration = Main.getInstance().getConfig();

	}

	public static int getTimeInterval() {
		return getConfiguration().getInt("settings.interval", 600);
	}

	public static int getTimeGiven() {
		return getConfiguration().getInt("settings.giveTime", 60);
	}

	public static boolean isAlertEnabled() {
		return getConfiguration().getBoolean("settings.alert.enable", true);
	}

	/**
	 * #  - 变量： %(interval) 间隔时间
	 * #  - 变量： %(time)     赠送时间
	 *
	 * @return 通知消息
	 */
	public static String getAlertMessage() {
		return MessageParser.parseColor(Objects.requireNonNull(getConfiguration().getString(
				"settings.alert.message",
				"&7您刚刚完成了一次在线 &f%(interval) 秒&7，获增了 &f%(time) 秒&7飞行时间。"
		)))
				.replace("%(interval)", Integer.toString(getTimeInterval()))
				.replace("%(time)", Integer.toString(getTimeGiven()));
	}

	public static FileConfiguration getConfiguration() {
		return configuration;
	}
}
