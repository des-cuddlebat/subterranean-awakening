package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.platform.GlStateManager;

import dev.emi.trinkets.api.TrinketsApi;
import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.item.ModInventoryBelt;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.GuiLighting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Arm;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;

@Mixin(InGameHud.class)
public abstract class InGameHudClientMixin extends DrawableHelper
{
	@Accessor
	public abstract int getScaledHeight();

	@Accessor
	public abstract int getScaledWidth();

	@Invoker
	public abstract void callRenderHotbarItem(int int_1, int int_2, float float_1, PlayerEntity playerEntity_1,
		ItemStack itemStack_1);

	@Inject(at = @At("HEAD"), method = "renderHotbar(F)V")
	protected void renderHotbarMixin(float myBoat, CallbackInfo ci)
	{
		MinecraftClient.getInstance().getTextureManager().bindTexture(new Identifier("textures/gui/widgets.png"));
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiLighting.enableForItems();
		
		Entity cameraEntity = MinecraftClient.getInstance().getCameraEntity();
		if (!(cameraEntity instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) cameraEntity;
		ItemStack belt = TrinketsApi.getTrinketComponent(player).getStack(Const.TrinketSlot.BELT);
		ItemStack hand = player.getMainHandStack();
		if(!hand.isEmpty() && hand.getItem() instanceof RangedWeaponItem)
			hand = player.getArrowType(hand);
		if (belt.isEmpty() || hand.isEmpty() || !(belt.getItem() instanceof ModInventoryBelt))
			return;
		ModInventoryBelt beltItem = (ModInventoryBelt) belt.getItem();
		if (beltItem.canGoIn(hand))
		{
			DefaultedList<ItemStack> list = DefaultedList.ofSize(5, ItemStack.EMPTY);
			Inventories.fromTag(belt.getOrCreateTag(), list);
			for (ItemStack beltStack : list)
			{
				if (ItemStack.areItemsEqual(hand, beltStack) && ItemStack.areTagsEqual(hand, beltStack))
				{
					if (player.getMainArm() == Arm.LEFT)
					{
						this.blit(getScaledHeight() / 2 - 91 - 29, getScaledHeight() - 23, 24, 22, 29, 24);
						callRenderHotbarItem(getScaledWidth() / 2 - 91 - 26, getScaledHeight() - 19,
							myBoat, player, beltStack);
					} else
					{
						this.blit(getScaledWidth() / 2 + 91, getScaledHeight() - 23, 53, 22, 29, 24);
						callRenderHotbarItem(getScaledWidth() / 2 + 91 + 10, getScaledHeight() - 19,
							myBoat, player, beltStack);
					}

					break;
				}
			}
		}
	}
}
