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
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;
import speedyg.eventler.BossEvent;
import speedyg.mesaj.Mesajlar;

public class Menu implements Listener {

	static Main main;

	public Menu(Main plugin) {
		Menu.main = plugin;
	}

	public static HashMap<String, Inventory> bossmenusu = new HashMap<String, Inventory>();
	public static ArrayList<String> bossolusturma = new ArrayList<String>();

	public static void oyuncuyaBossEnvanteriniAc(Player p) {
		bossmenusu.put(p.getName(),
				Bukkit.createInventory(null, 45, "§8§lOB-Boss §0§lv" + main.getDescription().getVersion()));
		bossmenusu.get(p.getName()).setItem(24, bossDuzenle());
		bossmenusu.get(p.getName()).setItem(32, bilgilendirmeItemi());
		bossmenusu.get(p.getName()).setItem(4, bossOlusturucu());
		bossmenusu.get(p.getName()).setItem(20, genelDuyuru());
		bossmenusu.get(p.getName()).setItem(14, bossManuelDogurt());
		bossmenusu.get(p.getName()).setItem(40, menuKapat());
		bossmenusu.get(p.getName()).setItem(12, bossSil());
		bossmenusu.get(p.getName()).setItem(30, reloadButon());

		p.openInventory(bossmenusu.get(p.getName()));
	}

