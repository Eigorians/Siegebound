package com.eastcompany.siegebound.manager.kit.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.eastcompany.siegebound.SiegeboundPlugin;
import com.eastcompany.siegebound.manager.kit.ItemCreator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;

public class KitGui {

	private Map<UUID, List<JobData>> jobs = new HashMap<>();

	private File kitFile;
	private FileConfiguration kitConfig;
	private static final int MAX_JOBS = 54; // 最大職業数を10に設定（必要に応じて変更可能）

	public KitGui() {
		this.kitFile = new File(SiegeboundPlugin.getInstance().getDataFolder(), "kit.yml");
		if (!kitFile.exists()) {
			try {
				kitFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.kitConfig = YamlConfiguration.loadConfiguration(kitFile);
		loadAllJobsFromConfig();
	}

	public static class JobData {
		private String jobName;
		private ItemStack[] equipment;
		private int points;
		private Material icon;

		public JobData(String jobName, ItemStack[] equipment, int points, Material icon) {
			this.jobName = jobName;
			this.equipment = equipment;
			this.points = points;
			this.icon = icon;
		}

		public String getJobName() {
			return jobName;
		}

		public void setJobName(String jobName) {
			this.jobName = jobName;
		}

		public ItemStack[] getEquipment() {
			return equipment;
		}

		public void setEquipment(ItemStack[] equipment) {
			this.equipment = equipment;
		}

		public int getPoints() {
			return points;
		}

		public void setPoints(int points) {
			this.points = points;
		}

		public Material getIcon() {
			return icon;
		}

		public void setIcon(Material icon) {
			this.icon = icon;
		}

		public static JobData createDefault(String name) {
			ItemStack[] defaultEquip = new ItemStack[] {
					ItemCreator.setitem(Material.IRON_SWORD, "sword"),
					ItemCreator.setitem(Material.IRON_HELMET, "armor"),
					ItemCreator.setitem(Material.IRON_CHESTPLATE, "armor"),
					ItemCreator.setitem(Material.IRON_LEGGINGS, "armor"),
					ItemCreator.setitem(Material.IRON_BOOTS, "armor")
			};
			int defaultPoints = 20;
			Material defaultIcon = Material.IRON_SWORD;
			return new JobData(name, defaultEquip, defaultPoints, defaultIcon);
		}
	}

	// 新規作成（名前重複時に (1),(2) をつける）
	public void createJob(Player player) {
		UUID uuid = player.getUniqueId();
		jobs.putIfAbsent(uuid, new ArrayList<>());

		List<JobData> jobList = jobs.get(uuid);
		if (jobList.size() >= MAX_JOBS) {
			player.sendMessage(ChatColor.RED + "職業の最大数に達しました。新規作成できません。");
			return;
		}

		String baseName = "新規作成";
		String jobName = baseName;
		int suffix = 0;

		while (isJobNameExists(uuid, jobName)) {
			suffix++;
			jobName = baseName + " (" + suffix + ")";
		}

		JobData jobData = JobData.createDefault(jobName);
		jobList.add(jobData);
		saveJobsToConfig(uuid);

		player.sendMessage(ChatColor.GREEN + "職業「" + jobName + "」を新規作成しました。");
	}

	private boolean isJobNameExists(UUID uuid, String jobName) {
		List<JobData> jobList = jobs.get(uuid);
		if (jobList == null)
			return false;
		for (JobData job : jobList) {
			if (job.getJobName().equals(jobName)) {
				return true;
			}
		}
		return false;
	}

	public void openJobSelectionGui(Player player) {
		UUID uuid = player.getUniqueId();
		List<JobData> jobList = jobs.get(uuid);

		if (jobList == null || jobList.isEmpty()) {
			player.sendMessage(ChatColor.RED + "職業がありません。");
			return;
		}

		int size = ((jobList.size() - 1) / 9 + 1) * 9; // 9の倍数に切り上げ
		if (size > 54)
			size = 54; // 最大54スロットに制限

		Inventory gui = Bukkit.createInventory(null, size, Component.text("職業一覧", NamedTextColor.GREEN));

		int displayCount = Math.min(jobList.size(), 54);
		for (int i = 0; i < displayCount; i++) {
			JobData job = jobList.get(i);
			ItemStack item = new ItemStack(job.getIcon());
			ItemMeta meta = item.getItemMeta();
			meta.displayName(Component.text(job.getJobName(), NamedTextColor.YELLOW));
			item.setItemMeta(meta);

			gui.setItem(i, item);
		}

		player.openInventory(gui);
	}

	// 職業装備をロード
	public void loadJobInventory(Player player, String jobName) {
		UUID uuid = player.getUniqueId();
		List<JobData> jobList = jobs.get(uuid);

		if (jobList == null) {
			player.sendMessage(ChatColor.RED + "職業データが見つかりません。");
			return;
		}

		for (JobData job : jobList) {
			if (job.getJobName().equals(jobName)) {
				player.getInventory().clear();
				player.getInventory().setContents(job.getEquipment());
				player.sendMessage(ChatColor.AQUA + "職業「" + jobName + "」の装備をロードしました。");
				return;
			}
		}

		player.sendMessage(ChatColor.RED + "指定した職業が見つかりません。");
	}

	// 現在のインベントリを職業装備として保存（職業名で特定）
	public void saveJobInventory(Player player, String jobName) {
		UUID uuid = player.getUniqueId();
		List<JobData> jobList = jobs.get(uuid);

		if (jobList == null) {
			player.sendMessage(ChatColor.RED + "職業データが見つかりません。");
			return;
		}

		for (JobData job : jobList) {
			if (job.getJobName().equals(jobName)) {
				job.setEquipment(player.getInventory().getContents());
				saveJobsToConfig(uuid);
				player.sendMessage(ChatColor.GREEN + "職業「" + jobName + "」の装備を保存しました。");
				return;
			}
		}

		player.sendMessage(ChatColor.RED + "指定した職業が見つかりません。");
	}

	// ポイント設定
	public void setJobPoints(Player player, String jobName, int points) {
		UUID uuid = player.getUniqueId();
		List<JobData> jobList = jobs.get(uuid);

		if (jobList == null) {
			player.sendMessage(ChatColor.RED + "職業データが見つかりません。");
			return;
		}

		for (JobData job : jobList) {
			if (job.getJobName().equals(jobName)) {
				job.setPoints(points);
				saveJobsToConfig(uuid);
				player.sendMessage(ChatColor.GREEN + "職業「" + jobName + "」のポイントを " + points + " に設定しました。");
				return;
			}
		}

		player.sendMessage(ChatColor.RED + "指定した職業が見つかりません。");
	}

	// 名前変更
	public void renameJob(Player player, String oldName, String newName) {
		UUID uuid = player.getUniqueId();
		List<JobData> jobList = jobs.get(uuid);

		if (jobList == null) {
			player.sendMessage(ChatColor.RED + "職業データが見つかりません。");
			return;
		}

		if (isJobNameExists(uuid, newName)) {
			player.sendMessage(ChatColor.RED + "その名前はすでに使われています。");
			return;
		}

		for (JobData job : jobList) {
			if (job.getJobName().equals(oldName)) {
				job.setJobName(newName);
				saveJobsToConfig(uuid);
				player.sendMessage(ChatColor.GREEN + "職業名を「" + newName + "」に変更しました。");
				return;
			}
		}

		player.sendMessage(ChatColor.RED + "指定した職業が見つかりません。");
	}

	// アイコン設定
	public void setJobIcon(Player player, String jobName, Material icon) {
		UUID uuid = player.getUniqueId();
		List<JobData> jobList = jobs.get(uuid);

		if (jobList == null) {
			player.sendMessage(ChatColor.RED + "職業データが見つかりません。");
			return;
		}

		for (JobData job : jobList) {
			if (job.getJobName().equals(jobName)) {
				job.setIcon(icon);
				saveJobsToConfig(uuid);
				player.sendMessage(ChatColor.GREEN + "職業「" + jobName + "」のアイコンを設定しました。");
				return;
			}
		}

		player.sendMessage(ChatColor.RED + "指定した職業が見つかりません。");
	}

	// Configに複数職業を保存
	// 保存処理
	private void saveJobsToConfig(UUID uuid) {
		List<JobData> jobList = jobs.get(uuid);
		if (jobList == null)
			return;

		String basePath = uuid.toString();
		kitConfig.set(basePath, null); // 一旦クリア

		for (int i = 0; i < jobList.size(); i++) {
			JobData job = jobList.get(i);
			String path = basePath + "." + i;
			kitConfig.set(path + ".jobName", job.getJobName());

			// equipmentを番号付きで保存
			ItemStack[] equipment = job.getEquipment();
			for (int j = 0; j < equipment.length; j++) {
				kitConfig.set(path + ".equipment." + j, equipment[j]);
			}

			kitConfig.set(path + ".points", job.getPoints());
			kitConfig.set(path + ".icon", job.getIcon().name());
		}

		try {
			kitConfig.save(kitFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 読み込み処理
	private void loadAllJobsFromConfig() {
		for (String key : kitConfig.getKeys(false)) {
			UUID uuid = UUID.fromString(key);
			List<JobData> jobList = new ArrayList<>();
			for (String indexKey : kitConfig.getConfigurationSection(key).getKeys(false)) {
				String path = key + "." + indexKey;
				String jobName = kitConfig.getString(path + ".jobName");

				ItemStack[] equipment = new ItemStack[36];
				for (int i = 0; i < 36; i++) {
					equipment[i] = kitConfig.getItemStack(path + ".equipment." + i);
				}

				int points = kitConfig.getInt(path + ".points", 20);
				Material icon = Material.getMaterial(kitConfig.getString(path + ".icon", "IRON_SWORD"));
				jobList.add(new JobData(jobName, equipment, points, icon));
			}
			jobs.put(uuid, jobList);
		}
	}

	public Map<UUID, List<JobData>> getJobs() {
		return jobs;
	}

}
