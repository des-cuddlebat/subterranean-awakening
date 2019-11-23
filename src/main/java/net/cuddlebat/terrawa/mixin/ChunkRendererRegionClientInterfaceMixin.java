package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.cuddlebat.terrawa.strangethings.IWorldGetter;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.world.World;

@Mixin(ChunkRendererRegion.class)
public abstract class ChunkRendererRegionClientInterfaceMixin implements IWorldGetter
{
	@Override
	@Accessor
	public abstract World getWorld();
}
