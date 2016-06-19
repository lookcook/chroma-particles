package com.lookcook.particles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ParticlesMain extends JavaPlugin{

	public HashMap<Player, Color> orbs = new HashMap<Player, Color>();
	public HashMap<Player, Color> snakes = new HashMap<Player, Color>();
	public HashMap<Player, Color> hats = new HashMap<Player, Color>();
	public HashMap<Player, Color> banners = new HashMap<Player, Color>();
	public HashMap<Player, Color> tornados = new HashMap<Player, Color>();
	public HashMap<Player, Color> halos = new HashMap<Player, Color>();
	public HashMap<Player, Color> pulses = new HashMap<Player, Color>();
	public ArrayList<String> favColor = new ArrayList<String>();
	public static ParticlesMain instance;
	
	public static ParticlesMain getInstance() {
		return instance;
	}
	
	public void onDisable() {
		save(favColor, new File(getDataFolder(), "favColor.dat"));
		instance = null;
	}
	
	@SuppressWarnings("unchecked")
	public void onEnable() {
		new SoundAPI();
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new ClickInventory(), instance);
		File dir = getDataFolder();
		if (!dir.exists()) {
			if (!dir.mkdir()) {
				System.out.println("Could Not Create Directory");
			}
		}
		favColor = (ArrayList<String>) load(new File(getDataFolder(), "favColor.dat"));

		if (favColor == null) {
			favColor = new ArrayList<String>();
		}
		
		new File(getDataFolder(), "favColor.dat").delete();
		if (!this.getConfig().contains("tophatInReplacementOfNormalHat")) {
			this.getConfig().addDefault("tophatInReplacementOfNormalHat", false);
		}
		if (!this.getConfig().contains("NoPermission")) {
			this.getConfig().addDefault("NoPermission", "&d&lParticles>> &cNo Permission.");
		}
		if (!this.getConfig().contains("ClearParticles")) {
			this.getConfig().addDefault("ClearParticles", "&d&lParticles>> &6Cleared all particles.");
		}
		if (!this.getConfig().contains("YouHavePermissionLore")) {
			this.getConfig().addDefault("YouHavePermissionLore", "&aYou have permission to use this particle!");
		}
		if (!this.getConfig().contains("NoPermissionLore")) {
			this.getConfig().addDefault("NoPermissionLore", "&cYou don't have permission to use this particle.");
		}
		if (!this.getConfig().contains("YouDontHaveAFavColour")) {
			this.getConfig().addDefault("YouDontHaveAFavColour", "&d&lParticles>> &cYou don't have a favorite colour yet!");
		}
		if (!this.getConfig().contains("UsageForFavoriteColour")) {
			this.getConfig().addDefault("UsageForFavoriteColour", "&d&lParticles>> &cUsage: /favoritecolour [player]");
		}
		if (!this.getConfig().contains("PlayerNeverJoinedServer")) {
			this.getConfig().addDefault("PlayerNeverJoinedServer", "&d&lParticles>> &cPlayer never joined server!");
		}
		if (!this.getConfig().contains("PlayerHasNoFavColour")) {
			this.getConfig().addDefault("PlayerHasNoFavColour", "&d&lParticles>> &cPlayer has no favorite colour!");
		}
		this.getConfig().options().copyDefaults(true);
		this.saveConfig();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("particles")) {
			if (sender instanceof Player) {
				((Player) sender).playSound(((Player) sender).getLocation(), SoundAPI.BLOCK_CHEST_OPEN, 1, 1);
				((Player) sender).openInventory(GUI.getInstance().getMainMenu((Player) sender));
			}
		}
		if (cmd.getName().equalsIgnoreCase("favoritecolour")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (!player.hasPermission("particles.checkfavoritecolour") && !player.hasPermission("particles.*") && !player.isOp()) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("NoPermission")));
					return false;
				}
				if (args.length == 0) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("UsageForFavoriteColour")));
				}
				if (args.length == 1) {
					int i = 0;
					for (OfflinePlayer oP : Bukkit.getOfflinePlayers()) {
						if (oP.getName().equalsIgnoreCase(args[0])) {
							if (getFavoriteColor(oP) == null) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("PlayerHasNoFavColour")));
								return false;
							}
							i++;
							player.openInventory(GUI.getInstance().favColourStats(oP));
						}
					}
					if (i == 0) {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("PlayerNeverJoinedServer")));
					}
				}
			}
		}
		return false;
	}
	
	public void clearParticles(Player player) {
		if (orbs.keySet().contains(player)) {
			orbs.remove(player);
		}
		if (snakes.keySet().contains(player)) {
			snakes.remove(player);
		}
		if (hats.keySet().contains(player)) {
			hats.remove(player);
		}
		if (banners.keySet().contains(player)) {
			banners.remove(player);
		}
		if (tornados.keySet().contains(player)) {
			tornados.remove(player);
		}
		if (halos.keySet().contains(player)) {
			halos.remove(player);
		}
		if (pulses.keySet().contains(player)) {
			pulses.remove(player);
		}
	}
	
	public void addColor(Player player, DyeColor color) {
		String remove = "";
		for (String string : favColor) {
			if (string.startsWith(player.getUniqueId() + "☾")) {
				remove = string;
			}
		}
		if (remove.equals("")) {
			favColor.add(player.getUniqueId() + "☾" + color.toString());
		} else {
			favColor.remove(remove);
			favColor.add(remove + "," + color.toString());
		}
	}
	
	public DyeColor getFavoriteColor(OfflinePlayer player) {
		String string = "";
		int x = 0;
		for (String string2 : ParticlesMain.getInstance().favColor) {
			if (string2.startsWith(player.getUniqueId() + "☾")) {
				string = string2;
				x++;
			}
		}
		if (x == 0) {
			return null;
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
		DyeColor color = null;
		int i = 0;
		for (String countString : count.keySet()) {
			if (count.get(countString) > i) {
				i = count.get(countString);
				color = DyeColor.valueOf(countString);
			}
		}
		return color;
	}
	public void save(Object o, File f) {
		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			oos.writeObject(o);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object load(File f) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			Object result = ois.readObject();
			ois.close();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}
