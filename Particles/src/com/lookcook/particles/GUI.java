package com.lookcook.particles;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI {

	public static GUI instance = new GUI();
	
	public static GUI getInstance() {
		return instance;
	}
	
	@SuppressWarnings("deprecation")
	public Inventory getMainMenu(Player player) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Particles");
		
		ItemStack wool = null;
		if (ParticlesMain.getInstance().getFavoriteColor(player) == null) {
			wool = rename(new ItemStack(Material.IRON_FENCE), ChatColor.ITALIC + "Favorite Color");
		} else {
			wool = rename(new ItemStack(Material.WOOL, 1, ParticlesMain.getInstance().getFavoriteColor(player).getData()), ChatColor.ITALIC + "Favorite Color");
		}
		ItemStack orbs = rename(new ItemStack(Material.COOKIE), ChatColor.AQUA + "Orbs");
		ItemStack snakes = rename(new ItemStack(Material.STICK), ChatColor.GREEN + "Snakes");
		ItemStack hats = rename(new ItemStack(Material.LEATHER_HELMET), ChatColor.RED + "Hats");
		ItemStack banners = rename(new ItemStack(Material.BANNER), ChatColor.YELLOW + "Banners");
		ItemStack tornados = rename(new ItemStack(Material.SULPHUR), ChatColor.WHITE + "Tornados");
		ItemStack pulses = rename(new ItemStack(Material.REDSTONE), ChatColor.DARK_RED + "Pulses");
		ItemStack halos = rename(new ItemStack(Material.DOUBLE_PLANT), ChatColor.GOLD + "Halos");
		ItemStack clearParticles = rename(new ItemStack(Material.MILK_BUCKET), ChatColor.BOLD + "Clear Particles");
		
		for (int i = 0; i < inv.getSize(); i++) {
			inv.setItem(i, getGlassPane(player));
		}
		inv.setItem(1, orbs);
		inv.setItem(3, snakes);
		inv.setItem(5, hats);
		inv.setItem(7, banners);
		inv.setItem(11, tornados);
		inv.setItem(13, pulses);
		inv.setItem(15, halos);
		inv.setItem(21, wool);
		inv.setItem(23, clearParticles);
		
		return inv;
	}
	
	@SuppressWarnings("deprecation")
	public void openColors(Player player, String name) {
		Inventory inv = Bukkit.createInventory(null, 27, name);
		ItemStack back = rename(new ItemStack(Material.BED), ChatColor.RED + "Back");
		for (DyeColor color : DyeColor.values()) {
			String itemName = color.toString().substring(0, 1).toUpperCase() + color.toString().substring(1, color.toString().length()).toLowerCase();
			ItemStack wool = rename(new ItemStack(Material.WOOL, 1, color.getData()), itemName.replace("_", " "));
			inv.addItem(wool);
		}
		for (int i = 18; i < 27; i++) {
			inv.setItem(i, getGlassPane(player));
		}
		for (ItemStack item : inv.getContents()) {
			if (item != null) {
				if (item.getType().equals(Material.WOOL)) {
					DyeColor color = DyeColor.getByData((byte) item.getDurability());
					ArrayList<String> lore = new ArrayList<String>();
					if (player.hasPermission(ChatColor.stripColor("particles." + name.replace(" ", "").toLowerCase() + "." + color.toString().toLowerCase()))
							|| player.hasPermission(ChatColor.stripColor("particles." + name.replace(" ", "").toLowerCase() + ".*"))
							|| player.hasPermission("particles.*") || player.isOp()) {
						lore.add(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("YouHavePermissionLore")));
					} else {
						lore.add(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("NoPermissionLore")));
					}
					ItemMeta meta = item.getItemMeta();
					meta.setLore(lore);
					item.setItemMeta(meta);
				}
			}
		}
		inv.setItem(26, back);
		player.openInventory(inv);
	}
	
	@SuppressWarnings("deprecation")
	public Inventory favColourStats(OfflinePlayer player) {
		Inventory inv = Bukkit.createInventory(null, 27, ChatColor.ITALIC + "Favorite Colour");
		ItemStack back = rename(new ItemStack(Material.BED), ChatColor.RED + "Back");
		String string = "";
		for (String string2 : ParticlesMain.getInstance().favColor) {
			if (string2.startsWith(player.getUniqueId() + "☾")) {
				string = string2;
			}
		}
		String[] firstList = string.split("☾");
		String[] colorList = firstList[1].split(",");
		HashMap<String, Integer> count = new HashMap<String, Integer>();
		for (String colorString : colorList) {
			if (count.keySet().contains(colorString)) {
				int numb = count.get(colorString);
				count.remove(colorString);
				count.put(colorString, numb+1);
				} else {
				count.put(colorString, 1);
			}
		}
		for (String colorString : count.keySet()) {
			DyeColor dyeColor = DyeColor.valueOf(colorString);
			String itemName = dyeColor.toString().substring(0, 1).toUpperCase() + dyeColor.toString().substring(1, dyeColor.toString().length()).toLowerCase();
			ItemStack wool = rename(new ItemStack(Material.WOOL, 1, dyeColor.getData()), itemName.replace("_", " "));
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GREEN + "You chose this colour " + ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + count.get(colorString) + ChatColor.GREEN + " times");
			ItemMeta meta = wool.getItemMeta();
			meta.setLore(lore);
			wool.setItemMeta(meta);
			inv.addItem(wool);
		}
		for (int i = 18; i < 27; i++) {
			inv.setItem(i, getGlassPane(player));
		}
		inv.setItem(22, rename(new ItemStack(Material.WOOL, 1, ParticlesMain.getInstance().getFavoriteColor(player).getData()), ChatColor.BOLD + player.getName() + "'s Favorite Colour"));
		inv.setItem(26, back);
		return inv;
	}
	
	public ItemStack rename(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public ItemStack getGlassPane(OfflinePlayer player) {
		ItemStack pane = null;
		if (ParticlesMain.getInstance().getFavoriteColor(player) == null) {
			pane = rename(new ItemStack(Material.STAINED_GLASS_PANE), " ");
		} else {
			pane = rename(new ItemStack(Material.STAINED_GLASS_PANE, 1, ParticlesMain.getInstance().getFavoriteColor(player).getData()), " ");
		}
		return pane;
	}
}
