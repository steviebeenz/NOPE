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
 * Checks jumping speed
 * 
 * @author imodm
 *
 */
public class Speed2 implements Check, Listener {

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
		if (player.isFlying() || player.isInsideVehicle() || player.isGliding())
			return;

		if (cp.timeSince(Global.Stat.FLYING) < 2000 || cp.timeSince(Global.Stat.RIPTIDE) < 2000)
			return;
		if (cp.timeSince(Global.Stat.ICE_TRAPDOOR) < 1000)
			return;
		if (cp.timeSince(Global.Stat.LEAVE_VEHICLE) < 500)
			return;
		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 200)
			return;
		if (cp.timeSince(Global.Stat.ON_SLIMEBLOCK) < 1000)
			return;
		if (cp.timeSince(Global.Stat.REDSTONE) < 1000)
			return;
		if (cp.hasMovementRelatedPotion())
			return;
		if (player.getFallDistance() > 4)
			return;
		if (player.getLocation().getBlock().isLiquid())
			return;

		Location to = event.getTo(), from = event.getFrom();

		double dist = to.distanceSquared(from);

		if (dist < .7)
			return;

		cp.flagHack(this, (int) Math.round((dist - .7) * 20), "Dist: &e" + dist + "&7 >= &a.7");
	}

	@Override
	public String getCategory() {
		return "Speed";
	}

	@Override
	public String getDebugName() {
		return "Speed#2";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
