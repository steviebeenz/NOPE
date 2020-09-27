package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.utils.MSG;

/**
 * Kicks the player with the specified reason. Generally should be used with
 * delay.
 * 
 * @author imodm
 *
 */
public class KickAction extends AbstractAction {

	private String reason;

	public KickAction(NOPE plugin, String reason) {
		super(plugin);
		this.reason = reason;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (!player.isOnline())
			return;
		Bukkit.getScheduler().runTask(plugin, () -> {
			player.getPlayer()
					.kickPlayer(MSG.color(MSG.replaceCheckPlaceholder(reason, plugin.getCPlayer(player), check)));
		});
	}

}
