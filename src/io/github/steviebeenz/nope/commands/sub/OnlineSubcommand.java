package io.github.steviebeenz.nope.commands.sub;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.PluginInfo;
import io.github.steviebeenz.nope.PluginInfo.Stats;
import io.github.steviebeenz.nope.commands.CommandResult;
import io.github.steviebeenz.nope.commands.Subcommand;
import io.github.steviebeenz.nope.utils.MSG;

public class OnlineSubcommand extends Subcommand {

	public OnlineSubcommand(NOPE plugin) {
		super(plugin);
	}

	@Override
	public List<String[]> tabCompletions(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "online";
	}

	@Override
	public CommandResult execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("nope.command.online")) {
			return CommandResult.NO_PERMISSION;
		}
		if (plugin.getPluginInfo() == null) {
			MSG.tell(sender, "&4[NOPE] &7Unable to grab plugin info at this time.");
			return CommandResult.ERROR;
		}
		PluginInfo info = plugin.getPluginInfo();
		Stats stats = info.getStats();
		MSG.tell(sender, String.format(
				"&4[NOPE] &7NOPE has reached &b%d&7 downloads, &a%d&7 reviews, and is averaging about &e%.2f&7.",
				stats.getDownloads(), stats.getReviews(), stats.getRating()));
		return CommandResult.SUCCESS;
	}

	@Override
	public String getUsage() {
		return "";
	}

	@Override
	public String getPermission() {
		return "nope.command." + getName();
	}

	@Override
	public String getDescription() {
		return "Get online information of NOPE";
	}

}
