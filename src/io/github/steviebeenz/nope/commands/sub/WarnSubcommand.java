package io.github.steviebeenz.nope.commands.sub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.commands.CommandResult;
import io.github.steviebeenz.nope.commands.Subcommand;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import io.github.steviebeenz.nope.utils.MSG;

public class WarnSubcommand extends Subcommand {

	public WarnSubcommand(NOPE plugin) {
		super(plugin);
	}

	@Override
	public List<String[]> tabCompletions(CommandSender sender, String[] args) {
		List<String[]> result = new ArrayList<>();
		result.add(null);
		result.add(new String[] { "h:" });
		result.add(new String[] { "v:" });
		return result;
	}

	@Override
	public String getName() {
		return "warn";
	}

	@Override
	public List<String> getAliases() {
		return new ArrayList<>(Arrays.asList("flag"));
	}

	@SuppressWarnings("deprecation")
	@Override
	public CommandResult execute(CommandSender sender, String[] args) {

		if (!sender.hasPermission("nope.command.warn"))
			return CommandResult.NO_PERMISSION;

		if (args.length < 4)
			return CommandResult.MISSING_ARGUMENT;

		OfflinePlayer t = Bukkit.getOfflinePlayer(args[1]);
		CPlayer cp = plugin.getCPlayer(t);

		String hackName = "", stringVl = "", current = "";
		for (int i = 2; i < args.length; i++) {
			String arg = args[i];
			if (arg.startsWith("h:")) {
				current = "hack";
				hackName += arg.substring(2);
				continue;
			} else if (arg.startsWith("v:")) {
				current = "vl";
				stringVl += arg.substring(2);
				continue;
			}

			if (current.equals("hack")) {
				hackName += " " + arg;
			} else if (current.equals("vl")) {
				stringVl += " " + arg;
			}
		}

		final String hackNameFinal = hackName;

		try {
			Integer.parseInt(stringVl);
		} catch (NumberFormatException e) {
			return CommandResult.INVALID_ARGUMENT;
		}

		cp.flagHack(new Check() {
			@Override
			public boolean lagBack() {
				return false;
			}

			@Override
			public CheckType getType() {
				return CheckType.MISC;
			}

			@Override
			public String getDebugName() {
				return "ManuallyIssued#1";
			}

			@Override
			public String getCategory() {
				return hackNameFinal;
			}

			@Override
			public void register(NOPE plugin) {
			}

		}, Integer.parseInt(stringVl));

		MSG.tell(sender, MSG.getString("Command.Warn", "&4NOPE > &7Warned &a%player% &7for &c%check%&7 (&3%vl%&7).")
				.replace("%player%", t.getName()).replace("%check%", hackName).replace("%vl%", stringVl));
		return CommandResult.SUCCESS;
	}

	@Override
	public String getUsage() {
		return "[player] h:[hack] v:[vl]";
	}

	@Override
	public String getPermission() {
		return "nope.command." + getName();
	}

	@Override
	public String getDescription() {
		return "Manually flag a player";
	}
}
