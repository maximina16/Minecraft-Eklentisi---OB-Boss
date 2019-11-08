package speedyg.menuler;

import java.util.ArrayList;
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

public class RastgeleAyarMenusu implements Listener {

	static Main main;

	public RastgeleAyarMenusu(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, Inventory> rastgeleayarmenusu = new HashMap<String, Inventory>();

	public static void oyuncuyaRastgeleMenusuAc(Player p, String bossadi) {
		rastgeleayarmenusu.put(bossadi, Bukkit.createInventory(null, 18, "§cLütfen işlem seçiniz.."));
		for (int i = 9; i < 18; i++)
			if (i != 13)
				rastgeleayarmenusu.get(bossadi).setItem(i, OdulMenusu.camlar());
		rastgeleayarmenusu.get(bossadi).setItem(13, Menu.geriDonItemi());
		rastgeleayarmenusu.get(bossadi).setItem(2, rastgeleDogur(bossadi));
		rastgeleayarmenusu.get(bossadi).setItem(6, birimAyarla());
		p.openInventory(rastgeleayarmenusu.get(bossadi));
	}

	private static ItemStack birimAyarla() {
		ItemStack item = new ItemStack(Material.VINE);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bBirim Ayarla");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Boss'un kaç birim içinde rastgele doğmasını");
		lore.add("§7istiyorsanız ayarlamanıza imkan sağlar.Tıkladığınızda");
		lore.add("§7Chat'ten bir sayı girmeniz istenir.Bu sayı");
		lore.add("§7doğma uzaklık birimi olarak ayarlanır.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack rastgeleDogur(String bossadi) {
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Rastgele-Dogma")) {
			ItemStack item = new ItemStack(Material.MAP);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cRastgele Doğma Kapat");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Bu sistemi kapatırsanız boss direkt");
			lore.add("§7olarak normal lokasyonunda doğar.");
			imeta.setLore(lore);
			item.setItemMeta(imeta);

			return item;
		} else {
			ItemStack item = new ItemStack(Material.getMaterial("EMPTY_MAP"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§aRastgele Doğma Aç");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Bu sistemi açarsanız boss normal");
			lore.add("§7lokasyonunun §b"
					+ main.getConfig().getInt("Bosslar." + bossadi + ".Rastgele-Dogma-Uzaklik-Birim")
					+ " birim§7 içerisinde herhangi");
			lore.add("§7bir yerde doğar.");
			imeta.setLore(lore);
			item.setItemMeta(imeta);

			return item;
		}
	}

	@EventHandler
	private void tiklamaEventiAyar(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(rastgeleayarmenusu.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(rastgeleDogur(DuzenMenu.bossduzen.get(p.getName())))) {
							rastgeleDogmaDegistir(DuzenMenu.bossduzen.get(p.getName()));
							oyuncuyaRastgeleMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(birimAyarla())) {
							p.closeInventory();
							DuzenMenu.rastgeledegistir.add(p.getName());
							p.sendMessage(Mesajlar.lutfenrastgelebirim);
						}
					}
				}
			}
		}
	}

	private void rastgeleDogmaDegistir(String bossadi) {
		boolean rastgele = main.getConfig().getBoolean("Bosslar." + bossadi + ".Rastgele-Dogma");
		main.getConfig().set("Bosslar." + bossadi + ".Rastgele-Dogma", !rastgele);
		main.saveConfig();
	}
}
