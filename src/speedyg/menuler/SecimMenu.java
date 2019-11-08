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
import org.bukkit.inventory.meta.SkullMeta;

import speedyg.boss.Main;
import speedyg.eventler.BossEvent;
import speedyg.mesaj.UTF_8;

public class SecimMenu implements Listener {

	static Main main;

	public SecimMenu(Main plugin) {
		SecimMenu.main = plugin;
	}

	public static HashMap<Player, Inventory> secimmenusu = new HashMap<Player, Inventory>();

	public static void oyuncuDuzenlemeSecimMenusuAc(Player p) {

		secimmenusu.put(p, Bukkit.createInventory(null, 54, "§c§lLütfen boss seçiniz..!"));
		int a = 0;
		for (String bosslar : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
			if (a == 49)
				a++;
			if (a <= secimmenusu.get(p).getSize() - 9)
				secimmenusu.get(p).setItem(a, kafaEkleyici(bosslar));
			a++;
		}
		for (int i = 45; i < 54; i++)
			secimmenusu.get(p).setItem(i, OdulMenusu.camlar());
		secimmenusu.get(p).setItem(49, Menu.geriDonItemi());
		p.openInventory(secimmenusu.get(p));

	}

	public static ItemStack kafaEkleyici(String bossadi) {
		ItemStack kafa = new ItemStack(Material.getMaterial("SKULL_ITEM"), 1, (short) 3);
		SkullMeta kmeta = (SkullMeta) kafa.getItemMeta();
		kmeta.setDisplayName(
				"                 §7" + UTF_8.florya + " §b§lBoss Bilgileri §7" + UTF_8.florya + "                 ");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("  ");
		lore.add("§l§7     ---------------------------------");
		lore.add("     §7|");
		lore.add("     §7|      §7Boss Adı : §a"
				+ main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi").replaceAll("&", "§"));
		lore.add("     §7|      §7Tip : §a" + main.getConfig().getString("Bosslar." + bossadi + ".Mob-Tipi"));
		lore.add("     §7|      §7Toplam Can : §3" + main.getConfig().getDouble("Bosslar." + bossadi + ".Can"));
		lore.add("     §7|      §7Güç : §4" + main.getConfig().getInt("Bosslar." + bossadi + ".Guc"));
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Rastgele-Dogma"))
			lore.add("     §7|      §7Rastgele Doğma Uzaklık : §c"
					+ main.getConfig().getInt("Bosslar." + bossadi + ".Rastgele-Dogma-Uzaklik-Birim") + " birim");
		lore.add("     §7|      §7Otomatik Doğma Süresi : §6"
				+ Menu.sureCevirici((int) main.getConfig().getDouble("Bosslar." + bossadi + ".Sure")));
		lore.add("     §7|      §7Ödül Verim Şekli : §f"
				+ main.getConfig().getString("Bosslar." + bossadi + ".Odul-Verim-Sekli"));
		lore.add("     §7|      §7Özel Vuruş Efekti Kullanma Şansı : §b%"
				+ main.getConfig().getInt("Bosslar." + bossadi + ".Ozel-Vurus-Efekt-Kullanma-Sansi"));
		lore.add("     §7|      §7Boss'un Pot Süresi : §d"
				+ Menu.sureCevirici(main.getConfig().getInt("Bosslar." + bossadi + ".Pot-Suresi")));
		// Durum kontrolculeri
		if (!main.getConfig().getBoolean("Bosslar." + bossadi + ".Duyuru"))
			lore.add("     §7|      §7Öldürüldü Duyurusu : §c" + UTF_8.carpi);
		else
			lore.add("     §7|      §7Öldürüldü Duyurusu : §a" + UTF_8.tik);

		if (!main.getConfig().getBoolean("Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu"))
			lore.add("     §7|      §7Ölmeden Doğma : §c" + UTF_8.carpi);
		else
			lore.add("     §7|      §7Ölmeden Doğma : §a" + UTF_8.tik);

		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Boss-Durum"))
			lore.add("     §7|      §7Otomatik Doğma : §a" + UTF_8.tik);
		else
			lore.add("     §7|      §7Otomatik Doğma : §c" + UTF_8.carpi);
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Kutlama"))
			lore.add("     §7|      §7Öldürülünce Kutlama Yapılsın Mı ? : §a" + UTF_8.tik);
		else
			lore.add("     §7|      §7Öldürülünce Kutlama Yapılsın Mı ? : §c" + UTF_8.carpi);
		if (BossEvent.bosslarim.get(bossadi) != null) {
			if (BossEvent.mobaGit(BossEvent.bosslarim.get(bossadi)) != null) {
				if (!BossEvent.mobaGit(BossEvent.bosslarim.get(bossadi)).isDead())
					lore.add("     §7|      §7Boss Canlı Mı ? : §a" + UTF_8.tik);
				else
					lore.add("     §7|      §7Boss Canlı Mı ? : §c" + UTF_8.carpi);
			} else {
				lore.add("     §7|      §7Boss Canlı Mı ? : §c" + UTF_8.carpi);
			}
		} else {
			lore.add("     §7|      §7Boss Canlı Mı ? : §c" + UTF_8.carpi);
		}
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Rastgele-Dogma"))
			lore.add("     §7|      §7Rastgele Doğma : §a" + UTF_8.tik);
		else
			lore.add("     §7|      §7Rastgele Doğma : §c" + UTF_8.carpi);
		lore.add("     §7|");
		lore.add("§l§7     ---------------------------------");
		kmeta.setLore(lore);
		kafa.setItemMeta(kmeta);

		return kafa;
	}

	@EventHandler
	private void secimTiklama(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null)
				if (e.getCurrentItem() != null)
					if (e.getInventory().getName() != null)
						if (e.getInventory().equals(secimmenusu.get(p))) {
							e.setCancelled(true);
							if (e.getCurrentItem().hasItemMeta())
								if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
									p.closeInventory();
									Menu.oyuncuyaBossEnvanteriniAc(p);
								} else {
									for (String moblar : main.getConfig().getConfigurationSection("Bosslar")
											.getKeys(false))
										if (e.getCurrentItem().equals(SecimMenu.kafaEkleyici(moblar))) {
											DuzenMenu.duzenMenusuAc(p, moblar);
										}
								}
						}
		}
	}

}
