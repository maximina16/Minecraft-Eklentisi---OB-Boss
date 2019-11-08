package speedyg.papi;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import speedyg.boss.Main;

public class PlaceHolder extends PlaceholderExpansion {
	Main main;

	public PlaceHolder(Main main) {
		this.main = main;
	}

	public boolean canRegister() {
		return true;
	}

	public String getAuthor() {
		return "SpeedyG(YSO)";
	}

	public String getIdentifier() {
		return "obboss";
	}

	public String getPlugin() {
		return null;
	}

	public String getVersion() {
		return "1.2.5";
	}

	public String onPlaceholderRequest(Player p, String identifier) {
		// %obboss_bossoldurme%
		if (identifier.equals("bossoldurme")) {
			return String.valueOf(BossAPI.oldurmeOgren(p.getName()));
		}
		return null;
	}

}
