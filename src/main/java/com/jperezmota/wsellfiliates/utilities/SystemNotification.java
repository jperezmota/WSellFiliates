package com.jperezmota.wsellfiliates.utilities;

import com.vaadin.ui.Notification;

public class SystemNotification {
	public static void showExceptionNotification(String message) {
		Notification.show("System Message: " + message, Notification.Type.ERROR_MESSAGE);
	}
}
