package speedyg.eventler;

import org.bukkit.Location;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Giant;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.MagmaCube;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Witch;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class BossCek {
	public static LivingEntity spawnMob(String mobtipi, Location yer) {
		switch (mobtipi) {
		case "SKELETON":
			Skeleton sk = (Skeleton) yer.getWorld().spawn(yer, Skeleton.class);
			sk.setRemoveWhenFarAway(false);
			return sk;
		case "ZOMBIE":
			Zombie zm = (Zombie) yer.getWorld().spawn(yer, Zombie.class);
			zm.setBaby(false);
			zm.setRemoveWhenFarAway(false);
			return zm;
		case "SPIDER":
			Spider sp = (Spider) yer.getWorld().spawn(yer, Spider.class);
			sp.setRemoveWhenFarAway(false);
			return sp;
		case "CAVE_SPIDER":
			CaveSpider cv = (CaveSpider) yer.getWorld().spawn(yer, CaveSpider.class);
			cv.setRemoveWhenFarAway(false);
			return cv;
		case "BLAZE":
			Blaze bl = (Blaze) yer.getWorld().spawn(yer, Blaze.class);
			bl.setRemoveWhenFarAway(false);
			return bl;
		case "BAT":
			Bat bt = (Bat) yer.getWorld().spawn(yer, Bat.class);
			bt.setRemoveWhenFarAway(false);
			return bt;
		case "PIG_ZOMBIE":
			PigZombie pz = (PigZombie) yer.getWorld().spawn(yer, PigZombie.class);
			pz.setRemoveWhenFarAway(false);
			return pz;
		case "WITCH":
			Witch wt = (Witch) yer.getWorld().spawn(yer, Witch.class);
			wt.setRemoveWhenFarAway(false);
			return wt;
		case "VILLAGER":
			Villager vl = (Villager) yer.getWorld().spawn(yer, Villager.class);
			vl.setRemoveWhenFarAway(false);
			return vl;
		case "WOLF":
			Wolf wf = (Wolf) yer.getWorld().spawn(yer, Wolf.class);
			wf.setRemoveWhenFarAway(false);
			return wf;
		case "SLIME":
			Slime sm = (Slime) yer.getWorld().spawn(yer, Slime.class);
			sm.setRemoveWhenFarAway(false);
			return sm;
		case "MAGMA_CUBE":
			MagmaCube mm = (MagmaCube) yer.getWorld().spawn(yer, MagmaCube.class);
			mm.setRemoveWhenFarAway(false);
			return mm;
		case "IRON_GOLEM":
			IronGolem ig = (IronGolem) yer.getWorld().spawn(yer, IronGolem.class);
			ig.setRemoveWhenFarAway(false);
			return ig;
		case "GIANT":
			Giant gi = (Giant) yer.getWorld().spawn(yer, Giant.class);
			gi.setRemoveWhenFarAway(false);
			return gi;
		default:
			Zombie def = (Zombie) yer.getWorld().spawn(yer, Zombie.class);
			def.setRemoveWhenFarAway(false);
			return def;
		}
	}
}
