package net.cuddlebat.terrawa.enchant;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import dev.emi.trinkets.api.TrinketsApi;
import net.cuddlebat.terrawa.api.interfaces.IEnchSupportTrinket;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.Registry;

public abstract class ModEnchHelper
{
	private static final HashMap<String, VanillaEnchTransform> TRANSFORMS = new HashMap<>();
	private static final String[] TIER = new String[]
	{
		"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "X+"
	};

	public static void doInit()
	{
		VanillaEnchTransform.EnchantmentCostCallback protCost = (level) -> 5 * level * (level + 1);
		VanillaEnchTransform.DescParamsGetter protAllParams = (level) -> new Object[] { 4.0f * level };
		VanillaEnchTransform.DescParamsGetter protParams = (level) -> new Object[] { 8.0f * level };
		TRANSFORMS.put(idOf(Enchantments.PROTECTION),
			new VanillaEnchTransform(true).withEnchCostGetter(protCost).withDescParamsGetter(protAllParams));
		TRANSFORMS.put(idOf(Enchantments.BLAST_PROTECTION),
			new VanillaEnchTransform(true).withEnchCostGetter(protCost).withDescParamsGetter(protParams));
		TRANSFORMS.put(idOf(Enchantments.FIRE_PROTECTION),
			new VanillaEnchTransform(true).withEnchCostGetter(protCost).withDescParamsGetter(protParams));
		TRANSFORMS.put(idOf(Enchantments.PROJECTILE_PROTECTION),
			new VanillaEnchTransform(true).withEnchCostGetter(protCost).withDescParamsGetter(protParams));
		TRANSFORMS.put(idOf(Enchantments.FEATHER_FALLING),
			new VanillaEnchTransform(true).withEnchCostGetter(protCost).withDescParamsGetter(protParams));

		VanillaEnchTransform.EnchantmentCostCallback dmgCost = (level) -> 10 * level;
		VanillaEnchTransform.DescParamsGetter dmgAllParams = (level) -> new Object[] { 1.0f * level };
		VanillaEnchTransform.DescParamsGetter dmgParams = (level) -> new Object[] { 2.5f * level };
		TRANSFORMS.put(idOf(Enchantments.SHARPNESS),
			new VanillaEnchTransform(true).withEnchCostGetter(dmgCost).withDescParamsGetter(dmgAllParams));
		TRANSFORMS.put(idOf(Enchantments.BANE_OF_ARTHROPODS),
			new VanillaEnchTransform(true).withEnchCostGetter(dmgCost).withDescParamsGetter(dmgParams));
		TRANSFORMS.put(idOf(Enchantments.SMITE),
			new VanillaEnchTransform(true).withEnchCostGetter(dmgCost).withDescParamsGetter(dmgParams));

		TRANSFORMS.put(idOf(Enchantments.POWER),
			new VanillaEnchTransform(true).withEnchCostGetter(dmgCost).withDescParamsGetter(dmgParams));
	}

	public static boolean doDesc(Enchantment ench)
	{
		return ench instanceof ModEnch || (TRANSFORMS.containsKey(idOf(ench)) && TRANSFORMS.get(idOf(ench)).doDesc());
	}

	private static String idOf(Enchantment ench)
	{
		return Registry.ENCHANTMENT.getId(ench).toString();
	}

	public static Object[] getDescParams(Enchantment ench, int level)
	{
		if (ench instanceof ModEnch)
			return ((ModEnch) ench).getDescParams(level);
		else if (TRANSFORMS.containsKey(idOf(ench)))
			return TRANSFORMS.get(idOf(ench)).getDescParams(level).orElse(new Object[0]);
		else
			return new Object[0];
	}

	public static int getCostOf(Enchantment ench, int level)
	{
		if (ench instanceof ModEnch)
			return ((ModEnch) ench).getCost(level);
		else if (TRANSFORMS.containsKey(idOf(ench)))
			return TRANSFORMS.get(idOf(ench)).getEnchCost(level).orElse(ench.getMinimumPower(level) / 5 * 5);
		else
			return ench.getMinimumPower(level) / 5 * 5;
	}

	public static int getArcanaCapacity(ItemStack stack)
	{
		// TODO going to hell for this obvs
		Item item = stack.getItem();
		return item.getEnchantability() * 10;
	}

