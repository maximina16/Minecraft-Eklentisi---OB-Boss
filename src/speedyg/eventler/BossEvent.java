package speedyg.eventler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import speedyg.boss.Main;
import speedyg.efektler.Efekt;
import speedyg.gosteris.Kutlama;
import speedyg.mesaj.Mesajlar;
import speedyg.papi.BossAPI;

public class BossEvent implements Listener {
	static Main main;

	public BossEvent(Main plugin) {
		main = plugin;
	}

	public static HashMap<String, UUID> bosslarim = new HashMap<String, UUID>();

	@SuppressWarnings("deprecation")
	public static void mob(String mobadi) {
		World world = Bukkit.getWorld(Main.lokayar.getString("Lokasyonlar." + mobadi + ".world"));
		double x = Main.lokayar.getDouble("Lokasyonlar." + mobadi + ".x");
		double y = Main.lokayar.getDouble("Lokasyonlar." + mobadi + ".y");
		double z = Main.lokayar.getDouble("Lokasyonlar." + mobadi + ".z");
		if (world != null) {
			Location loc = new Location(world, x, y, z);
			int ratgele;
			LivingEntity boss;
			if (main.getConfig().getBoolean("Bosslar." + mobadi + ".Rastgele-Dogma")) {
				Random rand = new Random();
				ratgele = rand.nextInt(main.getConfig().getInt("Bosslar." + mobadi + ".Rastgele-Dogma-Uzaklik-Birim"));
				boss = BossCek.spawnMob(main.getConfig().getString("Bosslar." + mobadi + ".Mob-Tipi"),
						loc.add(ratgele, 0.0D, ratgele / 2));
			} else {
				boss = BossCek.spawnMob(main.getConfig().getString("Bosslar." + mobadi + ".Mob-Tipi"), loc);
			}
			if (main.getConfig().getConfigurationSection("Bosslar." + mobadi + ".Pot-Efektleri") != null) {
				for (String potadi : main.getConfig().getConfigurationSection("Bosslar." + mobadi + ".Pot-Efektleri")
						.getKeys(false)) {
					if (main.getConfig().getInt("Bosslar." + mobadi + ".Pot-Efektleri." + potadi) > 0) {
						boss.addPotionEffect(new PotionEffect(PotionEffectType.getByName(potadi),
								main.getConfig().getInt("Bosslar." + mobadi + ".Pot-Suresi"),
								main.getConfig().getInt("Bosslar." + mobadi + ".Pot-Efektleri." + potadi)));
					}
				}
			}
			boss.getEquipment().clear();
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Kafa") != null) {
				boss.getEquipment().setHelmet(main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Kafa"));
			}
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Gogus") != null) {
				boss.getEquipment().setChestplate(main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Gogus"));
			}
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Pantolon") != null) {
				boss.getEquipment().setLeggings(main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Pantolon"));
			}
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Ayak") != null) {
				boss.getEquipment().setBoots(main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Ayak"));
			}
			if (main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Saldiri") != null) {
				boss.getEquipment().setItemInHand(main.getConfig().getItemStack("Bosslar." + mobadi + ".Set.Saldiri"));
			}
			boss.getEquipment().setBootsDropChance(0.0F);
			boss.getEquipment().setChestplateDropChance(0.0F);
			boss.getEquipment().setHelmetDropChance(0.0F);
			boss.getEquipment().setLeggingsDropChance(0.0F);
			boss.getEquipment().setItemInHandDropChance(0.0F);
			if (main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi") != null) {
				boss.setCustomName(
						main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi").replaceAll("&", "§"));
				boss.setCustomNameVisible(true);
			}
			double can = main.getConfig().getDouble("Bosslar." + mobadi + ".Can");
			boss.setMaxHealth(can);
			boss.setHealth(can);

			boss.setRemoveWhenFarAway(false);
			boss.setCollidable(true);

			bosslarim.put(mobadi, boss.getUniqueId());
			if (main.getConfig().getBoolean("Boss-Dogma-Duyurusu")) {
				if (!main.getConfig().getBoolean("Bosslar." + mobadi + ".Rastgele-Dogma")) {
					Bukkit.broadcastMessage(Mesajlar.dogmaduyurusu.replaceAll("<bossadi>",
							main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi").replaceAll("&", "§")));

					for (Player oyuncu : Bukkit.getOnlinePlayers())
						oyuncu.sendTitle(
								Mesajlar.dogmatitle.replaceAll("<boss>",
										main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi")
												.replaceAll("&", "§")),
								Mesajlar.dogmastitle.replaceAll("<boss>", main.getConfig()
										.getString("Bosslar." + mobadi + ".Gosterim-Adi").replaceAll("&", "§")));
				} else {
					Bukkit.broadcastMessage(Mesajlar.dogmaduyurusuayi
							.replaceAll("<birim>",
									String.valueOf(main.getConfig()
											.getInt("Bosslar." + mobadi + ".Rastgele-Dogma-Uzaklik-Birim")))
							.replaceAll("<bossadi>", main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi")
									.replaceAll("&", "§")));
					for (Player oyuncu : Bukkit.getOnlinePlayers())
						oyuncu.sendTitle(
								Mesajlar.dogmatitleayi
										.replaceAll("<birim>",
												String.valueOf(main.getConfig()
														.getInt("Bosslar." + mobadi + ".Rastgele-Dogma-Uzaklik-Birim")))
										.replaceAll("<boss>",
												main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi")
														.replaceAll("&", "§")),
								Mesajlar.dogmastitleayi
										.replaceAll("<birim>",
												String.valueOf(main.getConfig()
														.getInt("Bosslar." + mobadi + ".Rastgele-Dogma-Uzaklik-Birim")))
										.replaceAll("<boss>",
												main.getConfig().getString("Bosslar." + mobadi + ".Gosterim-Adi")
														.replaceAll("&", "§")));
				}
			}
		}
	}

	public static void bosslariDondur(String bossadi) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
			public void run() {
				try {
					if ((BossEvent.main.getConfig().getBoolean("Bosslar." + bossadi + ".Boss-Durum"))
							&& (BossEvent.main.getConfig().getInt("Bosslar." + bossadi + ".Can") > 0)
							&& (Main.lokayar.getString("Lokasyonlar." + bossadi + ".world") != null)) {
						if (BossEvent.bosslarim.get(bossadi) != null) {
							if (main.getConfig()
									.getBoolean("Bosslar." + bossadi + ".Canliyken-Yok-Olup-Yeni-Boss-Dogsun-Mu")) {
								mobaGit(bosslarim.get(bossadi)).remove();
								BossEvent.bosslarim.remove(bossadi);
								BossEvent.mob(bossadi);
							}
						} else {
							BossEvent.mob(bossadi);
						}
					}
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage("§cHata! §6Bosslari spawn ederken bir hata olustu!");
					Bukkit.getConsoleSender().sendMessage("§cHata Kodu; §4#001-ZAMANLAYICI");
				}
			}
		}, 0L, main.getConfig().getInt("Bosslar." + bossadi + ".Sure") * 20L);
	}

