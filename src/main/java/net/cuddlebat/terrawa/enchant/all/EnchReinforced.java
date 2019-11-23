package net.cuddlebat.terrawa.enchant.all;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.cuddlebat.terrawa.enchant.ModEnch;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class EnchReinforced extends ModEnch
{
	private static final UUID[] MODIFIER_IDS = new UUID[]
	{
		UUID.fromString("0e654069-385d-4355-977d-303c495251b0"),
		UUID.fromString("c0684e51-1dc9-435f-be8b-1bdc59a38def"),
		UUID.fromString("c6688578-94fd-459a-8a0d-735043443f1e"),
		UUID.fromString("91b2edd9-89a6-403d-b732-d14c6058f079")
	};
	
	public EnchReinforced()
	{
		super(Weight.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]
		{
			EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
		});
	}

	@Override
	public int getMaximumLevel()
	{
		return 3;
	}

	@Override
	public int getCost(int level)
	{
		return 30 * level;
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlotType() == slot)
		{
			multimap.put(EntityAttributes.ARMOR_TOUGHNESS.getId(), new EntityAttributeModifier(
				MODIFIER_IDS[slot.getEntitySlotId()], "Reinforced", level, EntityAttributeModifier.Operation.ADDITION));
		}
	}
}
