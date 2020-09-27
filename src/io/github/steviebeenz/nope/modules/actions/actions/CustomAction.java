package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.actions.ActionGroup;
import io.github.steviebeenz.nope.modules.actions.ActionManager;
import io.github.steviebeenz.nope.modules.checks.Check;

/**
 * Used to signify a custom action that the config has set, this is extremely
 * useful
 * 
 * @author imodm
 *
 */
public class CustomAction extends AbstractAction {

	private ActionGroup group;

	public CustomAction(NOPE plugin, String data) {
		super(plugin);
		group = new ActionGroup();
		group = plugin.getModule(ActionManager.class).getActions(data).get(0);
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		group.activate(player, check);
	}

}
