package net.cuddlebat.terrawa.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

@Mixin(OverworldChunkGenerator.class)
public abstract class OverworldGenMixin extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig>
{

	private OverworldGenMixin(IWorld iWorld_1, BiomeSource biomeSource_1, int int_1, int int_2, int int_3,
		OverworldChunkGeneratorConfig chunkGeneratorConfig_1, boolean boolean_1)
	{
		super(iWorld_1, biomeSource_1, int_1, int_2, int_3, chunkGeneratorConfig_1, boolean_1);
	}

//	@Override
//	public void populateNoise(IWorld world, Chunk chunk)
//	{
//		super.populateNoise(world, chunk);
//
//		// TODO just a test
////		if (world instanceof ChunkRegion)
////			world = ((ChunkRegion) world).method_19506();
////		Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(world);
////		if(component.isPresent())
////		{
////			System.out.println("Digging at " + chunk.getPos());
////			component.get().digCaverns((World) world, chunk);
////		}
//	}
	
	@Override
	public void generateFeatures(ChunkRegion region)
	{
		super.generateFeatures(region);
		
		World world = region.getWorld();
		Chunk chunk = region.getChunk(region.getCenterChunkX(), region.getCenterChunkZ());
		Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(world);
		if(component.isPresent())
		{
			// TODO ---- should this be here??
			System.out.println("Digging at " + chunk.getPos());
			component.get().digCaverns(world, chunk);
			// --------- out of ideas, guess it's possible this really wants the region
			System.out.println("Decorating at " + chunk.getPos());
			component.get().decorateBiomes(region, chunk, (OverworldChunkGenerator) (Object) this);
		}
	}

}
