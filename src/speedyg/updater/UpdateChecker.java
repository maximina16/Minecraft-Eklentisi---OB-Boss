package speedyg.updater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;

import speedyg.boss.Main;
import speedyg.mesaj.Mesajlar;

public class UpdateChecker {
	static Main main;
	public static Thread checkupdates = new Thread() {

		public void run() {

			URL url = null;
			try {
				url = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + Main.API);
			} catch (MalformedURLException localMalformedURLException) {
			}
			URLConnection conn = null;
			try {
				conn = url.openConnection();
			} catch (IOException localIOException) {
			}
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				if (br.readLine().equals(Main.currentversion)) {
					Bukkit.getConsoleSender().sendMessage(Mesajlar.prefix + "§dEklenti guncel!");
					Bukkit.getConsoleSender().sendMessage(Mesajlar.prefix + " §dEklenti basariyla aktif edildi!");
					Main.update = false;
				} else {
					Bukkit.getConsoleSender()
							.sendMessage(Mesajlar.prefix + "§cEklentiniz guncel degil! Lutfen guncelleyiniz!");
					Bukkit.getConsoleSender().sendMessage(Mesajlar.prefix + "§3Guncellemek icin; "
							+ "https://www.spigotmc.org/resources/ob-boss-tamam%C4%B1yla-t%C3%BCrk%C3%A7e-ve-orjinal-boss-eklentisi-tamam%C4%B1yla-%C3%96zelle%C5%9Ftirilebilir-gui-sistem.65832/");
					Main.update = true;
				}
			} catch (IOException localIOException1) {
			}

		}
	};

}
