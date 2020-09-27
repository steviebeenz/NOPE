package io.github.steviebeenz.nope.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.commands.sub.BanwaveSubcommand;
import io.github.steviebeenz.nope.commands.sub.ChecksSubcommand;
import io.github.steviebeenz.nope.commands.sub.ClearSubcommand;
import io.github.steviebeenz.nope.commands.sub.EnablechecksSubcommand;
import io.github.steviebeenz.nope.commands.sub.FalseSubcommand;
import io.github.steviebeenz.nope.commands.sub.HelpSubcommand;
import io.github.steviebeenz.nope.commands.sub.LookupSubcommand;
import io.github.steviebeenz.nope.commands.sub.OnlineSubcommand;
import io.github.steviebeenz.nope.commands.sub.ReloadSubcommand;
import io.github.steviebeenz.nope.commands.sub.RemovebanwaveSubcommand;
import io.github.steviebeenz.nope.commands.sub.ReportSubcommand;
import io.github.steviebeenz.nope.commands.sub.ResetSubcommand;
import io.github.steviebeenz.nope.commands.sub.TestAnimationSubcommand;
import io.github.steviebeenz.nope.commands.sub.TestlagSubcommand;
import io.github.steviebeenz.nope.commands.sub.TimeSubcommand;
import io.github.steviebeenz.nope.commands.sub.ToggleSubcommand;
import io.github.steviebeenz.nope.commands.sub.TrustSubcommand;
import io.github.steviebeenz.nope.commands.sub.VLSubcommand;
import io.github.steviebeenz.nope.commands.sub.WarnSubcommand;

public class NOPECommand extends AbstractCommand {

	private HelpSubcommand help;

	public NOPECommand(NOPE plugin) {
		super(plugin);
		help = new HelpSubcommand(plugin, this, 8);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		label = label.toUpperCase();
		if (super.onCommand(sender, command, label, args))
			return true;
		help.execute(sender, args);
		return true;
	}

	@Override
	public void enable() {
		super.enable();
		cmds.add(new FalseSubcommand(plugin));
		cmds.add(new VLSubcommand(plugin));
		cmds.add(new ClearSubcommand(plugin));
		cmds.add(new ReportSubcommand(plugin));
		cmds.add(new LookupSubcommand(plugin));
		cmds.add(new ToggleSubcommand(plugin));
		cmds.add(new TimeSubcommand(plugin));
		cmds.add(new BanwaveSubcommand(plugin));
		cmds.add(new ReloadSubcommand(plugin));
		cmds.add(new ResetSubcommand(plugin));
		cmds.add(new TestlagSubcommand(plugin));
		cmds.add(new RemovebanwaveSubcommand(plugin));
		cmds.add(new EnablechecksSubcommand(plugin));
		cmds.add(new OnlineSubcommand(plugin));
		cmds.add(new TestAnimationSubcommand(plugin));
		cmds.add(new WarnSubcommand(plugin));
		cmds.add(new ChecksSubcommand(plugin));
		cmds.add(new TrustSubcommand(plugin));
		cmds.add(help);
	}

	@Override
	public String getName() {
		return "nope";
	}
}
