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

public class KomutMenusu implements Listener {

	static Main main;

	public KomutMenusu(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, Inventory> komutmenusu = new HashMap<String, Inventory>();

	public static void komutMenusuAc(Player p, String mobadi) {
		komutmenusu.put(mobadi, Bukkit.createInventory(null, 18, "§cSeçim yapınız.."));
		komutmenusu.get(mobadi).setItem(13, Menu.geriDonItemi());
		komutmenusu.get(mobadi).setItem(2, KomutMenusu.itemcik());
		komutmenusu.get(mobadi).setItem(6, KomutMenusu.itemcik1());
		for (int i = 9; i < 18; i++)
			if (i != 13)
				komutmenusu.get(mobadi).setItem(i, OdulMenusu.camlar());
		p.openInventory(komutmenusu.get(mobadi));
	}

	public static ItemStack itemcik() {
		ItemStack item = new ItemStack(Material.STICK);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§aKomut Ekle");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Komut eklemek için tıklayın ve");
		lore.add("§7chate istediğiniz komutu giriniz.");
		lore.add("§7Komutlar konsoldan gönderilir yani");
		lore.add("§7komutu oyuncuya yönlendirmek için");
		lore.add("§b%oyuncu% §7değerini kullanın.");
		lore.add("§7Komutların başına '§4/§7' §ceklemeyiniz.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	public static ItemStack itemcik1() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§cKomut Sil");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Komut silmek için tıklayın ve");
		lore.add("§7menüden silmek itediğiniz komuta");
		lore.add("§7tıklayınız.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tilamaccik(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(komutmenusu.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							Odul_Komutlar_Secim_Menusu.odulkomutenvac(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(itemcik1())) {
							KomutSilSecMenusu.oyuncuyaKomutSilSecMenuAc(p);
						} else if (e.getCurrentItem().equals(itemcik())) {
							p.sendMessage(Mesajlar.komutgiriniz);
							p.closeInventory();
							DuzenMenu.komutekle.add(p.getName());
						}
					}
				}

			}
		}
	}

}
