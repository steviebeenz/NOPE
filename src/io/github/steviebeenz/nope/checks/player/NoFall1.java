package io.github.steviebeenz.nope.checks.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.checks.Global;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import io.github.steviebeenz.nope.NOPE;

public class NoFall1 implements Check, Listener {

	@Override
	public CheckType getType() {
		return CheckType.PLAYER;
	}

	private NOPE plugin;

	@Override
	public void register(NOPE plugin) throws OperationNotSupportedException {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	private Map<UUID, Double> highest = new HashMap<>();
	private Map<UUID, Long> change = new HashMap<>();

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);
		Location loc = player.getLocation();
		Vector vel = player.getVelocity();
		if (player.getFallDistance() != 0)
			return;
		change.put(player.getUniqueId(), System.currentTimeMillis());

		if (cp.timeSince(Global.Stat.COBWEB) < 100 || player.isGliding() || player.isFlying() || player.isRiptiding()) {
			highest.remove(player.getUniqueId());
			return;
		}

		if (cp.timeSince(Global.Stat.RIPTIDE) < 2000 || cp.timeSince(Global.Stat.IN_LIQUID) < 1000) {
			highest.remove(player.getUniqueId());
			return;
		}

		if (cp.timeSince(Global.Stat.CLIMBING) < 500)
			return;

		if (cp.timeSince(Global.Stat.DAMAGE_TAKEN) < 2000)
			return;

		if (player.getLocation().getBlock().isLiquid())
			return;

		if (vel.getY() >= 0) {
			if (highest.getOrDefault(player.getUniqueId(), 0d) < loc.getY())
				highest.put(player.getUniqueId(), loc.getY());
			return;
		}

		if (player.isOnGround()) {
			if (!highest.containsKey(player.getUniqueId()))
				return;

			double dist = highest.get(player.getUniqueId()) - loc.getY();
			double diff = (highest.get(player.getUniqueId()) - loc.getY()) - player.getFallDistance();
			highest.put(player.getUniqueId(), loc.getY());

			if (diff < .3)
				return;

			if (System.currentTimeMillis() - change.get(player.getUniqueId()) < 1000)
				return;

			cp.flagHack(this, Math.min((int) Math.abs(diff * 10) + 5, 30),
					String.format("Expected: &e%.3f&7\nReceived: &a%.3f", dist, player.getFallDistance()));
			return;
		}
	}

	@EventHandler
	public void onSwap(PlayerChangedWorldEvent event) {
		highest.remove(event.getPlayer().getUniqueId());
	}

	@EventHandler
	public void onToggle(PlayerToggleFlightEvent event) {
		if (!event.isFlying())
			return;
		highest.put(event.getPlayer().getUniqueId(), event.getPlayer().getLocation().getY());
	}

	@Override
	public String getCategory() {
		return "NoFall";
	}

	@Override
	public String getDebugName() {
		return getCategory() + "#1";
	}

	@Override
	public boolean lagBack() {
		return false;
	}

}
