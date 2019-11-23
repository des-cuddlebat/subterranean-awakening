package net.cuddlebat.terrawa.cardinal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import net.cuddlebat.terrawa.utils.IntRange;
import net.cuddlebat.terrawa.world.CavernsSystemLayer;
import net.cuddlebat.terrawa.world.Sector;
import net.cuddlebat.terrawa.world.SectorPos;
import net.cuddlebat.terrawa.world.SystemLayerBase;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CavernsComponent implements ICavernsComponent
{
	long seed;
	private HashMap<SectorPos, Sector> sectors = new HashMap<>();
	private SystemLayerBase<?>[] layers;
	private HashSet<SectorPos> generating = new HashSet<>();

	public CavernsComponent(World world)
	{
		this.seed = world.getSeed();
		
		layers = new SystemLayerBase<?>[]
		{
			new CavernsSystemLayer(
				world.getSeed(),
				world.getSeed() ^ 4651389465L,
				world.getSeed() ^ 356798465132L,
				world.getSeed() ^ 133389745L)
		};
	}

	public Sector getOrCreate(BlockPos at)
	{
		SectorPos pos = SectorPos.fromBlockPos(at);
		if (!sectors.containsKey(pos))
		{
			if(generating.contains(pos))
			{
				while(generating.contains(pos))
				{
					try
					{
						// I absolutely don't think this is how to handle threading
						// But I don't know how to handle threading so yes
						Thread.sleep(10);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						// Also I feel like this is a seriously bad workaround to everything
						e.printStackTrace();
					}
				}
				return sectors.get(pos);
			}
			
			generating.add(pos);
			sectors.put(pos, new Sector(pos, this));
			sectors.get(pos).generate();
			generating.remove(pos);
		}
		return sectors.get(pos);
	}
	
	@Override
	public Optional<Biome> maybeGetBiomeOverride(BlockPos pos)
	{
		Sector sec = getOrCreate(pos);
		return sec.maybeGetBiomeOverride(pos);
	}

	public SystemLayerBase<?>[] getLayers()
	{
		return layers;
	}

	public void setLayers(SystemLayerBase<?>[] layers)
	{
		this.layers = layers;
	}

	@Override
	public void digCaverns(World world, Chunk chunk)
	{
		Sector sec = getOrCreate(chunk.getPos().getCenterBlockPos());
		sec.digCaverns(world, chunk);
	}

	@Override
	public void decorateBiomes(IWorld world, Chunk chunk, ChunkGenerator<?> gen)
	{
		Sector sec = getOrCreate(chunk.getPos().getCenterBlockPos());
		sec.decorateBiomes(world, chunk, gen);
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		return new CompoundTag();
	}

	@Override
	public IntRange getRangeForPacket(int x, int z)
	{
		Sector sec = getOrCreate(new BlockPos(x, 0, z));
		return sec.getRangeForPacket(x, z);
	}

}
