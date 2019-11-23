package net.cuddlebat.terrawa;

import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
import net.cuddlebat.terrawa.cardinal.IDamageCooldownComponent;
import net.cuddlebat.terrawa.cardinal.IFishingComponent;
import net.cuddlebat.terrawa.cardinal.IProjectileComponent;
import net.minecraft.util.Identifier;

public class Const
{
	public static final String MODID = "terrawa";
	
	public static class Component
	{
		public static final ComponentType<IProjectileComponent> PROJECTILE_COMPONENT = 
	        ComponentRegistry.INSTANCE.registerIfAbsent(
	        	new Identifier("terrawa:projectilecomponent"), IProjectileComponent.class);
		
		public static final ComponentType<ICavernsComponent> CAVERNS_COMPONENT = 
	        ComponentRegistry.INSTANCE.registerIfAbsent(
	        	new Identifier("terrawa:cavernscomponent"), ICavernsComponent.class);
		
		public static final ComponentType<IDamageCooldownComponent> DAMAGE_COOLDOWN_COMPONENT = 
	        ComponentRegistry.INSTANCE.registerIfAbsent(
	        	new Identifier("terrawa:damagecomponent"), IDamageCooldownComponent.class);

		public static final ComponentType<IFishingComponent> FISHING_COMPONENT = 
	        ComponentRegistry.INSTANCE.registerIfAbsent(
	        	new Identifier("terrawa:fishingcomponent"), IFishingComponent.class);
	}
	
	public static class TrinketSlot
	{
		public static final String BELT = "legs:belt";
		public static final String NECKLACE = "chest:necklace";
		public static final String RING_MAINHAND = "hand:ring";
		public static final String RING_OFFHAND = "offhand:ring";
	}
}
