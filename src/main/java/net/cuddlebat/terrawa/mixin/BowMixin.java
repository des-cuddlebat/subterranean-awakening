package net.cuddlebat.terrawa.mixin;

import java.util.Map;
import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.terrawa.api.interfaces.ModBow;
import net.cuddlebat.terrawa.enchant.ModEnch;
import net.cuddlebat.terrawa.enchant.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@Mixin(BowItem.class)
public abstract class BowMixin extends RangedWeaponItem
{
	
	private BowMixin(Settings item$Settings_1)
	{
		super(item$Settings_1);
	}

	/**
	 * This method overrides the pull attribute to use custom pull time.
	 */
	@Inject(method = "<init>", at = @At("RETURN"))
	public void init(Item.Settings settings, CallbackInfo ci)
	{
		this.addPropertyGetter(new Identifier("pull"), (stack, world, entity) ->
		{
			return entity == null ? 0.0F :
				(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / getPullLength(stack);
		});
	}
	
	private static float getPullLength(ItemStack bow)
	{
		ModBow bowItem = bow.getItem() instanceof ModBow ? (ModBow) bow.getItem() : null;
		int baseTicks = bowItem == null ? 20 : bowItem.getPullTicks();
		int pullTimeLevel = EnchantmentHelper.getLevel(ModEnchantments.SWIFT_PULL, bow);
		// TODO get this somewhere more modularish
		return baseTicks - 0.1f*pullTimeLevel;
	}
	
	@Override
	public int getEnchantability()
	{
		return 4;
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "getPullProgress"),
		method = "onStoppedUsing")
	private float getPullProgressProxy(int ticks,
		ItemStack bow, World world, LivingEntity user, int utl)
	{
		float progress = ticks / getPullLength(bow);
		return Math.min(3.0f, (progress * (progress + 2.0F))) / 3.0F;
	}
	
	@Redirect(at = @At(value = "INVOKE", target = "net.minecraft.item.ArrowItem.createArrow"),
		method = "onStoppedUsing")
	private ProjectileEntity createArrowProxy(ArrowItem self, World worldIn, ItemStack stackIn, LivingEntity entityIn,
		ItemStack bow, World world, LivingEntity user, int utl)
	{
		ProjectileEntity projectile = self.createArrow(worldIn, stackIn, entityIn);
		
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(bow);
		for(Entry<Enchantment, Integer> entry : enchantments.entrySet())
		{
			if(entry.getKey() instanceof ModEnch)
			{
				((ModEnch)entry.getKey()).applyToArrow(projectile, entry.getValue());
			}
		}
		
		return projectile;
	}
}
