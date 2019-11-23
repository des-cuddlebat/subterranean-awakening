package net.cuddlebat.terrawa.client.render;

import net.cuddlebat.terrawa.Const;
import net.minecraft.client.render.entity.BlazeEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.util.Identifier;

public class PoisonElementalEntityRenderer extends BlazeEntityRenderer
{
	private static final Identifier TEXTURE = new Identifier(Const.MODID, "textures/entity/elemental/poison_elemental.png");
	
	public PoisonElementalEntityRenderer(EntityRenderDispatcher dispatcher)
	{
		super(dispatcher);
	}
	
	@Override
	protected Identifier method_3881(BlazeEntity elem)
	{
		return TEXTURE;
	}
}
