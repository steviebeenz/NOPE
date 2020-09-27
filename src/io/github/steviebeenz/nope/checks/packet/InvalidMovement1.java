package io.github.steviebeenz.nope.checks.packet;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Checks if pitch is invalid
 * 
 * @author imodm
 *
 */
public class InvalidMovement1 implements Check, Listener {

	private NOPE plugin;

	@Override
	public CheckType getType() {
		return CheckType.PACKET;
	}

	@Override
	public void register(NOPE plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);

		if (cp.timeSince(Global.Stat.TELEPORT) < 60000)
			return;

		double pitch = player.getLocation().getPitch();

		if (pitch >= -90 && pitch <= 90)
			return;

		cp.flagHack(this, plugin.getConfig().getInt("VlForInstaBan"));
	}

	@Override
	public String getCategory() {
		return "InvalidMovement";
	}

	@Override
	public String getDebugName() {
		return "InvalidMovement#1";
	}

	@Override
	public boolean lagBack() {
		return false;
	}
}
