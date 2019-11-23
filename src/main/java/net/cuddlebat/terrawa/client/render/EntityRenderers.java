package net.cuddlebat.terrawa.client.render;

import net.cuddlebat.terrawa.entity.mob.IvySkeleton;
import net.cuddlebat.terrawa.entity.mob.PoisonElemental;
import net.cuddlebat.terrawa.entity.mob.VenombiteSpider;
import net.cuddlebat.terrawa.entity.projectile.PoisonElementalProjectile;
import net.fabricmc.fabric.api.client.render.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class EntityRenderers
{
	public static void doRegister()
	{
		EntityRendererRegistry.INSTANCE.register(
			IvySkeleton.class, (dispatcher, context) -> new IvySkeletonEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(
			VenombiteSpider.class, (dispatcher, context) -> new VenombiteSpiderEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(
			PoisonElemental.class, (dispatcher, context) -> new PoisonElementalEntityRenderer(dispatcher));
		EntityRendererRegistry.INSTANCE.register(
			PoisonElementalProjectile.class, (dispatcher, context) -> new FlyingItemEntityRenderer
				<PoisonElementalProjectile>(dispatcher, context.getItemRenderer(), 0.75f));
	}
}
