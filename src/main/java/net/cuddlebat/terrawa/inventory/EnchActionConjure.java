package net.cuddlebat.terrawa.inventory;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class EnchActionConjure implements IEnchAction
{
	
	private ItemStack agent;
	private int count;
	private Enchantment ench;
	private int level;

	public EnchActionConjure(ItemStack agent, int count, Enchantment ench, int level)
	{
		super();
		this.agent = agent;
		this.count = count;
		this.ench = ench;
		this.level = level;
	}

	@Override
	public Text getButtonText()
	{
		return new LiteralText("Conjure ");
	}

	@Override
	public ArrayList<Text> getMouseText(ItemStack agent, ItemStack target, ItemStack book)
	{
		ArrayList<Text> result = new ArrayList<>();
		result.add(new LiteralText("Use up " + count + " ").append(agent.getName()));
		result.add(new LiteralText("to enchant the book with ").append(ench.getName(level)));
		result.add((ModEnchHelper.getDesc(ench, level)).orElse(new LiteralText("")));
		return result;
	}

	@Override
	public boolean isValid(ItemStack agent, ItemStack target, ItemStack book)
	{
		return !book.isEmpty()
			&& ItemStack.areItemsEqual(this.agent, agent)
			&& agent.getCount() >= count;
	}

	@Override
	public Optional<Text> getHintIfAlmostValid(ItemStack agent, ItemStack target, ItemStack book)
	{
		if (   !book.isEmpty()
			&& ItemStack.areItemsEqual(this.agent, agent)
			&& agent.getCount() < count)
		{
			return Optional.of(new LiteralText("Need " + count + " ").append(agent.getName()));
		}
		return Optional.empty();
	}

	@Override
	public void doApply(Inventory inventory, ItemStack agent, ItemStack target, ItemStack book)
	{
		if (book.getItem() == Items.BOOK)
		{
			inventory.setInvStack(2, new ItemStack(Items.ENCHANTED_BOOK));
			book = inventory.getInvStack(2);
		}
		
		Map<Enchantment, Integer> original = EnchantmentHelper.getEnchantments(book);
		if(!original.containsKey(ench))
			original.put(ench, 0);
		original.put(ench, Math.max(level, original.get(ench)));
		
		EnchantmentHelper.set(original, book);
		
		agent.decrement(count);
	}

}
