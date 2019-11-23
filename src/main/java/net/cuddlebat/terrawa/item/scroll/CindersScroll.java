package net.cuddlebat.terrawa.item.scroll;

import java.util.List;

import net.cuddlebat.terrawa.api.interfaces.MagicScroll;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class CindersScroll extends MagicScroll
{
	public CindersScroll(Settings settings)
	{
		super(settings);
	}

	@Override
	public void useScroll(World world, PlayerEntity player, Hand bookHand)
	{
		BlockPos least = player.getBlockPos().add(-6, -2, -6);
		BlockPos most  = player.getBlockPos().add( 6,  4,  6);
		world.getEntities(LivingEntity.class, new Box(least, most), (entity) ->
		{
			return entity.getType().getCategory() == EntityCategory.MONSTER;
		}).forEach((entity) ->
		{
			entity.setOnFireFor(10);
			entity.setAttacker(player);
		});
		
		for(int i = 0; i < 32; i++)
		{
			double ang = (i / 16.0) * Math.PI;
			double xVel = Math.sin(ang);
			double zVel = Math.cos(ang);
			world.addParticle(ParticleTypes.FLAME, player.x, player.y, player.z, xVel, 0.1, zVel);
			world.addParticle(ParticleTypes.SMOKE, player.x, player.y, player.z, xVel, 0.1, zVel);
		}
		
		world.playSound(player, player.getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH,
			SoundCategory.PLAYERS, 1.0f, 1.0f);
	}

	@Override
	public int getCooldownTicks()
	{
		return 160;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context)
	{
		list.add(new LiteralText("  Ignites nearby monster for 10 seconds").formatted(Formatting.YELLOW));
		
		super.appendTooltip(stack, world, list, context);
	}
}
