package speedyg.menuler;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import speedyg.boss.Main;
import speedyg.eventler.BossEvent;
import speedyg.mesaj.Mesajlar;

public class ManuelDogurMenu implements Listener {

	static Main main;

	public ManuelDogurMenu(Main plugin) {
		main = plugin;
	}

	public static HashMap<Player, Inventory> dogursecmenu = new HashMap<Player, Inventory>();

	public static void oyuncuyaDogurSecmenuAc(Player p) {
		dogursecmenu.put(p, Bukkit.createInventory(null, 54, "§cLütfen boss seçiniz.."));
		int a = 0;
		for (String bosslar : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
			if (a == 49)
				a++;
			if (a < 54)
				dogursecmenu.get(p).setItem(a, SecimMenu.kafaEkleyici(bosslar));
			a++;
		}
		for (int i = 45; i < 54; i++)
			dogursecmenu.get(p).setItem(i, OdulMenusu.camlar());
		dogursecmenu.get(p).setItem(49, Menu.geriDonItemi());
		p.openInventory(dogursecmenu.get(p));
	}

	@EventHandler
	private static void tiklamaev3(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(dogursecmenu.get(p))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							Menu.oyuncuyaBossEnvanteriniAc(p);
						} else {
							for (String bossadi : main.getConfig().getConfigurationSection("Bosslar").getKeys(false))
								if (e.getCurrentItem().equals(SecimMenu.kafaEkleyici(bossadi))) {
									if (main.getConfig().getInt("Bosslar." + bossadi + ".Can") > 0) {
										if (BossEvent.bosslarim.get(bossadi) != null) {
											if (main.getConfig().getBoolean(
													"Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu")) {
												if (BossEvent.mobaGit(BossEvent.bosslarim.get(bossadi)) != null)
													BossEvent.mobaGit(BossEvent.bosslarim.get(bossadi)).remove();
												BossEvent.bosslarim.remove(bossadi);
												BossEvent.mob(bossadi);
											} else {
												p.closeInventory();
												p.sendMessage(Mesajlar.prefix
														+ "§cBu boss'un ölmeden doğması kapalı. Boss düzenleme menüsünden önce aktif ediniz daha sonra manüel olarak doğurtabilirsiniz.");
											}
										} else {
											BossEvent.mob(bossadi);
										}
										p.closeInventory();
									} else {
										p.sendMessage(Mesajlar.caniyokbunun);
									}

								}
						}

					}
				}
			}
		}
	}

}
