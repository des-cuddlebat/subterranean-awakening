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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;

public class EnchVoidDischarge extends ModEnch
{
	public static final UUID DAMAGE_UUID = UUID.fromString("484dac37-fb7b-48be-88aa-74d50c58c527");
	
	public EnchVoidDischarge()
	{
		super(Weight.RARE, EnchantmentTarget.WEAPON,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
	
	@Override
	public void onAttackPre(LivingEntity attacker, Entity target, int level)
	{
		if(target instanceof LivingEntity)
		{
			attacker.damage(new VoidMeleeDamageSource(attacker), level);
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
		return new Object[] { 4 * level, level };
	}
	
	@Override
	public int getCost(int level)
	{
		return 40 * level + 10 * level * (level + 1);
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if(slot == EquipmentSlot.MAINHAND && !(stack.getItem() instanceof RangedWeaponItem))
		{
			multimap.put(ModAttributes.VOID_DAMAGE.getId(),
				new EntityAttributeModifier(DAMAGE_UUID, "Void discharge", 4 * level,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}
}
