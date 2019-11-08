package speedyg.menuler;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;
import speedyg.mesaj.UTF_8;

public class BossGiydir implements Listener {

	static Main main;

	public BossGiydir(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, Inventory> bossgiydir = new HashMap<String, Inventory>();

	public static void oyuncuyaBossGiydirmeMenusuAc(Player p, String mobadi) {
		bossgiydir.put(mobadi, Bukkit.createInventory(null, 36, "§cBoss'u giydir.."));
		for (int i = 0; i < 36; i++)
			if (bossgiydir.get(mobadi).getItem(i) == null) {
				if (i != 20 && i != 22 && i != 23 && i != 24 && i != 25)
					bossgiydir.get(mobadi).setItem(i, OdulMenusu.camlar());
			}

		bossgiydir.get(mobadi).setItem(11, arayer5());
		if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Saldiri") != null) {
			bossgiydir.get(mobadi).setItem(20, main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Saldiri"));
		}
		bossgiydir.get(mobadi).setItem(13, arayer1());
		if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Kafa") != null) {
			bossgiydir.get(mobadi).setItem(22, main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Kafa"));
		}
		bossgiydir.get(mobadi).setItem(14, arayer2());
		if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Gogus") != null) {
			bossgiydir.get(mobadi).setItem(23, main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Gogus"));
		}
		bossgiydir.get(mobadi).setItem(15, arayer3());
		if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Pantolon") != null) {
			bossgiydir.get(mobadi).setItem(24, main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Pantolon"));
		}
		bossgiydir.get(mobadi).setItem(16, arayer4());
		if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Ayak") != null) {
			bossgiydir.get(mobadi).setItem(25, main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Ayak"));
		}
		bossgiydir.get(mobadi).setItem(31, Menu.geriDonItemi());

		p.openInventory(bossgiydir.get(mobadi));
	}

	public static ItemStack arayer1() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 14);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§b" + UTF_8.icidoluyildiz + " §7Kask §b" + UTF_8.icidoluyildiz);
		item.setItemMeta(imeta);
		return item;
	}

	public static ItemStack arayer2() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 14);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§b" + UTF_8.icidoluyildiz + " §7Göğüslük §b" + UTF_8.icidoluyildiz);
		item.setItemMeta(imeta);
		return item;
	}

	public static ItemStack arayer3() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 14);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§b" + UTF_8.icidoluyildiz + " §7Pantolon §b" + UTF_8.icidoluyildiz);
		item.setItemMeta(imeta);
		return item;
	}

	public static ItemStack arayer4() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 14);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§b" + UTF_8.icidoluyildiz + " §7Ayakkabı §b" + UTF_8.icidoluyildiz);
		item.setItemMeta(imeta);
		return item;
	}

	public static ItemStack arayer5() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 14);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§b" + UTF_8.icidoluyildiz + " §7El Itemi §b" + UTF_8.icidoluyildiz);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private static void tiklamaEventi(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(bossgiydir.get(DuzenMenu.bossduzen.get(p.getName())))) {
						if (e.getCurrentItem().equals(arayer1()) || e.getCurrentItem().equals(arayer2())
								|| e.getCurrentItem().equals(arayer3()) || e.getCurrentItem().equals(arayer4())
								|| e.getCurrentItem().equals(arayer5())
								|| e.getCurrentItem().equals(OdulMenusu.camlar())) {
							e.setCancelled(true);
						} else if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							e.setCancelled(true);
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						}
					}

				}

			}

		}
	}

	@EventHandler
	private void envanterKapatma(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			if (e.getInventory() != null) {
				if (e.getInventory().equals(bossgiydir.get(DuzenMenu.bossduzen.get(p.getName())))) {
					ItemStack kask = e.getInventory().getItem(22);
					ItemStack gogusluk = e.getInventory().getItem(23);
					ItemStack pantolon = e.getInventory().getItem(24);
					ItemStack ayak = e.getInventory().getItem(25);
					ItemStack elitemi = e.getInventory().getItem(20);
					esyalariSetEt(DuzenMenu.bossduzen.get(p.getName()), kask, gogusluk, pantolon, ayak, elitemi);
				}

			}
		}
	}

	private static void esyalariSetEt(String mobadi, ItemStack kask, ItemStack gogusluk, ItemStack pantol,
			ItemStack ayak, ItemStack elitemi) {
		main.getConfig().set("Bosslar." + mobadi + ".Set.Saldiri", elitemi);
		main.getConfig().set("Bosslar." + mobadi + ".Set.Kafa", kask);
		main.getConfig().set("Bosslar." + mobadi + ".Set.Gogus", gogusluk);
		main.getConfig().set("Bosslar." + mobadi + ".Set.Pantolon", pantol);
		main.getConfig().set("Bosslar." + mobadi + ".Set.Ayak", ayak);
		main.saveConfig();
	}

}
