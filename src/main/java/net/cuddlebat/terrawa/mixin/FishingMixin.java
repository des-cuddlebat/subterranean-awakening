package net.cuddlebat.terrawa.mixin;

import java.util.Optional;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.api.interfaces.ModFishingRod;
import net.cuddlebat.terrawa.cardinal.FishingComponent;
import net.cuddlebat.terrawa.cardinal.IFishingComponent;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.loot.LootTables;

//TODO I possibly don't need priority here
@Mixin(value = FishingBobberEntity.class, priority = 10000) 
public abstract class FishingMixin extends Entity
{
	private FishingMixin(EntityType<?> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
	}
	
	@Override
	public boolean isOnFire()
	{
		return false;
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/world/loot/LootTables;FISHING_GAMEPLAY:Lnet/minecraft/util/Identifier;"),
		method = "method_6957")
	public Identifier getLootTableProxy(ItemStack rod)
	{
		if(rod.getItem() instanceof ModFishingRod)
		{
			return ((ModFishingRod)rod.getItem()).getLootTable();
		}
		return LootTables.FISHING_GAMEPLAY;
	}

	@Redirect(at = @At(value="INVOKE",
		target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"),
		method = "method_6957")
	public boolean spawnEntityProxy(World world, Entity entity)
	{
		entity.setInvulnerable(true);
		Optional<IFishingComponent> component = maybeGetComponent();
		Optional<ModFishingRod> rod = component.isPresent() ? component.get().maybeGetRod() : Optional.empty();
		if(rod.isPresent())
			entity.setPosition(entity.x, entity.y + 1, entity.z);
		return world.spawnEntity(entity);
	}
	
	@Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;II)V")
	public void setupComponent(World world, PlayerEntity player, int int_1, int int_2, CallbackInfo ci)
	{
		Optional<FishingComponent> component = maybeGetCastedComponent();
		if(component.isPresent())
		{
			Item mainhand = player.getMainHandStack().getItem();
			Item offhand = player.getOffHandStack().getItem();
			if(mainhand instanceof FishingRodItem)
			{
				if(mainhand instanceof ModFishingRod)
					component.get().setRod((ModFishingRod) mainhand);
			}
			else if (offhand instanceof FishingRodItem)
			{
				if(offhand instanceof ModFishingRod)
					component.get().setRod((ModFishingRod) offhand);
			}
		}
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/tag/FluidTags;WATER:Lnet/minecraft/tag/Tag;"),
		method = "tick")
	public Tag<Fluid> getFluidProxy()
	{
		Optional<IFishingComponent> component = maybeGetComponent();
		return component.isPresent() ? component.get().getFluid() : FluidTags.WATER;
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/block/Blocks;WATER:Lnet/minecraft/block/Block;"),
		method = "method_6949")
	public Block getFluidBlockProxy()
	{
		Optional<IFishingComponent> component = maybeGetComponent();
		return component.isPresent() ? component.get().getFluidBlock() : Blocks.WATER;
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/item/Items;FISHING_ROD:Lnet/minecraft/item/Item;"),
		method = "method_6959")
	public Item getRodProxy()
	{
		Optional<IFishingComponent> component = maybeGetComponent();
		Optional<ModFishingRod> rod = component.isPresent() ? component.get().maybeGetRod() : Optional.empty();
		return rod.isPresent() ? rod.get() : Items.FISHING_ROD;
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/particle/ParticleTypes;BUBBLE:Lnet/minecraft/particle/DefaultParticleType;"),
		method = "method_6949")
	public DefaultParticleType getBubbleParticleProxy()
	{
		Optional<IFishingComponent> component = maybeGetComponent();
		return component.isPresent() ? component.get().getBubbleParticle() : ParticleTypes.BUBBLE;
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/particle/ParticleTypes;FISHING:Lnet/minecraft/particle/DefaultParticleType;"),
		method = "method_6949")
	public DefaultParticleType getFishingParticleProxy()
	{
		Optional<IFishingComponent> component = maybeGetComponent();
		return component.isPresent() ? component.get().getFishingParticle() : ParticleTypes.FISHING;
	}

	@Redirect(at = @At(value="FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/particle/ParticleTypes;SPLASH:Lnet/minecraft/particle/DefaultParticleType;"),
		method = "method_6949")
	public DefaultParticleType getSplashParticleProxy()
	{
		Optional<IFishingComponent> component = maybeGetComponent();
		return component.isPresent() ? component.get().getSplashParticle() : ParticleTypes.SPLASH;
	}
	
	private Optional<IFishingComponent> maybeGetComponent()
	{
		FishingBobberEntity self = (FishingBobberEntity) (Object) this;
		return Const.Component.FISHING_COMPONENT.maybeGet(self);
	}
	
	private Optional<FishingComponent> maybeGetCastedComponent()
	{
		Optional<IFishingComponent> comp = maybeGetComponent();
		if(comp.isPresent() && comp.get() instanceof FishingComponent)
			return Optional.of((FishingComponent) comp.get());
		return Optional.empty();
	}
}
