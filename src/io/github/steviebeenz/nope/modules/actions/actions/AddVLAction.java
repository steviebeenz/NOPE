package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Adds VL to the player
 * 
 * @author imodm
 *
 */
public class AddVLAction extends AbstractAction {

	private int diff;

	public AddVLAction(NOPE plugin, int diff) {
		super(plugin);
		this.diff = diff;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		CPlayer cp = plugin.getCPlayer(player);
		cp.setVL(check.getCategory(), cp.getVL(check.getCategory()) + diff);
	}
}
