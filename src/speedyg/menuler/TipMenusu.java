package speedyg.menuler;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;
import speedyg.mesaj.UTF_8;

public class TipMenusu implements Listener {
	static Main main;

	public TipMenusu(Main plugin) {
		main = plugin;
	}

	private static String[] moblar = { "SKELETON", "ZOMBIE", "SPIDER", "CAVE_SPIDER", "BLAZE", "BAT", "PIG_ZOMBIE", "WITCH", "VILLAGER", "WOLF", "SLIME", "MAGMA_CUBE", "IRON_GOLEM", "GIANT" };
	public static HashMap<String, Inventory> tipmenusu = new HashMap<String, Inventory>();

	public static void oyuncuyaTipMenusuAc(Player p, String mobadi) {
		tipmenusu.put(mobadi, Bukkit.createInventory(null, 54, "§cTip seçiniz.."));
		for (int i = 18; i < 27; i++) {
			if (i != 22) {
				tipmenusu.get(mobadi).setItem(i, OdulMenusu.camlar());
			}
		}
		tipmenusu.get(mobadi).setItem(22, Menu.geriDonItemi());
		for (int i = 0; i < EntityType.values().length; i++) {
			tipmenusu.get(mobadi).setItem(i, mobegg(p, EntityType.values()[i]));
		}
		p.openInventory((Inventory) tipmenusu.get(mobadi));
	}

	@SuppressWarnings("deprecation")
	private static ItemStack mobegg(Player p, EntityType entityType) {
		ItemStack item = new ItemStack(Material.getMaterial("MOB_SPAWNER"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§cTür §b" + UTF_8.sagusttenok + " §4" + entityType);
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		if (main.getConfig().getString("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Mob-Tipi")
				.equals(entityType.getName())
				|| main.getConfig().getString("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Mob-Tipi")
						.contains(entityType.getName()))
			lore.add("§aŞuan bu mob türü aktif!");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private static void tiklamaEventi1(InventoryClickEvent e) {
		if ((e.getWhoClicked() instanceof Player)) {
			Player p = (Player) e.getWhoClicked();
			if ((e.getInventory() != null) && (e.getCurrentItem() != null)
					&& (e.getInventory().equals(tipmenusu.get(DuzenMenu.bossduzen.get(p.getName()))))) {
				e.setCancelled(true);
				if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
					DuzenMenu.duzenMenusuAc(p, (String) DuzenMenu.bossduzen.get(p.getName()));
				} else {
					for (int i = 0; i < EntityType.values().length; i++) {
						if (e.getCurrentItem().equals(mobegg(p, EntityType.values()[i]))) {
							mobTurDegistir(DuzenMenu.bossduzen.get(p.getName()), EntityType.values()[i]);
							DuzenMenu.duzenMenusuAc(p, (String) DuzenMenu.bossduzen.get(p.getName()));
							break;
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static void mobTurDegistir(String mobadi, EntityType entityType) {
		main.getConfig().set("Bosslar." + mobadi + ".Mob-Tipi", entityType.getName());
		main.saveConfig();
	}
}
