package net.cuddlebat.terrawa.inventory;

import java.util.ArrayList;
import java.util.HashMap;
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

public class EnchActionDisenchant implements IEnchAction
{
	private final ItemStack AGENT;

	public EnchActionDisenchant(ItemStack agent)
	{
		AGENT = agent;
	}

	@Override
	public Text getButtonText()
	{
		return new LiteralText("Disenchant");
	}

	@Override
	public ArrayList<Text> getMouseText(ItemStack agent, ItemStack target, ItemStack book)
	{
		ArrayList<Text> result = new ArrayList<>();
		
		int agentCost = 1 + ModEnchHelper.getArcanaUsed(target) / 10;
		result.add(new LiteralText("Moves all enchantments from the item"));
		result.add(new LiteralText("Will free up all arcana of the item"));
		result.add(new LiteralText("Consumes " + agentCost + " ").append(agent.getName()));
		return result;
	}

	@Override
	public boolean isValid(ItemStack agent, ItemStack target, ItemStack book)
	{
		if(target.isEmpty() || book.isEmpty() || !ItemStack.areItemsEqual(AGENT, agent))
			return false;
		
		int agentCost = 1 + ModEnchHelper.getArcanaUsed(target) / 10;
		
		return agent.getCount() >= agentCost;
	}

	@Override
	public Optional<Text> getHintIfAlmostValid(ItemStack agent, ItemStack target, ItemStack book)
	{
		if(target.isEmpty() || book.isEmpty() || isWrongAgent(agent))
			return Optional.empty();
		
		int agentCost = 1 + ModEnchHelper.getArcanaUsed(target) / 10;
		
		if((agent.isEmpty() && book.getItem() == Items.BOOK) || agent.getCount() < agentCost)
			return Optional.of(new LiteralText("Action requires " + agentCost + " ")
				.append(AGENT.getName()));
		
		return Optional.empty();
	}

	@Override
	public void doApply(Inventory inventory, ItemStack agent, ItemStack target, ItemStack book)
	{
		if(book.getItem() == Items.BOOK)
		{
			inventory.setInvStack(2, new ItemStack(Items.ENCHANTED_BOOK));
			book = inventory.getInvStack(2);
		}
		
		Map<Enchantment, Integer> targetEnch = EnchantmentHelper.getEnchantments(target);
		Map<Enchantment, Integer> bookEnch = EnchantmentHelper.getEnchantments(book);
		
		for(Entry<Enchantment, Integer> ench : targetEnch.entrySet())
		{
			if(!bookEnch.containsKey(ench.getKey()))
				bookEnch.put(ench.getKey(), 0);
			bookEnch.put(ench.getKey(), Math.max(ench.getValue(), bookEnch.get(ench.getKey())));
		}
		
		int agentCost = 1 + ModEnchHelper.getArcanaUsed(target) / 10;
		agent.decrement(agentCost);
		
		EnchantmentHelper.set(bookEnch, book);
		EnchantmentHelper.set(new HashMap<>(), target);
		
		book = new ItemStack(book.getItem());
	}
	
	private boolean isWrongAgent(ItemStack stack)
	{
		return !stack.isEmpty() && !ItemStack.areItemsEqual(stack, AGENT);
	}
}
