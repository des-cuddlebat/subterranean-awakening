package net.cuddlebat.terrawa.item.trinket;

import java.util.List;

import net.cuddlebat.terrawa.api.interfaces.IEnchSupportTrinket;
import net.cuddlebat.terrawa.enchant.all.EnchMagicSting;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class ArcanaFocus extends Item implements IEnchSupportTrinket
{
	public ArcanaFocus(Settings settings)
	{
		super(settings);
	}

	@Override
	public boolean canWearInSlot(String group, String slot)
	{
		return "ring".equals(slot);
	}
	
	@Override
	public String getSlotNameForTooltip()
	{
		return "Ring";
	}

	@Override
	public int getSupportFor(Enchantment ench)
	{
		return ench instanceof EnchMagicSting ? 3 : 0;
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> texts, TooltipContext context)
	{
		texts.add(new LiteralText("+3 virtual levels to Magic Sting enchantment")
			.formatted(Formatting.DARK_PURPLE));
		super.appendTooltip(stack, world, texts, context);
	}

	
}
