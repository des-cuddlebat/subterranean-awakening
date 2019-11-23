package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.cuddlebat.terrawa.inventory.ModContainers;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.EnchantingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@Mixin(EnchantingTableBlock.class)
public class EnchTableMixin
{
	@Inject(at = @At("HEAD"), cancellable = true, method = "activate")
	public void activate(BlockState state, World world_1, BlockPos blockPos,
		PlayerEntity player, Hand hand_1, BlockHitResult blockHitResult_1, CallbackInfoReturnable<Boolean> cir)
	{
		if (!world_1.isClient)
		{
			ContainerProviderRegistry.INSTANCE.openContainer(ModContainers.ENCH_GUI_ID, player,
				(buf) -> buf.writeBlockPos(blockPos));
		}
		cir.setReturnValue(true);
	}
}
