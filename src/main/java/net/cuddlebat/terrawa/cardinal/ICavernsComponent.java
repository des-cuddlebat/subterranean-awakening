package net.cuddlebat.terrawa.cardinal;

import java.util.Optional;

import nerdhub.cardinal.components.api.component.Component;
import net.cuddlebat.terrawa.utils.IntRange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public interface ICavernsComponent extends Component
{
	public void digCaverns(World world, Chunk chunk);

	public Optional<Biome> maybeGetBiomeOverride(BlockPos pos);

	public IntRange getRangeForPacket(int x, int z);

	public void decorateBiomes(IWorld world, Chunk chunk, ChunkGenerator<?> gen);
}
