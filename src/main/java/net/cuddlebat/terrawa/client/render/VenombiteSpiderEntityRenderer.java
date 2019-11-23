package net.cuddlebat.terrawa.client.render;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.entity.mob.VenombiteSpider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.SpiderEntityRenderer;
import net.minecraft.util.Identifier;

public class VenombiteSpiderEntityRenderer extends SpiderEntityRenderer<VenombiteSpider>
{
	private static final Identifier TEXTURE = new Identifier(Const.MODID, "textures/entity/spider/venombite_spider.png");
	public VenombiteSpiderEntityRenderer(EntityRenderDispatcher entityRenderDispatcher_1)
	{
		super(entityRenderDispatcher_1);
	}

	@Override
	protected Identifier method_4123(VenombiteSpider entity)
	{
		return TEXTURE;
	}
}
