package net.cuddlebat.terrawa.inventory;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class EnchActions
{
	private static ArrayList<IEnchAction> actions;
	
	public static Optional<IEnchAction> maybeMatch(ItemStack agent, ItemStack target, ItemStack book)
	{
		for(IEnchAction action : actions)
		{
			if(action.isValid(agent, target, book))
				return Optional.of(action);
		}
		return Optional.empty();
	}
	
	public static Optional<IEnchAction> maybeAlmostMatch(ItemStack agent, ItemStack target, ItemStack book)
	{
		for(IEnchAction action : actions)
		{
			if(action.getHintIfAlmostValid(agent, target, book).isPresent())
				return Optional.of(action);
		}
		return Optional.empty();
	}
	
	public static void doApply(IEnchAction action, Inventory inventory, ItemStack agent, ItemStack target, ItemStack book)
	{
		if(action.isValid(agent, target, book))
			action.doApply(inventory, agent, target, book);
	}
	
	public static void doInit()
	{
		actions = new ArrayList<>();
		
		actions.add(new EnchActionEnchant(new ItemStack(Items.LAPIS_LAZULI)));
		
		actions.add(new EnchActionDisenchant(new ItemStack(Items.COAL)));
		
		actions.add(new EnchActionConjure(Items.FLINT.getStackForRender(), 2, Enchantments.SHARPNESS, 1));
	}
}
