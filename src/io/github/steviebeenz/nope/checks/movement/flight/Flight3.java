package io.github.steviebeenz.nope.checks.movement.flight;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import io.github.steviebeenz.nope.NOPE;

/**
 * Checks if a player moves vertically straight up
 * 
 * @author imodm
 *
 */
public class Flight3 implements Check, Listener {

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

		if (player.isFlying() || cp.isInWeirdBlock() || player.isInsideVehicle() || player.isRiptiding())
			return;

		if (cp.hasMovementRelatedPotion() || cp.timeSince(Global.Stat.MOVEMENT_POTION) < 1000)
			return;

		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 2000)
			return;

		if (cp.timeSince(Global.Stat.FLYING) < 2000 || cp.timeSince(Global.Stat.RIPTIDE) < 2000)
			return;

		if (cp.timeSince(Global.Stat.BLOCK_PLACE) < 1000)
			return;

		if (cp.timeSince(Global.Stat.IN_LIQUID) < 1000)
			return;

		if (cp.timeSince(Global.Stat.TELEPORT) < 1000)
			return;

		if (cp.timeSince(Global.Stat.FLIGHT_GROUNDED) < 500)
			return;

		if (cp.timeSince(Global.Stat.LILY_PAD) < 500)
			return;

		if (player.hasPotionEffect(PotionEffectType.LEVITATION))
			return;

		if (cp.isBlockNearby(Material.SCAFFOLDING, 4, -2))
			return;

		if (cp.timeSince(Global.Stat.CLIMBING) < 1000) {
			return;
		}

		Location safe = cp.getLastSafeLocation();
		if (safe == null)
			return;

		if (event.getTo().getY() - 2 < safe.getY())
			return;

		if (event.getTo().getY() <= event.getFrom().getY())
			return;

		cp.flagHack(this, 10);
	}

	@Override
	public String getCategory() {
		return "Flight";
	}

	@Override
	public String getDebugName() {
		return "Flight#3";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
