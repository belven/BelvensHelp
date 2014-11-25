package belven.help;

import java.io.File;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import belven.help.listeners.PlayerListener;

public class HelpManager extends JavaPlugin {

	public final PlayerListener pl = new PlayerListener(this);
	public final String helpBookFileName = "Help Books.yml";
	public final String helpBookPath = "Help Books";

	@Override
	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(pl, this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		String commandSent = cmd.getName();

		switch (commandSent) {
		case "rules":
			giveHelpBook(p, ".Rules");
			return true;
		case "arenashelp":
			giveHelpBook(p, ".Arenas");
			return true;
		case "classeshelp":
			giveHelpBook(p, ".Classes");
			return true;
		case "teamshelp":
			giveHelpBook(p, ".Teams");
			return true;
		}

		return false;
	}

	public void giveHelpBook(Player p, String path) {
		ItemStack is = new ItemStack(Material.WRITTEN_BOOK);
		is.setItemMeta(getBookAtPath(is, helpBookPath + path));
		p.getInventory().addItem(is);
	}

	public BookMeta getBookAtPath(ItemStack is, String path) {
		BookMeta meta = (BookMeta) is.getItemMeta();
		File ymlFile = new File(getDataFolder(), helpBookFileName);
		YamlConfiguration loadedFile = YamlConfiguration.loadConfiguration(ymlFile);

		ConfigurationSection conf = loadedFile.getConfigurationSection(path);

		if (conf == null) {
			return meta;
		}

		meta.setTitle(conf.getString("Title"));

		if (conf.contains("Pages")) {
			for (Entry<String, Object> s : conf.getConfigurationSection("Pages").getValues(true).entrySet()) {
				meta.addPage((String) s.getValue());
			}
		}
		return meta;
	}
}