	public static int getArcanaUsed(ItemStack stack)
	{
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		int arcana = 0;
		for (Entry<Enchantment, Integer> ench : enchantments.entrySet())
		{
			arcana += getCostOf(ench.getKey(), ench.getValue());
		}
		return arcana;
	}

	public static int getApplyArcanaDelta(ItemStack target, ItemStack book)
	{
		Map<Enchantment, Integer> targetEnch = EnchantmentHelper.getEnchantments(target);
		Map<Enchantment, Integer> bookEnch = EnchantmentHelper.getEnchantments(book);
		int costDelta = 0;
		for (Entry<Enchantment, Integer> ench : bookEnch.entrySet())
		{
			if (targetEnch.containsKey(ench.getKey()))
				costDelta += Math.max(0, getCostOf(ench.getKey(), ench.getValue())
					- getCostOf(ench.getKey(), targetEnch.get(ench.getKey())));
			else
				costDelta += getCostOf(ench.getKey(), ench.getValue());
		}
		return costDelta;
	}

	/**
	 * 
	 * @param owner The LivingEntity whose equipment slots to check.
	 * @param ench  The enchantment to find support for
	 * @return Sum of levels of all support for this enchantment.
	 */
	public static int getSupportLevel(LivingEntity owner, Enchantment ench)
	{
		// TODO eventually I'd love to do trinket enchantments but
		int result = 0;
		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			result += getSupportLevelInternal(owner, ench, slot);
		}

		if (owner instanceof PlayerEntity)
		{
			Inventory trinketInv = TrinketsApi.getTrinketComponent((PlayerEntity) owner).getInventory();
			for (int i = 0; i < trinketInv.getInvSize(); i++)
			{
				if (trinketInv.getInvStack(i).getItem() instanceof IEnchSupportTrinket)
				{
					result += ((IEnchSupportTrinket) trinketInv.getInvStack(i).getItem()).getSupportFor(ench);
				}
			}
		}

		return result;
	}

	private static int getSupportLevelInternal(LivingEntity owner, Enchantment ench, EquipmentSlot slot)
	{
		ItemStack stack = owner.getEquippedStack(slot);
		int result = 0;
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
		for (Entry<Enchantment, Integer> entry : enchantments.entrySet())
		{ // Better be safe from anvils
			if (entry.getKey() instanceof ModEnch && entry.getKey().getEquipment(owner).containsKey(slot)
				&& ((ModEnch) entry.getKey()).isSupportOf(ench))
			{
				result += entry.getValue();
			}
		}
		return result;
	}

	public static void onAttackPre(LivingEntity attacker, Entity target)
	{
		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			ItemStack stack = attacker.getEquippedStack(slot);
			if (stack != null)
			{
				Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
				for (Entry<Enchantment, Integer> entry : enchantments.entrySet())
				{
					int level = entry.getValue() + getSupportLevel(attacker, entry.getKey());
					// Anvil check
					if (entry.getKey().getEquipment(attacker).containsKey(slot))
					{
						if (entry.getKey() instanceof ModEnch)
							((ModEnch) entry.getKey()).onAttackPre(attacker, target, level);
						else if (TRANSFORMS.containsKey(idOf(entry.getKey())))
							TRANSFORMS.get(idOf(entry.getKey())).onAttackPre(attacker, target, level);
					}
				}
			}
		}
	}

	public static Text getArcanaText(Enchantment ench, int level)
	{
		int cost = ModEnchHelper.getCostOf(ench, level);
		return new LiteralText("(").append(Integer.toString(cost)).append(" arc.)")
			.formatted(Formatting.DARK_GRAY);
	}

	public static Optional<Text> getDesc(Enchantment ench, int level)
	{
		if (!ModEnchHelper.doDesc(ench))
			return Optional.empty();

		Object[] params = ModEnchHelper.getDescParams(ench, level);

		return Optional.of(new TranslatableText("desc." + ench.getTranslationKey(), params)
			.formatted(Formatting.DARK_GRAY));
	}
	
	public static String getRomanNumeral(int tier)
	{
		return TIER[Math.min(tier, 10)];
	}

}
