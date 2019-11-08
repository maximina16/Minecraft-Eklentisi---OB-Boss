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

public class GucMenusu implements Listener {

	static Main main;

	public GucMenusu(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, Inventory> gucmenusu = new HashMap<String, Inventory>();

	public static void gucMenusuAc(Player p, String mobadi) {
		gucmenusu.put(mobadi, Bukkit.createInventory(null, 9, "§cGüç seçiniz.."));
		for (int i = 1; i <= 8; i++) {
			gucmenusu.get(mobadi).setItem(i - 1, gucitemleri(i));
		}
		gucmenusu.get(mobadi).setItem(8, Menu.geriDonItemi());

		p.openInventory(gucmenusu.get(mobadi));
	}

	private static ItemStack gucitemleri(int gucbuyuklugu) {
		if (gucbuyuklugu == 1) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), gucbuyuklugu, (short) 5);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§e1.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 2) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 6);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§a2.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 3) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 2);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§23.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 4) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 5);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§34.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 5) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 4);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§55.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 6) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 1);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§66.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 7) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 14);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§c7.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else if (gucbuyuklugu == 8) {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"), 1, (short) 15);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§48.Seviye Güç");
			item.setItemMeta(imeta);
			return item;
		} else {
			ItemStack item = new ItemStack(Material.getMaterial("WOOL"));
			return item;
		}
	}

	@EventHandler
	private void tiklamaEventi(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getInventory().equals(gucmenusu.get(DuzenMenu.bossduzen.get(p.getName())))) {
					if (e.getCurrentItem() != null) {
						e.setCancelled(true);
						for (int i = 1; i <= 8; i++)
							if (e.getCurrentItem().equals(GucMenusu.gucitemleri(i))) {
								gucSetEt(DuzenMenu.bossduzen.get(p.getName()), i);
								DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
							} else if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
								DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
							}

					}

				}

			}

		}
	}

	public static void gucSetEt(String mobadi, int buyukluk) {
		main.getConfig().set("Bosslar." + mobadi + ".Guc", buyukluk);
		main.saveConfig();
		return;
	}

}
