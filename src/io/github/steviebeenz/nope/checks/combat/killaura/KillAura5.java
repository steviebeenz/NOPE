package io.github.steviebeenz.nope.checks.combat.killaura;

import javax.naming.OperationNotSupportedException;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.modules.data.CPlayer;
import io.github.steviebeenz.nope.modules.npc.NPCModule;
import io.github.steviebeenz.nope.protocols.WrapperPlayClientUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.EntityUseAction;

/**
 * Checks to see if the entity that was hit is invalid/an NPC
 * 
 * @author imodm
 *
 */
public class KillAura5 implements Check {

	@Override
	public CheckType getType() {
		return CheckType.COMBAT;
	}

	private NOPE plugin;

	@Override
	public void register(NOPE plugin) throws OperationNotSupportedException {
		this.plugin = plugin;

		ProtocolManager manager = ProtocolLibrary.getProtocolManager();

		PacketAdapter adapter = new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
				WrapperPlayClientUseEntity packet = new WrapperPlayClientUseEntity(event.getPacket());
				Player player = event.getPlayer();

				if (packet.getType() != EntityUseAction.ATTACK)
					return;

				Entity ent = packet.getTarget(event);
				CPlayer cp = KillAura5.this.plugin.getCPlayer(player);

				if (ent != null)
					return;

				NPCModule npc = KillAura5.this.plugin.getModule(NPCModule.class);
				if (npc.isRegisteredNPC(packet.getTargetID()))
					return;

				Bukkit.getScheduler().runTask(plugin, () -> {
					cp.flagHack(KillAura5.this, 30, "Hit invalid entity");
				});
				return;
			}

			@Override
			public void onPacketSending(PacketEvent event) {
			}
		};
		manager.addPacketListener(adapter);

	}

	@Override
	public String getCategory() {
		return "KillAura";
	}

	@Override
	public String getDebugName() {
		return getCategory() + "#5";
	}

	@Override
	public boolean lagBack() {
		return false;
	}

}
