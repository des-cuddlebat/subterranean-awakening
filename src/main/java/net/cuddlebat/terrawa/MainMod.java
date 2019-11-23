package net.cuddlebat.terrawa;

import dev.emi.trinkets.api.TrinketSlots;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import nerdhub.cardinal.components.api.event.WorldComponentCallback;
import net.cuddlebat.terrawa.cardinal.CavernsComponent;
import net.cuddlebat.terrawa.cardinal.DamageCooldownComponent;
import net.cuddlebat.terrawa.cardinal.FishingComponent;
import net.cuddlebat.terrawa.cardinal.ProjectileComponent;
import net.cuddlebat.terrawa.effect.ModStatusEffects;
import net.cuddlebat.terrawa.enchant.ModEnchantments;
import net.cuddlebat.terrawa.entity.ModEntities;
import net.cuddlebat.terrawa.inventory.EnchActions;
import net.cuddlebat.terrawa.inventory.ModContainers;
import net.cuddlebat.terrawa.item.ModItems;
import net.cuddlebat.terrawa.network.ModPackets;
import net.cuddlebat.terrawa.subbiome.SubterraneanBiomes;
import net.cuddlebat.terrawa.world.feature.ModFeatures;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Identifier;

public class MainMod implements ModInitializer
{
	@Override
	public void onInitialize()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		System.out.println("Awakening the subterranean!");

		// Registries
		ModEnchantments.doRegister();
		ModEntities.doRegister();
		ModItems.doRegister();
		ModContainers.doRegister();
		ModStatusEffects.doRegister();
		ModPackets.doRegister();
		ModFeatures.doRegister();
		SubterraneanBiomes.doRegister();
		
		// My shit
		EnchActions.doInit();
		
		// Cave feature
//		CaveFeature feat = Registry.register(Registry.FEATURE, new Identifier("tutorial", "stone_spiral"),
//			new CaveFeature(DefaultFeatureConfig::deserialize));
//		Registry.BIOME.forEach(biome -> biome.addFeature(
//	        GenerationStep.Feature.RAW_GENERATION,
//	    	Biome.configureFeature(
//	    		feat,
//	    		new DefaultFeatureConfig(),
//	    		Decorator.COUNT_HEIGHTMAP,
//	    		new CountDecoratorConfig(1)
//	    	)
//	    ));

		// Trinkets setup
		TrinketSlots.addSubSlot("hand", "ring",
			new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
		TrinketSlots.addSubSlot("offhand", "ring",
			new Identifier("trinkets", "textures/item/empty_trinket_slot_ring.png"));
		TrinketSlots.addSubSlot("chest", "necklace",
			new Identifier("trinkets", "textures/item/empty_trinket_slot_necklace.png"));
		TrinketSlots.addSubSlot("legs", "belt",
			new Identifier("trinkets", "textures/item/empty_trinket_slot_belt.png"));

		// Cardinal Components setup
		EntityComponentCallback.event(ProjectileEntity.class).register((projectile, components) -> components
			.put(Const.Component.PROJECTILE_COMPONENT, new ProjectileComponent()));

		WorldComponentCallback.EVENT.register((world, components) -> components
			.put(Const.Component.CAVERNS_COMPONENT, new CavernsComponent(world)));

		EntityComponentCallback.event(LivingEntity.class).register((projectile, components) -> components
			.put(Const.Component.DAMAGE_COOLDOWN_COMPONENT, new DamageCooldownComponent()));

		EntityComponentCallback.event(FishingBobberEntity.class).register((bobber, components) -> components
			.put(Const.Component.FISHING_COMPONENT, new FishingComponent()));
	}
}
