package io.github.steviebeenz.nope.checks.packet;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.naming.OperationNotSupportedException;

import io.github.steviebeenz.nope.NOPE;
import io.github.steviebeenz.nope.modules.checks.Check;
import io.github.steviebeenz.nope.modules.checks.CheckType;
import io.github.steviebeenz.nope.protocols.WrapperPlayServerEntityMetadata;
import io.github.steviebeenz.nope.protocols.WrapperPlayServerRelEntityMove;
import io.github.steviebeenz.nope.protocols.WrapperPlayServerRelEntityMoveLook;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

/**
 * @deprecated Causes some issues with player position syncing
 * @author imodm
 *
 */
public class PlayerESP3 implements Check {

	@Override
	public CheckType getType() {
		return CheckType.PACKET;
	}

	private PacketAdapter blocker;
	private Map<UUID, Map<Entity, Location>> lastKnown = new HashMap<>();

	@Override
	public void register(NOPE plugin) throws OperationNotSupportedException {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		blocker = new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.REL_ENTITY_MOVE) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
			}

			@Override
			public void onPacketSending(PacketEvent event) {
				Player player = event.getPlayer();
				WrapperPlayServerRelEntityMove packet = new WrapperPlayServerRelEntityMove(event.getPacket());
				Entity ent = packet.getEntity(event);
				if (ent == null)
					return;
				if (canSee(player.getEyeLocation(), ent.getLocation())
						|| canSee(player.getEyeLocation(), ent.getLocation().add(0, 2, 0))) {
					set(player.getUniqueId(), ent, ent.getLocation());
					return;
				}

				event.setCancelled(true);
			}
		};
		manager.addPacketListener(blocker);
		blocker = new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
			}

			@Override
			public void onPacketSending(PacketEvent event) {
				Player player = event.getPlayer();
				WrapperPlayServerRelEntityMoveLook packet = new WrapperPlayServerRelEntityMoveLook(event.getPacket());
				Entity ent = packet.getEntity(event);
				if (ent == null)
					return;
				if (canSee(player.getEyeLocation(), ent.getLocation())
						|| canSee(player.getEyeLocation(), ent.getLocation().add(0, 2, 0))) {
					set(player.getUniqueId(), ent, ent.getLocation());
					return;
				}
				event.setCancelled(true);
			}
		};

		blocker = new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
			@Override
			public void onPacketReceiving(PacketEvent event) {
			}

			@Override
			public void onPacketSending(PacketEvent event) {
				Player player = event.getPlayer();
				WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(event.getPacket());
				Entity ent = packet.getEntity(event);
				if (ent == null)
					return;
				if (canSee(player.getEyeLocation(), ent.getLocation())
						|| canSee(player.getEyeLocation(), ent.getLocation().add(0, 2, 0))) {
					return;
				}
				event.setCancelled(true);
			}
		};

		manager.addPacketListener(blocker);
	}

	private void set(UUID key, Entity ent, Location value) {
		Map<Entity, Location> map = lastKnown.getOrDefault(key, new HashMap<>());
		map.put(ent, value);
		lastKnown.put(key, map);
	}

	private boolean canSee(Location source, Location target) {
		RayTraceResult result = source.getWorld().rayTraceBlocks(source, target.toVector().subtract(source.toVector()),
				100, FluidCollisionMode.NEVER, true);
		if (result == null || result.getHitBlock() == null)
			return false;

		Block hit = result.getHitBlock();
		if (!hit.getType().isOccluding())
			return true;

		return (result.getHitPosition().toLocation(source.getWorld()).distanceSquared(source) >= source
				.distanceSquared(target));
	}

	@Override
	public void disable() {
	}

	@Override
	public String getCategory() {
		return "PlayerESP";
	}

	@Override
	public String getDebugName() {
		return getCategory() + "#3";
	}

	@Override
	public boolean lagBack() {
		return false;
	}

}
