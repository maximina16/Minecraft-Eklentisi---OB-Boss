package speedyg.mesaj;

import org.bukkit.event.Listener;

import speedyg.boss.Main;

public class Mesajlar implements Listener {

	static Main main;

	public Mesajlar(Main plugin) {
		Mesajlar.main = plugin;
	}

	public static String prefix;
	public static String yetkimesaji;
	public static String reloadbasarili;
	public static String bosssilindi;
	public static String lokbelirlendi;
	public static String bossolusturuldu;
	public static String bossolusturunuz;
	public static String bubossvar;
	public static String isimgiriniz;
	public static String isimdegistirildi;
	public static String cangiriniz;
	public static String sayigiriniz;
	public static String cantamamlandi;
	public static String degerigiriniz;
	public static String suregiriniz;
	public static String suretamamlandi;
	public static String dogmaduyurusu;
	public static String duyuruoldurme;
	public static String caniyokbunun;
	public static String dogmatitle;
	public static String dogmastitle;
	public static String oldurmetitle;
	public static String oldurmestitle;
	public static String komutgiriniz;
	public static String lutfenrastgelebirim;
	public static String birimbasarili;
	public static String dogmaduyurusuayi;
	public static String dogmatitleayi;
	public static String dogmastitleayi;
	public static String efekt_sansi_degisti;
	public static String kucuk_sayi_giriniz;
	public static String efekt_sansi_giriniz;
	public static String pot_suresi_giriniz;
	public static String pot_suresi_degisti;

	public static void mesajlariYukle() {
		prefix = Main.dataayar.getString("Prefix").replaceAll("&", "§");
		yetkimesaji = Main.dataayar.getString("Mesajlar.Yetki-Mesaj").replaceAll("<p>", prefix).replaceAll("&", "§");
		reloadbasarili = Main.dataayar.getString("Mesajlar.Reload-Mesaj").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		bosssilindi = Main.dataayar.getString("Mesajlar.Boss-Silindi").replaceAll("<p>", prefix).replaceAll("&", "§");
		lokbelirlendi = Main.dataayar.getString("Mesajlar.Lokasyon-Belirlendi").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		bossolusturuldu = Main.dataayar.getString("Mesajlar.Boss-Olusturuldu").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		bossolusturunuz = Main.dataayar.getString("Mesajlar.Boss-Olusturun").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		bubossvar = Main.dataayar.getString("Mesajlar.Bu-Boss-Var").replaceAll("<p>", prefix).replaceAll("&", "§");
		isimgiriniz = Main.dataayar.getString("Mesajlar.Isim-Giriniz").replaceAll("<p>", prefix).replaceAll("&", "§");
		isimdegistirildi = Main.dataayar.getString("Mesajlar.Isim-Degistildi").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		cangiriniz = Main.dataayar.getString("Mesajlar.Can-Giriniz").replaceAll("<p>", prefix).replaceAll("&", "§");
		sayigiriniz = Main.dataayar.getString("Mesajlar.Sadece-Sayi").replaceAll("<p>", prefix).replaceAll("&", "§");
		cantamamlandi = Main.dataayar.getString("Mesajlar.Can-Degisti").replaceAll("<p>", prefix).replaceAll("&", "§");
		degerigiriniz = Main.dataayar.getString("Mesajlar.Duzenleme-Modundasin").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		suregiriniz = Main.dataayar.getString("Mesajlar.Sure-Giriniz").replaceAll("<p>", prefix).replaceAll("&", "§");
		suretamamlandi = Main.dataayar.getString("Mesajlar.Sure-Degistirildi").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		dogmaduyurusu = Main.dataayar.getString("Dogma-Duyuru").replaceAll("<p>", prefix).replaceAll("&", "§");
		duyuruoldurme = Main.dataayar.getString("Oldurme-Duyurusu").replaceAll("<p>", prefix).replaceAll("&", "§");
		caniyokbunun = Main.dataayar.getString("Mesajlar.Cani-0-Altinda").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		lutfenrastgelebirim = Main.dataayar.getString("Mesajlar.Rastgele-Birim-Giriniz").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		birimbasarili = Main.dataayar.getString("Mesajlar.Rastgele-Birim-Basarili").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		dogmatitle = Main.dataayar.getString("Title.Dogma.Title").replaceAll("<p>", prefix).replaceAll("&", "§");
		dogmastitle = Main.dataayar.getString("Title.Dogma.Sub-Title").replaceAll("<p>", prefix).replaceAll("&", "§");
		dogmaduyurusuayi = Main.dataayar.getString("Rastgele-Dogma-Mesaj").replaceAll("<p>", prefix).replaceAll("&",
				"§");

		dogmatitleayi = Main.dataayar.getString("Title.Dogma.Title-Random").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		dogmastitleayi = Main.dataayar.getString("Title.Dogma.Sub-Title-Random").replaceAll("<p>", prefix)
				.replaceAll("&", "§");

		oldurmetitle = Main.dataayar.getString("Title.Oldurme.Title").replaceAll("<p>", prefix).replaceAll("&", "§");
		oldurmestitle = Main.dataayar.getString("Title.Oldurme.Sub-Title").replaceAll("<p>", prefix).replaceAll("&",
				"§");
		komutgiriniz = Main.dataayar.getString("Mesajlar.Komut-Giriniz").replaceAll("<p>", prefix).replaceAll("&", "§");
		efekt_sansi_degisti = Main.dataayar.getString("Mesajlar.Efekt-Sansi-Degisti").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		kucuk_sayi_giriniz = Main.dataayar.getString("Mesajlar.Kucuk-Sayi-Giriniz").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		efekt_sansi_giriniz = Main.dataayar.getString("Mesajlar.Efekt-Sansi-Giriniz").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		pot_suresi_giriniz = Main.dataayar.getString("Mesajlar.Pot-Suresi-Giriniz").replaceAll("<p>", prefix)
				.replaceAll("&", "§");
		pot_suresi_degisti = Main.dataayar.getString("Mesajlar.Pot-Suresi-Degisti").replaceAll("<p>", prefix)
				.replaceAll("&", "§");

	}
}
