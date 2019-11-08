package speedyg.efektler;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Efekt {

	private Player oyuncu;
	private String efekt;
	private int guc;
	private LivingEntity boss;

	public Efekt(Player p, String efekt, int guc, LivingEntity boss) {
		this.oyuncu = p;
		this.efekt = efekt;
		this.guc = guc;
		this.boss = boss;
	}

	public void efektOlustur() {
		if (efekt.contains("Isinlanma")) {
			oyuncuyu_Isinla();
		} else if (efekt.contains("Oldurucu_Darbe")) {
			oldurucu_Darbe_Calistir();
		} else if (efekt.contains("Simsek_Saldirisi")) {
			simsek_Saldirisi_Yap();
		} else if (efekt.contains("Buyuk_Ok_Yagmuru")) {
			buyuk_Ok_Yagmuru_Olustur();
		} else if (efekt.contains("Buyuk_Alevli_Ok_Yagmuru")) {
			buyuk_Alevli_Ok_Yagmuru_Olustur();
		} else if (efekt.contains("Yardimcilar_Gonder")) {
			etraftaZombiDogur();
		} else if (efekt.contains("Oyunculari_Yanina_Cek")) {
			oyunculari_Yanina_Cek();
		}

	}

	private void oyunculari_Yanina_Cek() {
		Location bosyeri = boss.getLocation();
		for (Entity e : boss.getNearbyEntities(guc * 2, guc * 2, guc * 2))
			if (e instanceof Player) {
				Player p = (Player) e;
				p.teleport(bosyeri);
			}
	}

	private void etraftaZombiDogur() {
		Random r = new Random();
		for (int i = 0; i < guc * 4 / 3; i++) {
			int rx = r.nextInt(guc);
			int rz = r.nextInt(guc);
			Location loc = boss.getLocation().add(rx, 0, rz);
			loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
		}

	}

	private void buyuk_Alevli_Ok_Yagmuru_Olustur() {

		Location loc = oyuncu.getLocation();
		double x = loc.getX();
		double y = loc.getY() + 13;
		double z = loc.getZ();
		int r = 8;
		int i = 0;
		do {
			double angle = i * Math.PI / 180;
			x = (int) (loc.getX() + r * Math.cos(angle));
			z = (int) (loc.getZ() + r * Math.sin(angle));
			Location yagmurlok = new Location(loc.getWorld(), x, y, z);
			Arrow ok = (Arrow) oyuncu.getWorld().spawnEntity(yagmurlok, EntityType.ARROW);
			ok.setShooter(boss);
			ok.setFireTicks(300);
			i += 15;
			if (i > 358 && r > 1) {
				r = r - 1;
				i = 0;
				y = y + 1;
			}
		} while (i < 360);

	}

	private void buyuk_Ok_Yagmuru_Olustur() {
		Location loc = oyuncu.getLocation();

		double x = loc.getX();
		double y = loc.getY() + 13;
		double z = loc.getZ();
		int r = 8;
		int i = 0;
		do {
			double angle = i * Math.PI / 180;
			x = (int) (loc.getX() + r * Math.cos(angle));
			z = (int) (loc.getZ() + r * Math.sin(angle));
			Location yagmurlok = new Location(loc.getWorld(), x, y, z);
			Arrow ok = (Arrow) oyuncu.getWorld().spawnEntity(yagmurlok, EntityType.ARROW);
			ok.setShooter(boss);
			i += 15;
			if (i > 358 && r > 1) {
				r = r - 1;
				i = 0;
				y = y + 1;
			}
		} while (i < 360);

	}

	private void oyuncuyu_Isinla() {
		Random ran = new Random();
		int x = ran.nextInt(10);
		int y = ran.nextInt(5);
		int z = ran.nextInt(20);
		Vector vec = new Vector(x, y, z);
		oyuncu.setVelocity(vec);
	}

	private void simsek_Saldirisi_Yap() {
		boss.teleport(oyuncu.getLocation());
		for (int a = 0; a < (guc * 20); a++)
			oyuncu.getLocation().getWorld().playEffect(oyuncu.getLocation(), Effect.BLAZE_SHOOT, a);
		for (int i = 0; i < guc * 2 / 3 + 1; i++)
			oyuncu.getLocation().getWorld().strikeLightning(oyuncu.getLocation());

	}

	private void oldurucu_Darbe_Calistir() {
		for (int a = 0; a < (guc * 20); a++)
			oyuncu.getLocation().getWorld().playEffect(oyuncu.getLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, a);
		for (int i = 0; i < (guc * 20); i++)
			oyuncu.getLocation().getWorld().playEffect(oyuncu.getLocation(), Effect.SMOKE, i);
		oyuncu.setHealth(oyuncu.getHealth() / 2);
	}

}
