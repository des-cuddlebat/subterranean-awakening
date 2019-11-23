package net.cuddlebat.terrawa.mixin;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
import net.minecraft.entity.EntityCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorMixin
{
	@Accessor
	protected abstract IWorld getWorld();
	
	@Inject(at = @At("HEAD"), cancellable = true, method = "getEntitySpawnList")
	protected void getSpawnListMixin(EntityCategory category, BlockPos pos,
		CallbackInfoReturnable<List<Biome.SpawnEntry>> cir)
	{
		IWorld world = getWorld();
		if(world instanceof ChunkRegion)
			world = world.getWorld();
		Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(getWorld());
		Optional<Biome> override = component.isPresent() ?
			component.get().maybeGetBiomeOverride(pos) : Optional.empty();
		if(override.isPresent())
			cir.setReturnValue(override.get().getEntitySpawnList(category));
	}
	
//	@Redirect(at = @At(value = "INVOKE",
//		target = "Lnet/minecraft/world/ExtendedBlockView;getBiome(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/world/biome/Biome;"),
//		method = "getEntitySpawnList")
//	protected Biome getBiomeProxy(ExtendedBlockView view, BlockPos pos)
//	{
//		IWorld world = (IWorld) view;
//		if(world instanceof ChunkRegion)
//			world = world.getWorld();
//		Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(world);
//		Optional<Biome> override = component.isPresent() ?
//			component.get().maybeGetBiomeOverride(pos) : Optional.empty();
//		return override.orElse(world.getBiome(pos));
//	}

}