	private static ItemStack reloadButon() {
		ItemStack item = new ItemStack(Material.SLIME_BALL);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bGüncelle");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Bossları güncellemek için, §3zamanlayıcının");
		lore.add("§3başlaması§7 ve dosyaların güncellenmesi için");
		lore.add("§7bu butonu kullanabilirsiniz.");
		lore.add(" ");
		lore.add("§4!§r§cBu butona basmadan zamanlayıcı süreleri güncellenmez.");
		lore.add(" ");
		lore.add("§aTüm bosslarınızı ayarlayıp en son bu");
		lore.add("§abuton ile sistemi çalıştırınız");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack bossManuelDogurt() {
		ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bManüel Boss Doğurt");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Manuel olarak boss doğurtma");
		lore.add("§7işlemi için tıklayınız ve bir");
		lore.add("§7boss seçiniz..");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	public static ItemStack menuKapat() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bMenü Kapat");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Menüyü kapatmatk için tıklayınız.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack genelDuyuru() {
		if (!main.getConfig().getBoolean("Boss-Dogma-Duyurusu")) {
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§aBoss Doğma Duyurusunu Aç");
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§7Bosslar doğduğunda duyuru olarak");
			lore.add("§7ilan edilmesini §aistiyorsanız §7tıklayın.");
			imeta.setLore(lore);
			item.setItemMeta(imeta);
			return item;
		} else {
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cBoss Doğma Duyurusunu Kapat");
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§7Bosslar doğduğunda duyuru olarak");
			lore.add("§7ilan edilmesini §cistemiyorsanız§7 tıklayın.");
			imeta.setLore(lore);
			item.setItemMeta(imeta);
			return item;
		}
	}

	private static ItemStack bossSil() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§cBoss Silici");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Tıkladığınızda boss listesinin bulunduğu");
		lore.add("§7menü açılır, bosslardan hangisine");
		lore.add("§7tıklarsanız, o boss §c§nsilinir!§r");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack bossDuzenle() {
		ItemStack item = new ItemStack(Material.ENDER_CHEST);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bBoss Düzenleyici");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Bossların tüm düzenlemelerini");
		lore.add("§7bu menüdenyapabilirsiniz.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack bilgilendirmeItemi() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bBilgilendirme");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Boss'ları doğurma lokasyonunu ayarlamadan önce düzenleyiniz!");
		lore.add("§7Boss ile ilgili tüm özellikleri ve loksayonunu ayarlamak için düzenleme");
		lore.add("§7menüsüne giriniz. Istediğiniz şekilde düzenledikten sonra boss doğmasını");
		lore.add("§7aktifleştirmek için §aBoss Aç §7butonuna tıklayınız. Daha sonra");
		lore.add("§7bu meüdeki §bGüncelle§7 butonuna basınız. Belirlediğiniz sürede bosslar doğacaktır!");
		lore.add(" ");
		lore.add("§7Eklenti Sahibi : §bwww.mcoyuncubilgisi.com");
		lore.add("§7Eklenti Kodlayıcısı : §bYusuf Serhat Özgen");
		lore.add("§7Destek olmak için ininal barkod kodumuz : §b4091680064563");
		lore.add(" ");
		lore.add("§7Özel eklenti yaptırmak ve ya destek almak için,");
		lore.add("§7Discord Adresim; §bYusuf#7761");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack bossOlusturucu() {
		ItemStack item = new ItemStack(Material.getMaterial("SKULL_ITEM"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§aBoss Oluştur");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Yeni boss oluşturmak için tıklayınız!");
		lore.add("§7Tıkladığınızda chat açılır ve bir isim");
		lore.add("§7yazmanız istenir.Yazdıktan sonra otomatik");
		lore.add("§7olarak menüye yönlendirilirsiniz. Daha sonra");
		lore.add("§7düzenle menüsünden boss düzenleyebilirsiniz.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);
		return item;
	}

	@EventHandler
	private void tiklamEventi(InventoryClickEvent e) {
		if (e.getInventory() != null)
			if (e.getInventory().getName() != null)
				if (e.getWhoClicked() instanceof Player) {
					Player p = (Player) e.getWhoClicked();
					if (e.getInventory().equals(bossmenusu.get(p.getName())))
						if (e.getCurrentItem() != null)
							if (e.getCurrentItem().hasItemMeta()) {
								e.setCancelled(true);
								if (e.getCurrentItem().equals(Menu.bilgilendirmeItemi())) {
									p.closeInventory();
									p.sendMessage(
											"§7Bu eklenti §bwww.oyuncubilgisi.com §7ve §bwww.mcoyuncubilgisi.com§7 adminleri tarafından kodlanmıştır!");
									p.sendMessage("§7Bu mesaja sadece §c§ladminler§r§7 erişebilir!");
								} else if (e.getCurrentItem().equals(Menu.bossManuelDogurt())) {
									ManuelDogurMenu.oyuncuyaDogurSecmenuAc(p);
								} else if (e.getCurrentItem().equals(Menu.bossOlusturucu())) {
									p.closeInventory();
									Menu.bossolusturma.add(p.getName());
									p.sendMessage(Mesajlar.bossolusturunuz);
								} else if (e.getCurrentItem().equals(Menu.bossDuzenle())) {
									SecimMenu.oyuncuDuzenlemeSecimMenusuAc(p);
								} else if (e.getCurrentItem().equals(Menu.bossSil())) {
									SilmeMenu.oyuncuyaSilmeMenusuAc(p);
								} else if (e.getCurrentItem().equals(menuKapat())) {
									p.closeInventory();
								} else if (e.getCurrentItem().equals(Menu.genelDuyuru())) {
									dogmaDuyuruDeigsitir();
									Menu.oyuncuyaBossEnvanteriniAc(p);

								} else if (e.getCurrentItem().equals(Menu.reloadButon())) {
									main.reloadAt(p);
								}
							}

				}

	}

	private void dogmaDuyuruDeigsitir() {
		boolean duyuru = main.getConfig().getBoolean("Boss-Dogma-Duyurusu");
		if (duyuru) {
			duyuru = false;
			main.getConfig().set("Boss-Dogma-Duyurusu", duyuru);
		} else {
			duyuru = true;
			main.getConfig().set("Boss-Dogma-Duyurusu", duyuru);
		}
		main.saveConfig();
	}

	@EventHandler
	private void chatKontrolEdici(AsyncPlayerChatEvent e) {
		if (e.getPlayer() instanceof Player) {
			Player p = e.getPlayer();
			if (Menu.bossolusturma.contains(p.getName())) {
				e.setCancelled(true);
				if (!main.getConfig().getConfigurationSection("Bosslar").getKeys(false).contains(e.getMessage())) {
					String bossadi = e.getMessage();
					main.getConfig().set("Bosslar." + bossadi + ".Gosterim-Adi", "&3Standart Boss");
					main.getConfig().set("Bosslar." + bossadi + ".Boss-Durum", false);
					main.getConfig().set("Bosslar." + bossadi + ".Mob-Tipi", "SKELETON");
					main.getConfig().set("Bosslar." + bossadi + ".Can", 1000);
					main.getConfig().set("Bosslar." + bossadi + ".Duyuru", false);
					main.getConfig().set("Bosslar." + bossadi + ".Guc", 1);
					main.getConfig().set("Bosslar." + bossadi + ".Sure", 300);
					main.getConfig().set("Bosslar." + bossadi + ".Rastgele-Dogma-Uzaklik-Birim", 20);
					main.getConfig().set("Bosslar." + bossadi + ".Rastgele-Dogma", false);
					main.getConfig().set("Bosslar." + bossadi + ".Odul-Verim-Sekli", "Envanter");
					Main.lokayar.set("Lokasyonlar." + bossadi + ".x", 1);
					Main.lokayar.set("Lokasyonlar." + bossadi + ".y", 63);
					Main.lokayar.set("Lokasyonlar." + bossadi + ".z", 1);
					Main.lokayar.set("Lokasyonlar." + bossadi + ".world", "world");
					BossEvent.bosslariDondur(bossadi);
					Main.save(Main.lokayar, Main.lokasyondosyasi);
					main.saveConfig();
					p.sendMessage(Mesajlar.bossolusturuldu);
					Menu.bossolusturma.remove(p.getName());
					Menu.oyuncuyaBossEnvanteriniAc(p);

				} else {
					p.sendMessage(Mesajlar.bubossvar);

				}
			}

			if (DuzenMenu.isimdegistirici.contains(p.getName())) {
				e.setCancelled(true);
				String degiscekisim = e.getMessage();
				main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Gosterim-Adi", degiscekisim);
				main.saveConfig();
				p.sendMessage(Mesajlar.isimdegistirildi.replaceAll("<isim>", degiscekisim.replaceAll("&", "§")));
				DuzenMenu.isimdegistirici.remove(p.getName());
				DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
			}

			if (DuzenMenu.candegistirici.contains(p.getName())) {
				e.setCancelled(true);
				if (isInt(e.getMessage())) {
					Integer can = Integer.parseInt(e.getMessage());
					main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Can", can);
					main.saveConfig();
					p.sendMessage(Mesajlar.cantamamlandi);
					DuzenMenu.candegistirici.remove(p.getName());
					DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));

				} else {
					p.sendMessage(Mesajlar.sayigiriniz);
				}
			}

			if (DuzenMenu.dogmasuresidegistir.contains(p.getName())) {
				e.setCancelled(true);
				if (isInt(e.getMessage())) {
					Integer sure = Integer.parseInt(e.getMessage());
					main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Sure", sure);
					main.saveConfig();
					p.sendMessage(Mesajlar.suretamamlandi);
					DuzenMenu.dogmasuresidegistir.remove(p.getName());
					DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));

				} else {
					p.sendMessage(Mesajlar.sayigiriniz);
				}
			}

			if (DuzenMenu.komutekle.contains(p.getName())) {
				e.setCancelled(true);
				List<String> komutlar = main.getConfig()
						.getStringList("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Komutlar");
				String komut = e.getMessage();
				komutlar.add(komut);
				main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Komutlar", komutlar);
				main.saveConfig();
				DuzenMenu.komutekle.remove(p.getName());
				KomutMenusu.komutMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
			}

			if (DuzenMenu.rastgeledegistir.contains(p.getName())) {
				e.setCancelled(true);
				if (isInt(e.getMessage())) {
					Integer birim = Integer.parseInt(e.getMessage());
					main.getConfig().set(
							"Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Rastgele-Dogma-Uzaklik-Birim", birim);
					main.saveConfig();
					p.sendMessage(Mesajlar.birimbasarili);
					DuzenMenu.rastgeledegistir.remove(p.getName());
					RastgeleAyarMenusu.oyuncuyaRastgeleMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));

				} else {
					p.sendMessage(Mesajlar.sayigiriniz);
				}
			}

			if (DuzenMenu.potsuresidegistir.contains(p.getName())) {
				e.setCancelled(true);
				if (isInt(e.getMessage())) {
					int birim = Integer.parseInt(e.getMessage());
					main.getConfig().set("Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Pot-Suresi", birim);
					main.saveConfig();
					p.sendMessage(Mesajlar.pot_suresi_degisti);
					DuzenMenu.potsuresidegistir.remove(p.getName());
					EfektleriAyarla.oyuncuyaEfektDuzenlemeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
				} else {
					p.sendMessage(Mesajlar.sayigiriniz);
				}
			}

			if (DuzenMenu.efektsansidegistir.contains(p.getName())) {
				e.setCancelled(true);
				if (isInt(e.getMessage())) {
					int birim = Integer.parseInt(e.getMessage());
					if (birim >= 0 && birim <= 100) {
						main.getConfig().set(
								"Bosslar." + DuzenMenu.bossduzen.get(p.getName()) + ".Ozel-Vurus-Efekt-Kullanma-Sansi",
								birim);
						main.saveConfig();
						p.sendMessage(Mesajlar.efekt_sansi_degisti);
						DuzenMenu.efektsansidegistir.remove(p.getName());
						DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
					} else {
						p.sendMessage(Mesajlar.kucuk_sayi_giriniz);
					}
				} else {
					p.sendMessage(Mesajlar.sayigiriniz);
				}
			}

		}
	}

	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static ItemStack geriDonItemi() {
		ItemStack item = new ItemStack(Material.ARROW);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bGeri Dön");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Bir önceki menüye geri dönmek");
		lore.add("§7için tıklayabilirsiniz.");
		imeta.setLore(lore);
		item.setItemMeta(imeta);

		return item;
	}

	public static String sureCevirici(int sure) {
		int saniye = sure;
		int dakika, saat;
		dakika = saniye / 60;
		saniye = saniye % 60;
		saat = dakika / 60;
		dakika = dakika % 60;

		if (saat > 0)
			return saat + " saat, " + dakika + " dakika, " + saniye + " saniye";
		else if (dakika > 0)
			return dakika + " dakika, " + saniye + " saniye";
		else
			return saniye + " saniye";
	}

}