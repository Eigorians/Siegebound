package com.eastcompany.siegebound.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import com.eastcompany.siegebound.SiegeboundPlugin;

public class EventsManager {

	List<Listener> events = new ArrayList<Listener>();

	public void loadEvents() {
		register(new BlockDisplayListener());
		register(new DropEvents());
		register(new KitClickGUI());
	}

	private void register(Listener listener) {
		SiegeboundPlugin main = SiegeboundPlugin.getInstance();
		main.getServer().getPluginManager().registerEvents(listener, main);
		events.add(listener);
	}

	public void unregisterEvents() {
		for (Listener listener : events) {
			HandlerList.unregisterAll(listener);
		}
		events.clear();
	}
}
