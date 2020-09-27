package io.github.steviebeenz.nope.commands.sub;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.commands.CommandResult;
import io.github.steviebeenz.nope.commands.Subcommand;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.Checks;
import io.github.steviebeenz.nope.utils.MSG;

public class EnablechecksSubcommand extends Subcommand {

	public EnablechecksSubcommand(NOPE plugin) {
		super(plugin);
	}

	@Override
	public List<String[]> tabCompletions(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return "enablechecks";
	}

	@Override
	public CommandResult execute(CommandSender sender, String[] args) {
		if (!sender.hasPermission("nope.command.enablechecks")) {
			return CommandResult.NO_PERMISSION;
		}
		plugin.getConfig().set("Checks", null);
		for (Check check : plugin.getModule(Checks.class).getAllChecks()) {
			plugin.getConfig().set("Checks." + MSG.camelCase(check.getType() + "") + ".Enabled", true);
			plugin.getConfig().set(
					"Checks." + MSG.camelCase(check.getType() + "") + "." + check.getCategory() + ".Enabled", true);
			plugin.getConfig().set("Checks." + MSG.camelCase(check.getType() + "") + "." + check.getCategory() + "."
					+ check.getDebugName() + ".Enabled", true);
		}
		plugin.saveConfig();
		MSG.tell(sender, MSG.getString("Command.EnableChecks", "All checks successfully enabled."));
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
		return "Enable all checks";
	}

}
