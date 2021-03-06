package io.github.steviebeenz.nope.modules.bans;

import java.util.UUID;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.AbstractModule;

/**
 * Used to register bans in ban plugins.
 * 
 * @author imodm
 *
 */
public abstract class BanHook extends AbstractModule {
	public BanHook(NOPE plugin) {
		super(plugin);
	}

	@Override
	public void enable() {
	}

	@Override
	public void disable() {
	}

	public void ban(UUID player) {
		ban(player, null);
	}

	public void ban(UUID player, String reason) {
		ban(player, reason, -1);
	}

	public abstract void ban(UUID player, String reason, long time);

	public abstract int bans(UUID player);

}
