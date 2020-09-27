package io.github.steviebeenz.nope.checks.movement;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.Global;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Checks if a player moves vertically up
 * 
 * @author imodm
 *
 */
public class Spider1 implements Check, Listener {

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

		Location to = event.getTo();
		Location from = event.getFrom();

		if (player.isOnGround() || player.isFlying())
			return;

		if (to.getY() < from.getY())
			return;

		if (cp.isBlockNearby(Material.SCAFFOLDING, 4, -2))
			return;

		if (cp.timeSince(Global.Stat.COBWEB) < 500)
			return;

		if (cp.hasMovementRelatedPotion() || cp.timeSince(Global.Stat.MOVEMENT_POTION) < 1000)
			return;

		if (cp.timeSince(Global.Stat.FLYING) < 2000 || cp.timeSince(Global.Stat.RIPTIDE) < 2000)
			return;

		if (cp.timeSince(Global.Stat.SHULKER) < 1000)
			return;

		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 500)
			return;

		if (cp.isInClimbingBlock())
			return;

		if (cp.timeSince(Global.Stat.ON_GROUND) < 1000)
			return;

		if (cp.timeSince(Global.Stat.IN_LIQUID) < 1000)
			return;

		if (cp.timeSince(Global.Stat.BLOCK_PLACE) < 1000)
			return;

		if (cp.timeSince(Global.Stat.CLIMBING) < 500)
			return;

		if (cp.timeSince(Global.Stat.REDSTONE) < 1000)
			return;

		if (player.isInsideVehicle() && player.getVehicle().getType() == EntityType.HORSE)
			return;

		if (player.getLocation().getBlock().isLiquid())
			return;

		if (player.getNearbyEntities(1, 3, 1).stream().anyMatch(e -> e.getType() == EntityType.BOAT))
			return;

		double dist = cp.distanceToGround() - 1 + (player.getLocation().getY() % 1);

		double max = 1.2595999415636072;
		if (dist <= max)
			return;

		if ((dist + "").contains("999999999")) {
			cp.flagHack(this, 30, "Dist: " + dist);
		} else {
			cp.flagHack(this, 20, "Dist: " + dist);
		}
	}

	@Override
	public String getCategory() {
		return "Spider";
	}

	@Override
	public String getDebugName() {
		return getCategory() + "#1";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
