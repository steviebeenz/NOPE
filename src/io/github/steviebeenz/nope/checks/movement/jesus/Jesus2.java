package io.github.steviebeenz.nope.checks.movement.jesus;

import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Specifically checks if the client/server is saying the player is on ground
 * while on water
 * 
 * @author imodm
 *
 */
public class Jesus2 implements Check, Listener {

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

		if (!player.isOnGround())
			return;

		if (!player.getLocation().getBlock().isLiquid())
			return;
		if (player.getLocation().add(0, 1, 0).getBlock().isLiquid())
			return;

		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				Block b = player.getLocation().clone().add(x, -1, z).getBlock();
				if (b.getType().isSolid())
					return;
			}
		}

		cp.flagHack(this, 50);

	}

	@Override
	public String getCategory() {
		return "Jesus";
	}

	@Override
	public String getDebugName() {
		return getCategory() + "#2";
	}

	@Override
	public boolean lagBack() {
		return true;
	}
}
