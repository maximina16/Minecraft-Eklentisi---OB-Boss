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
import speedyg.mesaj.Mesajlar;

public class EfektleriAyarla implements Listener {

	static Main main;

	public EfektleriAyarla(Main plugin) {
		EfektleriAyarla.main = plugin;
	}

	public static HashMap<String, Inventory> efetklerinMenusu = new HashMap<String, Inventory>();

	public static void oyuncuyaEfektDuzenlemeMenusuAc(Player p, String bossadi) {
		efetklerinMenusu.put(bossadi, Bukkit.createInventory(null, 18, "§cSeçim yapınız.."));
		for (int i = 9; i < 18; i++)
			if (i != 13)
				efetklerinMenusu.get(bossadi).setItem(i, OdulMenusu.camlar());
		efetklerinMenusu.get(bossadi).setItem(13, Menu.geriDonItemi());
		efetklerinMenusu.get(bossadi).setItem(2, potEfektleri(bossadi));
		efetklerinMenusu.get(bossadi).setItem(4, potSuresi());
		efetklerinMenusu.get(bossadi).setItem(6, digerEfektler(bossadi));
		p.openInventory(efetklerinMenusu.get(bossadi));
	}

	private static ItemStack potEfektleri(String bossadi) {
		if (main.getConfig().getConfigurationSection("Bosslar." + bossadi + ".Pot-Efektleri") != null) {
			if (main.getConfig().getConfigurationSection("Bosslar." + bossadi + ".Pot-Efektleri").getKeys(false)
					.size() > 0) {
				ItemStack item = new ItemStack(Material.POTION);

				ItemMeta imeta = item.getItemMeta();
				imeta.setDisplayName("§bPot Efektleri");
				ArrayList<String> lore = new ArrayList<String>();
				lore.clear();
				lore.add("§7Boss'unuza pot efektleri eklemek");
				lore.add("§7için tıklayınız.");
				imeta.setLore(lore);
				item.setItemMeta(imeta);

				return item;
			} else {
				ItemStack item = new ItemStack(Material.GLASS_BOTTLE);

				ItemMeta imeta = item.getItemMeta();
				imeta.setDisplayName("§bPot Efektleri");
				ArrayList<String> lore = new ArrayList<String>();
				lore.clear();
				lore.add("§7Boss'unuza pot efektleri eklemek");
				lore.add("§7için tıklayınız.");
				imeta.setLore(lore);
				item.setItemMeta(imeta);

				return item;
			}
		} else {
			ItemStack item = new ItemStack(Material.GLASS_BOTTLE);

			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§bPot Efektleri");
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§7Boss'unuza pot efektleri eklemek");
			lore.add("§7için tıklayınız.");
			imeta.setLore(lore);
			item.setItemMeta(imeta);

			return item;
		}
	}

	private static ItemStack digerEfektler(String bossadi) {
		ItemStack item = new ItemStack(Material.REDSTONE);

		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bVuruş Efektleri");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Boss'unuza özel efektler");
		lore.add("§7eklemek için tıklayınız.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack potSuresi() {
		ItemStack item = new ItemStack(Material.getMaterial("WATCH"));

		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bPot Sürelerini Ayarla");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Boss'unuza eklediğiniz pot efektlerinin");
		lore.add("§7sürelerini ayarlamak için tıklayın.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tiklamaEventiEf(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(efetklerinMenusu.get(DuzenMenu.bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(potEfektleri(DuzenMenu.bossduzen.get(p.getName())))) {
							PotMenusu.oyuncuyaPotEnvanteriAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(digerEfektler(DuzenMenu.bossduzen.get(p.getName())))) {
							EfektDuzenle.oyuncuyaOzelEfektDuzenlemeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(potSuresi())) {
							DuzenMenu.potsuresidegistir.add(p.getName());
							p.closeInventory();
							p.sendMessage(Mesajlar.pot_suresi_giriniz);
						}
					}
				}
			}
		}

	}
}
