package com.lookcook.particles;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickInventory implements Listener{

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		ParticlesMain.getInstance().clearParticles(event.getPlayer());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInvClick(InventoryClickEvent event) {
		Inventory inv = event.getInventory();
		Player player = (Player) event.getWhoClicked();
		ItemStack item = event.getCurrentItem();
		
		if (item == null) {
			return;
		}
		if (inv == null) {
			return;
		}
		if (inv.getName() == null) {
			return;
		}
		if (inv.equals(player.getInventory())) {
			return;
		}
		if (inv.getName().equals(ChatColor.ITALIC + "Favorite Colour")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
		}
		if (inv.getName().equals(GUI.getInstance().getMainMenu(player).getName())) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS) || item.getType().equals(Material.AIR)) {
				return;
			}
			if (item.getType().equals(Material.WOOL) || item.getType().equals(Material.IRON_FENCE)) {
				for (String string : ParticlesMain.getInstance().favColor) {
					if (string.startsWith(player.getUniqueId() + "☾")) {
						player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
						player.openInventory(GUI.getInstance().favColourStats(player));
						return;
					}
				}
				player.closeInventory();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("YouDontHaveAFavColour")));
				return;
			}
			if (item.getType().equals(Material.MILK_BUCKET)) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',ParticlesMain.getInstance().getConfig().getString("ClearParticles")));
				ParticlesMain.getInstance().clearParticles(player);
				player.playSound(player.getLocation(), SoundAPI.ENTITY_GENERIC_DRINK, 1, 1);
				return;
			}
			player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
			GUI.getInstance().openColors(player, item.getItemMeta().getDisplayName().substring(0, item.getItemMeta().getDisplayName().length()-1) + " Colours");
		}
		if (inv.getName().equals(ChatColor.GREEN + "Snake Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().snakes.keySet().contains(player)) {
					if (ParticlesMain.getInstance().snakes.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().snakes.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				Effects.getInstance().snake(player, color);
				player.closeInventory();
			}
		}
		if (inv.getName().equals(ChatColor.AQUA + "Orb Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().orbs.keySet().contains(player)) {
					if (ParticlesMain.getInstance().orbs.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().orbs.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				Effects.getInstance().orb(player, color);
				player.closeInventory();
			}
		}
		if (inv.getName().equals(ChatColor.RED + "Hat Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().hats.keySet().contains(player)) {
					if (ParticlesMain.getInstance().hats.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().hats.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				if (ParticlesMain.getInstance().getConfig().getBoolean("tophatInReplacementOfNormalHat")) {
					Effects.getInstance().tophat(player, color);
				} else {
					Effects.getInstance().hat(player, color);
				}
				player.closeInventory();
			}
		}
		if (inv.getName().equals(ChatColor.YELLOW + "Banner Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().banners.keySet().contains(player)) {
					if (ParticlesMain.getInstance().banners.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().banners.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				Effects.getInstance().banner(player, color);
				player.closeInventory();
			}
		}
		if (inv.getName().equals(ChatColor.WHITE + "Tornado Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().tornados.keySet().contains(player)) {
					if (ParticlesMain.getInstance().tornados.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().tornados.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				Effects.getInstance().tornado(player, color);
				player.closeInventory();
			}
		}
		if (inv.getName().equals(ChatColor.GOLD + "Halo Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().halos.keySet().contains(player)) {
					if (ParticlesMain.getInstance().halos.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().halos.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				Effects.getInstance().halo(player, color);
				player.closeInventory();
			}
		}
		if (inv.getName().equals(ChatColor.DARK_RED + "Pulse Colours")) {
			event.setCancelled(true);
			if (item.getType().equals(Material.STAINED_GLASS_PANE) || item.getType().equals(Material.THIN_GLASS)) {
				return;
			}
			if (item.getType().equals(Material.BED)) {
				player.playSound(player.getLocation(), SoundAPI.BLOCK_NOTE_PLING, 1, 1);
				player.openInventory(GUI.getInstance().getMainMenu(player));
			}
			if (item.getType().equals(Material.WOOL)) {
				if (!HasPermission(item, player)) {
					return;
				}
				DyeColor dyeColor = DyeColor.getByData((byte) item.getDurability());
				Color color = dyeColor.getColor();
				if (ParticlesMain.getInstance().pulses.keySet().contains(player)) {
					if (ParticlesMain.getInstance().pulses.get(player).equals(color)) {
						return;
					}
				}
				ParticlesMain.getInstance().clearParticles(player);
				ParticlesMain.getInstance().pulses.put(player, color);
				ParticlesMain.getInstance().addColor(player, dyeColor);
				Effects.getInstance().pulse(player, color);
				player.closeInventory();
			}
		}
	}

	private boolean HasPermission(ItemStack item, Player player) {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("YouHavePermissionLore")));
		if (item.getItemMeta().getLore().equals(lore)) {
			player.playSound(player.getLocation(), SoundAPI.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
			return true;
		}
		player.closeInventory();
		player.playSound(player.getLocation(), SoundAPI.ENTITY_ITEM_BREAK, 1, 1);
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', ParticlesMain.getInstance().getConfig().getString("NoPermission")));
		return false;
	}
}
