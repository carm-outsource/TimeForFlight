package com.carmwork.plugin.timeforflight.listeners;

import com.carmwork.plugin.timeforflight.managers.DataManager;
import com.carmwork.plugin.timeforflight.models.UserData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		UserData playerCache = DataManager.loadData(player.getUniqueId());
		if (player.hasPermission("timeforflight.getflighttime")
				&& !player.hasPermission("timeforflight.unlimited")) {
			playerCache.startGivenTask(player);
		}

	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		UserData playerCache = DataManager.getData(player.getUniqueId());

		player.setAllowFlight(false);
		player.setFlying(false);


		DataManager.unloadData(player.getUniqueId());
	}
}
