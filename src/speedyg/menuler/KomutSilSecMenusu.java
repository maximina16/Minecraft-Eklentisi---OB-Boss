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

import speedyg.boss.Main;

public class KomutSilSecMenusu implements Listener {

	static Main main;

	public KomutSilSecMenusu(Main plugin) {
		main = plugin;
	}

	public static HashMap<Player, Inventory> komutsilmenusu = new HashMap<Player, Inventory>();

	public static void oyuncuyaKomutSilSecMenuAc(Player p) {
		komutsilmenusu.put(p, Bukkit.createInventory(null, 54, "§cSilmek istediğinizi seçiniz..."));
		komutsilmenusu.get(p).setItem(49, Menu.geriDonItemi());
		List<String> komutlar = main.getConfig().getStringList("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Komutlar");
		for (int i = 0; i < komutlar.size(); i++)
			komutsilmenusu.get(p).setItem(i, komutEkleyici(komutlar.get(i), i));

		p.openInventory(komutsilmenusu.get(p));
	}

	public static ItemStack komutEkleyici(String komut, int i) {
		ItemStack item = new ItemStack(Material.getMaterial("WOOL"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§b" + (i + 1));
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Komut;");
		lore.add("§b/" + komut);
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tikgel(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(komutsilmenusu.get(p))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							KomutMenusu.komutMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else {
							List<String> komutlar = main.getConfig()
									.getStringList("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Komutlar");
							for (int i = 0; i < komutlar.size(); i++)
								if (e.getCurrentItem().equals(komutEkleyici(komutlar.get(i), i))) {
									komutlar.remove(komutlar.get(i));
									main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Komutlar",
											komutlar);
									main.saveConfig();
								}
							oyuncuyaKomutSilSecMenuAc(p);
						}
					}
				}

			}
		}
	}

}
