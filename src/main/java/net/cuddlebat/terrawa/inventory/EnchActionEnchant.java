package net.cuddlebat.terrawa.inventory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class EnchActionEnchant implements IEnchAction
{
	private final ItemStack AGENT;

	public EnchActionEnchant(ItemStack agent)
	{
		AGENT = agent;
	}

	@Override
	public Text getButtonText()
	{
		return new LiteralText("Enchant");
	}

	@Override
	public ArrayList<Text> getMouseText(ItemStack agent, ItemStack target, ItemStack book)
	{
		ArrayList<Text> result = new ArrayList<>();
		int arcanaDelta = ModEnchHelper.getApplyArcanaDelta(target, book);
		int agentCost = 1 + arcanaDelta / 10;
		result.add(new LiteralText("Transfers all enchantment from the book"));
		result.add(new LiteralText("Will take up " + arcanaDelta + " more arcana"));
		result.add(new LiteralText("Consumes " + agentCost + " ").append(agent.getName()));
		return result;
	}

	@Override
	public boolean isValid(ItemStack agent, ItemStack target, ItemStack book)
	{
		// This is kinda crazy isn't it
		if(    target.isEmpty() || book.isEmpty() || book.getItem() != Items.ENCHANTED_BOOK
			|| !ItemStack.areItemsEqual(AGENT, agent))
			return false;
		
		int arcanaPre = ModEnchHelper.getArcanaUsed(target);
		int arcanaDelta = ModEnchHelper.getApplyArcanaDelta(target, book);
		int arcanaCap = ModEnchHelper.getArcanaCapacity(target);
		
		return arcanaPre + arcanaDelta <= arcanaCap && agent.getCount() >= 1 + arcanaDelta / 10;
	}

	@Override
	public Optional<Text> getHintIfAlmostValid(ItemStack agent, ItemStack target, ItemStack book)
	{
		// This is kinda crazy isn't it
		if(target.isEmpty() || book.isEmpty() || book.getItem() != Items.ENCHANTED_BOOK
			|| (!ItemStack.areItemsEqual(AGENT, agent) && !agent.isEmpty()))
			return Optional.empty();
		
		int arcanaPre = ModEnchHelper.getArcanaUsed(target);
		int arcanaDelta = ModEnchHelper.getApplyArcanaDelta(target, book);
		int arcanaCap = ModEnchHelper.getArcanaCapacity(target);
		
		if(arcanaPre + arcanaDelta > arcanaCap)
			return Optional.of(new LiteralText("Item does not have sufficient arcana capacity!"));
		
		if(agent.isEmpty() || agent.getCount() < (1 + arcanaDelta / 10))
			return Optional.of(new LiteralText("Action requires " + (1 + arcanaDelta / 10) + " ")
				.append(AGENT.getName()));
		
		return Optional.empty();
	}

	@Override
	public void doApply(Inventory inventory, ItemStack agent, ItemStack target, ItemStack book)
	{
		Map<Enchantment, Integer> targetEnch = EnchantmentHelper.getEnchantments(target);
		Map<Enchantment, Integer> bookEnch = EnchantmentHelper.getEnchantments(book);
		
		for(Entry<Enchantment, Integer> ench : bookEnch.entrySet())
		{
			if(!targetEnch.containsKey(ench.getKey()))
				targetEnch.put(ench.getKey(), 0);
			targetEnch.put(ench.getKey(), Math.max(ench.getValue(), targetEnch.get(ench.getKey())));
		}
		
		agent.decrement(1 + ModEnchHelper.getApplyArcanaDelta(target, book) / 10);
		
		EnchantmentHelper.set(targetEnch, target);
		
		book = new ItemStack(book.getItem());
		if(book.getItem() == Items.ENCHANTED_BOOK)
		{
			inventory.setInvStack(2, new ItemStack(Items.BOOK));
		}
	}
}
