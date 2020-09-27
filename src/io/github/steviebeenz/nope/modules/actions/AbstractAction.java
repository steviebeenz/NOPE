package io.github.steviebeenz.nope.modules.actions;

import org.bukkit.OfflinePlayer;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;

/**
 * Represents an action that can be used against a player
 * {@link AbstractConditionalAction} is for boolean checks
 * 
 * @author imodm
 *
 */
public abstract class AbstractAction {

	protected NOPE plugin;

	public AbstractAction(NOPE plugin) {
		this.plugin = plugin;
	}

	/**
	 * Ideally @param player should never be an OfflinePlayer.
	 * 
	 * @param check
	 */
	public abstract void execute(OfflinePlayer player, Check check);
}
