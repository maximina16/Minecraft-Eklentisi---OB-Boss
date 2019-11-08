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

public class Odul_Komutlar_Secim_Menusu implements Listener {

	static Main main;

	public Odul_Komutlar_Secim_Menusu(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, Inventory> odulkomutenv = new HashMap<String, Inventory>();

	public static void odulkomutenvac(Player p, String mobadi) {
		odulkomutenv.put(mobadi, Bukkit.createInventory(null, 18, "§cSeçim yapınız.."));
		odulkomutenv.get(mobadi).setItem(13, Menu.geriDonItemi());
		odulkomutenv.get(mobadi).setItem(2, odulEkle());
		odulkomutenv.get(mobadi).setItem(4, sansAyarla());
		odulkomutenv.get(mobadi).setItem(6, komutEkle());
		for (int i = 9; i < 18; i++)
			if (i != 13)
				odulkomutenv.get(mobadi).setItem(i, OdulMenusu.camlar());

		p.openInventory(odulkomutenv.get(mobadi));
	}

	private static ItemStack sansAyarla() {
		ItemStack item = new ItemStack(Material.getMaterial("EXP_BOTTLE"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bŞans Ayarla");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Itemlerin drop şanslarını");
		lore.add("§7ayarlamak için tıklayınız.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack odulEkle() {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bÖdül Ekle");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Item olarak ödül eklemek");
		lore.add("§7ya da düzenlemek için");
		lore.add("§7tıklayabilirsiniz.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack komutEkle() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bKomut Düzenle");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Tıkladığınızda komut düzenleme");
		lore.add("§7menüsü açılır.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tiklamaEventiKontrol(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(odulkomutenv.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(odulEkle())) {
							OdulMenusu.oyuncuyaOdulMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(komutEkle())) {
							KomutMenusu.komutMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(sansAyarla())) {
							SansMenusu.oyuncuyaSansMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));

						}
					}

				}

			}
		}
	}

}
