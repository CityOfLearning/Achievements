package com.dyn.achievements.proxy;

import org.lwjgl.input.Keyboard;

import com.dyn.achievements.gui.Search;
import com.rabbit.gui.GuiFoundation;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
/**
 * Client class inherits from the proxy interface.
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class Client implements Proxy {

	/**
	 * Stores the key the user presses to activate the achievement menu.
	 */
	private KeyBinding achievementKey;

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

	/**
	 * Listens for when a key is pressed.
	 * <p>
	 * If the menu is already open just return.
	 * If the menu isn't open and the achievement key is pressed then open the menu.
	 * <p>
	 * @param event
	 */
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if ((Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
			return;
		}
		if (achievementKey.isPressed()) {
			GuiFoundation.proxy.display(new Search());
			//GuiFoundation.proxy.display(new AchMap(AchievementsMod.sm));
		}
	}

	/**
	 * Initializes the achievement key and registers it.
	 */
	@Override
	public void init() {

		FMLCommonHandler.instance().bus().register(this);

		achievementKey = new KeyBinding("key.toggle.achievementui", Keyboard.KEY_N, "key.categories.toggle");

		ClientRegistry.registerKeyBinding(achievementKey);
	}
}