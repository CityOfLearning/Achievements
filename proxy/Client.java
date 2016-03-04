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

public class Client implements Proxy {

	private KeyBinding achievementKey;

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}

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

	@Override
	public void init() {

		FMLCommonHandler.instance().bus().register(this);

		achievementKey = new KeyBinding("key.toggle.achievementui", Keyboard.KEY_N, "key.categories.toggle");

		ClientRegistry.registerKeyBinding(achievementKey);
	}
}