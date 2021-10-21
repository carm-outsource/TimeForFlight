package cc.carm.plugin.timeforflight.models;

import cc.carm.plugin.timeforflight.Main;
import cc.carm.plugin.timeforflight.enums.Permissions;
import cc.carm.plugin.timeforflight.managers.ConfigManager;
import cc.carm.plugin.timeforflight.utils.MessageParser;
import cc.carm.plugin.timeforflight.utils.TimeFormat;
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

    private File dataFile;
    private FileConfiguration data;

    boolean fileLoaded;

    private int remainTime;

    private BukkitRunnable giveTask;
    private BukkitRunnable flyTask;


    public UserData(UUID uuid) {
        this.uuid = uuid;
        File userdatasFolder = new File(Main.getInstance().getDataFolder() + "/userdata");
        if (!userdatasFolder.isDirectory() || !userdatasFolder.exists()) {
            userdatasFolder.mkdir();
        }
        this.dataFile = new File(userdatasFolder, this.uuid + ".yml");
        this.remainTime = 0;


        this.fileLoaded = dataFile.exists();
        if (fileLoaded) {
            this.data = YamlConfiguration.loadConfiguration(dataFile);

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
                    if (getRemainTime() == 10) {
                        player.sendMessage(MessageParser.parseColor("§c注意！§7您的飞行剩余时间仅剩10秒，请您注意安全！"));
                    }
                } else {
                    player.sendMessage(MessageParser.parseColor("&7您已经用尽剩余的飞行时长，已为您关闭飞行状态。"));
                    stopFly(player);
                }
            }
        };
        flyTask.runTaskTimer(Main.getInstance(), 0L, 20L);

    }

    public void stopFly(Player player) {
        if (this.flyTask != null) {
            this.flyTask.cancel();
        }
        player.setAllowFlight(false);
        player.setFlying(false);
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
    public void setTime(int time){
        this.remainTime = time;
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
                        || !player.hasPermission(Permissions.GET_TIME.toString())
                        || player.hasPermission(Permissions.UNLIMITED.toString())) {
                    cancel();
                }
                addTimeInConfig();
                if (ConfigManager.isAlertEnabled()) {
                    player.sendMessage(ConfigManager.getAlertMessage());
                }
            }
        };

        getGiveTask().runTaskTimerAsynchronously(Main.getInstance(),
                ConfigManager.getTimeInterval() * 20L,
                ConfigManager.getTimeInterval() * 20L);
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

    public File getDataFile() {
        return dataFile;
    }

    public FileConfiguration getData() {
        return data;
    }

    public int getRemainTime() {
        return remainTime;
    }

    private void checkFile() {
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException ex) {
                Bukkit.getLogger().info("Could not load file " + "/userdata/" + "yml" + ex);
            }
        }
        if (!isFileLoaded()) {
            this.data = YamlConfiguration.loadConfiguration(dataFile);
            this.fileLoaded = true;
        }
    }

    public String getRemainTimeString() {
        return TimeFormat.getTimeString(getRemainTime());
    }

    public void saveData() {
        checkFile();
        getData().set("remainTime", getRemainTime());
        try {
            getData().save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
