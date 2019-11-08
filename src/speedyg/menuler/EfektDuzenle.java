package speedyg.menuler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;
import speedyg.mesaj.UTF_8;

public class EfektDuzenle implements Listener {

	static Main main;

	public EfektDuzenle(Main plugin) {
		EfektDuzenle.main = plugin;
	}

	private final static int maxpot = 7;
	private static List<String> efektler = new ArrayList<String>();
	private static HashMap<String, Inventory> ozelEfetkler = new HashMap<String, Inventory>();

	public static void oyuncuyaOzelEfektDuzenlemeMenusuAc(Player p, String bossadi) {
		efektler = Arrays.asList("Isinlanma", "Oldurucu_Darbe", "Simsek_Saldirisi", "Buyuk_Ok_Yagmuru",
				"Buyuk_Alevli_Ok_Yagmuru", "Yardimcilar_Gonder", "Oyunculari_Yanina_Cek");

		ozelEfetkler.put(bossadi, Bukkit.createInventory(null, 27, "§cEfektleri düzenleyiniz..."));
		ozelEfetkler.get(bossadi).setItem(27 - 5, Menu.geriDonItemi());
		for (int i = 27 - 9; i < 27; i++)
			if (i != 27 - 5)
				ozelEfetkler.get(bossadi).setItem(i, OdulMenusu.camlar());
		for (int i = 0; i < efektler.size(); i++)
			ozelEfetkler.get(bossadi).setItem(i, efektTuru(p, efektler.get(i)));
		p.openInventory(ozelEfetkler.get(bossadi));
	}

	private static ItemStack efektTuru(Player p, String turu) {
		if (main.getConfig()
				.getInt("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Vurus-Efektleri." + turu) == 0) {
			ItemStack item = new ItemStack(Material.ENDER_PEARL);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cTür §b" + UTF_8.sagusttenok + " " + turu.replaceAll("_", " "));
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§cDeaktif");
			imeta.setLore(lore);
			item.setItemMeta(imeta);
			return item;
		} else {
			ItemStack item = new ItemStack(Material.getMaterial("EYE_OF_ENDER"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cTür §b" + UTF_8.sagusttenok + " " + turu.replaceAll("_", " "));
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§aAktif");
			lore.add("§7Vuruş Efekt Gücü: §b" + main.getConfig()
					.getInt("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Vurus-Efektleri." + turu));
			imeta.setLore(lore);
			imeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			item.setItemMeta(imeta);
			return item;
		}
	}

	@EventHandler
	private void tikTik(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(ozelEfetkler.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							EfektleriAyarla.oyuncuyaEfektDuzenlemeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else {
							for (int i = 0; i < efektler.size(); i++)
								if (e.getCurrentItem().equals(efektTuru(p, efektler.get(i)))) {
									efektDegistir(DuzenMenu.bossduzen.get(p.getName()), efektler.get(i));
									oyuncuyaOzelEfektDuzenlemeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
								}
						}
					}
				}
			}
		}

	}

	private void efektDegistir(String mobadi, String turu) {
		int potdurumu = main.getConfig().getInt("Bosslar." + mobadi + ".Vurus-Efektleri." + turu);
		if (potdurumu < maxpot)
			main.getConfig().set("Bosslar." + mobadi + ".Vurus-Efektleri." + turu, (potdurumu + 1));
		else
			main.getConfig().set("Bosslar." + mobadi + ".Vurus-Efektleri." + turu, null);
		main.saveConfig();
	}

}
