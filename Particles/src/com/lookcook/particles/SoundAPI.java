package com.lookcook.particles;

import org.bukkit.Bukkit;
import org.bukkit.Sound;

public class SoundAPI {
	
	public static Sound BLOCK_CHEST_OPEN, ENTITY_ITEM_BREAK, 
	ENTITY_EXPERIENCE_ORB_PICKUP, ENTITY_GENERIC_DRINK, BLOCK_NOTE_PLING;
	
	public SoundAPI() {
		if (Bukkit.getVersion().contains("1.9") || Bukkit.getVersion().contains("1.1")) {
			SoundAPI.BLOCK_CHEST_OPEN = Sound.valueOf("BLOCK_CHEST_OPEN");
			SoundAPI.ENTITY_ITEM_BREAK = Sound.valueOf("ENTITY_ITEM_BREAK");
			SoundAPI.ENTITY_EXPERIENCE_ORB_PICKUP = Sound.valueOf("ENTITY_EXPERIENCE_ORB_PICKUP");
			SoundAPI.ENTITY_GENERIC_DRINK = Sound.valueOf("ENTITY_GENERIC_DRINK");
			SoundAPI.BLOCK_NOTE_PLING = Sound.valueOf("BLOCK_NOTE_PLING");
		} else {
			SoundAPI.BLOCK_CHEST_OPEN = Sound.valueOf("CHEST_OPEN");
			SoundAPI.ENTITY_ITEM_BREAK = Sound.valueOf("ITEM_BREAK");
			SoundAPI.ENTITY_EXPERIENCE_ORB_PICKUP = Sound.valueOf("ORB_PICKUP");
			SoundAPI.ENTITY_GENERIC_DRINK = Sound.valueOf("DRINK");
			SoundAPI.BLOCK_NOTE_PLING = Sound.valueOf("NOTE_PLING");
		}
	}
}
