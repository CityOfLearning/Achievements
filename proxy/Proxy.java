package com.dyn.achievements.proxy;

import com.dyn.achievements.handlers.NotificationsManager;

public interface Proxy {
	public NotificationsManager getNotificationsManager();

	public void init();

	public void renderGUI();
}