package cc.carm.plugin.timeforflight.commands;

import cc.carm.plugin.timeforflight.models.UserData;
import cc.carm.plugin.timeforflight.utils.MessageParser;
import cc.carm.plugin.timeforflight.enums.Permissions;
import cc.carm.plugin.timeforflight.managers.DataManager;
import cc.carm.plugin.timeforflight.utils.TimeFormat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 开关飞行的指令
 */
public class ToggleFlyCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageParser.parseColor("该指令只允许玩家使用。"));
            sender.sendMessage(MessageParser.parseColor("您可以输入 /togglefly <玩家名> 设置某个玩家的飞行状态。"));
            return true;
        }

        Player player = (Player) sender;

        UserData userData = DataManager.getData(player.getUniqueId());

        if (args.length == 0 || !sender.isOp()) {
            if (player.getAllowFlight()) {
                // 在玩家开启飞行的状态之下
                if (player.hasPermission(Permissions.ALLOW_FLIGHT.toString()) || player.hasPermission(Permissions.UNLIMITED.toString())) {
                    userData.stopFly(player);
                    player.sendMessage(MessageParser.parseColor("§f已为您关闭飞行状态，剩余飞行时长 " + userData.getRemainTimeString() + " 。"));
                    return true;
                } else {
                    sender.sendMessage(MessageParser.parseColor("&c抱歉！&f但您没有使用该指令的权限。"));
                }
            } else {
                //在玩家没有开启飞行的状态下
                if (player.hasPermission(Permissions.UNLIMITED.toString())) {
                    player.setAllowFlight(true);
                    player.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
                    return true;
                } else if (player.hasPermission(Permissions.ALLOW_FLIGHT.toString())) {
                    if (userData.canFly()) {
                        userData.startFlyTask(player);
                        player.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
                        player.sendMessage(MessageParser.parseColor("&f剩余飞行时长 &a" + TimeFormat.getTimeString(userData.getRemainTime())));
                        return true;
                    } else {
                        player.sendMessage(MessageParser.parseColor("&c您的飞行时长不足，无法开启飞行。"));
                        return true;
                    }
                } else {
                    player.sendMessage(MessageParser.parseColor("&c抱歉！&f但您没有使用该指令的权限。"));
                    return true;
                }
            }


        } else if (args.length == 1) {
            if (!sender.isOp()) {
                sender.sendMessage(MessageParser.parseColor("&c抱歉！&f但您没有使用该指令的权限。"));
                return true;
            }


            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage("玩家 " + args[0] + " 不在线。");
                return true;
            }
            UserData targetData = DataManager.getData(target.getUniqueId());

            if (target.getAllowFlight()) {
                //在目标玩家正在飞行的状态下。
                if (target.hasPermission(Permissions.ALLOW_FLIGHT.toString()) || target.hasPermission(Permissions.UNLIMITED.toString())) {
                    userData.stopFly(target);
                    target.sendMessage(MessageParser.parseColor("§f已为您关闭飞行状态，剩余飞行时长 " + userData.getRemainTimeString() + " 。"));
                    return true;
                } else {
                    sender.sendMessage(MessageParser.parseColor("&c抱歉！&f但您没有使用该指令的权限。"));
                }
            } else {
                //在目标玩家没有飞行的状态下
                if (target.hasPermission(Permissions.UNLIMITED.toString())) {

                    target.setAllowFlight(true);
                    target.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
                    sender.sendMessage(MessageParser.parseColor("已为玩家 " + target.getName() + " 开启飞行。"));
                    return true;
                } else if (target.hasPermission(Permissions.ALLOW_FLIGHT.toString())) {
                    if (targetData.canFly()) {
                        targetData.startFlyTask(target);
                        target.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
                        target.sendMessage(MessageParser.parseColor("&f剩余飞行时长 &a" + TimeFormat.getTimeString(userData.getRemainTime())));
                        sender.sendMessage(MessageParser.parseColor("已为玩家 " + target.getName() + " 开启飞行。"));

                        return true;
                    } else {
                        target.sendMessage(MessageParser.parseColor("&c您的飞行时长不足，无法开启飞行。"));
                        sender.sendMessage(MessageParser.parseColor("玩家 " + target.getName() + " 飞行时间不足。"));
                        return true;
                    }
                } else {
                    sender.sendMessage(MessageParser.parseColor("玩家 " + target.getName() + " 没有飞行权限。"));
                    return true;
                }
            }

        }


        return true;
    }


}
