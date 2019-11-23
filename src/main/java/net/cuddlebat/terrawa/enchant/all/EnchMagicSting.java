package net.cuddlebat.terrawa.enchant.all;

import java.util.UUID;

import com.google.common.collect.Multimap;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.IProjectileComponent;
import net.cuddlebat.terrawa.cardinal.ProjectileComponent;
import net.cuddlebat.terrawa.enchant.ModEnch;
import net.cuddlebat.terrawa.entity.attribute.ModAttributes;
import net.cuddlebat.terrawa.utils.MagicMeleeDamageSource;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;

public class EnchMagicSting extends ModEnch
{
	public static final UUID DAMAGE_UUID = UUID.fromString("46371f61-7106-479b-b5aa-068102a77a89");
	
	public EnchMagicSting()
	{
		super(Weight.RARE, EnchantmentTarget.WEAPON,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}

	@Override
	public void applyToArrow(ProjectileEntity arrow, int level)
	{
		IProjectileComponent comp = Const.Component.PROJECTILE_COMPONENT.maybeGet(arrow).orElse(null);
		
		if(comp != null && comp instanceof ProjectileComponent)
		{
			((ProjectileComponent) comp).addMagicDamage(level);
		}
		else
		{
			System.out.println("Could not find projectile component");
		}
	}
	
	@Override
	public int getMaximumLevel()
	{
		return 5;
	}
	
	@Override
	public int getCost(int level)
	{
		return 15*level;
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if(slot == EquipmentSlot.MAINHAND && !(stack.getItem() instanceof RangedWeaponItem))
		{
			multimap.put(ModAttributes.MAGIC_DAMAGE.getId(),
				new EntityAttributeModifier(DAMAGE_UUID, "Magic sting", level,
					EntityAttributeModifier.Operation.ADDITION));
		}
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack)
	{
		return EnchantmentTarget.BOW.isAcceptableItem(stack.getItem())
			|| EnchantmentTarget.WEAPON.isAcceptableItem(stack.getItem());
	}
}
