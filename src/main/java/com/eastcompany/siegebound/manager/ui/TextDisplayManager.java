package com.eastcompany.siegebound.manager.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.eastcompany.siegebound.manager.SiegeManager;

import net.kyori.adventure.text.Component;

public class TextDisplayManager {

	private static final Map<DisplayKey, TextDisplayInstance> displays = new HashMap<>();

	private static Map<UUID, String> clickbox = new HashMap<UUID, String>();

	public static void adddisplaylocation(String string, Location location) {

		World world = location.getWorld();

		ArmorStand clickHitbox = world.spawn(location.clone().add(0, -0.5, 0), ArmorStand.class, stand -> {

			stand.setPersistent(false);
			stand.setGravity(false);
			stand.setInvisible(true);

		});
		SiegeManager.addEntity(clickHitbox);

		clickbox.put(clickHitbox.getUniqueId(), string);
	}

	public static TextDisplayInstance create(String id, Player player, Location location, Component message,
			ItemStack itemStack) {

		DisplayKey key = new DisplayKey(id, player.getUniqueId());

		// 既に同じIDが存在する場合は削除して置き換え
		if (displays.containsKey(key)) {
			displays.get(key).remove();
		}
		TextDisplayInstance display = new TextDisplayInstance(player, location, message, itemStack);
		displays.put(key, display);

		return display;
	}

	public static TextDisplayInstance getDisplayFromArmorStand(Player player, Entity entity) {
		String id = clickbox.get(entity.getUniqueId());
		if (id == null)
			return null;
		return displays.get(new DisplayKey(id, player.getUniqueId()));
	}

	public static TextDisplayInstance getdisplay(String id, Player player) {
		return displays.get(new DisplayKey(id, player.getUniqueId()));
	}

	public static void removeByID(String id) {
		// 消す対象をまず全部集める
		List<DisplayKey> keysToRemove = new ArrayList<>();

		for (Entry<DisplayKey, TextDisplayInstance> entry : displays.entrySet()) {
			if (entry.getKey().getText().equals(id)) {
				// 表示本体を消す処理があるなら先に呼んでおく
				entry.getValue().remove();
				keysToRemove.add(entry.getKey());
			}
		}

		// まとめて削除
		for (DisplayKey key : keysToRemove) {
			displays.remove(key);
		}
	}

	public static void updateDisplay(Player player) {
		for (Entry<DisplayKey, TextDisplayInstance> map : displays.entrySet()) {
			map.getValue().hideFromAllExcept();
		}
	}

	/** 全削除 */
	public static void removeAll() {
		for (TextDisplayInstance display : displays.values()) {
			display.remove();
		}
		displays.clear();
		for (UUID uuid : clickbox.keySet()) {
			Entity entity = Bukkit.getEntity(uuid);
			if (entity != null) {
				entity.remove();
			}
		}
		clickbox.clear();
	}

	public static boolean isHitbox(Entity entity) {
		return clickbox.containsKey(entity.getUniqueId());
	}

	public static String getHitboxId(Entity clicked) {
		return clickbox.get(clicked.getUniqueId());
	}

}