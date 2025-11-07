package com.eastcompany.siegebound.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class DropEvents implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		if (SiegeboundPlugin.getSiegeManager() != null) {
			if (SiegeboundPlugin.getSiegeManager().getPlayerManager().contains(event.getPlayer())) {
				event.setCancelled(true);
			}
		}
	}
}
