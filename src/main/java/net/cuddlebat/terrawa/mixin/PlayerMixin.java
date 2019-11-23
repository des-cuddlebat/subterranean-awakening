package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.emi.trinkets.api.TrinketsApi;
import net.cuddlebat.terrawa.api.interfaces.IEffectResistanceTrinket;
import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.cuddlebat.terrawa.entity.attribute.ModAttributes;
import net.cuddlebat.terrawa.utils.MagicMeleeDamageSource;
import net.cuddlebat.terrawa.utils.VoidMeleeDamageSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity
{
	
	private PlayerMixin(EntityType<? extends LivingEntity> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
	}

	@Inject(at = @At("HEAD"), method = "attack")
	protected void attackMixin(Entity target, CallbackInfo cb)
	{
		LivingEntity attacker = (LivingEntity) (Object) this;
		
		if(target.isAttackable())
			ModEnchHelper.onAttackPre(attacker, target);
		
		double magicDamage = attacker.getAttributeInstance(ModAttributes.MAGIC_DAMAGE).getValue();
		double voidDamage = attacker.getAttributeInstance(ModAttributes.VOID_DAMAGE).getValue();
		if(magicDamage > 0)
		{
			DamageSource source = new MagicMeleeDamageSource(attacker);
			target.damage(source, (float) magicDamage);
		}
		if(voidDamage > 0)
		{
			DamageSource source = new VoidMeleeDamageSource(attacker);
			target.damage(source, (float) voidDamage);
		}
	}
	
	@Override
	public boolean isPotionEffective(StatusEffectInstance inst)
	{
		Inventory trinketInv = TrinketsApi.getTrinketComponent((PlayerEntity) (Object) this).getInventory();
		for(int i = 0; i < trinketInv.getInvSize(); i++)
		{
			if(trinketInv.getInvStack(i).getItem() instanceof IEffectResistanceTrinket)
			{
				IEffectResistanceTrinket trinket
					= (IEffectResistanceTrinket)trinketInv.getInvStack(i).getItem();
				if(trinket.doesPreventEffect(inst.getEffectType()))
					return false;
			}
		}
		return super.isPotionEffective(inst);
	}
	
//	@Inject(at = @At("TAIL"), method = "attackLivingEntity")
//	private void attackLivingEntityMixin(LivingEntity other, CallbackInfo ci)
//	{
//		LivingEntity attacker = (LivingEntity) (Object) this;
//		double magicDamage = attacker.getAttributeInstance(ModAttributes.MAGIC_DAMAGE).getValue();
//		double voidDamage = attacker.getAttributeInstance(ModAttributes.MAGIC_DAMAGE).getValue();
//		if(magicDamage > 0)
//		{
//			DamageSource source = new MagicMeleeDamageSource(attacker);
//			other.damage(source, (float) magicDamage);
//		}
//		if(voidDamage > 0)
//		{
//			DamageSource source = new VoidMeleeDamageSource(attacker);
//			other.damage(source, (float) voidDamage);
//		}
//	}
}
