package speedyg.menuler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;

public class SansMenusu implements Listener {

	static Main main;

	public SansMenusu(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, Inventory> sansmenusu = new HashMap<String, Inventory>();

	public static void oyuncuyaSansMenusuAc(Player p, String mobadi) {
		sansmenusu.put(mobadi, Bukkit.createInventory(null, OdulMenusu.maxodul, "§cDrop şanslarını ayarlarınız.."));

		for (int i = OdulMenusu.maxodul - 9; i < OdulMenusu.maxodul; i++) {
			if (i != OdulMenusu.maxodul - 5)
				sansmenusu.get(mobadi).setItem(i, OdulMenusu.camlar());
		}
		for (int a = 0; a < OdulMenusu.maxodul - 9; a++) {
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Oduller." + a) != null)
				sansmenusu.get(mobadi).setItem(a, sansGosterici(mobadi, a));
		}

		sansmenusu.get(mobadi).setItem(OdulMenusu.maxodul - 5, Menu.geriDonItemi());

		p.openInventory(sansmenusu.get(mobadi));
	}

	public static ItemStack sansGosterici(String bossadi, int a) {
		ItemStack item = new ItemStack(main.getConfig().getItemStack("Bosslar." + bossadi + ".Oduller." + a));
		ItemMeta imeta = item.getItemMeta();
		List<String> lore = new ArrayList<String>();
		lore = Arrays.asList(" ", "§c§l-%5 §r§7Sol Tık §8|§r §7Sağ Tık§r §a§l+%5",
				"    §b§lDüşme Şansı  §e§l%" + main.getConfig().getInt("Bosslar." + bossadi + ".Drop-Sans." + a));
		List<String> asillore;
		if (imeta.hasLore())
			asillore = imeta.getLore();
		else
			asillore = new ArrayList<String>();
		for (int i = 0; i < lore.size(); i++)
			asillore.add(lore.get(i));
		imeta.setLore(asillore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tiklamaSansEvent(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(sansmenusu.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							Odul_Komutlar_Secim_Menusu.odulkomutenvac(p, DuzenMenu.bossduzen.get(p.getName()));
						} else {
							for (int i = 0; i < OdulMenusu.maxodul - 9; i++) {
								if (e.getSlot() == i) {
									int sans = main.getConfig().getInt(
											"Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Drop-Sans." + i);
									if (e.getClick().equals(ClickType.RIGHT)
											|| e.getClick().equals(ClickType.SHIFT_RIGHT)) {
										// artir
										if ((sans + 5) <= 100)
											main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName())
													+ ".Drop-Sans." + i, (sans + 5));
									} else if (e.getClick().equals(ClickType.LEFT)
											|| e.getClick().equals(ClickType.SHIFT_LEFT)) {
										// azalt
										if ((sans - 5) >= 0)
											main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName())
													+ ".Drop-Sans." + i, (sans - 5));
									}
									main.saveConfig();
									SansMenusu.oyuncuyaSansMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
								}

							}
						}
					}
				}
			}
		}
	}

}
