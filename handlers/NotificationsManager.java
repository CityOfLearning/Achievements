package com.dyn.achievements.handlers;

import java.util.ArrayList;
import java.util.List;

import com.dyn.achievements.gui.RequirementNotification;

import net.minecraft.client.Minecraft;

public class NotificationsManager {
	private final List<RequirementNotification> notificationList = new ArrayList<RequirementNotification>();

	public NotificationsManager() {

	}

	public void addNotification(String title, String subTitle) {
		notificationList.add(new RequirementNotification(Minecraft.getMinecraft(), title, subTitle));
	}

	public void removeNotification(RequirementNotification notification) {
		notificationList.remove(notification);
	}

	public void renderNotifications() {
		for (int i = 0; i < notificationList.size(); i++) {
			notificationList.get(i).drawNotification(i);
		}
	}
}
