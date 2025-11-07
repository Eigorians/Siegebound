package com.eastcompany.siegebound.listener;

import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.kit.gui.KitGui;
import com.eastcompany.siegebound.manager.kit.gui.KitGui.JobData;

import net.md_5.bungee.api.ChatColor;

// KitGuiのインスタンスを受け取るコンストラクタを用意
public class KitClickGUI implements Listener {
	private KitGui kitGui;

	public KitClickGUI() {
		this.kitGui = SiegeboundPlugin.getGUI();
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player))
			return;
		Player player = (Player) event.getWhoClicked();

		if (!event.getView().getTitle().equals(ChatColor.GREEN + "職業一覧"))
			return;

		event.setCancelled(true);

		ItemStack clicked = event.getCurrentItem();
		if (clicked == null || !clicked.hasItemMeta())
			return;

		String clickedJobName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

		UUID uuid = player.getUniqueId();
		List<JobData> jobList = kitGui.getJobs().get(uuid);

		if (jobList == null)
			return;

		for (JobData job : jobList) {
			if (job.getJobName().equals(clickedJobName)) {
				// 装備ロードなどの処理
				kitGui.loadJobInventory(player, clickedJobName);
				player.closeInventory();
				break;
			}
		}
	}
}
