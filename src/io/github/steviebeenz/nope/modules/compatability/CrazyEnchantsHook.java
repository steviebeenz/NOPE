package io.github.steviebeenz.nope.modules.compatability;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import me.badbones69.crazyenchantments.api.CrazyEnchantments;
import me.badbones69.crazyenchantments.api.enums.CEnchantments;
import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.events.player.PlayerFlagEvent;
import io.github.steviebeenz.nope.modules.checks.Check;

public class CrazyEnchantsHook extends AbstractCompatability {

	private CrazyEnchantments ce;

	public CrazyEnchantsHook(NOPE plugin) {
		super(plugin);
		this.ce = CrazyEnchantments.getInstance();
	}

	@Override
	public String getName() {
		return "CrazyEnchantments";
	}

	@EventHandler
	public void blastCheck(PlayerFlagEvent event) {
		Player player = event.getPlayer();
		Check check = event.getCheck();
		if (!check.getCategory().equals("FastBreak"))
			return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (!ce.hasEnchantment(item, CEnchantments.BLAST))
			return;
		event.setCancelled(true);
	}

}
