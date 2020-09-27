package io.github.steviebeenz.nope.checks.movement.flight;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.steviebeenz.nope.NOPE;

/**
 * Checks if a player moves completely horizontally without being on the ground
 * 
 * @author imodm
 *
 */
public class Flight1 implements Check, Listener {

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

		if (player.isOnGround())
			return;
		if (player.isFlying() || cp.isInClimbingBlock() || player.isInsideVehicle() || player.isRiptiding())
			return;
		if (cp.timeSince(Global.Stat.FLYING) < 500)
			return;
		if (cp.timeSince(Global.Stat.IN_LIQUID) < 400)
			return;
		if (cp.timeSince(Global.Stat.RESPAWN) < 1000)
			return;

		if (player.getNearbyEntities(2, 3, 2).stream().anyMatch(e -> e.getType() == EntityType.BOAT))
			return;

		Location to = event.getTo(), from = event.getFrom();

		if (to.getY() != from.getY())
			return;

		if (cp.timeSince(Global.Stat.COBWEB) < 500)
			return;

		if (cp.timeSince(Global.Stat.TELEPORT) < 500)
			return;

		if (cp.timeSince(Global.Stat.ON_GROUND) <= 300)
			return;
		if (cp.timeSince(Global.Stat.BLOCK_PLACE) < 1500)
			return;
		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 2000)
			return;

		if (player.getVelocity().getY() > 0)
			return;

		cp.flagHack(this, 30);
	}

	@Override
	public String getCategory() {
		return "Flight";
	}

	@Override
	public String getDebugName() {
		return "Flight#1";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
