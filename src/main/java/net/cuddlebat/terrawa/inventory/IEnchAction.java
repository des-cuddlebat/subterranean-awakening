package net.cuddlebat.terrawa.inventory;

import java.util.ArrayList;
import java.util.Optional;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public interface IEnchAction
{

	public Text getButtonText();

	public ArrayList<Text> getMouseText(ItemStack agent, ItemStack target, ItemStack book);

	public boolean isValid(ItemStack agent, ItemStack target, ItemStack book);

	public Optional<Text> getHintIfAlmostValid(ItemStack agent, ItemStack target, ItemStack book);

	public void doApply(Inventory inventory, ItemStack agent, ItemStack target, ItemStack book);
}
