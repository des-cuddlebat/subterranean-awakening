package net.cuddlebat.terrawa.mixin;

import java.util.List;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.ICavernsComponent;
import net.cuddlebat.terrawa.strangethings.IBiomeOverride;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

@Mixin(DebugHud.class)
public class DebugHudClientMixin
{
	@Inject(at = @At("RETURN"), method = "getLeftText")
	protected void getLeftTextMixin(CallbackInfoReturnable<List<String>> cir)
	{
		PlayerEntity player = MinecraftClient.getInstance().player;
		BlockPos pos = player.getBlockPos();
		ChunkPos cp = new ChunkPos(pos);
		if(pos.getY() >= 0 && pos.getY() < 256 && player.world.isChunkLoaded(cp.x, cp.z))
		{
//			Optional<ICavernsComponent> component = Const.Component.CAVERNS_COMPONENT.maybeGet(player.world);
//			Optional<Biome> override = component.isPresent() ?
//				component.get().maybeGetBiomeOverride(pos) : Optional.empty();
			IBiomeOverride biomeInfo = (IBiomeOverride) player.world.getChunk(cp.x, cp.z);
			Optional<Biome> override = biomeInfo.maybeGetBiomeOverride(pos);
			if(override.isPresent())
			{
				List<String> list = cir.getReturnValue();
				list.add("Subterranean biome: " + Registry.BIOME.getId(override.get()));
			}
			else
			{
				List<String> list = cir.getReturnValue();
				list.add("Subterranean chunk init: " + biomeInfo.isInitialized());
			}
		}
	}
}
