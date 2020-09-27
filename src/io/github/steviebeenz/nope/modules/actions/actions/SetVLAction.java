package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.data.CPlayer;

/**
 * Sets the player VL to the specified value
 * 
 * @author imodm
 *
 */
public class SetVLAction extends AbstractAction {

	private int diff;

	public SetVLAction(NOPE plugin, int diff) {
		super(plugin);
		this.diff = diff;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		CPlayer cp = plugin.getCPlayer(player);
		cp.setVL(check.getCategory(), diff);
	}

}
