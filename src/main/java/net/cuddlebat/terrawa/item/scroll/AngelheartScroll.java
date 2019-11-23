package net.cuddlebat.terrawa.item.scroll;

import java.util.List;

import net.cuddlebat.terrawa.api.interfaces.MagicScroll;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class AngelheartScroll extends MagicScroll
{
	public AngelheartScroll(Settings settings)
	{
		super(settings);
	}

	@Override
	public void useScroll(World world, PlayerEntity player, Hand bookHand)
	{
		player.addPotionEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1));
	}

	@Override
	public int getCooldownTicks()
	{
		return 100;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context)
	{
		list.add(new LiteralText("  Heals 2 hearts of life").formatted(Formatting.YELLOW));
		
		super.appendTooltip(stack, world, list, context);
	}
}
