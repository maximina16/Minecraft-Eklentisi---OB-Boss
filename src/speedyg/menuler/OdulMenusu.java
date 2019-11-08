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

public class OdulMenusu implements Listener {

	static Main main;

	public OdulMenusu(Main plugin) {
		main = plugin;
	}

	public static final int maxodul = 36;
	public static HashMap<String, Inventory> odulenvanter = new HashMap<String, Inventory>();

	public static void oyuncuyaOdulMenusuAc(Player p, String mobadi) {
		odulenvanter.put(mobadi, Bukkit.createInventory(null, maxodul, "§cÖdülleri ekleyiniz.."));

		for (int i = maxodul - 9; i < maxodul; i++) {
			if (i != OdulMenusu.maxodul - 5)
				odulenvanter.get(mobadi).setItem(i, camlar());
		}
		for (int a = 0; a < maxodul - 9; a++) {
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Oduller." + a) != null)
				odulenvanter.get(mobadi).setItem(a,
						main.getConfig().getItemStack("Bosslar." + mobadi + ".Oduller." + a));
		}

		odulenvanter.get(mobadi).setItem(maxodul - 5, Menu.geriDonItemi());

		p.openInventory(odulenvanter.get(mobadi));
	}

	public static ItemStack camlar() {
		ItemStack item = new ItemStack(Material.getMaterial("STAINED_GLASS_PANE"), 1, (short) 7);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§7" + UTF_8.superyildiz);
		item.setItemMeta(imeta);

		return item;
	}

	@EventHandler
	private static void tiklamaEventiodul(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(odulenvanter.get(DuzenMenu.bossduzen.get(p.getName())))) {
						if (e.getCurrentItem().equals(camlar())) {
							e.setCancelled(true);
						} else if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							e.setCancelled(true);
							Odul_Komutlar_Secim_Menusu.odulkomutenvac(p, DuzenMenu.bossduzen.get(p.getName()));
						}
					}

				}
			}
		}
	}

	@EventHandler
	private static void kapatmenus(InventoryCloseEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = (Player) e.getPlayer();
			if (e.getInventory() != null) {
				if (e.getInventory().equals(odulenvanter.get(DuzenMenu.bossduzen.get(p.getName()))))
					for (int i = 0; i < maxodul - 9; i++) {
						main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Oduller." + i,
								e.getInventory().getItem(i));

					}
				main.saveConfig();
			}
		}

	}
}
