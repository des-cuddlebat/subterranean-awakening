package net.cuddlebat.terrawa.input;

import org.lwjgl.glfw.GLFW;

import dev.emi.trinkets.api.TrinketsApi;
import io.netty.buffer.Unpooled;
import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.item.ModInventoryBelt;
import net.cuddlebat.terrawa.network.ModPackets;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.PacketByteBuf;

public class KeyBindings
{
	public static final FabricKeyBinding BELT = FabricKeyBinding.Builder.create(
		new Identifier(Const.MODID, "belt"),
		InputUtil.Type.KEYSYM,
		GLFW.GLFW_KEY_B,
		"Subterranean Awakening")
		.build();
	
	public static void doRegister()
	{
		KeyBindingRegistry.INSTANCE.register(BELT);
		
		ClientTickCallback.EVENT.register(e ->
		{
		    if(BELT.wasPressed())
		    {
		    	ItemStack hand = e.player.getMainHandStack();
		    	ItemStack belt = TrinketsApi.getTrinketComponent(e.player).getStack(Const.TrinketSlot.BELT);
		    	if(!belt.isEmpty() && belt.getItem() instanceof ModInventoryBelt)
		    	{
		    		ModInventoryBelt beltItem = (ModInventoryBelt)belt.getItem();
		    		PacketByteBuf empty = new PacketByteBuf(Unpooled.buffer()); 
		    		if(!hand.isEmpty() && beltItem.canGoIn(hand))
		    		{
		    			ClientSidePacketRegistry.INSTANCE.sendToServer(ModPackets.CYCLE_BELT, empty);
		    		}
		    		else
		    		{
		    			ClientSidePacketRegistry.INSTANCE.sendToServer(ModPackets.OPEN_BELT, empty);
		    		}
		    	}
		    }
		});
	}
}
