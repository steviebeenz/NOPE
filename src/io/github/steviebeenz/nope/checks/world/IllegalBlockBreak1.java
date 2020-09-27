package io.github.steviebeenz.nope.checks.world;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Checks if block broken is liquid
 * 
 * @author imodm
 *
 */
public class IllegalBlockBreak1 implements Check, Listener {

	private NOPE plugin;

	@Override
	public CheckType getType() {
		return CheckType.WORLD;
	}

	@Override
	public void register(NOPE plugin) {
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onBlockPlace(BlockBreakEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);
		if (!event.getBlock().isLiquid())
			return;

		cp.flagHack(this, 50);
	}

	@Override
	public String getCategory() {
		return "IllegalBlockBreak";
	}

	@Override
	public String getDebugName() {
		return "IllegalBlockBreak#1";
	}

	@Override
	public boolean lagBack() {
		return false;
	}
}
