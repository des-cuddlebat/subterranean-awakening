//package net.cuddlebat.terrawa.world;
//
//import java.util.Optional;
//import java.util.Random;
//import java.util.function.Function;
//
//import com.mojang.datafixers.Dynamic;
//
//import net.cuddlebat.terrawa.Const;
//import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.ChunkRegion;
//import net.minecraft.world.IWorld;
//import net.minecraft.world.World;
//import net.minecraft.world.chunk.Chunk;
//import net.minecraft.world.gen.chunk.ChunkGenerator;
//import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
//import net.minecraft.world.gen.feature.DefaultFeatureConfig;
//import net.minecraft.world.gen.feature.Feature;
//
//public class CaveFeature extends Feature<DefaultFeatureConfig>
//{
//
//	public CaveFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> func)
//	{
//		super(func);
//	}
//
//	@Override
//	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> var2, Random rand,
//		BlockPos pos, DefaultFeatureConfig var5)
//	{
//		if (world instanceof ChunkRegion)
//			world = ((ChunkRegion) world).method_19506();
//		Chunk chunk = world.getChunk(pos);
//		Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(world);
//		if (component.isPresent())
//		{
//			System.out.println(chunk.getPos().x + " " + chunk.getPos().z);
//			component.get().digCaverns((World) world, chunk);
//			// TODO this could better use a different phase of the gen process
////			component.get().decorateBiomes((World) world, chunk);
//		}
//
//		return true;
//	}
//
//}
