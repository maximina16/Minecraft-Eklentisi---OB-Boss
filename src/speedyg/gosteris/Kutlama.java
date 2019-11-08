package speedyg.gosteris;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import speedyg.boss.Main;

public class Kutlama {

	Main main;

	public Kutlama(Main plugin) {
		main = plugin;
	}

	public static void kutlamaYap(Player oyuncu) {
		Random ates = new Random();
		int rastgele = ates.nextInt(5);
		FireworkEffect.Type tip;

		switch (rastgele) {
		case 1:
			tip = FireworkEffect.Type.BALL_LARGE;
			break;
		case 2:
			tip = FireworkEffect.Type.BURST;
			break;
		case 3:
			tip = FireworkEffect.Type.CREEPER;
			break;
		case 4:
			tip = FireworkEffect.Type.STAR;
			break;
		case 5:
			tip = FireworkEffect.Type.BALL;
			break;
		default:
			tip = FireworkEffect.Type.BALL_LARGE;
			break;
		}

		Firework fw = (Firework) oyuncu.getWorld().spawnEntity(oyuncu.getLocation(), EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();
		FireworkEffect effect = FireworkEffect.builder().flicker(true).withColor(Color.AQUA).withFade(Color.RED)
				.withColor(Color.YELLOW).withFade(Color.FUCHSIA).with(tip).trail(true).build();
		fwm.setPower(1);
		fwm.addEffect(effect);
		fw.setFireworkMeta(fwm);
	}
}
