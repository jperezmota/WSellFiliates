package com.jperezmota.wsellfiliates.utilities;

import com.vaadin.ui.Notification;

public class SystemNotificationUtil {
	public static void showExceptionNotification(String message) {
		Notification.show("System Message: " + message, Notification.Type.ERROR_MESSAGE);
	}
	
	public static void showSuccessfulOperationNotification(String message) {
		Notification.show("Congratulation! " + message).setDelayMsec(2000);
	}
}
