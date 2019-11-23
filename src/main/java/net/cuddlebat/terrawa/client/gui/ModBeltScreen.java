package net.cuddlebat.terrawa.client.gui;

import com.mojang.blaze3d.platform.GlStateManager;

import net.cuddlebat.terrawa.inventory.ModBeltContainer;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModBeltScreen extends AbstractContainerScreen<ModBeltContainer>
{
	private static final Identifier TEXTURE = new Identifier("textures/gui/container/hopper.png");

	public ModBeltScreen(ModBeltContainer container, PlayerInventory playerInventory, Text text)
	{
		super(container, playerInventory, text);
		this.containerHeight = 133;
	}

	@Override
	protected void drawForeground(int int_1, int int_2)
	{
		this.font.draw(this.title.asFormattedString(), 12.0F, 5.0F, 4210752);
		this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F,
			(float) (this.containerHeight - 96 + 2), 4210752);
	}

	@Override
	protected void drawBackground(float whatever, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(TEXTURE);
		int left = (this.width - this.containerWidth) / 2;
		int top = (this.height - this.containerHeight) / 2;
		this.blit(left, top, 0, 0, this.containerWidth, this.containerHeight);
	}

	@Override
	public void render(int x, int y, float myBoat)
	{
		myBoat = minecraft.getTickDelta();
		renderBackground();
		super.render(x, y, myBoat);
		drawMouseoverTooltip(x, y);
	}

}
