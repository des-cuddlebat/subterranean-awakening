package net.cuddlebat.terrawa.world;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Random;

import net.cuddlebat.terrawa.subbiome.SubBiome;
import net.cuddlebat.terrawa.subbiome.SubterraneanBiomes;
import net.cuddlebat.terrawa.tests.ParentedTwoInts;
import net.cuddlebat.terrawa.tests.TwoInts;
import net.cuddlebat.terrawa.utils.IntRange;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CavernsSectorLayer extends SectorLayerBase
{
	private byte[][] biomes = new byte[SectorPos.SECTOR_SIZE][SectorPos.SECTOR_SIZE];
	private HashMap<Byte, SubBiome> biomeMap = new HashMap<>();
	private CavernsSystemLayer layer;
	
	public CavernsSectorLayer(int x, int z, CavernsSystemLayer layer)
	{
		super(x, z);
		
		this.layer = layer;
		
		// TODO this.toTag()
		// TODO use CaveSystem toTag/fromTag
	}
	
	@Override
	public void generate()
	{
		
		int size = SectorPos.SECTOR_SIZE;
		
		double[][] preMap = new double[size][size];
		double[][] thrMap = new double[size][size];
		
		for(int xRel = 0; xRel < size; xRel++)
		{
			for(int zRel = 0; zRel < size; zRel++)
			{
				double xPos = size * x + xRel;
				double zPos = size * z + zRel;
				
				double preAB = layer.mainNoise.eval(xPos * layer.mainStretch, zPos * layer.mainStretch);
				preMap[xRel][zRel] = preAB;
				
				double thr = layer.thresholdBase + layer.thresholdEffect *
					layer.thrNoise.eval(xPos * layer.thresholdStretch, zPos * layer.thresholdStretch);
				thrMap[xRel][zRel] = thr;
			}			
		}
		
		Random rand = new Random(layer.randSeed ^ ((long)x << 32) ^ z);

		for(int i = 0; i < layer.biomeAttempts; i++)
		{
			// This just works, I promise
			int xRel = rand.nextInt(size / layer.biomePartitionBias)
				+ ((size / layer.biomePartitionBias) * (i % layer.biomePartitionBias));
			int zRel = rand.nextInt(size / layer.biomePartitionBias)
				+ ((size / layer.biomePartitionBias) * ((i / layer.biomePartitionBias) % layer.biomePartitionBias));
			
			HashSet<TwoInts> biomeCoordPairs = tryBiome(xRel, zRel, preMap, thrMap);
			if(biomeCoordPairs != null)
			{
				SubBiome biome = SubterraneanBiomes.pickRandomUnweighted(rand);
				if(!biomeMap.containsValue(biome))
					biomeMap.put((byte)(biomeMap.size() + 1), biome);
				
				byte biomeIndex = biomeMap.entrySet().stream()
					.filter(entry -> biome.equals(entry.getValue()))
					.map(Entry<Byte, SubBiome>::getKey)
					.findFirst().get();
				
				for(TwoInts pair : biomeCoordPairs)
				{
					biomes[pair.getX()][pair.getY()] = biomeIndex;
				}
			}
		}
	}
	
	private int sampleAltitude(int xRel, int zRel)
	{
		int xPos = xRel + SectorPos.SECTOR_SIZE * this.x;
		int zPos = zRel + SectorPos.SECTOR_SIZE * this.z;
		double altMiddle = (layer.altTop + layer.altBottom) * 0.5; 
		double altVariance = (layer.altTop - layer.altBottom) * 0.5;
		double altNoise =
			  layer.altNoise.eval(1 * xPos * layer.altStretch, 1 * zPos * layer.altStretch) * 1.0
			+ layer.altNoise.eval(2 * xPos * layer.altStretch, 2 * zPos * layer.altStretch) * 0.5
			+ layer.altNoise.eval(4 * xPos * layer.altStretch, 4 * zPos * layer.altStretch) * 0.25;
		altNoise /= 1.75;
		return (int)(altMiddle + altVariance * altNoise);
	}
	
	private double sampleIntensity(int xRel, int zRel)
	{
		int xPos = xRel + SectorPos.SECTOR_SIZE * this.x;
		int zPos = zRel + SectorPos.SECTOR_SIZE * this.z;
		double preAB = layer.mainNoise.eval(xPos * layer.mainStretch, zPos * layer.mainStretch);
		if(biomes[xRel][zRel] != 0)
			return 1.0 + preAB;
		
		double ab = Math.abs(preAB);
		double thr = layer.thresholdBase + layer.thresholdEffect *
			layer.thrNoise.eval(xPos * layer.thresholdStretch, zPos * layer.thresholdStretch);
		
		return Math.max((thr - ab) / thr, 0);
	}

	private int getBottom(int altitude, double intensity)
	{
		final double x = intensity;
		final double caveScale = 0.5 * layer.caveHeight;
		final double biomeScale = 0.5 * 0.25 * (layer.biomeHeight - layer.caveHeight);
		final double cavePart = caveScale * Math.min(1, (x - 1) * (x - 1) * (x - 1) + 1);
		final double biomePart = biomeScale * Math.max(0, x * (x * (x * (x * (6 * x - 45) + 130) - 180) + 120) - 31);
        return (int)(altitude - cavePart - biomePart);
	}

	private int getTop(int altitude, double intensity)
	{
		final double x = intensity;
		final double caveScale = 0.5 * layer.caveHeight;
		final double biomeScale = 0.5 * 0.75 * (layer.biomeHeight - layer.caveHeight);
		final double cavePart = caveScale * Math.min(1, (x - 1) * (x - 1) * (x - 1) + 1);
		final double biomePart = biomeScale * Math.max(0, x * (x * (x * (x * (6 * x - 45) + 130) - 180) + 120) - 31);
        return (int)(altitude + cavePart + biomePart + 1);
	}
	
	public Optional<IntRange> getCaveBoundaries(int xRel, int zRel)
	{
		double intensity = sampleIntensity(xRel, zRel);
		if(intensity == 0)
			return Optional.empty();
		int altitude = sampleAltitude(xRel, zRel);
		int bottom = getBottom(altitude, intensity);
		int top = getTop(altitude, intensity);
		return Optional.of(new IntRange(bottom, top));
	}
	
	private static HashSet<TwoInts> tryBiome(int xRel, int yRel, double[][] preMap, double thrMap[][])
	{
		if(preMap[xRel][yRel] < thrMap[xRel][yRel])
			return null;
		
		LinkedList<ParentedTwoInts> q = new LinkedList<ParentedTwoInts>(); // TODO where is queue ;w;
		q.add(new ParentedTwoInts(new TwoInts(xRel, yRel), new TwoInts(xRel, yRel)));
		HashSet<TwoInts> result = new HashSet<TwoInts>();
		while(!q.isEmpty())
		{
			ParentedTwoInts ints = q.get(0);
			if(!isInBounds(ints.getSelf()))
				return null; // No partial biomes on sector border
			double self = preMap[ints.getSelf().getX()][ints.getSelf().getY()];
			double thr = thrMap[ints.getSelf().getX()][ints.getSelf().getY()];
			double parent = preMap[ints.getParent().getX()][ints.getParent().getY()];
			q.remove(0);
			if(!result.contains(ints.getSelf()) && self > 0 &&
				(self < parent || self > thr))
			{
				result.add(ints.getSelf());
				addNeighbors(q, ints.getSelf());
			}
		}
		
		return result;
	}

	private static void addNeighbors(LinkedList<ParentedTwoInts> q, TwoInts self)
	{
		q.add(new ParentedTwoInts(self, new TwoInts(self.getX() + 1, self.getY())));
		q.add(new ParentedTwoInts(self, new TwoInts(self.getX() - 1, self.getY())));
		q.add(new ParentedTwoInts(self, new TwoInts(self.getX(), self.getY() + 1)));
		q.add(new ParentedTwoInts(self, new TwoInts(self.getX(), self.getY() - 1)));
	}

	private static boolean isInBounds(TwoInts self)
	{
		return self.getX() >= 0 && self.getX() < SectorPos.SECTOR_SIZE
			&& self.getY() >= 0 && self.getY() < SectorPos.SECTOR_SIZE;
	}

	@Override
	public void digCaverns(World world, Chunk chunk)
	{
		ChunkPos cp = chunk.getPos();
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				int x = Math.floorMod((cp.x * 16 + i), SectorPos.SECTOR_SIZE);
				int z = Math.floorMod((cp.z * 16 + j), SectorPos.SECTOR_SIZE);
				Optional<IntRange> maybeRange = this.getCaveBoundaries(x, z);
				if(maybeRange.isPresent())
				{	
					IntRange range = maybeRange.get();
					if(biomes[x][z] != 0)
					{
						SubBiome biome = (SubBiome) biomeMap.get(biomes[x][z]);
						biome.digColumn(chunk, i, j, range);
					}
					else
					{
						for(int k = range.getMin(); k < range.getMax(); k++)
						{
							chunk.setBlockState(new BlockPos(i, k, j), Blocks.CAVE_AIR.getDefaultState(), false);
						}
					}
				}
			}
		}
	}

	@Override
	public void decorateBiomes(IWorld world, Chunk chunk, ChunkGenerator<?> gen)
	{
		ChunkPos cp = chunk.getPos();
		Random rand = new Random(layer.randSeed ^ ((long)cp.x << 32) ^ cp.z);
		IntRange[][] heightmap = new IntRange[16][16];
		SubBiome[][] biomemap = new SubBiome[16][16];
		HashSet<SubBiome> biomesToDecorate = new HashSet<>();
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < 16; j++)
			{
				int xRel = Math.floorMod((cp.x * 16 + i), SectorPos.SECTOR_SIZE);
				int zRel = Math.floorMod((cp.z * 16 + j), SectorPos.SECTOR_SIZE);
				IntRange range = this.getCaveBoundaries(xRel, zRel).orElse(new IntRange(0, 0));
				Optional<SubBiome> maybeBiome = biomes[xRel][zRel] == 0
					? Optional.empty() : Optional.of(biomeMap.get(biomes[xRel][zRel]));
				heightmap[i][j] = range;
				biomemap[i][j] = maybeBiome.orElse(null);
				if(maybeBiome.isPresent())
					biomesToDecorate.add(maybeBiome.get());
//				if(maybeRange.isPresent())
//				{
//					IntRange range = maybeRange.get();
//					if(biomes[x][z] != 0)
//					{
//						SubBiome biome = (SubBiome) biomeMap.get(biomes[x][z]);
//						biome.decorateColumn(world, chunk, i, j, range, rand);
//					}
//				}
			}
		}
		
		for(Biome b : biomesToDecorate)
		{
			((SubBiome)b).decorateSubterraneanChunk(world, chunk, gen, heightmap, biomemap, rand);
		}
		
	}

	@Override
	public Optional<Biome> maybeGetBiomeOverride(BlockPos pos)
	{
		int xRel = Math.floorMod(pos.getX(), SectorPos.SECTOR_SIZE);
		int zRel = Math.floorMod(pos.getZ(), SectorPos.SECTOR_SIZE);
		if(biomes[xRel][zRel] == 0)
			return Optional.empty();
		int grace = 3;
		IntRange range = getCaveBoundaries(xRel, zRel).get(); // should be safe
		if(pos.getY() >= range.getMin() - grace && pos.getY() <= range.getMax() + grace)
			return Optional.of(biomeMap.get(biomes[xRel][zRel]));
		return Optional.empty();
	}
}
