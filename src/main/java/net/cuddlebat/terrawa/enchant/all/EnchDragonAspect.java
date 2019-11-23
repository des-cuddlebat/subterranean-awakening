package net.cuddlebat.terrawa.enchant.all;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.cuddlebat.terrawa.effect.ModStatusEffects;
import net.cuddlebat.terrawa.enchant.ModEnch;
import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.cuddlebat.terrawa.entity.attribute.ModAttributes;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;

public class EnchDragonAspect extends ModEnch
{
	private static final UUID DAMAGE_UUID = UUID.fromString("b686bcac-904c-4130-ab0c-f5f6d4574612");

	public EnchDragonAspect()
	{
		super(Weight.RARE, EnchantmentTarget.WEAPON,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
	
	@Override
	public void onAttackPre(LivingEntity attacker, Entity target, int level)
	{
		if(target instanceof LivingEntity)
		{
			LivingEntity t = (LivingEntity)target;
			int ticks = getDuration(level); 
			if(t.hasStatusEffect(ModStatusEffects.IMMOLATING_FLAME))
			{
				// ticks will never exceed by > 1s; This is to properly tick every second
				ticks += t.getStatusEffect(ModStatusEffects.IMMOLATING_FLAME).getDuration() % 20;
			}
			t.addPotionEffect(new StatusEffectInstance(
				StatusEffects.POISON, ticks, getAmplifier(level)));
		}
	}

//	@Override
//	public void applyToArrow(ProjectileEntity arrow, int level)
//	{
//		if(arrow instanceof ArrowEntity)
//		{
//			((ArrowEntity)arrow).addEffect(new StatusEffectInstance(
//				StatusEffects.POISON, getDuration(level), getAmplifier(level)));
//		}
//	}
	
	private int getAmplifier(int level)
	{
		return level;
	}
	
	private int getDuration(int level)
	{
		return 80 + 20 * level;
	}
	
	@Override
	public int getMaximumLevel()
	{
		return 1;
	}
	
	@Override
	public Object[] getDescParams(int level)
	{
		return new Object[] { 5, ModEnchHelper.getRomanNumeral(level - 1), 4.0 + level, 4.0 * level };
	}

	@Override
	public int getCost(int level)
	{
		return 90 + 60*level;
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if(slot == EquipmentSlot.MAINHAND/* && !(stack.getItem() instanceof RangedWeaponItem)*/)
		{
			multimap.put(ModAttributes.MAGIC_DAMAGE.getId(),
				new EntityAttributeModifier(DAMAGE_UUID, "Dragon Aspect", 5,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}

//	@Override
//	public boolean isAcceptableItem(ItemStack stack)
//	{
//		return EnchantmentTarget.BOW.isAcceptableItem(stack.getItem())
//			|| EnchantmentTarget.WEAPON.isAcceptableItem(stack.getItem());
//	}
}
