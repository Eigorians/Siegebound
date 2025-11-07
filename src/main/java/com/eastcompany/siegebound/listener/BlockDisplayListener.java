package com.eastcompany.siegebound.listener;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.ui.TextDisplayInstance;
import com.eastcompany.siegebound.manager.ui.TextDisplayManager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BlockDisplayListener implements Listener {

	/** 右クリック判定 */
	@EventHandler
	public void onPlayerInteract(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		Entity clicked = event.getRightClicked();
		if (!(clicked instanceof ArmorStand))
			return;

		if (handleClick(player, clicked, ClickType.RIGHT)) {
			event.setCancelled(true);
		}
	}

	/** 左クリック判定 */
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player player))
			return;
		if (!(event.getEntity() instanceof ArmorStand clicked))
			return;

		if (handleClick(player, clicked, ClickType.LEFT)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof ArmorStand clicked))
			return;

		if (TextDisplayManager.isHitbox(clicked)) {
			event.setCancelled(true);
		}
	}

	private boolean handleClick(Player player, Entity clicked, ClickType type) {
		TextDisplayInstance instance = TextDisplayManager.getDisplayFromArmorStand(player, clicked);
		if (instance == null)
			return false;

		// クリックされた ArmorStand から baseId を取得
		String baseId = TextDisplayManager.getHitboxId(clicked);
		if (baseId == null)
			return false;

		// ---- クリック動作 ----
		if (baseId.startsWith("ready")) {
			boolean b;
			if (type == ClickType.RIGHT) {
				player.playSound(
						player.getLocation(),
						Sound.UI_BUTTON_CLICK,
						1f,
						1f);
				b = SiegeboundPlugin.getSiegeManager().getStartingManager().addReady(player);
				if (b) {
					instance.rename(Component.text("[準備完了！]", NamedTextColor.GREEN));
					instance.setItem(new ItemStack(Material.GLOW_ITEM_FRAME));
				}
			} else {
				player.playSound(
						player.getLocation(),
						Sound.BLOCK_LEVER_CLICK,
						1f,
						0.8f);
				b = SiegeboundPlugin.getSiegeManager().getStartingManager().reduce(player);
				if (b) {
					instance.rename(Component.text("[右クリックで準備完了！]", NamedTextColor.YELLOW));
					instance.setItem(new ItemStack(Material.ITEM_FRAME));
				}
			}
		} else if (baseId.startsWith("kitselector")) {

			SiegeboundPlugin.getGUI().openJobSelectionGui(player);

		} else if (baseId.startsWith("kitcreator")) {

			SiegeboundPlugin.getGUI().createJob(player);
		}
		if (!SiegeboundPlugin.debug) {
			player.sendMessage("Debug クリックされたディスプレイ: " + baseId + "（" + type.name() + "クリック）");
		}
		return true;
	}

	/** クリックの種類を区別するための簡易enum */
	private enum ClickType {
		LEFT, RIGHT

	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		TextDisplayManager.updateDisplay(player);
	}
}
