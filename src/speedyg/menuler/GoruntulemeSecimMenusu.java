package speedyg.menuler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import speedyg.boss.Main;

public class GoruntulemeSecimMenusu implements Listener {

	static Main main;

	public GoruntulemeSecimMenusu(Main plugin) {
		main = plugin;
	}

	private static HashMap<Player, Inventory> gsecmenu = new HashMap<Player, Inventory>();
	private static HashMap<Player, Inventory> odulmenusu = new HashMap<Player, Inventory>();

	public static void oyuncuMenuAc(Player p) {
		gsecmenu.put(p, Bukkit.createInventory(null, 54, Main.dataayar.getString("Boss-Goruntuleme-Menusu.Ana-Menu-Ismi").replaceAll("&", "§")));
		gsecmenu.get(p).setItem(49, Menu.menuKapat());
		int a = 0;
		for (String bossadi : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
			if (a == 49)
				a++;
			if (a <= gsecmenu.get(p).getSize())
				gsecmenu.get(p).setItem(a, kafalar(bossadi));
			a++;
		}
		p.openInventory(gsecmenu.get(p));
	}

	private static void oyuncuyaOdulleriGoster(Player p, String mobadi) {
		odulmenusu.put(p, Bukkit.createInventory(null, OdulMenusu.maxodul,
				Main.dataayar.getString("Boss-Goruntuleme-Menusu.Menu-Ismi")
						.replaceAll("<bossadi>",
								main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi").replaceAll("&", "§"))
						.replaceAll("<oyuncu>", p.getName()).replaceAll("&", "§")));
		for (int i = OdulMenusu.maxodul - 9; i < OdulMenusu.maxodul; i++) {
			if (i != OdulMenusu.maxodul - 5)
				odulmenusu.get(p).setItem(i, OdulMenusu.camlar());
		}
		List<String> lore = Main.dataayar.getStringList("Boss-Goruntuleme-Menusu.Drop-Sansi-Gosterimi");
		for (int a = 0; a < OdulMenusu.maxodul - 9; a++) {
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Oduller." + a) != null) {
				ItemStack gosterimitemi = new ItemStack(
						main.getConfig().getItemStack("Bosslar." + mobadi + ".Oduller." + a));
				ItemMeta imeta = gosterimitemi.getItemMeta();
				List<String> asillore;
				if (imeta.hasLore())
					asillore = imeta.getLore();
				else
					asillore = new ArrayList<String>();
				for (int i = 0; i < lore.size(); i++)
					asillore.add(lore.get(i).replaceAll("&", "§").replaceAll("<sans>",
							String.valueOf(main.getConfig().getInt("Bosslar." + mobadi + ".Drop-Sans." + a))));
				imeta.setLore(asillore);
				gosterimitemi.setItemMeta(imeta);
				odulmenusu.get(p).setItem(a, gosterimitemi);

			}
		}

		odulmenusu.get(p).setItem(OdulMenusu.maxodul - 5, Menu.geriDonItemi());
		p.openInventory(odulmenusu.get(p));
	}

	private static ItemStack kafalar(String bossadi) {
		ItemStack item = new ItemStack(Material.getMaterial("SKULL_ITEM"));
		SkullMeta imeta = (SkullMeta) item.getItemMeta();
		imeta.setDisplayName("§bBoss Adı : "
				+ main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi").replaceAll("&", "§"));
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Ödül envanterini görüntülemek");
		lore.add("§7istediğiniz boss'a tıklayınız.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tiktik(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(gsecmenu.get(p))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.menuKapat())) {
							p.closeInventory();
						} else {
							for (String bossad : main.getConfig().getConfigurationSection("Bosslar").getKeys(false))
								if (e.getCurrentItem().equals(kafalar(bossad))) {
									oyuncuyaOdulleriGoster(p, bossad);
								}
						}
					} else if (e.getInventory().equals(odulmenusu.get(p))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							oyuncuMenuAc(p);
						}
					}
				}

			}
		}

	}

}
