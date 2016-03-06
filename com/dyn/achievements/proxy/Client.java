package com.dyn.achievements.proxy;

import org.lwjgl.input.Keyboard;

import com.dyn.achievements.gui.Search;
import com.rabbit.gui.GuiFoundation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

public class Client implements Proxy {

	private KeyBinding achievementKey;

	@Override
	public void init() {

		FMLCommonHandler.instance().bus().register(this);

		this.achievementKey = new KeyBinding("key.toggle.achievementui", Keyboard.KEY_N, "key.categories.toggle");

		ClientRegistry.registerKeyBinding(this.achievementKey);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if ((Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
			return;
		}
		if (this.achievementKey.isPressed()) {
			GuiFoundation.proxy.display(new Search());
			// GuiFoundation.proxy.display(new AchMap(AchievementsMod.sm));
		}
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}
}