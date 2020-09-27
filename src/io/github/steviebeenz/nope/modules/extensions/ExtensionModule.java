package io.github.steviebeenz.nope.modules.extensions;

import org.bukkit.Bukkit;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.AbstractModule;

public class ExtensionModule extends AbstractModule {

	public ExtensionModule(NOPE plugin) {
		super(plugin);
	}

	@Override
	public void enable() {
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
			new PAPIExtension(plugin).register();
	}

	@Override
	public void disable() {

	}

}
