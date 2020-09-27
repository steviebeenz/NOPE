package io.github.steviebeenz.nope.checks.movement.flight;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.steviebeenz.nope.NOPE;

/**
 * 
 * Checks if the player's last ongrond position is too low and too far away
 * <i>conveniently</i> also checks Jesus
 * 
 * @author imodm
 * 
 */
public class Flight4 implements Check, Listener {

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

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);

		if (cp.hasMovementRelatedPotion() || cp.timeSince(Global.Stat.MOVEMENT_POTION) < 3000)
			return;

		if (cp.isBlockNearby(Material.SCAFFOLDING, 4, -2))
			return;

		if (cp.timeSince(Global.Stat.COBWEB) < 500)
			return;

		if (player.isInsideVehicle())
			return;

		if (player.isFlying() || cp.timeSince(Global.Stat.FLYING) < 5000 || player.isOnGround()
				|| cp.timeSince(Global.Stat.FLIGHT_GROUNDED) < 500 || cp.timeSince(Global.Stat.RIPTIDE) < 5000)
			return;

		if (cp.timeSince(Global.Stat.TELEPORT) < 4000)
			return;

		if (cp.timeSince(Global.Stat.CLIMBING) < 4000)
			return;

		if (cp.timeSince(Global.Stat.IN_LIQUID) < 500)
			return;

		if (cp.timeSince(Global.Stat.ON_GROUND) < 1000)
			return;

		if (cp.timeSince(Global.Stat.REDSTONE) < 1000)
			return;

		if (cp.timeSince(Global.Stat.IN_VEHICLE) < 1000)
			return;

		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 2000)
			return;

		if (player.getLocation().getBlock().isLiquid())
			return;

		if (player.getNearbyEntities(2, 3, 2).stream().anyMatch(e -> e.getType() == EntityType.BOAT))
			return;

		Location safe = cp.getLastSafeLocation();
		if (safe == null)
			return;
		if (!safe.getWorld().equals(player.getWorld()))
			return;

		if (!safe.getWorld().equals(player.getLocation().getWorld()))
			return;

		double yDiff = safe.getY() - player.getLocation().getY();

		if (yDiff >= 0)
			return;

		double dist = safe.distanceSquared(player.getLocation());

		if (dist < 10)
			return;

		cp.flagHack(this, Math.max(Math.min((int) Math.round((dist - 10) * 10.0), 50), 10),
				"Dist: &e" + dist + "&7 >= &a10\n&7YDiff: &e" + yDiff + "&7 < 0");
	}

	@Override
	public String getCategory() {
		return "Flight";
	}

	@Override
	public String getDebugName() {
		return "Flight#4";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
