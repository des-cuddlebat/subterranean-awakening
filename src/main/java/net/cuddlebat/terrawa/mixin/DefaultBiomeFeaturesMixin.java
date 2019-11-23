package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.cuddlebat.terrawa.world.NoopCarver;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

@Mixin(value = DefaultBiomeFeatures.class) 
public abstract class DefaultBiomeFeaturesMixin
{

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/world/gen/carver/Carver;CAVE:Lnet/minecraft/world/gen/carver/Carver;"),
		method = "addLandCarvers")
	private static Carver<ProbabilityConfig> caveCarverProxy()
	{
		return new NoopCarver(ProbabilityConfig::deserialize, 256);
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/world/gen/carver/Carver;CAVE:Lnet/minecraft/world/gen/carver/Carver;"),
		method = "addOceanCarvers")
	private static Carver<ProbabilityConfig> caveCarverOceanProxy()
	{
		return new NoopCarver(ProbabilityConfig::deserialize, 256);
	}
}
