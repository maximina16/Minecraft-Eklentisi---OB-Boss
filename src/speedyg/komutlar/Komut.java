package speedyg.komutlar;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import speedyg.boss.Main;
import speedyg.menuler.DuzenMenu;
import speedyg.menuler.GoruntulemeSecimMenusu;
import speedyg.menuler.Menu;
import speedyg.mesaj.Mesajlar;

public class Komut implements CommandExecutor {

	static Main main;

	public Komut(Main plugin) {
		Komut.main = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("obboss.admin")) {

				if (!DuzenMenu.candegistirici.contains(p.getName()) && !DuzenMenu.isimdegistirici.contains(p.getName())
						&& !Menu.bossolusturma.contains(p.getName())
						&& !DuzenMenu.dogmasuresidegistir.contains(p.getName())
						&& !DuzenMenu.komutekle.contains(p.getName())
						&& !DuzenMenu.rastgeledegistir.contains(p.getName())
						&& !DuzenMenu.efektsansidegistir.contains(p.getName())
						&& !DuzenMenu.potsuresidegistir.contains(p.getName()))
					Menu.oyuncuyaBossEnvanteriniAc(p);
				else
					p.sendMessage(Mesajlar.degerigiriniz);

			} else if (p.hasPermission("obboss.goruntule")) {
				GoruntulemeSecimMenusu.oyuncuMenuAc(p);
			} else {
				p.sendMessage(Mesajlar.yetkimesaji);
			}

		} else {
			Bukkit.getConsoleSender().sendMessage("§7[§4OB-Boss§7] §cBu komut §4§noyun icerisinden§r§c gecerlidir!");
		}
		return false;
	}

}
