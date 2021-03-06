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
 * Same as @see Flight1 but different onGround detection
 * 
 * @author imodm
 *
 */
public class Flight2 implements Check, Listener {

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

		if (player.isFlying() || cp.isInWeirdBlock() || player.isInsideVehicle())
			return;

		if (cp.timeSince(Global.Stat.FLYING) < 500 || cp.timeSince(Global.Stat.RIPTIDE) < 1000)
			return;

		Location to = event.getTo(), from = event.getFrom();

		if (to.getY() != from.getY())
			return;

		if (player.getNearbyEntities(2, 3, 2).stream().anyMatch(e -> e.getType() == EntityType.BOAT))
			return;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (player.getLocation().clone().add(x, -.1, z).getBlock().getType().isSolid())
					return;
				if (player.getLocation().clone().add(x, -1.5, z).getBlock().getType().isSolid())
					return;
				if (player.getLocation().clone().add(x, 0, z).getBlock().getType() != Material.AIR)
					return;
			}
		}

		if (cp.timeSince(Global.Stat.FLIGHT_GROUNDED) < 1000)
			return;

		if (cp.timeSince(Global.Stat.LILY_PAD) < 500)
			return;

		cp.flagHack(this, 20);
	}

	@Override
	public String getCategory() {
		return "Flight";
	}

	@Override
	public String getDebugName() {
		return "Flight#2";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
