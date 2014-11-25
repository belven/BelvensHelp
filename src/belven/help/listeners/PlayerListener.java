package belven.help.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import belven.help.HelpManager;

public class PlayerListener implements Listener {
	private final HelpManager plugin;

	public PlayerListener(HelpManager instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerLoginEvent(PlayerLoginEvent event) {
		if (!event.getPlayer().hasPlayedBefore()) {
			plugin.giveHelpBook(event.getPlayer(), ".Rules");
			plugin.giveHelpBook(event.getPlayer(), ".Arenas");
			plugin.giveHelpBook(event.getPlayer(), ".Classes");
			plugin.giveHelpBook(event.getPlayer(), ".Teams");
		}
	}
}