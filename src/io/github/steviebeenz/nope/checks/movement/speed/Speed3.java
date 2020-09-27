package io.github.steviebeenz.nope.checks.movement.speed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Checks average speeds
 * 
 * @author imodm
 *
 */
public class Speed3 implements Check, Listener {

	private NOPE plugin;

	@Override
	public CheckType getType() {
		return CheckType.MOVEMENT;
	}

	private Map<UUID, List<Double>> distances = new HashMap<>();

	@Override
	public void register(NOPE plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	private final int SIZE = 15;

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);
		if (player.isFlying() || player.isInsideVehicle())
			return;

		if (cp.timeSince(Global.Stat.FLYING) < 2000 || cp.timeSince(Global.Stat.RIPTIDE) < 2000)
			return;
		if (cp.timeSince(Global.Stat.ICE_TRAPDOOR) < 1000 || cp.timeSince(Global.Stat.ON_ICE) < 2000)
			return;
		if (cp.hasMovementRelatedPotion())
			return;
		if (cp.timeSince(Global.Stat.DISABLE_GLIDE) < 1000)
			return;
		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 1000)
			return;
		if (cp.timeSince(Global.Stat.SOUL_SPEED) < 500)
			return;

		if (player.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE) && player.isSwimming())
			return;

		Location to = event.getTo(), from = event.getFrom();

		double dist = Math.abs(to.getX() - from.getX()) + Math.abs(to.getZ() - from.getZ());

		List<Double> ds = distances.getOrDefault(player.getUniqueId(), new ArrayList<>());

		ds.add(0, dist);

		for (int i = SIZE; i < ds.size(); i++) {
			ds.remove(i);
		}

		distances.put(player.getUniqueId(), ds);

		if (ds.size() < SIZE)
			return;

		double avg = 0;

		for (double d : ds)
			avg += d;

		avg /= ds.size();

		double max = .5575286;

		if (avg < max)
			return;

		cp.flagHack(this, (int) Math.round((avg - max) * 40) + 10, "Avg: &e" + avg + "&7 >= &a" + max);
	}

	@Override
	public String getCategory() {
		return "Speed";
	}

	@Override
	public String getDebugName() {
		return "Speed#3";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
