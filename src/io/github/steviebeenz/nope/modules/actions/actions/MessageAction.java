package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.utils.MSG;

/**
 * Sends a message to a specified target. The target can be ALL or a permission.
 * 
 * @author imodm
 *
 */
public class MessageAction extends AbstractAction {

	private String target, message;

	public MessageAction(NOPE plugin, String target, String message) {
		super(plugin);
		this.message = message;
		this.target = target;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		String replace = MSG.replaceCheckPlaceholder(message, plugin.getCPlayer(player), check);
		if (target.contains(".")) {
			MSG.tell(target, replace);
			return;
		}
		if (target.equalsIgnoreCase("all")) {
			MSG.announce(replace);
			return;
		}
		MSG.warn("Unknown message target: " + target);
	}

}
