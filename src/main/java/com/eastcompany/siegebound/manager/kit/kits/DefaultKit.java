package com.eastcompany.siegebound.manager.kit.kits;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.eastcompany.siegebound.manager.kit.KitItem;

import net.kyori.adventure.text.format.NamedTextColor;

public class DefaultKit {

	private final String name;
	private final List<KitItem> kits;
	private final NamedTextColor namedTextColor;

	public DefaultKit(String name, List<KitItem> kits, NamedTextColor namedTextColor) {
		this.name = name;
		this.kits = kits;
		this.namedTextColor = namedTextColor;
	}

	public List<KitItem> getKits() {
		return kits;
	}

	public String getName() {
		return name;
	}

	public NamedTextColor getColor() {
		return namedTextColor;
	}

	public KitItem getKitForPlayer(Player player) {
		if (kits == null || kits.isEmpty()) {
			return null;
		}

		ItemStack[] contents = player.getInventory().getContents();
		for (ItemStack stack : contents) {
			if (stack == null)
				continue;

			Material mat = stack.getType();
			for (KitItem kit : kits) {
				if (kit.getMaterial() == mat)
					return kit;
			}
		}

		return kits.get(0);
	}
}
