package io.github.steviebeenz.nope.commands.sub;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.commands.CommandResult;
import io.github.steviebeenz.nope.commands.Subcommand;
import io.github.steviebeenz.nope.modules.trust.TrustFactor;
import io.github.steviebeenz.nope.utils.MSG;

public class TrustSubcommand extends Subcommand {

	private TrustFactor trust;

	public TrustSubcommand(NOPE plugin) {
		super(plugin);
		trust = plugin.getModule(TrustFactor.class);
	}

	@Override
	public List<String[]> tabCompletions(CommandSender sender, String[] args) {
		return null;
	}

	@Override
	public String getName() {
		return "trust";
	}

	@Override
	public String getUsage() {
		return "<player>";
	}

	@Override
	public String getDescription() {
		return "View a player's trust factor";
	}

	@Override
	public String getPermission() {
		return "nope.command.trust";
	}

	@Override
	public CommandResult execute(CommandSender sender, String[] args) {
		Player target = null;
		if (args.length == 1) {
			if (sender instanceof Player) {
				target = (Player) sender;
			} else {
				return CommandResult.PLAYER_REQUIRED;
			}
		} else if (sender.hasPermission("nope.command.trust.others")) {
			target = Bukkit.getPlayer(args[1]);
		} else {
			return CommandResult.NO_PERMISSION;
		}
		if (target == null)
			return CommandResult.INVALID_ARGUMENT;

		MSG.tell(sender, trust.format(target.getUniqueId()));

		MSG.tell(sender,
				MSG.getString("Command.Trust", "&e%player% &7has a trust factor of &a%trust%&7.")
						.replace("%player%", target.getName())
						.replace("%trust%", trust.recalculate(target.getUniqueId()) + ""));

		return CommandResult.SUCCESS;
	}

}
