package net.cuddlebat.terrawa.world.feature;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.world.feature.LumishroomFeature.Shape;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public abstract class ModFeatures
{
	public static final Feature<DefaultFeatureConfig> SUB_JUNGLE_BUSH = new SubJungleBushFeature(
		DefaultFeatureConfig::deserialize, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState());
	public static final Feature<DefaultFeatureConfig> SUB_ICE_SPIKES = new SpikeFeature(
		DefaultFeatureConfig::deserialize, Blocks.PACKED_ICE.getDefaultState());
	public static final Feature<DefaultFeatureConfig> SUB_FLAT_LUMISHROOM = new LumishroomFeature(
		DefaultFeatureConfig::deserialize, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.CYAN_WOOL.getDefaultState(), Shape.FLAT);
	public static final Feature<DefaultFeatureConfig> SUB_BOWL_LUMISHROOM = new LumishroomFeature(
		DefaultFeatureConfig::deserialize, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.PURPLE_WOOL.getDefaultState(), Shape.BOWL);
	public static final Feature<DefaultFeatureConfig> SUB_CUP_LUMISHROOM = new LumishroomFeature(
		DefaultFeatureConfig::deserialize, Blocks.MUSHROOM_STEM.getDefaultState(), Blocks.BLUE_WOOL.getDefaultState(), Shape.CUP);
	
	private ModFeatures()
	{
	}
	
	public static void doRegister()
	{
		Registry.register(Registry.FEATURE, new Identifier(Const.MODID, "jungle_bush"), SUB_JUNGLE_BUSH);
		Registry.register(Registry.FEATURE, new Identifier(Const.MODID, "ice_spikes"), SUB_ICE_SPIKES);
		Registry.register(Registry.FEATURE, new Identifier(Const.MODID, "flat_lumishroom"), SUB_FLAT_LUMISHROOM);
		Registry.register(Registry.FEATURE, new Identifier(Const.MODID, "bowl_lumishroom"), SUB_BOWL_LUMISHROOM);
		Registry.register(Registry.FEATURE, new Identifier(Const.MODID, "cup_lumishroom"), SUB_CUP_LUMISHROOM);
	}

}
