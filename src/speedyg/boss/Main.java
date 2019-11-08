package speedyg.boss;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import speedyg.eventler.BossEvent;
import speedyg.komutlar.Komut;
import speedyg.menuler.BossGiydir;
import speedyg.menuler.DuzenMenu;
import speedyg.menuler.EfektDuzenle;
import speedyg.menuler.EfektleriAyarla;
import speedyg.menuler.GoruntulemeSecimMenusu;
import speedyg.menuler.GucMenusu;
import speedyg.menuler.KomutMenusu;
import speedyg.menuler.KomutSilSecMenusu;
import speedyg.menuler.ManuelDogurMenu;
import speedyg.menuler.Menu;
import speedyg.menuler.OdulMenusu;
import speedyg.menuler.Odul_Komutlar_Secim_Menusu;
import speedyg.menuler.PotMenusu;
import speedyg.menuler.RastgeleAyarMenusu;
import speedyg.menuler.SansMenusu;
import speedyg.menuler.SecimMenu;
import speedyg.menuler.SilmeMenu;
import speedyg.menuler.TipMenusu;
import speedyg.mesaj.Mesajlar;
import speedyg.papi.BossAPI;
import speedyg.papi.PlaceHolder;
import speedyg.updater.UpdateChecker;

public class Main extends JavaPlugin implements Listener {

	public static File datadosyasi;
	public static FileConfiguration dataayar;
	public static File lokasyondosyasi;
	public static FileConfiguration lokayar;
	public static File apidosyasi;
	public static FileConfiguration apiayar;
	public static int API = 65832;
	public static boolean update = false;
	public static String currentversion = "1.7.1";

