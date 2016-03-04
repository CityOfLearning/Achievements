package com.dyn.achievements.proxy;

import com.dyn.achievements.handlers.EventHandler;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

public class Server implements Proxy {

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}

	@Override
	public void init() {
		EventHandler eH = new EventHandler();
		
		FMLCommonHandler.instance().bus().register(eH);
		
		MinecraftForge.EVENT_BUS.register(eH);
	}

}