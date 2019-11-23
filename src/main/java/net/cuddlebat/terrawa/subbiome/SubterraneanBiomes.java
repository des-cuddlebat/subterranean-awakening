package net.cuddlebat.terrawa.subbiome;

import java.util.ArrayList;
import java.util.Random;

import net.cuddlebat.terrawa.Const;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

public class SubterraneanBiomes
{
	private static ArrayList<SubBiome> all = new ArrayList<>();
	
	public static final SubBiome JUNGLE = new JungleSubBiome(settingsOf(Biomes.JUNGLE));
	public static final SubBiome FROST = new FrostSubBiome(settingsOf(Biomes.ICE_SPIKES));
	public static final SubBiome DROUGHT = new DroughtSubBiome(settingsOf(Biomes.DESERT));
	public static final SubBiome LUMISHROOM = new LumishroomSubBiome(settingsOf(Biomes.MUSHROOM_FIELDS));
	
	public static SubBiome pickRandomUnweighted(Random rand)
	{
		// TODO debug rigged
//		return all.get(rand.nextInt(all.size()));
		return DROUGHT;
	}
	
	public static void doRegister()
	{
		all.add(Registry.register(Registry.BIOME, new Identifier(Const.MODID, "sub_jungle"    ), JUNGLE    ));
		all.add(Registry.register(Registry.BIOME, new Identifier(Const.MODID, "sub_frost"     ), FROST     ));
		all.add(Registry.register(Registry.BIOME, new Identifier(Const.MODID, "sub_drought"   ), DROUGHT   ));
		all.add(Registry.register(Registry.BIOME, new Identifier(Const.MODID, "sub_lumishroom"), LUMISHROOM));
	}
	
	private static Biome.Settings settingsOf(Biome parent)
	{
		return new Biome.Settings()
			.category(parent.getCategory())
			.surfaceBuilder(parent.getSurfaceBuilder())
			.downfall(parent.getRainfall())
			.temperature(parent.getTemperature())
			.depth(parent.getDepth())
			.precipitation(parent.getPrecipitation())
			.scale(parent.getScale())
			.waterColor(parent.getWaterColor())
			.waterFogColor(parent.getWaterFogColor())
			.parent(Registry.BIOME.getId(parent).toString());
	}
}
