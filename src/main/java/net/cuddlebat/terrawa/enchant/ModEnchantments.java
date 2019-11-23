package net.cuddlebat.terrawa.enchant;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.enchant.all.EnchArmorbreak;
import net.cuddlebat.terrawa.enchant.all.EnchDragonAspect;
import net.cuddlebat.terrawa.enchant.all.EnchDwarvenPlating;
import net.cuddlebat.terrawa.enchant.all.EnchFrostbite;
import net.cuddlebat.terrawa.enchant.all.EnchLifeDrain;
import net.cuddlebat.terrawa.enchant.all.EnchLifeforce;
import net.cuddlebat.terrawa.enchant.all.EnchMagicSting;
import net.cuddlebat.terrawa.enchant.all.EnchPoison;
import net.cuddlebat.terrawa.enchant.all.EnchReinforced;
import net.cuddlebat.terrawa.enchant.all.EnchRootingArrow;
import net.cuddlebat.terrawa.enchant.all.EnchSwiftPull;
import net.cuddlebat.terrawa.enchant.all.EnchVoidDischarge;
import net.cuddlebat.terrawa.enchant.all.EnchVoidSting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public abstract class ModEnchantments
{
	private ModEnchantments()
	{
	}
	// melee+bow damage enchs
	public static final ModEnch MAGIC_STING = new EnchMagicSting();
	public static final ModEnch VOID_STING = new EnchVoidSting();
	// melee+bow effect enchs
	public static final ModEnch POISON = new EnchPoison();
	public static final ModEnch ARMORBREAK = new EnchArmorbreak();
	public static final ModEnch FROSTBITE = new EnchFrostbite();
	// melee enchs
	public static final ModEnch LIFE_DRAIN = new EnchLifeDrain();
	public static final ModEnch VOID_DISCHARGE = new EnchVoidDischarge();
	public static final ModEnch DRAGON_ASPECT = new EnchDragonAspect();
	
	// bow enchs
	public static final ModEnch SWIFT_PULL = new EnchSwiftPull();
	public static final ModEnch ROOTING_ARROW = new EnchRootingArrow();
	
	// armor (all)
	public static final ModEnch DWARVEN_PLATING = new EnchDwarvenPlating();
	public static final ModEnch LIFEFORCE = new EnchLifeforce();
	public static final ModEnch REINFORCED = new EnchReinforced();

	public static void doRegister()
	{
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "magic_sting"), MAGIC_STING);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "void_sting"), VOID_STING);

		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "poison"), POISON);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "armorbreak"), ARMORBREAK);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "frostbite"), FROSTBITE);

		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "life_drain"), LIFE_DRAIN);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "void_discharge"), VOID_DISCHARGE);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "dragon_aspect"), DRAGON_ASPECT);
		
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "swift_pull"), SWIFT_PULL);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "rooting_arrow"), ROOTING_ARROW);

		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "dwarven_plating"), DWARVEN_PLATING);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "lifeforce"), LIFEFORCE);
		Registry.register(Registry.ENCHANTMENT, new Identifier(Const.MODID, "reinforced"), REINFORCED);
	}
}
