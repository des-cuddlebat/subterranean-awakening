package net.cuddlebat.terrawa.enchant.all;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.IProjectileComponent;
import net.cuddlebat.terrawa.cardinal.ProjectileComponent;
import net.cuddlebat.terrawa.enchant.ModEnch;
import net.cuddlebat.terrawa.entity.attribute.ModAttributes;
import net.cuddlebat.terrawa.utils.VoidMeleeDamageSource;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;

public class EnchLifeDrain extends ModEnch
{
	public static final UUID DAMAGE_UUID = UUID.fromString("4e772f7d-77c4-4180-9ce0-8bc802ecbd03");
	
	public EnchLifeDrain()
	{
		super(Weight.RARE, EnchantmentTarget.WEAPON,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
	
	@Override
	public void onAttackPre(LivingEntity attacker, Entity target, int level)
	{
		if(target instanceof LivingEntity)
		{
			attacker.heal(level);
		}
	}
	
	@Override
	public int getMaximumLevel()
	{
		return 3;
	}
	
	@Override
	public Object[] getDescParams(int level)
	{
		return new Object[] { level, level };
	}
	
	@Override
	public int getCost(int level)
	{
		return 30 * level + 5 * level * (level + 1);
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if(slot == EquipmentSlot.MAINHAND && !(stack.getItem() instanceof RangedWeaponItem))
		{
			multimap.put(ModAttributes.VOID_DAMAGE.getId(),
				new EntityAttributeModifier(DAMAGE_UUID, "Life drain", level,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}
}
