package com.lookcook.particles;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class Effects {

	public static Effects instance = new Effects();

	public static Effects getInstance() {
		return instance;
	}

	public void pulse(final Entity player, final Color color) {
		new BukkitRunnable() {
			double increment = 1;

			public void run() {
				if (!ParticlesMain.getInstance().pulses.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().pulses.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				loc.add(0, 0.2, 0);
				for (int degree = 0; degree < 360; degree = degree + 25) {
					double radian = Math.toRadians(degree);
					loc.add(Math.cos(radian) * increment, 0, Math.sin(radian) * increment);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							20);
					loc.subtract(Math.cos(radian) * increment, 0, Math.sin(radian) * increment);
				}
				loc.subtract(0, 0.2, 0);
				increment = increment + 0.5;
				if (increment > 4) {
					increment = 0;
				}
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 3);
	}

	public void halo(final Entity player, final Color color) {
		new BukkitRunnable() {
			
			int degree = 0;

			public void run() {
				if (!ParticlesMain.getInstance().halos.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().halos.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				double radian = Math.toRadians(degree);
				loc.add(Math.cos(radian) / 2, 2, Math.sin(radian) / 2);
				ParticleEffect.REDSTONE.display(
						new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc, 20);
				loc.subtract(Math.cos(radian) / 2, 2, Math.sin(radian) / 2);
				degree = degree + 20;
				if (degree == 360) {
					degree = 0;
				}
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 5);
	}

	public void tornado(final Entity player, final Color color) {
		new BukkitRunnable() {
			int increment = 0;

			public void run() {
				if (!ParticlesMain.getInstance().tornados.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().tornados.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				double width = 0.5;
				for (int degree = increment; degree < 400 + increment; degree = degree + 10) {
					double radian = Math.toRadians(degree);
					loc.add(Math.cos(radian) * width, 0.08, Math.sin(radian) * width);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							20);
					loc.subtract(Math.cos(radian) * width, 0, Math.sin(radian) * width);
					width = width + 0.018;
				}
				increment = increment + 7;
				if (increment == 360) {
					increment = 0;
				}
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}

	public void snake(final Entity player, final Color color) {
		new BukkitRunnable() {
			double y = 2;
			boolean down = true;
			int degree = 0;

			public void run() {
				if (!ParticlesMain.getInstance().snakes.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().snakes.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				if (down == true) {
					y = y - 0.02;
				}
				if (down == false) {
					y = y + 0.02;
				}
				if (y == 2) {
					down = true;
				}
				if (y < 0) {
					down = false;
				}
				if (degree == 360) {
					degree = 0;
				}
				if (degree < 360) {
					double radian = Math.toRadians(degree);
					double x = Math.cos(radian);
					double z = Math.sin(radian);
					loc.add(x, y, z);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							20);
					loc.subtract(x, y, z);
					degree = degree + 10;
				}

			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}

	public void banner(final Entity player, final Color color) {
		new BukkitRunnable() {
			public void run() {
				if (!ParticlesMain.getInstance().banners.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().banners.get(player).equals(color)) {
					this.cancel();
					return;
				}
				displayBanner(player.getLocation(), color);
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}

	public void displayBanner(final Location loc, final Color color) {
		new BukkitRunnable() {
			int count = 0;

			public void run() {
				double y = 2;
				while (y > 0) {
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()),
							new Location(loc.getWorld(), loc.getX(), loc.getY() + y, loc.getZ()), 'c');
					y = y - 0.2;
					count++;
					if (count > 40) {
						this.cancel();
						return;
					}
				}
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}

	public void orb(final Entity player, final Color color) {
		new BukkitRunnable() {
			int degree = 0;

			public void run() {
				if (!ParticlesMain.getInstance().orbs.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().orbs.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				if (degree == 360) {
					degree = 0;
				}
				if (degree < 360) {
					double radian1 = Math.toRadians(degree);
					double radian2 = Math.toRadians(degree + 120);
					double radian3 = Math.toRadians(degree + 240);
					loc.add(Math.cos(radian1), 1, Math.sin(radian1));
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							20);
					loc.subtract(Math.cos(radian1), 1, Math.sin(radian1));
					loc.add(Math.cos(radian2), 1, Math.sin(radian2));
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							20);
					loc.subtract(Math.cos(radian2), 1, Math.sin(radian2));
					loc.add(Math.cos(radian3), 1, Math.sin(radian3));
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							20);
					loc.subtract(Math.cos(radian3), 1, Math.sin(radian3));
					degree = degree + 8;
				}

			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}

	public void hat(final Entity player, final Color color) {
		new BukkitRunnable() {
			public void run() {
				if (!ParticlesMain.getInstance().hats.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().hats.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				loc.add(0, 2, 0);
				for (int degree = 0; degree < 360; degree = degree + 20) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 1.5, 0, sin / 1.5);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 1.5, 0, sin / 1.5);
				}
				loc.add(0, 0.2, 0);
				for (int degree = 0; degree < 360; degree = degree + 30) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 2, 0, sin / 2);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 2, 0, sin / 2);
				}
				loc.add(0, 0.2, 0);
				for (int degree = 0; degree < 360; degree = degree + 40) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 3, 0, sin / 3);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 3, 0, sin / 3);
				}
				for (int degree = 0; degree < 360; degree = degree + 60) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 4, 0, sin / 4);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 4, 0, sin / 4);
				}
				ParticleEffect.REDSTONE.display(
						new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc, 'c');
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}

	public void tophat(final Entity player, final Color color) {
		new BukkitRunnable() {
			public void run() {
				if (!ParticlesMain.getInstance().hats.keySet().contains(player)) {
					this.cancel();
					return;
				}
				if (!ParticlesMain.getInstance().hats.get(player).equals(color)) {
					this.cancel();
					return;
				}
				Location loc = player.getLocation();
				loc.add(0, 2, 0);
				for (int degree = 0; degree < 360; degree = degree + 20) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 1.5, 0, sin / 1.5);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 1.5, 0, sin / 1.5);
				}
				loc.add(0, 0.3, 0);
				for (int degree = 0; degree < 360; degree = degree + 80) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 2, 0, sin / 2);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 2, 0, sin / 2);
				}
				loc.add(0, 0.3, 0);
				for (int degree = 0; degree < 360; degree = degree + 80) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 2, 0, sin / 2);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 2, 0, sin / 2);
				}
				loc.add(0, 0.3, 0);
				for (int degree = 0; degree < 360; degree = degree + 20) {
					double radian = Math.toRadians(degree);
					double cos = Math.cos(radian);
					double sin = Math.sin(radian);
					loc.add(cos / 2, 0, sin / 2);
					ParticleEffect.REDSTONE.display(
							new ParticleEffect.OrdinaryColor(color.getRed(), color.getGreen(), color.getBlue()), loc,
							'c');
					loc.subtract(cos / 2, 0, sin / 2);
				}
			}
		}.runTaskTimer(ParticlesMain.getInstance(), 0, 1);
	}
}
