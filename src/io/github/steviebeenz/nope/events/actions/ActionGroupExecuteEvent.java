package io.github.steviebeenz.nope.events.actions;

import io.github.steviebeenz.nope.modules.actions.ActionGroup;
import io.github.steviebeenz.nope.modules.checks.Check;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActionGroupExecuteEvent extends Event implements Cancellable {

	private static final HandlerList handlers = new HandlerList();

	private ActionGroup group;
	private OfflinePlayer player;
	private Check check;

	private boolean cancel = false;

	public ActionGroupExecuteEvent(ActionGroup group, OfflinePlayer player, Check check) {
		this.group = group;
		this.player = player;
	}

	public ActionGroup getActionGroup() {
		return group;
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public Check getCheck() {
		return check;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

}
