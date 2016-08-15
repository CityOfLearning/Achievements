package com.dyn.achievements.proxy;

import com.dyn.achievements.handlers.EventHandler;

import net.minecraftforge.common.MinecraftForge;

public class Server implements Proxy {

	@Override
	public void init() {
		EventHandler eH = new EventHandler();

		MinecraftForge.EVENT_BUS.register(eH);
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}

}