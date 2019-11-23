package net.cuddlebat.terrawa;

import net.cuddlebat.terrawa.client.render.EntityRenderers;
import net.cuddlebat.terrawa.input.KeyBindings;
import net.fabricmc.api.ClientModInitializer;

public class ClientMod implements ClientModInitializer {
	@Override
	public void onInitializeClient()
	{
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		EntityRenderers.doRegister();
		KeyBindings.doRegister();
	}
}
