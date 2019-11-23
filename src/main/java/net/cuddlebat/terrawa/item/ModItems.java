package net.cuddlebat.terrawa.item;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.item.scroll.AngelheartScroll;
import net.cuddlebat.terrawa.item.scroll.CindersScroll;
import net.cuddlebat.terrawa.item.trinket.ArcanaFocus;
import net.cuddlebat.terrawa.item.trinket.MossyNecklace;
import net.cuddlebat.terrawa.item.trinket.PotionBelt;
import net.cuddlebat.terrawa.item.trinket.RubyWardRing;
import net.minecraft.item.Item;
import net.minecraft.item.Item.Settings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItems
{
	public static final LavaFishingRod LAVA_FISHING_ROD = new LavaFishingRod(
		new Item.Settings().maxDamage(127).group(ItemGroup.TOOLS));
	public static final SpellcastersTome SPELLCASTERS_TOME = new SpellcastersTome(
		new Item.Settings().maxDamage(127).group(ItemGroup.TOOLS));
	
	public static final ArcanaFocus ARCANA_FOCUS = new ArcanaFocus(
		new Item.Settings().maxCount(1).group(ItemGroup.MISC));
	public static final MossyNecklace MOSSY_NECKLACE = new MossyNecklace(
		new Item.Settings().maxCount(1).group(ItemGroup.MISC));
	public static final RubyWardRing RUBY_WARD = new RubyWardRing(
		new Item.Settings().maxCount(1).group(ItemGroup.MISC));
	
	public static final PotionBelt POTION_BELT = new PotionBelt(
		new Item.Settings().maxCount(1).group(ItemGroup.MISC));
	
	public static final Item VENOMOUS_FANG = new Item(
		new Item.Settings().group(ItemGroup.MISC));
	public static final Item ESSENCE_OF_POISON = new Item(
		new Item.Settings().group(ItemGroup.MISC));
	public static final Item MOSSY_BONE = new Item(
		new Item.Settings().group(ItemGroup.MISC));
	
	public static final Item CINDERS_SCROLL = new CindersScroll(
		new Item.Settings().group(ItemGroup.MISC));
	public static final Item ANGELHEART_SCROLL = new AngelheartScroll(
		new Item.Settings().group(ItemGroup.MISC));
	
	public static final Item MOLTEN_FISH = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item OBSIDIAN_FISH = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item PRISMATIC_FISH = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item BLAZEFISH = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item CODPER = new Item(new Settings().group(ItemGroup.MISC));
	public static final Item LAVA_LILY = new Item(new Settings().group(ItemGroup.MISC));

	public static void doRegister()
	{
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "lava_fishing_rod"), LAVA_FISHING_ROD);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "spellcasters_tome"), SPELLCASTERS_TOME);
		
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "arcana_focus"), ARCANA_FOCUS);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "mossy_necklace"), MOSSY_NECKLACE);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "ruby_ward_ring"), RUBY_WARD);

		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "potion_belt"), POTION_BELT);
		
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "venomous_fang"), VENOMOUS_FANG);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "essence_of_poison"), ESSENCE_OF_POISON);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "mossy_bone"), MOSSY_BONE);

		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "cinders_scroll"), CINDERS_SCROLL);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "angelheart_scroll"), ANGELHEART_SCROLL);
		
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "molten_fish"), MOLTEN_FISH);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "obsidian_fish"), OBSIDIAN_FISH);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "prismatic_fish"), PRISMATIC_FISH);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "blazefish"), BLAZEFISH);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "codper"), CODPER);
		Registry.register(Registry.ITEM, new Identifier(Const.MODID, "lava_lily"), LAVA_LILY);
	}

}
