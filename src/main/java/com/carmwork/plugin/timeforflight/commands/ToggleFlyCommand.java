package com.carmwork.plugin.timeforflight.commands;

import com.carmwork.plugin.timeforflight.managers.DataManager;
import com.carmwork.plugin.timeforflight.models.UserData;
import com.carmwork.plugin.timeforflight.utils.MessageParser;
import com.carmwork.plugin.timeforflight.utils.TimeFormat;
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
		if (args.length == 0 || !sender.isOp()) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(MessageParser.parseColor("该指令只允许玩家使用。"));
				sender.sendMessage(MessageParser.parseColor("您可以输入 /togglefly <玩家名> 设置某个玩家的飞行状态。"));
				return true;
			}

			Player player = (Player) sender;

			UserData userData = DataManager.getData(player.getUniqueId());
			if (player.hasPermission("timeforflight.unlimited")) {

				player.setAllowFlight(true);
				player.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
				return true;
			} else if (player.hasPermission("timeforflight.allowflight")) {
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
		} else if (args.length == 1) {
			if (!sender.isOp()) {
				sender.sendMessage(MessageParser.parseColor("&c抱歉！&f但您没有使用该指令的权限。"));
				return true;
			}
			Player player = Bukkit.getPlayer(args[0]);
			if (player == null) {
				sender.sendMessage("玩家 " + args[0] + " 不在线。");
				return true;
			}

			UserData userData = DataManager.getData(player.getUniqueId());
			if (player.hasPermission("timeforflight.unlimited")) {

				player.setAllowFlight(true);
				player.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
				sender.sendMessage(MessageParser.parseColor("已为玩家 " + player.getName() + " 开启飞行。"));
				return true;
			} else if (player.hasPermission("timeforflight.allowflight")) {
				if (userData.canFly()) {
					userData.startFlyTask(player);
					player.sendMessage(MessageParser.parseColor("&f您现在可以飞行了！"));
					player.sendMessage(MessageParser.parseColor("&f剩余飞行时长 &a" + TimeFormat.getTimeString(userData.getRemainTime())));
					sender.sendMessage(MessageParser.parseColor("已为玩家 " + player.getName() + " 开启飞行。"));

					return true;
				} else {
					player.sendMessage(MessageParser.parseColor("&c您的飞行时长不足，无法开启飞行。"));
					sender.sendMessage(MessageParser.parseColor("玩家 " + player.getName() + " 飞行时间不足。"));
					return true;
				}
			} else {
				sender.sendMessage(MessageParser.parseColor("玩家 " + player.getName() + " 没有飞行权限。"));
				return true;
			}


		}
		return true;
	}


}
