package net.cuddlebat.terrawa.entity;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.entity.mob.IvySkeleton;
import net.cuddlebat.terrawa.entity.mob.PoisonElemental;
import net.cuddlebat.terrawa.entity.mob.VenombiteSpider;
import net.cuddlebat.terrawa.entity.projectile.PoisonElementalProjectile;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities
{
	public static final EntityType<IvySkeleton> IVY_SKELETON = FabricEntityTypeBuilder
		.create(EntityCategory.MONSTER, IvySkeleton::new)
		.size(EntityDimensions.fixed(1, 2)).build();
	public static final EntityType<VenombiteSpider> VENOMBITE_SPIDER = FabricEntityTypeBuilder
		.create(EntityCategory.MONSTER, VenombiteSpider::new)
		.size(EntityDimensions.fixed(1, 1)).build();
	public static final EntityType<PoisonElemental> POISON_ELEMENTAL = FabricEntityTypeBuilder
		.create(EntityCategory.MONSTER, PoisonElemental::new)
		.size(EntityDimensions.fixed(1, 2)).build();

	public static final EntityType<PoisonElementalProjectile> POISON_PROJECTILE = FabricEntityTypeBuilder
		.<PoisonElementalProjectile>create(EntityCategory.MISC, PoisonElementalProjectile::new)
		.size(EntityDimensions.fixed(1, 2)).build();
	
	public static void doRegister()
	{
		Registry.register(Registry.ENTITY_TYPE,
			new Identifier(Const.MODID, "ivy_skeleton"),
			IVY_SKELETON);
		Registry.register(Registry.ENTITY_TYPE,
			new Identifier(Const.MODID, "venombite_spider"),
			VENOMBITE_SPIDER);
		Registry.register(Registry.ENTITY_TYPE,
			new Identifier(Const.MODID, "poison_elemental"),
			POISON_ELEMENTAL);

		Registry.register(Registry.ENTITY_TYPE,
			new Identifier(Const.MODID, "poison_projectile"),
			POISON_PROJECTILE);
	}
}
