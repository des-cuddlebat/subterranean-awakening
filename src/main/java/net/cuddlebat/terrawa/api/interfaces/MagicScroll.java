package net.cuddlebat.terrawa.api.interfaces;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class MagicScroll extends Item
{
	public MagicScroll(Settings settings)
	{
		super(settings);
	}
	
	public abstract void useScroll(World world, PlayerEntity player, Hand bookHand);

	public abstract int getCooldownTicks();
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context)
	{
		super.appendTooltip(stack, world, list, context);
		
		list.add(new LiteralText("  Cooldown: " + (getCooldownTicks() / 20) + "s")
			.formatted(Formatting.DARK_GRAY));
		list.add(new LiteralText("  For use with spellcaster's tome.")
			.formatted(Formatting.DARK_GRAY));
	}
}
