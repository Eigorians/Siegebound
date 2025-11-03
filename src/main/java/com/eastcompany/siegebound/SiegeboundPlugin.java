package com.eastcompany.siegebound;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.text.Component;

public class SiegeboundPlugin extends JavaPlugin{
	
	
	@Override
	public void onEnable() {
		
		
		
		
		Bukkit.broadcast(Component.text("Hello mother Fuker"));
	}

}
