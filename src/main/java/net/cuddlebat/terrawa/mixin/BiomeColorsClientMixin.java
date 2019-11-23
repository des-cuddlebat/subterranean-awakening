package net.cuddlebat.terrawa.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
import net.cuddlebat.terrawa.strangethings.IBiomeOverride;
import net.cuddlebat.terrawa.strangethings.IWorldGetter;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.ExtendedBlockView;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsClientMixin
{
	@Redirect(at = @At(value = "INVOKE",
		target = "Lnet/minecraft/world/ExtendedBlockView;getBiome(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome;"),
		method = "getColor")
	private static Biome getBiomeProxy(ExtendedBlockView self, BlockPos pos)
	{
		Optional<World> maybeWorld = tryGetWorldFromView(self);
		if(!maybeWorld.isPresent())
			return self.getBiome(pos);
		World world = maybeWorld.get();
		IBiomeOverride biomes = (IBiomeOverride) (WorldChunk) world.getChunk(pos);
//		Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(world);
//		Optional<Biome> override = component.isPresent() ?
//			component.get().maybeGetBiomeOverride(pos) : Optional.empty();
		return biomes.maybeGetBiomeOverride(pos).orElse(self.getBiome(pos));
	}
	
	
	private static Optional<World> tryGetWorldFromView(ExtendedBlockView view)
	{
		if(view instanceof World)
			return Optional.of((World)view);
		if(view instanceof ChunkRegion)
			return Optional.of(((ChunkRegion)view).getWorld());
		if(view instanceof ChunkRendererRegion)
			return Optional.of(((IWorldGetter)view).getWorld());
		return Optional.empty();
	}
}
