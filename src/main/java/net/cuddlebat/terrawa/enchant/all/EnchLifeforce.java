package net.cuddlebat.terrawa.enchant.all;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.cuddlebat.terrawa.enchant.ModEnch;
import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;

public class EnchLifeforce extends ModEnch
{
	private static final UUID[] MODIFIER_IDS = new UUID[]
	{
		UUID.fromString("dde92d1c-888a-4a5d-a887-e01ebd75dce3"),
		UUID.fromString("645de98d-d874-4917-8e08-a680bb52fb63"),
		UUID.fromString("04f081b3-1580-4fa8-9891-a3ec096def2c"),
		UUID.fromString("dda5eae8-9644-44bf-bac1-c3820c095820")
	};

	public EnchLifeforce()
	{
		super(Weight.RARE, EnchantmentTarget.ARMOR, new EquipmentSlot[]
		{
			EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET
		});
	}

	@Override
	public int getMaximumLevel()
	{
		return 4;
	}
	
	@Override
	public Object[] getDescParams(int level)
	{
		return new Object[] { 2 * level, level };
	}

	@Override
	public int getCost(int level)
	{
		return 20 * level;
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if (stack.getItem() instanceof ArmorItem && ((ArmorItem) stack.getItem()).getSlotType() == slot)
		{
			multimap.put(EntityAttributes.MAX_HEALTH.getId(),
				new EntityAttributeModifier(MODIFIER_IDS[slot.getEntitySlotId()], "Lifeforce", level * 2,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}
}
