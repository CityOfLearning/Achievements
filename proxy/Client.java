package com.dyn.achievements.proxy;

import org.lwjgl.input.Keyboard;

import com.dyn.achievements.gui.DynOverlay;
import com.dyn.achievements.gui.Search;
import com.dyn.achievements.handlers.NotificationsManager;
import com.rabbit.gui.GuiFoundation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Client implements Proxy {

	private KeyBinding achievementKey;
	private KeyBinding hideGuiKey;

	private boolean hideGui = false;

	private final NotificationsManager notificationsManager = new NotificationsManager();
	private final DynOverlay achOverlay = new DynOverlay();

	@Override
	public NotificationsManager getNotificationsManager() {
		return notificationsManager;
	}

	@Override
	public void init() {

		FMLCommonHandler.instance().bus().register(this);

		achievementKey = new KeyBinding("key.toggle.achievementui", Keyboard.KEY_N, "key.categories.toggle");
		hideGuiKey = new KeyBinding("key.toggle.achievementgui", Keyboard.KEY_H, "key.categories.toggle");

		ClientRegistry.registerKeyBinding(hideGuiKey);
		ClientRegistry.registerKeyBinding(achievementKey);
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {

		if ((Minecraft.getMinecraft().currentScreen instanceof GuiChat)) {
			return;
		}
		if (achievementKey.isPressed()) {
			GuiFoundation.proxy.display(new Search());
		}

		if (hideGuiKey.isPressed()) {
			hideGui = !hideGui;
		}
	}

	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
		if (Minecraft.getMinecraft().inGameHasFocus && !hideGui) {
			achOverlay.drawOverlay();
		}
		if (!(Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu)) {
			notificationsManager.renderNotifications();
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