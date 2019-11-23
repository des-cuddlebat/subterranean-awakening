package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.IDamageCooldownComponent;
import net.cuddlebat.terrawa.effect.ModStatusEffect;
import net.cuddlebat.terrawa.effect.ModStatusEffects;
import net.cuddlebat.terrawa.entity.attribute.ModAttributes;
import net.cuddlebat.terrawa.utils.MagicMeleeDamageSource;
import net.cuddlebat.terrawa.utils.VoidMeleeDamageSource;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;

@Mixin(LivingEntity.class)
public class LivingEntityMixin
{
	@Redirect(at = @At(value="FIELD", target = "field_6253", opcode = Opcodes.GETFIELD), method="damage")
	public float getLoggedDamageProxy(LivingEntity self, DamageSource source, float amount)
	{
		IDamageCooldownComponent comp = Const.Component.DAMAGE_COOLDOWN_COMPONENT.maybeGet(self).orElse(null);
		if(comp != null)
		{
			return comp.getLoggedDamage(source, amount);
		}
		return 0.0f;
	}
	
	@Redirect(at = @At(value="FIELD", target = "field_6253", opcode = Opcodes.PUTFIELD), method="damage")
	public void setLoggedDamageProxy(LivingEntity self, float value, DamageSource source, float amount)
	{
		IDamageCooldownComponent comp = Const.Component.DAMAGE_COOLDOWN_COMPONENT.maybeGet(self).orElse(null);
		if(comp != null)
		{
			comp.logDamage(source, amount);
		}
	}
	
	@Inject(at = @At("HEAD"), method = "tick")
	public void tickMixin(CallbackInfo cb)
	{
		LivingEntity self = (LivingEntity) (Object) this;
		IDamageCooldownComponent comp = Const.Component.DAMAGE_COOLDOWN_COMPONENT.maybeGet(self).orElse(null);
		if(comp != null)
		{
			comp.tick();
		}
		
		for(StatusEffectInstance inst : self.getStatusEffects())
		{
			if(inst.getEffectType() instanceof ModStatusEffect)
				((ModStatusEffect)inst.getEffectType()).tick(inst, self);
		}
	}
	
	@Inject(at = @At("HEAD"), cancellable = true, method = "setHealth")
	public void setHealthBleedingMixin(float amount, CallbackInfo ci)
	{
		LivingEntity self = (LivingEntity) (Object) this;
		if(self.hasStatusEffect(ModStatusEffects.BLEEDING) && amount > self.getHealth())
			ci.cancel();
	}
	
	@Inject(at = @At("RETURN"), cancellable = true, method = "applyEnchantmentsToDamage")
	private void incrDamageTaken(DamageSource source, float original, CallbackInfoReturnable<Float> cir)
	{
		LivingEntity self = (LivingEntity) (Object) this;
		float modified = cir.getReturnValueF();
		if(self.hasStatusEffect(ModStatusEffects.VULNERABILITY))
			modified += 0.1F * original * self.getStatusEffect(ModStatusEffects.VULNERABILITY).getAmplifier();
		cir.setReturnValue(modified);
	}

	@Inject(at = @At("TAIL"), method = "initAttributes()V")
	private void initAttributesMixin(CallbackInfo ci)
	{
		LivingEntity self = (LivingEntity) (Object) this;
		self.getAttributeContainer().register(ModAttributes.MAGIC_DAMAGE);
		self.getAttributeContainer().register(ModAttributes.VOID_DAMAGE);
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
////		self.getAttributeContainer().register(ModAttributes.MAGIC_DAMAGE);
////		self.getAttributeContainer().register(ModAttributes.VOID_DAMAGE);
//	}
}
