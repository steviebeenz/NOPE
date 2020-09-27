package io.github.steviebeenz.nope.modules.actions.actions;

import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.utils.MSG;

/**
 * Accessibility option that is effectively just {@link MessageAction} but
 * targets only the player.
 * 
 * @author imodm
 *
 */
public class MessagePlayerAction extends AbstractAction {

	private String message;

	public MessagePlayerAction(NOPE plugin, String message) {
		super(plugin);
		this.message = message;
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (!player.isOnline())
			return;
		MSG.tell(player.getPlayer(), MSG.replaceCheckPlaceholder(message, plugin.getCPlayer(player), check));
	}

}
