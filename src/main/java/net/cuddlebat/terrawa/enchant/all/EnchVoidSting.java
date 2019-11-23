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

public class EnchVoidSting extends ModEnch
{
	public static final UUID DAMAGE_UUID = UUID.fromString("485a20dc-889a-4b4c-9e8d-c181ed86c706");
	
	public EnchVoidSting()
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
			((ProjectileComponent) comp).addVoidDamage(level);
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
		return 20*level;
	}

	@Override
	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot,
		Multimap<String, EntityAttributeModifier> multimap)
	{
		if(slot == EquipmentSlot.MAINHAND && !(stack.getItem() instanceof RangedWeaponItem))
		{
			multimap.put(ModAttributes.VOID_DAMAGE.getId(),
				new EntityAttributeModifier(DAMAGE_UUID, "Void sting", level,
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
