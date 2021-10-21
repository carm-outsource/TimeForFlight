package cc.carm.plugin.timeforflight.commands;

import cc.carm.plugin.timeforflight.models.UserData;
import cc.carm.plugin.timeforflight.utils.MessageParser;
import cc.carm.plugin.timeforflight.enums.Permissions;
import cc.carm.plugin.timeforflight.managers.DataManager;
import cc.carm.plugin.timeforflight.utils.TimeFormat;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员管理、查看玩家飞行时间的指令
 */
public class TimeForFlightCommand implements CommandExecutor, TabCompleter {

    public static boolean help(CommandSender sender) {
        sendMessage(sender, "&6&l限时飞行 &7管理指令帮助");
        sendMessage(sender, "&8- &fset <玩家> <秒数> &7设置玩家的飞行时间 ");
        sendMessage(sender, "&8- &fadd <玩家> <秒数> &7添加玩家的飞行时间 ");
        sendMessage(sender, "&8- &fremove <玩家> <秒数> &7移除玩家的飞行时间 ");
        sendMessage(sender, "&8- &fget <玩家> &7查看玩家的飞行时间 ");
        sendMessage(sender, "&8- &fclear <玩家> &7清空玩家的飞行时间 ");
        sendMessage(sender, "&8- &fhelp &7查看此帮助 ");
        return true;
    }

    public static boolean noPerm(CommandSender sender) {
        sender.sendMessage(MessageParser.parseColor("&c抱歉！&7但您没有这么做的权限。"));
        return true;
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(MessageParser.parseColor(message));
    }

    public static void sendMessage(Player player, String message) {
        player.sendMessage(MessageParser.parseColor(message));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission(Permissions.ADMIN.toString())) return noPerm(sender);
        if (args.length > 3 || args.length < 2) return help(sender);
        String aim = args[0].toLowerCase();
        if (args.length == 2) {
            if (aim.equalsIgnoreCase("clear")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UserData targetData = DataManager.getData(target.getUniqueId());
                if (targetData.isFileLoaded() && targetData.getRemainTime() > 0) {
                    targetData.setTime(0);
                }
                DataManager.unloadData(target.getUniqueId());
                sendMessage(sender, "&f已清空玩家 " + target.getName() + " 的飞行时间。");
                return true;
            } else if (aim.equalsIgnoreCase("get")) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UserData targetData = DataManager.getData(target.getUniqueId());
                if (targetData.isFileLoaded() && targetData.getRemainTime() > 0) {
                    sendMessage(sender, "&f玩家" + target.getName() + " 的剩余飞行时间为 " + TimeFormat.getTimeString(targetData.getRemainTime()) + "。");
                } else {
                    sendMessage(sender, "&f玩家" + target.getName() + " 的剩余飞行时间为 " + TimeFormat.getTimeString(0) + "。");
                }
                DataManager.unloadData(target.getUniqueId());

                return true;
            } else {
                return help(sender);
            }

        } else if (args.length == 3) {
            if (aim.equalsIgnoreCase("add") || aim.equalsIgnoreCase("remove") || aim.equalsIgnoreCase("set")) {
                int value;
                try {
                    value = Integer.parseInt(args[2]);
                } catch (Exception ignore) {
                    sendMessage(sender, "请填写正确的数字。");
                    return true;
                }


                OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                UserData targetData = DataManager.getData(target.getUniqueId());

                if (aim.equalsIgnoreCase("add")) {
                    targetData.addTime(value);
                    sendMessage(sender, "成功为玩家 " + target.getName() + " 添加飞行时间 " + TimeFormat.getTimeString(value) + "。");
                } else if (aim.equalsIgnoreCase("remove")) {
                    targetData.removeTime(value);
                    sendMessage(sender, "成功为玩家 " + target.getName() + " 移除飞行时间 " + TimeFormat.getTimeString(value) + "。");
                } else if (aim.equalsIgnoreCase("set")) {
                    targetData.setTime(value);
                    sendMessage(sender, "成功设置玩家 " + target.getName() + " 的飞行时间 " + TimeFormat.getTimeString(value) + "。");
                }
                DataManager.unloadData(target.getUniqueId());
                return true;
            } else {
                return help(sender);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(Permissions.ADMIN.toString())) {
            return ImmutableList.of();
        }
        switch (args.length) {
            case 1: {
                List<String> completions = new ArrayList<>();
                List<String> strings = new ArrayList<>();
                strings.add("help");
                strings.add("set");
                strings.add("add");
                strings.add("remove");
                strings.add("clear");
                for (String s : strings) {
                    if (StringUtil.startsWithIgnoreCase(s, args[0].toLowerCase())) {
                        completions.add(s);
                    }
                }
                return completions;
            }
            case 2: {
                String aim = args[1];
                if (aim.equalsIgnoreCase("add")
                        || aim.equalsIgnoreCase("remove")
                        || aim.equalsIgnoreCase("set")
                        || aim.equalsIgnoreCase("clear")) {
                    List<String> completions = new ArrayList<>();
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (StringUtil.startsWithIgnoreCase(pl.getName(), args[1].toLowerCase())) {
                            completions.add(pl.getName());
                        }
                    }
                    return completions;
                }
            }
            default:
                return ImmutableList.of();
        }
    }

}
