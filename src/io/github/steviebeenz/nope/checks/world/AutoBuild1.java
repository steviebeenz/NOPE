package io.github.steviebeenz.nope.checks.world;

import javax.naming.OperationNotSupportedException;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.util.Vector;

/**
 * Compares block location distance to click location
 * 
 * @author imodm
 *
 */
public class AutoBuild1 implements Check, Listener {

	@Override
	public CheckType getType() {
		return CheckType.WORLD;
	}

	private NOPE plugin;

	@Override
	public void register(NOPE plugin) throws OperationNotSupportedException {
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		CPlayer cp = plugin.getCPlayer(player);
		Block block = event.getBlock();
		Vector aim = player.getEyeLocation().getDirection().normalize()
				.multiply(player.getEyeLocation().distance(block.getLocation()));
		Block actual = player.getEyeLocation().clone().add(aim).getBlock();
		if (!event.getBlockAgainst().getType().isSolid())
			return;
		if (block.equals(actual))
			return;
		if (block.getType().toString().contains("BED"))
			return;
		if (block.getType() == Material.TALL_GRASS || block.getType() == Material.SCAFFOLDING)
			return;
		double dist = block.getLocation().distanceSquared(actual.getLocation());
		if (dist <= 2)
			return;
		cp.flagHack(this, 20, "Dist: &e" + dist);
	}

	@Override
	public String getCategory() {
		return "AutoBuild";
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
