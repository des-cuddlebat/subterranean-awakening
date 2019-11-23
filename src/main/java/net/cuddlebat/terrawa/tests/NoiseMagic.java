package net.cuddlebat.terrawa.tests;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import net.cuddlebat.terrawa.noise.OpenSimplexNoise;

public class NoiseMagic
{
	private static int SECTOR_SIZE = 2048;
	private static int PART_SIZE = 1024;
	private static int PART_BIOME_ATTEMPTS = 2*64;
	private static int PARTITION_BIAS = 8; // 3 = uniformly distributes all attempts in a 3x3 grid.

	private static OpenSimplexNoise NOISE_A = new OpenSimplexNoise(8794627985L);
	private static OpenSimplexNoise NOISE_T = new OpenSimplexNoise(45495618L);
//	private static OpenSimplexNoise NOISE_H = new OpenSimplexNoise(3145495618L);
	
	public static void main(String[] args) throws IOException
	{
		BufferedImage imageABT = new BufferedImage(SECTOR_SIZE, SECTOR_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
		BufferedImage imageABH = new BufferedImage(SECTOR_SIZE, SECTOR_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
		
		for(int x = 0; x < SECTOR_SIZE / PART_SIZE; x++)
		{
			for(int y = 0; y < SECTOR_SIZE / PART_SIZE; y++)
			{
				long timePart = System.nanoTime();
				doPart(imageABT, imageABH, x, y);
				timePart = System.nanoTime() - timePart;
				System.out.printf("Took %.3f ms.%n", timePart / 1e6);
			}
		}
		
		// Draw chunk borders
		
		for(int i = 0; i < SECTOR_SIZE; i++)
		{
			for(int j = 0; j < SECTOR_SIZE; j++)
			{
				if(((i & 15) == 0) && (((j >> 1) % 2) == 0) ||
				   ((j & 15) == 0) && (((i >> 1) % 2) == 0))
				{
					imageABT.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}

		File outBi = new File("TestBi.png");
		ImageIO.write(imageABT, "png", outBi);
		
		//File outH = new File("TestH.png");
		//ImageIO.write(imageABH, "png", outH);
		
		System.out.println("Test done");
	}

	//@SuppressWarnings("unused")
	private static void doPart(BufferedImage imageABT, BufferedImage imageABH,
		int xPart, int yPart) throws IOException
	{
		Random rand = new Random(7852685L ^ (xPart << 13) ^ yPart);
		double stretchAB = 0.04;
		double thresholdAB = 0.23;
		double stretchT = 0.02;
		double thresholdT = 0.07;
//		double stretchH = 0.01;
//		double offsetH = 128.0;
//		double multiH = 96.0;
		
		double[][] preMap = new double[PART_SIZE][PART_SIZE];
		double[][] thrMap = new double[PART_SIZE][PART_SIZE];
		
		for(int i = 0; i < PART_SIZE; i++)
		{
			for(int j = 0; j < PART_SIZE; j++)
			{
				int x = xPart * PART_SIZE + i;
				int y = yPart * PART_SIZE + j;
				
				double preAB = getOctaves(NOISE_A, x * stretchAB, y * stretchAB, 1);
				double ab = Math.abs(preAB);
				preMap[i][j] = preAB;
				
				double thr = thresholdAB + thresholdT *
					getOctaves(NOISE_T, x * stretchT, y * stretchT, 1);
				thrMap[i][j] = thr;

//				double h = offsetH + multiH *
//					getOctaves(NOISE_H, x * stretchH, y * stretchH, 1);
				
				int colorAB = Math.max(0, (int)(128 - 64 * (ab / thr) * (ab / thr)));
//				int colorH = new Color((int)h, (int)h, (int)h).getRGB();
				boolean dig = ab < thr;
				imageABT.setRGB(x, y, dig ? new Color(colorAB, colorAB, colorAB).getRGB() : Color.BLACK.getRGB());
//				imageABH.setRGB(x, y, dig ? colorH : Color.BLACK.getRGB());
			}			
		}

		for(int i = 0; i < PART_BIOME_ATTEMPTS; i++)
		{
			int xS = rand.nextInt(PART_SIZE / PARTITION_BIAS)
				+ ((PART_SIZE / PARTITION_BIAS) * (i % PARTITION_BIAS));
			int yS = rand.nextInt(PART_SIZE / PARTITION_BIAS)
				+ ((PART_SIZE / PARTITION_BIAS) * ((i / PARTITION_BIAS) % PARTITION_BIAS));
			int col = new Color(64 + 64*rand.nextInt(2), 64 + 64*rand.nextInt(2), 64 + 64*rand.nextInt(2)).getRGB();
			
			HashSet<TwoInts> biomeCoordPairs = tryBiome(xS, yS, preMap, thrMap);
			if(biomeCoordPairs != null)
			{
				for(TwoInts pair : biomeCoordPairs)
				{
					imageABT.setRGB(
						xPart * PART_SIZE + pair.getX(),
						yPart * PART_SIZE + pair.getY(),
						col);
				}
			}
		}
	}

	private static HashSet<TwoInts> tryBiome(int xS, int yS, double[][] preMap, double thrMap[][])
	{
		if(preMap[xS][yS] < thrMap[xS][yS])
			return null;
		
		LinkedList<ParentedTwoInts> q = new LinkedList<ParentedTwoInts>(); // TODO where is queue ;w;
		q.add(new ParentedTwoInts(new TwoInts(xS, yS), new TwoInts(xS, yS)));
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
		return self.getX() >= 0 && self.getX() < PART_SIZE
			&& self.getY() >= 0 && self.getY() < PART_SIZE;
	}

	private static double getOctaves(OpenSimplexNoise noise, double x, double y, int octaves)
	{
		double total = 0;
		for (int i = 0; i < octaves; i++)
		{
			double result = noise.eval(x * (1 << i), y * (1 << i));
			total += result / (1 << i);
		}
		return total / (2.0 - 2.0 / (1 << octaves));
	}
}
