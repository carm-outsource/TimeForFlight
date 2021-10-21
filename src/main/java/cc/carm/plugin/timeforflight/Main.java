package cc.carm.plugin.timeforflight;

import cc.carm.plugin.timeforflight.commands.TimeForFlightCommand;
import cc.carm.plugin.timeforflight.commands.ToggleFlyCommand;
import cc.carm.plugin.timeforflight.managers.ConfigManager;
import cc.carm.plugin.timeforflight.managers.DataManager;
import cc.carm.plugin.timeforflight.listeners.PlayerListener;
import org.bukkit.Bukkit;
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
        Main.getInstance().getCommand("TimeForFlight").setExecutor(new TimeForFlightCommand());
        Main.getInstance().getCommand("ToggleFly").setExecutor(new ToggleFlyCommand());

        logInfo("注册监听器");
        Bukkit.getPluginManager().registerEvents(new PlayerListener(),this);

        logInfo("初始化数据管理");
        DataManager.init();
    }

    public static void logInfo(String message) {
        Main.getInstance().getLogger().log(Level.INFO, message);
    }

}