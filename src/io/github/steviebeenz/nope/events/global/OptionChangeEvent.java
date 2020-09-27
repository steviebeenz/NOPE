package io.github.steviebeenz.nope.events.global;

import io.github.steviebeenz.nope.modules.data.Option;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Called when a Global NOPE Option is changed
 * 
 * @author imodm
 *
 */
public class OptionChangeEvent extends Event {

	private static final HandlerList handlers = new HandlerList();

	private Option option;

	public OptionChangeEvent(Option option) {
		this.option = option;
	}

	public Option getOption() {
		return option;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
