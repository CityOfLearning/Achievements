package com.dyn.achievements.proxy;

public class Client implements Proxy {

	@Override
	public void init() {

	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Render GUI when on call from client
	}
}