package net.cuddlebat.terrawa.client.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class SpellTomeModel extends BookModel implements IEntityItemModel
{
	private static final Identifier TEXTURE = new Identifier("textures/entity/enchanting_table_book.png");

	@Override
	public void render(ItemStack stack)
	{
		MinecraftClient.getInstance().getTextureManager().bindTexture(TEXTURE);
		this.render(1, 1, 1, 1, 0, 0.0625F); // h
	}
}
