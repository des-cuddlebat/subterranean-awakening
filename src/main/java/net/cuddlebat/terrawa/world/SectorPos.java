package net.cuddlebat.terrawa.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

public class SectorPos
{
	public static final int SECTOR_SIZE = 1024;
	public static final int SECTOR_OFFSET = 0;
	
	private int x;
	private int z;
	
	public SectorPos(int x, int z)
	{
		super();
		this.x = x;
		this.z = z;
	}

	public int getX()
	{
		return x;
	}
	
	public int getZ()
	{
		return z;
	}
	
	@Override // TODO default impl
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + z;
		return result;
	}

	@Override // TODO default impl
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectorPos other = (SectorPos) obj;
		if (x != other.x)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public static SectorPos fromChunkPos(ChunkPos pos)
	{
		return new SectorPos(
			Math.floorDiv(pos.x, SECTOR_SIZE / 16),
			Math.floorDiv(pos.z, SECTOR_SIZE / 16));
	}
	
	public static SectorPos fromBlockPos(BlockPos pos)
	{
		return new SectorPos(
			Math.floorDiv(pos.getX(), SECTOR_SIZE),
			Math.floorDiv(pos.getZ(), SECTOR_SIZE));
	}
}
