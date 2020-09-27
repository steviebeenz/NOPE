package io.github.steviebeenz.nope.commands.sub;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.commands.CommandResult;
import io.github.steviebeenz.nope.commands.Subcommand;
import io.github.steviebeenz.nope.utils.MSG;

public class ReloadSubcommand extends Subcommand {

	public ReloadSubcommand(NOPE plugin) {
		super(plugin);
	}

	@Override
	public List<String[]> tabCompletions(CommandSender sender, String[] args) {
		return new ArrayList<>();
	}

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public CommandResult execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("nope.command.reload")) {
			return CommandResult.NO_PERMISSION;
		}
		plugin.setConfig(YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml")));
		plugin.setLang(YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "lang.yml")));
		plugin.reload();
		MSG.tell(sender, MSG.getString("Command.Reload", "Successfully reloaded."));
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
		return "Reload NOPE configuration and plugin";
	}

}
