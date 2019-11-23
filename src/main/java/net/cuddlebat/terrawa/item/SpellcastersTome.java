package net.cuddlebat.terrawa.item;

import java.util.List;
import java.util.function.Predicate;

import net.cuddlebat.terrawa.api.interfaces.MagicScroll;
import net.cuddlebat.terrawa.client.model.IEntityItemModel;
import net.cuddlebat.terrawa.client.model.IRendersAsEntity;
import net.cuddlebat.terrawa.client.model.SpellTomeModel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SpellcastersTome extends RangedWeaponItem implements IRendersAsEntity
{
	private static final IEntityItemModel MODEL = new SpellTomeModel();
	
	public SpellcastersTome(Settings settings)
	{
		super(settings);
	}

	@Override
	public IEntityItemModel getModel()
	{
		return MODEL;
	}

	@Override
	public Predicate<ItemStack> getProjectiles()
	{
		return (stack) ->
		{
			return stack.getItem() instanceof MagicScroll;
		};
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack scroll = player.getArrowType(player.getStackInHand(hand));
		if(!scroll.isEmpty() && player.getItemCooldownManager().getCooldownProgress(this, 0.0f) == 0.0f)
		{
			MagicScroll scrollItem = ((MagicScroll)scroll.getItem());
			scrollItem.useScroll(world, player, hand);
			player.getItemCooldownManager().set(this, scrollItem.getCooldownTicks());
			player.swingHand(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);
			scroll.decrement(1);
			
			return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
		}
		
		return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
	}
	
	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> list, TooltipContext context)
	{
		list.add(new LiteralText("  Use to cast scroll spells!").formatted(Formatting.YELLOW));
		
		super.appendTooltip(stack, world, list, context);
	}
}