	@EventHandler
	private void hasarEventi(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof LivingEntity)) {
			LivingEntity vurulan = (LivingEntity) e.getEntity();
			if (main.getConfig().getConfigurationSection("Bosslar") != null) {
				for (String bossadi : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
					if (vurulan.equals(mobaGit(bosslarim.get(bossadi)))) {
						vurulan.getWorld().playEffect(vurulan.getLocation().add(0.0D, 1.0D, 0.0D), Effect.SMOKE, 15);
						if (main.getConfig().getString("Bosslar." + bossadi + ".Can-Gosterim-Tarzi") != null)
							if (main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi") != null) {
								vurulan.setCustomName(main.getConfig()
										.getString("Bosslar." + bossadi + ".Can-Gosterim-Tarzi")
										.replaceAll("<bossadi>",
												main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi")
														.replaceAll("&", "§"))
										.replaceAll("<can>",
												bossCaniYuzdesi(bossadi, mobaGit(bosslarim.get(bossadi)).getHealth()))
										.replaceAll("&", "§"));
							}
						Random rand = new Random();
						int deger = rand.nextInt(100);
						if (deger < main.getConfig().getInt("Yaninda-Mesaj-Gonderme-Random")) {
							for (Entity varliklar : mobaGit(bosslarim.get(bossadi)).getNearbyEntities(20.0D, 20.0D,
									20.0D)) {
								if ((varliklar instanceof Player)) {
									Player yo = (Player) varliklar;
									List<String> gonderilcekmesajlar = Main.dataayar
											.getStringList("Boss-Vurma-Mesajlari");
									Random random = new Random();
									int rastgele = random.nextInt(gonderilcekmesajlar.size() - 1);
									yo.sendMessage(
											((String) gonderilcekmesajlar.get(rastgele))
													.replaceAll("<boss>",
															main.getConfig()
																	.getString("Bosslar." + bossadi + ".Gosterim-Adi"))
													.replaceAll("<can>",
															String.valueOf(bossCaniYuzdesi(bossadi,
																	mobaGit(bosslarim.get(bossadi)).getHealth())))
													.replaceAll("&", "§"));
								}
							}
						}

					}
				}
			}
		}
	}

	@EventHandler
	private void efektleriCalistir(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)) {
			if (main.getConfig().getConfigurationSection("Bosslar") != null) {
				for (String bossadi : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
					if ((e.getDamager().equals(mobaGit(bosslarim.get(bossadi)))) && (main.getConfig()
							.getConfigurationSection("Bosslar." + bossadi + ".Vurus-Efektleri") != null)) {
						Player p = (Player) e.getEntity();
						LivingEntity vurucu = (LivingEntity) e.getDamager();
						Set<String> acikefektler = main.getConfig()
								.getConfigurationSection("Bosslar." + bossadi + ".Vurus-Efektleri").getKeys(false);
						ArrayList<String> efektler = new ArrayList<String>();
						for (String sss : acikefektler) {
							efektler.add(sss);
						}
						int gucgenel = main.getConfig().getInt("Bosslar." + bossadi + ".Guc");
						e.setDamage(e.getDamage() + (e.getDamage() * gucgenel / 3));
						Random rand = new Random();
						int rastgele = rand.nextInt(
								main.getConfig().getConfigurationSection("Bosslar." + bossadi + ".Vurus-Efektleri")
										.getKeys(false).size());
						int vurmaihtimali = rand.nextInt(100);
						int vurmaihtimaliasil = main.getConfig()
								.getInt("Bosslar." + bossadi + ".Ozel-Vurus-Efekt-Kullanma-Sansi");
						if (vurmaihtimali < vurmaihtimaliasil) {
							Efekt efekt = new Efekt(p, efektler.get(rastgele),
									main.getConfig().getInt(
											"Bosslar." + bossadi + ".Vurus-Efektleri." + efektler.get(rastgele)),
									vurucu);
							efekt.efektOlustur();
						}
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	private void olumKontrol(EntityDeathEvent e) {
		if (main.getConfig().getConfigurationSection("Bosslar") != null) {
			for (String bossadi : main.getConfig().getConfigurationSection("Bosslar").getKeys(false)) {
				if ((e.getEntity().equals(mobaGit(bosslarim.get(bossadi)))
						|| e.getEntity() == mobaGit(bosslarim.get(bossadi)))
						&& ((e.getEntity().getKiller() instanceof Player))) {
					bosslarim.remove(bossadi);
					Player p = e.getEntity().getKiller();
					if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Kutlama")) {
						Kutlama.kutlamaYap(p);
					}
					BossAPI.oldurmeEkle(p.getName(), 1);

					List<String> komutlar = main.getConfig().getStringList("Bosslar." + bossadi + ".Komutlar");
					for (int i = 0; i < komutlar.size(); i++) {
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
								((String) komutlar.get(i)).replaceAll("%oyuncu%", p.getName()));
					}
					if (main.getConfig().getBoolean("Bosslar." + bossadi + ".Duyuru")) {
						Bukkit.broadcastMessage(Mesajlar.duyuruoldurme.replaceAll("<oyuncu>", p.getName())
								.replaceAll("<bossadi>", main.getConfig()
										.getString("Bosslar." + bossadi + ".Gosterim-Adi").replaceAll("&", "§")));
						for (Player oyuncular : Bukkit.getOnlinePlayers())
							oyuncular.sendTitle(
									Mesajlar.oldurmetitle.replaceAll("<oyuncu>", p.getName()).replaceAll("<boss>",
											main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi")
													.replaceAll("&", "§")),
									Mesajlar.oldurmestitle.replaceAll("<oyuncu>", p.getName()).replaceAll("<boss>",
											main.getConfig().getString("Bosslar." + bossadi + ".Gosterim-Adi")
													.replaceAll("&", "§")));
					}

					e.getDrops().clear();
					if (main.getConfig().getString("Bosslar." + bossadi + ".Odul-Verim-Sekli").equals("Envanter")) {
						for (int i = 0; i < 27; i++) {
							Random rand = new Random();
							int rast = rand.nextInt(100);
							int dropsansi = main.getConfig().getInt("Bosslar." + bossadi + ".Drop-Sans." + i);
							if ((dropsansi > rast) && (main.getConfig()
									.getItemStack("Bosslar." + bossadi + ".Oduller." + i) != null)) {
								p.getInventory().addItem(new ItemStack[] {
										main.getConfig().getItemStack("Bosslar." + bossadi + ".Oduller." + i) });
							}
						}
					} else {
						for (int i = 0; i < 27; i++) {
							Random rand = new Random();
							int rast = rand.nextInt(100);
							int dropsansi = main.getConfig().getInt("Bosslar." + bossadi + ".Drop-Sans." + i);
							if ((dropsansi > rast) && (main.getConfig()
									.getItemStack("Bosslar." + bossadi + ".Oduller." + i) != null)) {
								e.getDrops().add(main.getConfig().getItemStack("Bosslar." + bossadi + ".Oduller." + i));
							}
						}
					}
				} else if (e.getEntity().equals(mobaGit(bosslarim.get(bossadi)))) {
					bosslarim.remove(bossadi);
				}
			}
		}
	}

	public static String bossCaniYuzdesi(String mobadi, double suankican) {
		double x = suankican / main.getConfig().getDouble("Bosslar." + mobadi + ".Can") * 100.0D;
		DecimalFormat df = new DecimalFormat("#.##");
		String dx = df.format(x);
		return dx;
	}

	public static LivingEntity mobaGit(UUID uuid) {
		for (World world : Bukkit.getWorlds()) {
			for (Entity entity : world.getEntities()) {
				if (entity.getUniqueId().equals(uuid))
					return (LivingEntity) entity;
			}
		}

		return null;
	}

}
