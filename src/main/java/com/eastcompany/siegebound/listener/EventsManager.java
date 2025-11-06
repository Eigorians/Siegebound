package com.eastcompany.siegebound.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class EventsManager {

	List<Listener> events = new ArrayList<Listener>();

	public void loadEvents() {

		SiegeboundPlugin main = SiegeboundPlugin.getInstance();

		BlockDisplayListener blockDisplayClickListener = new BlockDisplayListener();

		main.getServer().getPluginManager().registerEvents(blockDisplayClickListener, main);
		events.add(blockDisplayClickListener);
	}

	public void unregisterEvents() {
		for (Listener listener : events) {
			HandlerList.unregisterAll(listener);
		}
		events.clear();
	}
}