	public void onEnable() {
		UpdateChecker.checkupdates.start();
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getPluginManager().registerEvents(new Mesajlar(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new RastgeleAyarMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Menu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SansMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new TipMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SecimMenu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DuzenMenu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EfektDuzenle(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BossAPI(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new SilmeMenu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GucMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EfektleriAyarla(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new KomutSilSecMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new OdulMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new Odul_Komutlar_Secim_Menusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new GoruntulemeSecimMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new KomutMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BossGiydir(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PotMenusu(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BossEvent(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ManuelDogurMenu(this), this);

		if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PlaceHolder(this).register();
		}
		getCommand("boss").setExecutor(new Komut(this));
		veriDosyasiOlustur();
		bossDosyasiOlustur();
		apiDosyasiOlustur();
		this.saveDefaultConfig();
		this.zamanlayicilariYenile();
		Mesajlar.mesajlariYukle();
		bugEngelleyici();
	}

	public void zamanlayicilariYenile() {
		Bukkit.getServer().getScheduler().cancelTasks(this);
		for (String bossadi : getConfig().getConfigurationSection("Bosslar").getKeys(false))
			BossEvent.bosslariDondur(bossadi);
	}

	@EventHandler
	private void updateKontrol(PlayerJoinEvent e) {
		if (Main.update)
			if (e.getPlayer().hasPermission("obboss.admin") || e.getPlayer().isOp()) {
				e.getPlayer().sendMessage(Mesajlar.prefix + "§cEklentiniz guncel degil! Lutfen guncelleyiniz!");
				e.getPlayer().sendMessage(Mesajlar.prefix + "§3Guncellemek icin; "
						+ "https://www.spigotmc.org/resources/ob-boss-tamam%C4%B1yla-t%C3%BCrk%C3%A7e-ve-orjinal-boss-eklentisi-tamam%C4%B1yla-%C3%96zelle%C5%9Ftirilebilir-gui-sistem.65832/");
			}
	}

	@EventHandler
	public void cikisBugEngelleme(PlayerQuitEvent e) {

		if (DuzenMenu.isimdegistirici.contains(e.getPlayer().getName())) {
			DuzenMenu.isimdegistirici.remove(e.getPlayer().getName());
		}
		if (Menu.bossolusturma.contains(e.getPlayer().getName())) {
			Menu.bossolusturma.remove(e.getPlayer().getName());
		}

		if (DuzenMenu.candegistirici.contains(e.getPlayer().getName())) {
			DuzenMenu.candegistirici.remove(e.getPlayer().getName());
		}

		if (DuzenMenu.dogmasuresidegistir.contains(e.getPlayer().getName())) {
			DuzenMenu.dogmasuresidegistir.remove(e.getPlayer().getName());
		}

		if (DuzenMenu.komutekle.contains(e.getPlayer().getName())) {
			DuzenMenu.komutekle.remove(e.getPlayer().getName());
		}
		if (DuzenMenu.rastgeledegistir.contains(e.getPlayer().getName())) {
			DuzenMenu.rastgeledegistir.remove(e.getPlayer().getName());
		}

		if (DuzenMenu.efektsansidegistir.contains(e.getPlayer().getName())) {
			DuzenMenu.efektsansidegistir.remove(e.getPlayer().getName());
		}

		if (DuzenMenu.potsuresidegistir.contains(e.getPlayer().getName())) {
			DuzenMenu.potsuresidegistir.remove(e.getPlayer().getName());
		}
	}

	private void bugEngelleyici() {
		@SuppressWarnings("unchecked")
		List<Player> oyuncular = (List<Player>) Bukkit.getOnlinePlayers();
		for (Player p : oyuncular)
			p.closeInventory();
	}

	public void onDisable() {
		for (String bossadi : getConfig().getConfigurationSection("Bosslar").getKeys(false))
			if (BossEvent.bosslarim != null)
				if (BossEvent.bosslarim.get(bossadi) != null)
					if (BossEvent.mobaGit(BossEvent.bosslarim.get(bossadi)) != null)
						BossEvent.mobaGit(BossEvent.bosslarim.get(bossadi)).remove();

		update = false;
		bugEngelleyici();

		BossEvent.bosslarim.clear();
	}

	private void apiDosyasiOlustur() {
		apidosyasi = new File(getDataFolder(), "veri.yml");
		if (!apidosyasi.exists()) {
			apidosyasi.getParentFile().mkdirs();
			saveResource("veri.yml", false);
		}
		apiayar = new YamlConfiguration();
		try {
			apiayar.load(apidosyasi);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration ulasApiDosyasi() {
		return apiayar;
	}

	public void apileriYenile() {
		try {
			ulasApiDosyasi().load(apidosyasi);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage("§8[§4OB-Boss§8] §eAPI&c dosyasi hatasi!");
			e.printStackTrace();
		}
	}

	//

	private void veriDosyasiOlustur() {
		datadosyasi = new File(getDataFolder(), "mesajlar.yml");
		if (!datadosyasi.exists()) {
			datadosyasi.getParentFile().mkdirs();
			saveResource("mesajlar.yml", false);
		}
		dataayar = new YamlConfiguration();
		try {
			dataayar.load(datadosyasi);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration ulasVeriDosyasi() {
		return dataayar;
	}

	public void verileriYenile() {
		try {
			ulasVeriDosyasi().load(datadosyasi);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage("§8[§4OB-Boss§8] §eVeri&c dosyasi hatasi!");
			e.printStackTrace();
		}
	}

	//
	private void bossDosyasiOlustur() {
		Main.lokasyondosyasi = new File(getDataFolder(), "lokasyonlar.yml");
		if (!lokasyondosyasi.exists()) {
			lokasyondosyasi.getParentFile().mkdirs();
			saveResource("lokasyonlar.yml", false);
		}
		lokayar = new YamlConfiguration();
		try {
			lokayar.load(lokasyondosyasi);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getLocFile() {
		return lokayar;
	}

	public void reloadLocations() {
		try {
			getLocFile().load(lokasyondosyasi);
		} catch (IOException | InvalidConfigurationException e) {
			Bukkit.getConsoleSender().sendMessage("§8[§4OB-Boss§8] §eVeri&c dosyasi hatasi!");
			e.printStackTrace();
		}
	}

	public static void save(FileConfiguration ymlConfig, File ymlFile) {
		try {
			ymlConfig.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reloadAt(Player p) {
		reloadConfig();
		this.verileriYenile();
		Mesajlar.mesajlariYukle();
		this.apileriYenile();
		reloadLocations();
		this.zamanlayicilariYenile();
		p.sendMessage(Mesajlar.prefix + " Reload başarılı!");
	}

}
