package io.github.steviebeenz.nope.commands.sub;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.commands.CommandResult;
import io.github.steviebeenz.nope.commands.Subcommand;
import io.github.steviebeenz.nope.modules.animations.AnimationManager;
import io.github.steviebeenz.nope.modules.animations.AnimationManager.AnimationType;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.utils.MSG;

public class TestAnimationSubcommand extends Subcommand {

	public TestAnimationSubcommand(NOPE plugin) {
		super(plugin);
	}

	@Override
	public List<String[]> tabCompletions(CommandSender sender, String[] args) {
		List<String[]> result = new ArrayList<>();
		List<String> names = new ArrayList<>();
		for (AnimationType type : AnimationType.values())
			names.add(type.toString());
		result.add(names.toArray(new String[names.size()]));
		return result;
	}

	@Override
	public String getName() {
		return "testanimation";
	}

	@Override
	public CommandResult execute(CommandSender sender, String[] args) {
		AnimationManager aMod = plugin.getModule(AnimationManager.class);

		if (args.length < 2) {
			MSG.tell(sender, "Please specify an animation.");
			return CommandResult.MISSING_ARGUMENT;
		}

		Check check = new Check() {

			@Override
			public void register(NOPE plugin) throws OperationNotSupportedException {
			}

			@Override
			public boolean lagBack() {
				return false;
			}

			@Override
			public CheckType getType() {
				return CheckType.MISC;
			}

			@Override
			public String getDebugName() {
				return "AnimationTest#1";
			}

			@Override
			public String getCategory() {
				return "AnimationTest";
			}
		};

		Player animTarget = null;

		if (args.length > 2 && sender.hasPermission("nope.command.testanimation.others")) {
			animTarget = Bukkit.getPlayer(args[2]);
		} else if (sender instanceof Player) {
			animTarget = (Player) sender;
		}

		if (animTarget == null)
			return CommandResult.INVALID_ARGUMENT;

		AnimationType type;
		try {
			type = AnimationType.valueOf(args[1].toUpperCase());
		} catch (IllegalFormatException e) {
			MSG.tell(sender, MSG.getString("Command.TestAnimation.Unknown", "Unknown Animation."));
			return CommandResult.INVALID_ARGUMENT;
		}

		aMod.startAnimation((Player) sender, aMod.createAnimation(type, (Player) sender, check));
		return CommandResult.SUCCESS;
	}

	@Override
	public String getUsage() {
		return "[animation] <player>";
	}

	@Override
	public String getPermission() {
		return "nope.command." + getName();
	}

	@Override
	public String getDescription() {
		return "Test an animation";
	}

}
