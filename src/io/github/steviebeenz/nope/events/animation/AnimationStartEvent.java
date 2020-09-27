package io.github.steviebeenz.nope.events.animation;

import io.github.steviebeenz.nope.modules.animations.AbstractAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when an animation starts.
 * 
 * @author imodm
 *
 */
public class AnimationStartEvent extends Event implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	private AbstractAnimation animation;

	private boolean cancel = false;

	private Player player;

	public AnimationStartEvent(Player player, AbstractAnimation animation) {
		this.player = player;
		this.animation = animation;
	}

	public Player getPlayer() {
		return player;
	}

	public AbstractAnimation getAnimation() {
		return animation;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
