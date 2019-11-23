package net.cuddlebat.terrawa.client.gui;

import java.util.ArrayList;
import java.util.Optional;

import com.mojang.blaze3d.platform.GlStateManager;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.inventory.IEnchAction;
import net.cuddlebat.terrawa.inventory.ModEnchContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModEnchScreen extends AbstractContainerScreen<ModEnchContainer>
{
	private static final Identifier TEXTURE = new Identifier(Const.MODID, "textures/gui/container/ench_gui.png");

	public ModEnchScreen(ModEnchContainer container, PlayerInventory playerInventory, Text text)
	{
		super(container, playerInventory, text);
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

		// I'm gonna learn with LibGUI someday, promise
		int buttonTop = top + 52;
		int buttonLeft = left + 60;

		if (!container.hasValidAction())
		{
			this.blit(buttonLeft, buttonTop, 0, 185, 108, 19);
		} else if (isInBounds(mouseX, mouseY))
		{
			this.blit(buttonLeft, buttonTop, 0, 204, 108, 19);
		} else
		{
			this.blit(buttonLeft, buttonTop, 0, 166, 108, 19);
		}
	}

	@Override
	public boolean mouseClicked(double x, double y, int someInt)
	{

		if (isInBounds((int)x, (int)y))
		{
			if (container.onButtonClick(this.playerInventory.player, 0))
			{
				this.minecraft.interactionManager.clickButton(this.container.syncId, 0);
				return true;
			}
		}

		return super.mouseClicked(x, y, someInt);
	}

	@Override
	public void render(int x, int y, float myBoat)
	{
		myBoat = minecraft.getTickDelta();
		renderBackground();
		super.render(x, y, myBoat);
		drawMouseoverTooltip(x, y);
		
		Optional<IEnchAction> action = container.maybeGetAction(); 
		Optional<IEnchAction> hint = container.maybeGetHint();
		IEnchAction renderAction = action.orElse(hint.orElse(null));
		if(renderAction == null)
			return;

		Text buttonText = renderAction.getButtonText();
		int left = (this.width - this.containerWidth) / 2;
		int top = (this.height - this.containerHeight) / 2;
		MinecraftClient.getInstance().getFontManager().getTextRenderer(MinecraftClient.DEFAULT_TEXT_RENDERER_ID)
			.draw(buttonText.asFormattedString(), left + 65, top + 57, 0x140A00);
		

		if (isInBounds(x, y))
		{
			ArrayList<Text> mouseText = null;
			ItemStack agent = container.getInventory().getInvStack(0);
			ItemStack target = container.getInventory().getInvStack(1);
			ItemStack book = container.getInventory().getInvStack(2);
			if (container.hasValidAction())
			{
				mouseText = renderAction.getMouseText(agent, target, book);
			}
			else
			{
				mouseText = new ArrayList<>();
				mouseText.add(renderAction.getHintIfAlmostValid(agent, target, book).get());
			}
			
			ArrayList<String> lines = new ArrayList<>();
			for(Text text : mouseText)
			{
				lines.add(text.asFormattedString());
			}
			
			renderTooltip(lines, x, y);
		}
	}
	
	private boolean isInBounds(int x, int y)
	{
		int left = (this.width - this.containerWidth) / 2;
		int top = (this.height - this.containerHeight) / 2;

		// I'm gonna learn with LibGUI someday, promise
		int buttonTop = top + 52;
		int buttonLeft = left + 60;
		int buttonRight = buttonLeft + 108;
		int buttonBottom = buttonTop + 19;
		
		return x >= buttonLeft && x <= buttonRight && y >= buttonTop && y <= buttonBottom;
	}

}
