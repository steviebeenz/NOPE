package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.bans.Banwave;
import io.github.steviebeenz.nope.modules.bans.Banwave.BanwaveInfo;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import io.github.steviebeenz.nope.utils.MSG;

/**
 * Queues the player for a banwave
 * 
 * @author imodm
 *
 */
public class BanwaveAction extends AbstractAction {

	private long time;
	private String reason;

	public BanwaveAction(NOPE plugin, long time, String reason) {
		super(plugin);
		this.time = time;
		this.reason = reason;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (plugin.getModule(Banwave.class).isInBanwave(player.getUniqueId()))
			return;
		CPlayer cp = plugin.getCPlayer(player);

		BanwaveInfo info = plugin.getModule(Banwave.class).new BanwaveInfo(player.getUniqueId(), check, time, reason);
		plugin.getModule(Banwave.class).addPlayer(player.getUniqueId(), info);
		Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
			String r = MSG.replaceCheckPlaceholder(reason, cp, check);

			String token = cp.saveLog(check);
			r = r.replace("%token%", token);

			plugin.getModule(Banwave.class).addPlayer(player.getUniqueId(), info);
		});

	}

}
