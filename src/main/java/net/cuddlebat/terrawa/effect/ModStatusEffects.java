package net.cuddlebat.terrawa.effect;

import java.util.UUID;

import net.cuddlebat.terrawa.Const;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class ModStatusEffects
{
	private static final UUID ARMORBREAK_ID = UUID.fromString("7aa13234-88d4-4b7b-b5f9-f24c6398bfe6");
	private static final UUID CHILL_MOVSPD_ID = UUID.fromString("2929f69b-394d-4fc4-a258-e8ce61b3af24");
	private static final UUID CHILL_ATKSPD_ID = UUID.fromString("72e332ab-af6b-485f-9fdb-9a38f88008cb");
	private static final UUID ROOTED_ID = UUID.fromString("09fd3e1a-6a83-4c40-8150-cba319f96de9");
	
	private ModStatusEffects()
	{
	}
	
	public static final StatusEffect VULNERABILITY =
		new ModStatusEffect(StatusEffectType.HARMFUL, 0xA83246);
	public static final StatusEffect ARMORBREAK =
		new ModStatusEffect(StatusEffectType.HARMFUL, 0x32325A)
			.addAttributeModifier(EntityAttributes.ARMOR, ARMORBREAK_ID.toString(), -0.2, Operation.MULTIPLY_TOTAL);
	public static final StatusEffect BLEEDING =
		new ModStatusEffect(StatusEffectType.HARMFUL, 0xE63214);
	public static final StatusEffect IMMOLATING_FLAME =
		new ModStatusEffect(StatusEffectType.HARMFUL, 0xFCC603)
			.withOnTick((inst, entity) -> // TODO effects actually have a mechanic for this
			{
				if (inst.getDuration() % 20 == 0)
					entity.damage(DamageSource.ON_FIRE, 4 * inst.getAmplifier());
			});
	public static final StatusEffect CHILLED =
		new ModStatusEffect(StatusEffectType.HARMFUL, 0xA6DDED)
			.addAttributeModifier(EntityAttributes.MOVEMENT_SPEED, CHILL_MOVSPD_ID.toString(), -0.15, Operation.MULTIPLY_TOTAL)
			.addAttributeModifier(EntityAttributes.ATTACK_SPEED, CHILL_ATKSPD_ID.toString(), -0.1, Operation.MULTIPLY_TOTAL);
	public static final StatusEffect ROOTED =
		new ModStatusEffect(StatusEffectType.HARMFUL, 0x17470c)
			.addAttributeModifier(EntityAttributes.MOVEMENT_SPEED, ROOTED_ID.toString(), -0.95, Operation.MULTIPLY_TOTAL);
	
	public static void doRegister()
	{
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Const.MODID, "vulnerability"), VULNERABILITY);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Const.MODID, "armorbreak"), ARMORBREAK);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Const.MODID, "bleeding"), BLEEDING);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Const.MODID, "immolating_flame"), IMMOLATING_FLAME);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Const.MODID, "chilled"), CHILLED);
		Registry.register(Registry.STATUS_EFFECT, new Identifier(Const.MODID, "rooted"), ROOTED);
	}
}
