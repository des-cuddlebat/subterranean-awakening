package net.cuddlebat.terrawa.inventory;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.client.gui.ModBeltScreen;
import net.cuddlebat.terrawa.client.gui.ModEnchScreen;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.container.BlockContext;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public abstract class ModContainers
{
	public static final Identifier ENCH_GUI_ID = new Identifier(Const.MODID, "ench_gui"); 
	public static final Identifier BELT_GUI_ID = new Identifier(Const.MODID, "belt_gui"); 
	
	public static void doRegister()
	{
		ContainerProviderRegistry.INSTANCE.registerFactory(ENCH_GUI_ID, (syncId, id, player, buf)
			-> new ModEnchContainer(syncId, player.inventory,
				BlockContext.create(player.world, buf.readBlockPos())));
		ScreenProviderRegistry.INSTANCE.registerFactory(ENCH_GUI_ID, (syncId, id, player, buf)
			-> new ModEnchScreen(new ModEnchContainer(syncId, player.inventory,
				BlockContext.create(player.world, buf.readBlockPos())),
			player.inventory, new LiteralText("Magic happens here")));

		ContainerProviderRegistry.INSTANCE.registerFactory(BELT_GUI_ID, (syncId, id, player, buf)
			-> new ModBeltContainer(syncId, player.inventory));
		ScreenProviderRegistry.INSTANCE.registerFactory(BELT_GUI_ID, (syncId, id, player, buf)
			-> new ModBeltScreen(new ModBeltContainer(syncId, player.inventory), player.inventory,
				new LiteralText("Potion belt")));
	}
}
