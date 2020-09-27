package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.BlockFace;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Teleports the player to their {@link CPlayer#getLastSafeLocation()} This
 * naturally means it doesn't work against certain check types such as KillAura
 * 
 * @author imodm
 *
 */
public class CancelAction extends AbstractAction {

	public CancelAction(NOPE plugin) {
		super(plugin);
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (!player.isOnline())
			return;
		CPlayer cp = plugin.getCPlayer(player);

		Location safe = cp.getLastSafeLocation();
		if (safe == null)
			return;
		if (!safe.getWorld().equals(player.getPlayer().getWorld()))
			return;
		if (!safe.getBlock().getRelative(BlockFace.DOWN).getType().isSolid())
			return;
		if (safe.getBlock().getRelative(BlockFace.UP).getType() != Material.AIR)
			return;
		player.getPlayer().teleport(cp.getLastSafeLocation());
	}

}
