package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.terrawa.client.model.IRendersAsEntity;
import net.minecraft.client.render.item.ItemDynamicRenderer;
import net.minecraft.item.ItemStack;

@Mixin(ItemDynamicRenderer.class)
public class ItemDynamicRendererClientMixin
{
	@Inject(at = @At("HEAD"), cancellable = true, method = "render(Lnet/minecraft/item/ItemStack;)V")
	protected void renderMixin(ItemStack stack, CallbackInfo ci)
	{
		if(stack.getItem() instanceof IRendersAsEntity)
		{
			((IRendersAsEntity)stack.getItem()).getModel().render(stack);
			ci.cancel();
		}
	}
}
