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

public class EnchDwarvenPlating extends ModEnch
{
	private static final UUID[] MODIFIER_IDS = new UUID[]
	{
		UUID.fromString("0f355d64-1cf2-4bd3-a7e6-1b425f6a0e9f"),
		UUID.fromString("9d7090c0-c697-469d-92dc-97f64f8f655d"),
		UUID.fromString("40027018-c0fb-4800-ac9f-3b7e3320b79f"),
		UUID.fromString("6c5bbc1f-aacd-49b9-893a-d9430a995a9e")
	};

	public EnchDwarvenPlating()
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
		return 20 * level + 5 * level * (level + 1);
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlotType() == slot)
		{
			multimap.put(EntityAttributes.ARMOR.getId(),
				new EntityAttributeModifier(MODIFIER_IDS[slot.getEntitySlotId()], "Dwarven plating", level,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}
}
