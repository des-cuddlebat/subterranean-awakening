package net.cuddlebat.terrawa.strangethings;

import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.world.World;

public interface IWorldGetter
{
	@Accessor
	public World getWorld();
}
