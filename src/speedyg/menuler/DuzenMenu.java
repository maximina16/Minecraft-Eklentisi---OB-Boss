package speedyg.menuler;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import speedyg.boss.Main;
import speedyg.eventler.BossEvent;
import speedyg.mesaj.Mesajlar;
import speedyg.mesaj.UTF_8;

public class DuzenMenu implements Listener {

	static Main main;

	public DuzenMenu(Main plugin) {
		DuzenMenu.main = plugin;
	}

	public static HashMap<String, Inventory> duzenmenusu = new HashMap<String, Inventory>();
	public static HashMap<String, String> bossduzen = new HashMap<String, String>();

	public static ArrayList<String> isimdegistirici = new ArrayList<String>();
	public static ArrayList<String> candegistirici = new ArrayList<String>();
	public static ArrayList<String> dogmasuresidegistir = new ArrayList<String>();
	public static ArrayList<String> komutekle = new ArrayList<String>();
	public static ArrayList<String> rastgeledegistir = new ArrayList<String>();
	public static ArrayList<String> efektsansidegistir = new ArrayList<String>();
	public static ArrayList<String> potsuresidegistir = new ArrayList<String>();

	public static void duzenMenusuAc(Player p, String bossadi) {
		bossduzen.put(p.getName(), bossadi);
		if (main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi") != null)
			duzenmenusu.put(bossadi, Bukkit.createInventory(null, 54, "§c" + UTF_8.sagusttenok
					+ main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi").replaceAll("&", "§")));
		else
			duzenmenusu.put(bossadi, Bukkit.createInventory(null, 54, "§c" + UTF_8.sagusttenok + "Boss Adı Hatası!"));
		duzenmenusu.get(bossadi).setItem(12, isimDegistir());
		duzenmenusu.get(bossadi).setItem(30, rastgeleDogur(bossadi));
		duzenmenusu.get(bossadi).setItem(32, sansDegistir());
		duzenmenusu.get(bossadi).setItem(11, mobTipDegistir());
		duzenmenusu.get(bossadi).setItem(15, canDegistir());
		duzenmenusu.get(bossadi).setItem(20, lokasyonAyarlayici(bossadi));
		duzenmenusu.get(bossadi).setItem(27, efeklerinItemis(bossadi));
		duzenmenusu.get(bossadi).setItem(4, SecimMenu.kafaEkleyici(bossadi));
		duzenmenusu.get(bossadi).setItem(13, dogmaAyariButonu(bossadi));
		duzenmenusu.get(bossadi).setItem(24, dogmaSuresiAyarlayiciitem());
		duzenmenusu.get(bossadi).setItem(22, bossAcKapa(bossadi));
		duzenmenusu.get(bossadi).setItem(28, duyuruDegistir(bossadi));
		duzenmenusu.get(bossadi).setItem(25, gucDegistir());
		duzenmenusu.get(bossadi).setItem(31, odulleriAyarla());
		duzenmenusu.get(bossadi).setItem(40, bossYaninaGit());
		duzenmenusu.get(bossadi).setItem(19, bossGiydir());
		duzenmenusu.get(bossadi).setItem(35, kutlamaAcKapa(bossadi));
		duzenmenusu.get(bossadi).setItem(34, verimSekli(bossadi));
		duzenmenusu.get(bossadi).setItem(14, worlddekiBossSil(bossadi));

		for (int i = 45; i < 54; i++)
			duzenmenusu.get(bossadi).setItem(i, OdulMenusu.camlar());
		duzenmenusu.get(bossadi).setItem(49, Menu.geriDonItemi());
		p.openInventory(duzenmenusu.get(bossadi));
	}

	private static ItemStack dogmaAyariButonu(String bossadi) {
		ItemStack item;
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu")) {
			item = new ItemStack(Material.WATER_BUCKET);
		} else {
			item = new ItemStack(Material.getMaterial("BUCKET"));
		}
		ItemMeta imeta = item.getItemMeta();
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu")) {
			imeta.setDisplayName("§cÖldürüp Tekrar Doğurma Kapat");
		} else {
			imeta.setDisplayName("§aÖldürüp Tekrar Doğurma Aç");
		}
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Eğer açık konumda olursa boss ölmese bile");
		lore.add("§7boss doğma zamanı geldiğinde ölmeyen boss'u");
		lore.add("§7yok edip yerine yeni bir boss doğurur. Eğer");
		lore.add("§7kapalı konumda kalırsa boss ölene kadar bekler.");
		lore.add("§7Boss öldükten sonra yeni boss doğurur.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack worlddekiBossSil(String bossadi) {
		ItemStack item = new ItemStack(Material.getMaterial("COMPASS"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bDoğmuş Boss'u Sil");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Şuanda doğmuş olan boss'u siler");
		lore.add("§7silmek için tıklayabilirsiniz.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack bossYaninaGit() {
		ItemStack item = new ItemStack(Material.getMaterial("ENDER_PEARL"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bBoss'un Yanına Git");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Şuanda doğmuş olan boss'un yanına");
		lore.add("§7ışınlanırsınız.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack rastgeleDogur(String bossadi) {
		ItemStack item = new ItemStack(Material.getMaterial("MAP"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bRastgele Doğma Ayarları");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Rastgele doğma ayarlarına girmek");
		lore.add("§7ve düzenlemek için tıklayınız.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack kutlamaAcKapa(String bossadi) {
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Kutlama")) {
			ItemStack item = new ItemStack(Material.getMaterial("FIREWORK"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cKutlama Kapat");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda boss kesildiğinde kutlama");
			lore.add("§7yapılması §cengellenir§7.");
			imeta.setLore(lore);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		} else {
			ItemStack item = new ItemStack(Material.getMaterial("FIREWORK"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§aKutlama Aç");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda boss kesildiğinde kutlama");
			lore.add("§7yapılması §asağlanır§7.");
			imeta.setLore(lore);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		}
	}

	private static ItemStack efeklerinItemis(String bossadi) {
		ItemStack item = new ItemStack(Material.getMaterial("REDSTONE"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bEfekt Sistemi");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda efeklerin ayar menüsü açılır ve");
		lore.add("§7efektleri düzenlemenize imkan sağlanır.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);
		return item;
	}

	private static ItemStack bossAcKapa(String bossadi) {
		if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Boss-Durum")) {
			ItemStack item = new ItemStack(Material.getMaterial("LEVER"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cBoss Kapat");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda boss pasifleştirilir ve");
			lore.add("§7normal döngüde doğması §cengellenir§7.");
			imeta.setLore(lore);
			imeta.addEnchant(Enchantment.DURABILITY, 5, true);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		} else {
			ItemStack item = new ItemStack(Material.getMaterial("LEVER"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§aBoss Aç");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda boss aktifleştirilir ve");
			lore.add("§7normal döngüde doğması §asağlanır§7.");
			lore.add("§cTüm düzenlemeleri yaptıktan sonra açmanız önerilir!");
			imeta.setLore(lore);
			imeta.addEnchant(Enchantment.DURABILITY, 5, true);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		}
	}

	private static ItemStack lokasyonAyarlayici(String bossadi) {
		ItemStack item = new ItemStack(Material.getMaterial("BED"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bLokasyon Sistemi");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda, lokasyonunuz boss doğma");
		lore.add("§7lokasyonu olarak ayarlanır!");
		lore.add("§7Güncel Lokasyon Bilgileri;");
		lore.add("§7X: §b" + Main.lokayar.getInt("Lokasyonlar." + bossadi + ".x"));
		lore.add("§7Y: §b" + Main.lokayar.getInt("Lokasyonlar." + bossadi + ".y"));
		lore.add("§7Z: §b" + Main.lokayar.getInt("Lokasyonlar." + bossadi + ".z"));
		lore.add("§7Dünya: §b" + Main.lokayar.getString("Lokasyonlar." + bossadi + ".world"));
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack dogmaSuresiAyarlayiciitem() {
		ItemStack item = new ItemStack(Material.getMaterial("WATCH"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bDoğma Süresi Ayarla");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda, chatten bir süre girmeniz");
		lore.add("§7istenir. Bu sayı saniye cinsinde doğma");
		lore.add("§7süresi olarak ayarlanır!");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack verimSekli(String mobadi) {
		if (main.getConfig().getString("Bosslar." + mobadi + ".Odul-Verim-Sekli").equals("Yer")
				|| main.getConfig().getString("Bosslar." + mobadi + ".Odul-Verim-Sekli").contains("Yer")) {
			ItemStack item = new ItemStack(Material.getMaterial("HOPPER"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§aÖdülü Envantere Ver");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda, boss kesildiğinde itemleri");
			lore.add("§7yere düşmez, §aenvantere verilir§7!");
			imeta.setLore(lore);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		} else {
			ItemStack item = new ItemStack(Material.getMaterial("HOPPER"));
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§cÖdülü Yere Düşür");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda, boss kesildiğinde itemleri");
			lore.add("§7envantere vermez, §cyere düşürülür§7!");
			imeta.setLore(lore);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		}
	}

	private static ItemStack odulleriAyarla() {
		ItemStack item = new ItemStack(Material.getMaterial("CHEST"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bÖdülleri/Dropları Ayarla");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda ödül ayarlama");
		lore.add("§7ve düzenleme menüsü açılır. Boss");
		lore.add("§7ile ilgili dropları ve ödülleri");
		lore.add("§7buradan ayarlayabilirsiniz.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack gucDegistir() {
		ItemStack item = new ItemStack(Material.getMaterial("DIAMOND_SWORD"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bGüç Değiştir");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda güç değiştirme menüsü");
		lore.add("§7açılır ve güç seçmeniz istenir.");
		lore.add("§7Güç seçtikten sonra otomatik olarak düzenlemeye");
		lore.add("§7 geri döndürülürsünüz.");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;
	}

	private static ItemStack isimDegistir() {
		ItemStack item = new ItemStack(Material.getMaterial("NAME_TAG"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bİsim Değiştir");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda chat açılır ve isim");
		lore.add("§7girmeniz istenir. Renk kodları kullanabilirsiniz;");
		lore.add("§8&8§a,§e&e§a,§d&d§a,§4&4§a,§6&6§a,§9&9§a,§7&7§a,§b&b§a...");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;

	}

	private static ItemStack canDegistir() {
		ItemStack item = new ItemStack(Material.APPLE);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bCan Değiştir");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda chat açılır ve can");
		lore.add("§7girmeniz istenir. Sayı harici birşey");
		lore.add("§7girerseniz §r§c§nhata§r§7 verecektir!");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;

	}

	private static ItemStack sansDegistir() {
		ItemStack item = new ItemStack(Material.BEACON);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bEfekt Kullanım Şansı Değiştir");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda chat açılır ve şans");
		lore.add("§7girmeniz istenir. Sayı harici birşey");
		lore.add("§7girerseniz veya 100'den büyük 0'dan küçük");
		lore.add("§7bir değer girerseniz §r§c§nhata§r§7 verecektir!");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);
		return item;

	}

	private static ItemStack mobTipDegistir() {
		ItemStack item = new ItemStack(Material.getMaterial("MOB_SPAWNER"));
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bMob Değiştir");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("§7Tıkladığınızda mob değiştirme menüsü açılır ve");
		lore.add("§7istediğiniz boss ile güncellemenize olanak sağlar!");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;

	}

	private static ItemStack duyuruDegistir(String mobadi) {
		if (main.getConfig().getBoolean("Bosslar." + mobadi + ".Duyuru")) {
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§4Duyuru Kapat");
			ArrayList<String> lore = new ArrayList<String>();
			lore.add("§7Tıkladığınızda duyuru kapatır yani");
			lore.add("§7oyuncular bu bossu öldürdüğünde");
			lore.add("§7duyuru §cyapılmamasını§7 sağlar.");
			imeta.setLore(lore);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		} else {
			ItemStack item = new ItemStack(Material.PAPER);
			ItemMeta imeta = item.getItemMeta();
			imeta.setDisplayName("§aDuyuru Aç");
			ArrayList<String> lore = new ArrayList<String>();
			lore.clear();
			lore.add("§7Tıkladığınızda duyuru açar yani");
			lore.add("§7oyuncular bu bossu öldürdüğünde");
			lore.add("§7duyuru §ayapılmasını§7 sağlar.");
			imeta.setLore(lore);
			imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
			imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
			imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
			item.setItemMeta(imeta);

			return item;
		}

	}

	private static ItemStack bossGiydir() {
		ItemStack item = new ItemStack(Material.DIAMOND_CHESTPLATE);
		ItemMeta imeta = item.getItemMeta();
		imeta.setDisplayName("§bBoss Giydir");
		ArrayList<String> lore = new ArrayList<String>();
		lore.clear();
		lore.add("§7Tıkladığınızda boş menü açar ve");
		lore.add("§7boss'a giydirmek istediğiniz itemleri");
		lore.add("§7koymanıza olanak sağlar!");
		imeta.setLore(lore);
		imeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		imeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		imeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		imeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		imeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		item.setItemMeta(imeta);

		return item;

	}

	@EventHandler
	private void duzenlemeEditTiklama(InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			Player p = (Player) e.getWhoClicked();
			if (e.getInventory() != null) {
				if (e.getCurrentItem() != null) {
					if (e.getInventory().equals(duzenmenusu.get(bossduzen.get(p.getName())))) {
						e.setCancelled(true);
						if (e.getCurrentItem().equals(Menu.geriDonItemi())) {
							SecimMenu.oyuncuDuzenlemeSecimMenusuAc(p);
						} else if (e.getCurrentItem().equals(efeklerinItemis(DuzenMenu.bossduzen.get(p.getName())))) {
							EfektleriAyarla.oyuncuyaEfektDuzenlemeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(bossGiydir())) {
							BossGiydir.oyuncuyaBossGiydirmeMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(bossAcKapa(DuzenMenu.bossduzen.get(p.getName())))) {
							bossdurumDegistir(DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem()
								.equals(lokasyonAyarlayici(DuzenMenu.bossduzen.get(p.getName())))) {
							lokasyonunuMobLokYap(p, DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(dogmaSuresiAyarlayiciitem())) {
							p.closeInventory();
							dogmasuresidegistir.add(p.getName());
							p.sendMessage(Mesajlar.suregiriniz);
						} else if (e.getCurrentItem().equals(duyuruDegistir(DuzenMenu.bossduzen.get(p.getName())))) {
							duyuruayarla(DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(mobTipDegistir())) {
							TipMenusu.oyuncuyaTipMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(canDegistir())) {
							p.closeInventory();
							candegistirici.add(p.getName());
							p.sendMessage(Mesajlar.cangiriniz);
						} else if (e.getCurrentItem().equals(sansDegistir())) {
							p.closeInventory();
							efektsansidegistir.add(p.getName());
							p.sendMessage(Mesajlar.efekt_sansi_giriniz);
						} else if (e.getCurrentItem().equals(kutlamaAcKapa(DuzenMenu.bossduzen.get(p.getName())))) {
							kutlamaDegistir(DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem()
								.equals(DuzenMenu.dogmaAyariButonu(DuzenMenu.bossduzen.get(p.getName())))) {
							dogmaAyariDegistir(DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(isimDegistir())) {
							p.closeInventory();
							isimdegistirici.add(p.getName());
							p.sendMessage(Mesajlar.isimgiriniz);
						} else if (e.getCurrentItem().equals(gucDegistir())) {
							GucMenusu.gucMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(odulleriAyarla())) {
							Odul_Komutlar_Secim_Menusu.odulkomutenvac(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(verimSekli(DuzenMenu.bossduzen.get(p.getName())))) {
							verimSekliDegistir(DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(worlddekiBossSil(DuzenMenu.bossduzen.get(p.getName())))) {
							bossuOldur(DuzenMenu.bossduzen.get(p.getName()));
							DuzenMenu.duzenMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(rastgeleDogur(DuzenMenu.bossduzen.get(p.getName())))) {
							RastgeleAyarMenusu.oyuncuyaRastgeleMenusuAc(p, DuzenMenu.bossduzen.get(p.getName()));
						} else if (e.getCurrentItem().equals(bossYaninaGit())) {
							p.closeInventory();
							if (BossEvent.bosslarim.get(DuzenMenu.bossduzen.get(p.getName())) != null)
								if (BossEvent
										.mobaGit(BossEvent.bosslarim.get(DuzenMenu.bossduzen.get(p.getName()))) != null)
									if (!BossEvent
											.mobaGit(BossEvent.bosslarim.get(DuzenMenu.bossduzen.get(p.getName())))
											.isDead())
										p.teleport(BossEvent
												.mobaGit(BossEvent.bosslarim.get(DuzenMenu.bossduzen.get(p.getName())))
												.getLocation());
									else
										p.sendMessage(Mesajlar.prefix + "§cBu boss canlı değil!");
								else
									p.sendMessage(Mesajlar.prefix + "§cBu boss canlı değil!");
							else
								p.sendMessage(Mesajlar.prefix + "§cBu boss canlı değil!");
						}
					}
				}

			}
		}
	}

	private void bossuOldur(String mobadi) {
		if (BossEvent.bosslarim.get(mobadi) != null) {
			Entity mob = BossEvent.mobaGit(BossEvent.bosslarim.get(mobadi));
			mob.remove();
			BossEvent.bosslarim.remove(mobadi);
		}
	}

	private void kutlamaDegistir(String bossadi) {
		boolean kutlama = main.getConfig().getBoolean("Bosslar." + bossadi + ".Kutlama");
		main.getConfig().set("Bosslar." + bossadi + ".Kutlama", !kutlama);
		main.saveConfig();
	}

	private void dogmaAyariDegistir(String bossadi) {
		boolean cyoybdk = main.getConfig().getBoolean("Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu");
		main.getConfig().set("Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu", !cyoybdk);
		main.saveConfig();
	}

	private void bossdurumDegistir(String mob) {
		boolean durum = main.getConfig().getBoolean("Bosslar." + mob + ".Boss-Durum");
		main.getConfig().set("Bosslar." + mob + ".Boss-Durum", !durum);
		main.saveConfig();

	}

	private void lokasyonunuMobLokYap(Player p, String string) {
		double x = p.getLocation().getX();
		double y = p.getLocation().getY();
		double z = p.getLocation().getZ();
		String world = p.getWorld().getName();
		Main.lokayar.set("Lokasyonlar." + string + ".x", x);
		Main.lokayar.set("Lokasyonlar." + string + ".y", y);
		Main.lokayar.set("Lokasyonlar." + string + ".z", z);
		Main.lokayar.set("Lokasyonlar." + string + ".world", world);
		Main.save(Main.lokayar, Main.lokasyondosyasi);
		p.sendMessage(Mesajlar.lokbelirlendi);

	}

	private static void verimSekliDegistir(String mobadi) {
		String suankidurum = main.getConfig().getString("Bosslar." + mobadi + ".Odul-Verim-Sekli");
		if (suankidurum.equals("Yer") || suankidurum.contains("Yer")) {
			suankidurum = "Envanter";
			main.getConfig().set("Bosslar." + mobadi + ".Odul-Verim-Sekli", suankidurum);
			main.saveConfig();
		} else if (suankidurum.equals("Envanter") || suankidurum.contains("Envanter")) {
			suankidurum = "Yer";
			main.getConfig().set("Bosslar." + mobadi + ".Odul-Verim-Sekli", suankidurum);
			main.saveConfig();
		}
	}

	private static void duyuruayarla(String mob) {
		boolean durum = main.getConfig().getBoolean("Bosslar." + mob + ".Duyuru");
		main.getConfig().set("Bosslar." + mob + ".Duyuru", !durum);
		main.saveConfig();
	}

}