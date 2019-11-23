package net.cuddlebat.terrawa.network;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dev.emi.trinkets.api.TrinketsApi;
import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.entity.trait.IOnClientConstruct;
import net.cuddlebat.terrawa.inventory.ModContainers;
import net.cuddlebat.terrawa.item.ModInventoryBelt;
import net.cuddlebat.terrawa.strangethings.IBiomeOverride;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.RidingMinecartSoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.WorldChunk;

public class ModPackets
{
	public static final Identifier OPEN_BELT = new Identifier(Const.MODID, "open_belt");
	public static final Identifier CYCLE_BELT = new Identifier(Const.MODID, "cycle_belt");

	public static final Identifier SEND_BIOME_OVERRIDES = new Identifier(Const.MODID, "biome_overrides");

	public static final Identifier SPAWN_NONLIVING = new Identifier(Const.MODID, "spawn_nonliving");

	public static void doRegister()
	{
		ServerSidePacketRegistry.INSTANCE.register(OPEN_BELT, (packetContext, attachedData) ->
		{
			packetContext.getTaskQueue().execute(() ->
			{
				PlayerEntity player = packetContext.getPlayer();
				ItemStack belt = TrinketsApi.getTrinketComponent(player).getStack(Const.TrinketSlot.BELT);
		    	if(!belt.isEmpty() && belt.getItem() instanceof ModInventoryBelt)
		    	{
					ContainerProviderRegistry.INSTANCE.openContainer(ModContainers.BELT_GUI_ID, player, (buf) -> {});
				}
			});
		});
		
		ServerSidePacketRegistry.INSTANCE.register(CYCLE_BELT, (packetContext, attachedData) ->
		{
			packetContext.getTaskQueue().execute(() ->
			{
				PlayerEntity player = packetContext.getPlayer();
				ItemStack hand = player.getMainHandStack();
				if(!hand.isEmpty() && hand.getItem() instanceof RangedWeaponItem)
					hand = player.getArrowType(hand);
				int index = player.inventory.getSlotWithStack(hand);
				ItemStack belt = TrinketsApi.getTrinketComponent(player).getStack(Const.TrinketSlot.BELT);
		    	if(!belt.isEmpty() && belt.getItem() instanceof ModInventoryBelt)
		    	{
		    		ModInventoryBelt beltItem = (ModInventoryBelt)belt.getItem();
					DefaultedList<ItemStack> stacks = DefaultedList.ofSize(5, ItemStack.EMPTY);
					Inventories.fromTag(belt.getOrCreateTag(), stacks);
					// Two cycles worst case; can't store hand item in empty slot
					boolean foundMatch = false;
					for (int i = 0; i < 10; i++)
					{
						ItemStack slotStack = stacks.get(i % 5);

						if(foundMatch && !slotStack.isEmpty())
						{
							ItemStack taken = slotStack.split(1/*slotStack.getMaxCount()*/);
//							player.setStackInHand(Hand.MAIN_HAND, taken);
							player.inventory.setInvStack(index, taken);
							break;
						}
							
						if(ItemStack.areItemsEqual(hand, slotStack) && ItemStack.areTagsEqual(hand, slotStack))
						{
							if(hand.getCount() + slotStack.getCount() <= beltItem.getSlotsMaxCount())
							{
								slotStack.increment(hand.getCount());
//								player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
								player.inventory.setInvStack(index, ItemStack.EMPTY);
								foundMatch = true;
							}
						}
					}
					
					Inventories.toTag(belt.getOrCreateTag(), stacks);
				}
			});
		});
		

		ClientSidePacketRegistry.INSTANCE.register(SEND_BIOME_OVERRIDES, (packetContext, attachedData) ->
		{
			int x = attachedData.readInt();
			int z = attachedData.readInt();
			int[] bottoms = attachedData.readIntArray();
			int[] tops = attachedData.readIntArray();
			byte[] biomes = attachedData.readByteArray();

			int biomeCount = attachedData.readInt();
			Map<Byte, String> map = new HashMap<>();
			for(int i = 0; i < biomeCount; i++)
			{
				byte index = attachedData.readByte();
				String biome = attachedData.readString();
				map.put(index, biome);
			}
			
			packetContext.getTaskQueue().execute(() ->
			{
				WorldChunk chunk = (WorldChunk)packetContext.getPlayer().world.getChunk(x, z);
				((IBiomeOverride)chunk).setData(bottoms, tops, biomes, map);
			});
		});
		

		ClientSidePacketRegistry.INSTANCE.register(SPAWN_NONLIVING, (packetContext, attachedData) ->
		{
			int id = attachedData.readVarInt();
			UUID uuid = attachedData.readUuid();
			EntityType<?> entityTypeId = Registry.ENTITY_TYPE.get(attachedData.readVarInt());
			double x = attachedData.readDouble();
			double y = attachedData.readDouble();
			double z = attachedData.readDouble();
			byte pitch = attachedData.readByte();
			byte yaw = attachedData.readByte();
			int entityData = attachedData.readInt();
			short xVel = attachedData.readShort();
			short yVel = attachedData.readShort();
			short zVel = attachedData.readShort();
			
			packetContext.getTaskQueue().execute(() ->
			{
				Entity entity = entityTypeId.create(packetContext.getPlayer().world);
				Entity owner = packetContext.getPlayer().world.getEntityById(entityData);
				if(owner != null)
				{
					if(entity instanceof ProjectileEntity)
						((ProjectileEntity)entity).setOwner(owner);
					if(entity instanceof ExplosiveProjectileEntity && owner instanceof LivingEntity)
						((ExplosiveProjectileEntity)entity).owner = (LivingEntity) owner;
				}
				
				if(entity instanceof IOnClientConstruct)
					((IOnClientConstruct)entity).applyConstructionParams(x, y, z, xVel, yVel, zVel);
				
				entity.updateTrackedPosition(x, y, z);
				entity.pitch = pitch * 360 / 256.0F;
				entity.yaw = yaw * 360 / 256.0F;
				entity.setEntityId(id);
				entity.setUuid(uuid);
				((ClientWorld)packetContext.getPlayer().world).addEntity(id, entity);
			});
		});
	}
}
