package net.cuddlebat.terrawa.client.render;

import net.cuddlebat.terrawa.Const;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class IvySkeletonEntityRenderer extends SkeletonEntityRenderer
{
	private static final Identifier TEXTURE = new Identifier(Const.MODID, "textures/entity/skeleton/ivy_skeleton.png");
	public IvySkeletonEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1)
	{
		super(entityRenderDispatcher_1);
		this.addFeature(new IvySkeletonOverlayRenderer<>(this));
	}

	@Override
	protected Identifier method_4119(AbstractSkeletonEntity abstractSkeletonEntity_1)
	{
		return TEXTURE;
	}
}
