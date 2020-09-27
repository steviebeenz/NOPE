package io.github.steviebeenz.nope.modules.actions.actions;

import io.github.steviebeenz.nope.modules.animations.AbstractAnimation;
import io.github.steviebeenz.nope.modules.animations.AnimationManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.actions.AbstractAction;
import io.github.steviebeenz.nope.modules.actions.ActionGroup;
import io.github.steviebeenz.nope.modules.actions.ActionManager;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.utils.MSG;

/**
 * Runs an animation and then the {@link ActionGroup} after it is done
 * 
 * @author imodm
 *
 */
public class AnimationAction extends AbstractAction {

	@SuppressWarnings("unused")
	private String data;
	private ActionGroup group;
	private AnimationManager.AnimationType type;

	public AnimationAction(NOPE plugin, AnimationManager.AnimationType type, String data) {
		super(plugin);
		this.data = data;
		this.type = type;

		group = plugin.getModule(ActionManager.class).getActions(data).get(0);
	}

	@Override
	public void execute(OfflinePlayer player, Check check) {
		if (!player.isOnline()) {
			group.activate(player, check);
			return;
		}

		Player p = player.getPlayer();
		if (p == null || !p.isValid()) {
			MSG.warn(p.getName() + " is invalid?");
			group.activate(player, check);
			return;
		}

		AbstractAnimation animation = plugin.getModule(AnimationManager.class).createAnimation(type, p, check);
		animation.setEndAction(this.group);
		if (plugin.getModule(AnimationManager.class).startAnimation(p, animation))
			return;
		MSG.warn("Unable to start animation for " + player.getName());
	}

}
