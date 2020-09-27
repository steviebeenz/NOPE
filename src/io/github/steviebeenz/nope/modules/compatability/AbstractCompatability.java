package io.github.steviebeenz.nope.modules.compatability;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.AbstractModule;

/**
 * Responsible for preventing false flags with specific plugins.
 * 
 * @author imodm
 *
 */
public abstract class AbstractCompatability extends AbstractModule implements Listener {

	public AbstractCompatability(NOPE plugin) {
		super(plugin);
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
	}

	public abstract String getName();
}
