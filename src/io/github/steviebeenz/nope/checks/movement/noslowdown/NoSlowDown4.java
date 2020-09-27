package io.github.steviebeenz.nope.checks.movement.noslowdown;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.steviebeenz.nope.NOPE;

/**
 * Checks the player's speed in a snapshot of time while on ground
 * 
 * @author imodm
 *
 */
public class NoSlowDown4 implements Check, Listener {

	private NOPE plugin;

	@Override
	public CheckType getType() {
		return CheckType.MOVEMENT;
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
		if (player.isFlying() || player.isInsideVehicle())
			return;

		if (cp.timeSince(Global.Stat.DISABLE_FLIGHT) < 2000)
			return;

		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 1000)
			return;

		if (!player.isBlocking())
			return;

		if (cp.timeSince(Global.Stat.IN_LIQUID) < 500)
			return;

		Location to = event.getTo(), from = event.getFrom();

		double dist = Math.abs(to.getX() - from.getX()) + Math.abs(to.getZ() - from.getZ());

		if (dist < .47)
			return;

		cp.flagHack(this, (int) Math.round((dist - .43) * 400.0), "Dist: &e" + dist + "&7 >= &a.47");
	}

	@Override
	public String getCategory() {
		return "NoSlowDown";
	}

	@Override
	public String getDebugName() {
		return "NoSlowDown#4";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
