package net.cuddlebat.terrawa.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.terrawa.api.interfaces.ITrinketWithSlotInfo;
import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

@Mixin(ItemStack.class)
public class ItemStackClientMixin
{

	@Inject(at = @At("HEAD"), cancellable = true, method = "appendEnchantments")
	private static void appendEnchantments(List<Text> list, ListTag tag, CallbackInfo ci)
	{
		for (int int_1 = 0; int_1 < tag.size(); ++int_1)
		{
			CompoundTag subTag = tag.getCompoundTag(int_1);
			Registry.ENCHANTMENT.getOrEmpty(Identifier.tryParse(subTag.getString("id")))
				.ifPresent((ench) ->
				{
					int level = subTag.getInt("lvl");
					Text text = ench.getName(level);
					if(Screen.hasShiftDown())
					{
						text.append(" ").append(ModEnchHelper.getArcanaText(ench, level));
						list.add(text);
						text = new LiteralText("  ");
						text.append(ModEnchHelper.getDesc(ench, level).orElse(new LiteralText("")));
					}
					list.add(text);
				});
		}
		ci.cancel();
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "getName()Lnet/minecraft/text/Text;"), method = "getTooltip")
	public Text getNameProxy(ItemStack stack)
	{
		Text text = stack.getName();

		if (Screen.hasShiftDown() && (stack.isEnchantable() || stack.hasEnchantments())
			&& stack.getItem() != Items.BOOK)
		{
			Text myText = new LiteralText("");

			int arcanaUsed = ModEnchHelper.getArcanaUsed(stack);
			int arcanaCap = ModEnchHelper.getArcanaCapacity(stack);

			myText.append(" (").append(arcanaUsed + " / ").append(arcanaCap + " arc. used)");

			myText.formatted(Formatting.DARK_GRAY);
			text.append(myText);
		}
		return text;
	}
	
	@Redirect(at = @At(value = "INVOKE",
		target = "Lnet/minecraft/item/Item;appendTooltip(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Ljava/util/List;Lnet/minecraft/client/item/TooltipContext;)V"),
		method = "getTooltip")
	private void appendTooltipRedirect(Item item, ItemStack stack, World world, List<Text> list, TooltipContext context)
	{
		if(item instanceof ITrinketWithSlotInfo)
			list.add(new LiteralText("  ")
				.append(((ITrinketWithSlotInfo)stack.getItem()).getSlotNameForTooltip())
				.formatted(Formatting.DARK_GRAY));
		item.appendTooltip(stack, world, list, context);
	}

}
