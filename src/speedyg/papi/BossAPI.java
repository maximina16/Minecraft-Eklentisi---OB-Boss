package speedyg.papi;

import org.bukkit.event.Listener;

import speedyg.boss.Main;

public class BossAPI implements Listener {

	static Main main;

	public BossAPI(Main plugin) {
		main = plugin;
	}

	public static void oldurmeSetEt(String oyuncuadi, int oldurme) {
		Main.apiayar.set("Boss-Oldurme." + oyuncuadi, oldurme);
		Main.save(Main.apiayar, Main.apidosyasi);
	}

	public static void oldurmeEkle(String oyuncuadi, int oldurme) {
		Main.apiayar.set("Boss-Oldurme." + oyuncuadi, (Main.apiayar.getInt("Boss-Oldurme." + oyuncuadi) + oldurme));
		Main.save(Main.apiayar, Main.apidosyasi);
	}

	public static void oldurmeSil(String oyuncuadi, int oldurme) {
		Main.apiayar.set("Boss-Oldurme." + oyuncuadi, (Main.apiayar.getInt("Boss-Oldurme." + oyuncuadi) - oldurme));
		Main.save(Main.apiayar, Main.apidosyasi);
	}

	public static int oldurmeOgren(String oyuncuadi) {
		return Main.apiayar.getInt("Boss-Oldurme." + oyuncuadi);
	}

}
