package io.github.steviebeenz.nope.modules.actions;

import io.github.steviebeenz.nope.modules.actions.actions.CommandAction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.utils.MSG;

/**
 * Similar to {@link CommandAction} but the console executes this command
 * 
 * @author imodm
 *
 */
public class ConsoleCommandAction extends AbstractAction {

	private String command;

	public ConsoleCommandAction(NOPE plugin, String command) {
		super(plugin);
		this.command = command;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (!player.isOnline())
			return;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
				MSG.replaceCheckPlaceholder(command, plugin.getCPlayer(player), check));
	}

}
