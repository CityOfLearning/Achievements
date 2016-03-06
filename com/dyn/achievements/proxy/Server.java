package com.dyn.achievements.proxy;

import com.dyn.achievements.handlers.EventHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;
/**
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class Server implements Proxy {

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}
	/**
	 * Initializes the event handler and registers it with the bus
	 */
	@Override
	public void init() {
		EventHandler eH = new EventHandler();
		
		FMLCommonHandler.instance().bus().register(eH);
		
		MinecraftForge.EVENT_BUS.register(eH);
	}

}