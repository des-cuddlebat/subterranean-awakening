package net.cuddlebat.terrawa.client.render;

import com.mojang.blaze3d.platform.GlStateManager;

import net.cuddlebat.terrawa.Const;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.StrayEntityModel;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class IvySkeletonOverlayRenderer<T extends MobEntity & RangedAttackMob, M extends EntityModel<T>>
	extends FeatureRenderer<T, M>
{
	private static final Identifier TEXTURE = new Identifier(Const.MODID, "textures/entity/skeleton/ivy_skeleton_overlay.png");
	private final StrayEntityModel<T> model = new StrayEntityModel<>(0.25F, true);

	public IvySkeletonOverlayRenderer(FeatureRendererContext<T, M> context)
	{
		super(context);
	}

	@Override
	public void render(T mobEntity_1, float float_1, float float_2, float float_3, float float_4, float float_5,
		float float_6, float float_7)
	{
		this.getModel().copyStateTo(this.model);
		this.model.method_19689(mobEntity_1, float_1, float_2, float_3);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.bindTexture(TEXTURE);
		this.model.method_17088(mobEntity_1, float_1, float_2, float_4, float_5, float_6, float_7);
	}

	public boolean hasHurtOverlay()
	{
		return true;
	}
}