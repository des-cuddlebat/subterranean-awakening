package net.cuddlebat.terrawa.mixin;

import java.util.Optional;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.api.interfaces.ModFishingRod;
import net.cuddlebat.terrawa.cardinal.IFishingComponent;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.FishingBobberEntityRenderer;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Mixin(value = FishingBobberEntityRenderer.class) 
public abstract class FishingRendererClientMixin extends EntityRenderer<FishingBobberEntity>
{

	private FishingRendererClientMixin(EntityRenderDispatcher entityRenderDispatcher_1)
	{
		super(entityRenderDispatcher_1);
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/item/Items;FISHING_ROD:Lnet/minecraft/item/Item;"),
		method = "method_3974")
	public Item getRodProxy(FishingBobberEntity entity, double banana, double orange, double kiwi, float apple, float plum)
	{
		Optional<IFishingComponent> component = Const.Component.FISHING_COMPONENT.maybeGet(entity);
		Optional<ModFishingRod> rod = component.isPresent() ? component.get().maybeGetRod() : Optional.empty();
		return rod.isPresent() ? rod.get() : Items.FISHING_ROD;
	}

	@Inject(at = @At("HEAD"), cancellable = true, method = "method_3975")
	public void getTextureMixin(FishingBobberEntity entity, CallbackInfoReturnable<Identifier> cir)
	{
		Optional<IFishingComponent> component = Const.Component.FISHING_COMPONENT.maybeGet(entity);
		Optional<ModFishingRod> rod = component.isPresent() ? component.get().maybeGetRod() : Optional.empty();
		if(rod.isPresent())
			cir.setReturnValue(rod.get().getBobberTexture());
	}
}
