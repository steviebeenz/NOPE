package io.github.steviebeenz.nope.checks.movement.speed;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Checks non-jumping speed
 * 
 * @author imodm
 *
 */
public class Speed1 implements Check, Listener {

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

		if (cp.timeSince(Global.Stat.FLYING) < 2000)
			return;
		if (cp.timeSince(Global.Stat.REDSTONE) < 2000)
			return;
		if (cp.hasMovementRelatedPotion())
			return;

		Location to = event.getTo(), from = event.getFrom();
		if (to.getY() != from.getY())
			return;

		if (cp.timeSince(Global.Stat.VERTICAL_CHANGE) < 1000)
			return;
		if (cp.timeSince(Global.Stat.SOUL_SPEED) < 500)
			return;

		double dist = to.distanceSquared(from);

		double max = .1300168;

		if (dist <= max)
			return;

		cp.flagHack(this, (int) Math.round((dist - max) * 20) + 5, "&7Dist: &e" + dist + "&7 > &a" + max);
	}

	@Override
	public String getCategory() {
		return "Speed";
	}

	@Override
	public String getDebugName() {
		return "Speed#1";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
