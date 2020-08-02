package com.carmwork.plugin.timeforflight.commands;

import com.carmwork.plugin.timeforflight.utils.MessageParser;
import com.google.common.collect.ImmutableList;
import org.bukkit.Bukkit;
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

	/*
	 * - set <player> <second> 设置玩家剩余的飞行时间
	 * - add <player> <second> 为玩家添加飞行时间
	 * - remove <player> <second>  移除玩家的飞行时间
	 * - clear <player>  情况玩家的飞行时间
	 *
	 * - help 查看此帮助
	 */

	public static boolean help(CommandSender sender) {
		sendMessage(sender, "&6&l限时飞行 &7管理指令帮助");
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
		if (!sender.hasPermission("timeforflight.admin")) return noPerm(sender);
		if (args.length > 3 || args.length < 1) return help(sender);


		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if (!sender.hasPermission("timeforflight.admin")) {
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
