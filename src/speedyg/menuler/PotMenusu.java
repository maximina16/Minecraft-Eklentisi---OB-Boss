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
import speedyg.mesaj.UTF_8;

public class PotMenusu implements Listener {

	static Main main;

	public PotMenusu(Main plugin) {
		main = plugin;
	}

	private final static int maxpot = 10;
	public static HashMap<String, Inventory> potmenusu = new HashMap<String, Inventory>();
	private static ArrayList<String> potlar = new ArrayList<String>();

	public static void oyuncuyaPotEnvanteriAc(Player p, String mobadi) {
		potlar.clear();
		potlar.add("SLOW");
		potlar.add("DAMAGE_RESISTANCE");
		potlar.add("INCREASE_DAMAGE");
		potlar.add("INVISIBILITY");
		potlar.add("JUMP");
		potlar.add("WEAKNESS");
		potlar.add("SPEED");
		potmenusu.put(mobadi, Bukkit.createInventory(null, 18, "§cEklenmek istediğine tıkla!"));
		potmenusu.get(mobadi).setItem(13, Menu.geriDonItemi());
		for (int i = 9; i < 18; i++)
			if (i != 13)
				potmenusu.get(mobadi).setItem(i, OdulMenusu.camlar());
		for (int a = 0; a < potlar.size() + 1; a++)
			if (a >= 1)
				potmenusu.get(mobadi).setItem(a, potTuru(p, potlar.get(a - 1)));

		p.openInventory(potmenusu.get(mobadi));
	}

	private static String turkcelestir(String potturu) {
		String geridon;
		switch (potturu) {
		case "SLOW":
			geridon = "§4Yavaşlık";
			break;
		case "DAMAGE_RESISTANCE":
			geridon = "§aDirenç";
			break;
		case "INCREASE_DAMAGE":
			geridon = "§aKuvvet";
			break;
		case "INVISIBILITY":
			geridon = "§aGörünmezlik";
			break;
		case "JUMP":
			geridon = "§aZıplama Desteği";
			break;
		case "WEAKNESS":
			geridon = "§4Zayıflık";
			break;
		case "SPEED":
			geridon = "§aHız";
			break;
		default:
			geridon = "§4HATA!";
			break;
		}
		return geridon;
	}

	private static ItemStack potTuru(Player p, String turu) {
		if (main.getConfig().getInt("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Pot-Efektleri." + turu) == 0) {
			ItemStack item = new ItemStack(Material.GLASS_BOTTLE, 1);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cTür §b" + UTF_8.sagusttenok + " " + turkcelestir(turu));
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§cDeaktif");
			imeta.setLore(lore);
			item.setItemMeta(imeta);
			return item;
		} else {
			ItemStack item = new ItemStack(Material.POTION, 1);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cTür §b" + UTF_8.sagusttenok + turkcelestir(turu));
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§aAktif");
			lore.add("§7Pot Efekt Gücü : §b"
					+ main.getConfig().getInt("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Pot-Efektleri." + turu));
			imeta.setLore(lore);
			item.setItemMeta(imeta);
			return item;
		}
	}

	@EventHandler
	private void tiklama2(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(potmenusu.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							EfektleriAyarla.oyuncuyaEfektDuzenlemeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else {
							for (int i = 0; i < potlar.size(); i++)
								if (e.getCurrentItem().equals(potTuru(p, potlar.get(i)))) {
									potDurumuDegistir(DuzenMenu.bossduzen.get(p.getName()), potlar.get(i));
									PotMenusu.oyuncuyaPotEnvanteriAc(p, DuzenMenu.bossduzen.get(p.getName()));
								}
						}

					}
				}

			}
		}
	}

	private void potDurumuDegistir(String mobadi, String potadi) {
		int potdurumu = main.getConfig().getInt("Bosslar." + mobadi + ".Pot-Efektleri." + potadi);
		if (potdurumu < maxpot)
			main.getConfig().set("Bosslar." + mobadi + ".Pot-Efektleri." + potadi, (potdurumu + 1));
		else
			main.getConfig().set("Bosslar." + mobadi + ".Pot-Efektleri." + potadi, null);
		main.saveConfig();
	}
}
