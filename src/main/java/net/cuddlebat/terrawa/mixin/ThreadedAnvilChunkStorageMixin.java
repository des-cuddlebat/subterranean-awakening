package net.cuddlebat.terrawa.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.Unpooled;
import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
import net.cuddlebat.terrawa.network.ModPackets;
import net.cuddlebat.terrawa.utils.IntRange;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.Packet;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ThreadedAnvilChunkStorage;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(ThreadedAnvilChunkStorage.class)
public class ThreadedAnvilChunkStorageMixin
{
	@Inject(at = @At("RETURN"), method = "sendChunkDataPackets")
	private void sendChunkDataPacketsMixin(ServerPlayerEntity player, Packet<?>[] packets, WorldChunk chunk, CallbackInfo ci)
	{
		Optional<ICavernsComponent> maybeComp = Const.Component.CAVERNS_COMPONENT.maybeGet(chunk.getWorld());
		if(!maybeComp.isPresent())
			return;
		ICavernsComponent component = maybeComp.get();
		int x = chunk.getPos().x;
		int z = chunk.getPos().z;
		int[] bottoms = new int[256];
		int[] tops = new int[256];
		byte[] biomes = new byte[256];
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Map<Byte, String> map = new HashMap<>();
		Map<String, Byte> mapReverse = new HashMap<>();
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				int k = 16 * i + j;
				IntRange range = component.getRangeForPacket(x * 16 + i, z * 16 + j);
				bottoms[k] = range.getMin();
				tops[k] = range.getMax();
				mutable.set(x * 16 + i, Math.max(range.getMin(), 0), z * 16 + j);
				Optional<Biome> override = component.maybeGetBiomeOverride(mutable);
				if(override.isPresent())
				{
					String id = Registry.BIOME.getId(override.get()).toString();
					if(!mapReverse.containsKey(id))
					{
						mapReverse.put(id, (byte) (map.size() - 1));
						map.put((byte) (map.size() - 1), id);
					}
					biomes[k] = mapReverse.get(id);
				}
				else
				{
					biomes[k] = 0;
				}
			}
		}

		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		
		buf.writeInt(x);
		buf.writeInt(z);
		buf.writeIntArray(bottoms);
		buf.writeIntArray(tops);
		buf.writeByteArray(biomes);
		buf.writeInt(map.size());
		for(Map.Entry<Byte, String> entry : map.entrySet())
		{
			buf.writeByte(entry.getKey());
			buf.writeString(entry.getValue());
		}
		
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ModPackets.SEND_BIOME_OVERRIDES, buf);
	}
	
//	int x = attachedData.readInt();
//	int z = attachedData.readInt();
//	WorldChunk chunk = (WorldChunk)packetContext.getPlayer().world.getChunk(x, z);
//	int[] bottoms = attachedData.readIntArray();
//	int[] tops = attachedData.readIntArray();
//	byte[] biomes = attachedData.readByteArray();
//
//	int biomeCount = attachedData.readInt();
//	Map<Byte, String> map = new HashMap<>();
//	for(int i = 0; i < biomeCount; i++)
//	{
//		byte index = attachedData.readByte();
//		String biome = attachedData.readString();
//		map.put(index, biome);
//	}
//	((IBiomeOverride)chunk).setData(bottoms, tops, biomes, map);
}
