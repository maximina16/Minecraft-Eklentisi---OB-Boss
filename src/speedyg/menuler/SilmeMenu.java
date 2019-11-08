package speedyg.menuler;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;
import speedyg.mesaj.Mesajlar;
import speedyg.mesaj.UTF_8;

public class SilmeMenu implements Listener {

	static Main main;

	public SilmeMenu(Main plugin) {
		SilmeMenu.main = plugin;
	}

	public static HashMap<Player, Inventory> silsecmenu = new HashMap<Player, Inventory>();
	public static HashMap<Player, Inventory> onaymenusu = new HashMap<Player, Inventory>();
	private static HashMap<Player, String> boss = new HashMap<Player, String>();

	public static void oyuncuyaSilmeMenusuAc(Player p) {
		silsecmenu.put(p, Bukkit.createInventory(null, 54, "§c§lSilinecek bossu seçiniz..!"));
		int a = 0;
		for (String bosslar : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
			if (a == 49)
				a++;
			if (a < 54)
				silsecmenu.get(p).setItem(a, SecimMenu.kafaEkleyici(bosslar));
			a++;
		}
		for (int i = 45; i < 54; i++)
			silsecmenu.get(p).setItem(i, OdulMenusu.camlar());
		silsecmenu.get(p).setItem(49, Menu.geriDonItemi());
		p.openInventory(silsecmenu.get(p));

	}

	@EventHandler
	public void silOnayaGonder(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getInventory().equals(silsecmenu.get(p))) {
					e.setCancelled(true);
					if (e.getCurrentItem() != null) {
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							Menu.oyuncuyaBossEnvanteriniAc(p);
						} else {
							for (String bosslar : main.getConfig().getConfigurationSection("Bosslar").getKeys(false))
								if (e.getCurrentItem().equals(SecimMenu.kafaEkleyici(bosslar))) {
									boss.put(p, bosslar);
									SilmeMenu.onayMenusuAc(p, bosslar);
								}
						}
					}

				}

			}
		}
	}

	public static void onayMenusuAc(Player p, String bossadi) {
		onaymenusu.put(p, Bukkit.createInventory(null, 9, "§cBoss silmeyi onaylıyor musun?"));
		onaymenusu.get(p).setItem(2, sil());
		onaymenusu.get(p).setItem(4, araItemi());
		onaymenusu.get(p).setItem(6, silme());

		p.openInventory(onaymenusu.get(p));
	}

	private static ItemStack sil() {
		ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 5);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§aEvet Onaylıyorum");
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack silme() {
		ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 14);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§cHayır Onaylamıyorum");
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack araItemi() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 11);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§a<-- " + UTF_8.tik + "      §4" + UTF_8.carpi + " -->");
		item.setItemMeta(imeta);

		return item;
	}

	@EventHandler
	private void sil(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null)
					if (e.getInventory().getName() != null) {
						if (e.getInventory().equals(onaymenusu.get(p))) {
							e.setCancelled(true);
							if (e.getCurrentItem().equals(sil())) {
								main.getConfig().set("Bosslar." + SilmeMenu.boss.get(p), null);
								Main.lokayar.set("Lokasyonlar." + SilmeMenu.boss.get(p), null);
								main.saveConfig();
								Main.save(Main.lokayar, Main.lokasyondosyasi);
								SilmeMenu.boss.remove(p);
								p.sendMessage(Mesajlar.bosssilindi);
								Menu.oyuncuyaBossEnvanteriniAc(p);
							} else if (e.getCurrentItem().equals(silme())) {
								oyuncuyaSilmeMenusuAc(p);
							}

						}

					}

			}
		}
	}

}