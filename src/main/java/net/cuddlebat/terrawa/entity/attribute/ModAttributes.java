package net.cuddlebat.terrawa.entity.attribute;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;

public class ModAttributes
{
	public static final EntityAttribute MAGIC_DAMAGE = new ClampedEntityAttribute(null,
		"generic.terrawa.magic_damage", 0, 0, 1024);
	public static final EntityAttribute VOID_DAMAGE = new ClampedEntityAttribute(null,
		"generic.terrawa.void_damage", 0, 0, 1024);
//	public static EntityAttribute FIRE_DAMAGE = new ClampedEntityAttribute(null,
//		"generic.terrawa.fire_damage", 0, 0, 1024);
}
