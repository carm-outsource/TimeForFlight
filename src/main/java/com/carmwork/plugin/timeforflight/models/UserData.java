package com.carmwork.plugin.timeforflight.models;

import com.carmwork.plugin.timeforflight.Main;
import com.carmwork.plugin.timeforflight.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UserData {


	UUID uuid;

	private File datafile;
	private FileConfiguration data;

	boolean fileLoaded;

	private int remainTime;

	private BukkitRunnable giveTask;
	private BukkitRunnable flyTask;


	public UserData(UUID uuid) {
		this.uuid = uuid;
		File userdatasFolder = new File(Main.getInstance().getDataFolder() + "/userdatas");
		if (!userdatasFolder.isDirectory() || !userdatasFolder.exists()) {
			userdatasFolder.mkdir();
		}
		this.datafile = new File(userdatasFolder, this.uuid + ".yml");
		this.remainTime = 0;


		this.fileLoaded = datafile.exists();
		if (fileLoaded) {
			this.data = YamlConfiguration.loadConfiguration(datafile);

			readData();
		}
	}

	public void startFlyTask(Player player) {
		player.setAllowFlight(true);
		flyTask = new BukkitRunnable() {
			@Override
			public void run() {
				if (canFly()) {
					removeTime(1);
				} else {
					player.setAllowFlight(false);
					player.setFlying(false);
				}
			}
		};
		flyTask.runTaskTimer(Main.getInstance(), 0L, 20L);

	}

	public void stopFly() {
		if (this.flyTask != null) {
			this.flyTask.cancel();
		}
	}

	public void readData() {
		this.remainTime = getData().getInt("remainTime");
	}

	public void addTime(int remainTime) {
		this.remainTime += remainTime;
	}

	public void removeTime(int time) {
		this.remainTime -= time;
	}

	public void addTimeInConfig() {
		addTime(ConfigManager.getTimeGiven());
	}

	public boolean canFly() {
		return getRemainTime() > 0;
	}


	public BukkitRunnable getGiveTask() {
		return giveTask;
	}

	public void startGivenTask(Player player) {
		giveTask = new BukkitRunnable() {
			@Override
			public void run() {
				if (!player.isOnline()
						|| !player.hasPermission("timeforflight.getflighttime")
						|| player.hasPermission("timeforflight.unlimited")) {
					cancel();
				}
				addTimeInConfig();
				if (ConfigManager.isAlertEnabled()) {
					player.sendMessage(ConfigManager.getAlertMessage());
				}
			}
		};

		getGiveTask().runTaskTimerAsynchronously(Main.getInstance(),
				ConfigManager.getTimeInterval() * 20,
				ConfigManager.getTimeInterval() * 20);
	}

	public void stopTasks() {
		if (getGiveTask() != null) {
			getGiveTask().cancel();
		}
		if (this.flyTask != null) {
			this.flyTask.cancel();
		}
	}

	public boolean isFileLoaded() {
		return fileLoaded;
	}

	public File getDatafile() {
		return datafile;
	}

	public FileConfiguration getData() {
		return data;
	}

	public int getRemainTime() {
		return remainTime;
	}

	private void checkFile() {
		if (!datafile.exists()) {
			try {
				datafile.createNewFile();
			} catch (IOException ex) {
				Bukkit.getLogger().info("Could not load file " + "/userdatas/" + "yml" + ex);
			}
		}
		if (!isFileLoaded()) {
			this.data = YamlConfiguration.loadConfiguration(datafile);
			this.fileLoaded = true;
		}
	}

	public void saveData() {
		checkFile();
		getData().set("remainTime", getRemainTime());
		try {
			getData().save(datafile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
