package io.github.steviebeenz.nope.events.animation;

import io.github.steviebeenz.nope.modules.animations.AbstractAnimation;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when an animation ends, whether because the player left early or if it
 * ended naturally.
 * 
 * @author imodm
 *
 */
public class AnimationEndEvent extends Event {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	private AbstractAnimation animation;

	private Player player;

	public AnimationEndEvent(Player player, AbstractAnimation animation) {
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

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
