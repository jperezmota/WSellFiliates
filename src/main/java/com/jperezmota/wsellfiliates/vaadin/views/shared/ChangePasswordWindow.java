package com.jperezmota.wsellfiliates.vaadin.views.shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.Validator;

import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.User;
import com.jperezmota.wsellfiliates.services.AsignedCouponImplService;
import com.jperezmota.wsellfiliates.services.SecurityImplService;
import com.jperezmota.wsellfiliates.utilities.BeanUtil;
import com.jperezmota.wsellfiliates.utilities.SystemNotificationUtil;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ChangePasswordWindow extends Window{
	
	private String username;
	private SecurityImplService authService;
	
	private VerticalLayout windowLayout;
	private Label lblMandatoryFields;
	private PasswordField txtNewPassword;
	private PasswordField txtNewPasswordConfirmation;
	private Button btnChangePassword;
	private Button btnCancel;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	public ChangePasswordWindow(String username) {
		this.username = username;
		authService = BeanUtil.getBean(SecurityImplService.class);
		createInterface();
	}
	
	private void createInterface() {
		configureRootLayout();
		createComponents();
		addComponentsToUI();
	}
	
	private void configureRootLayout() {
		center();
		setModal(true);
		setResizable(false);
		setCaption("Chaing password to " + this.username);
	}
	
	private void createComponents() {
		windowLayout = new VerticalLayout();
		windowLayout.setMargin(true);
		
		lblMandatoryFields = new Label("Both fields are mandatories.");
		
		txtNewPassword = new PasswordField("New Password");
		txtNewPassword.setWidth("100%");
		
		txtNewPasswordConfirmation = new PasswordField("New Password Confirmation");
		txtNewPasswordConfirmation.setWidth("100%");
		
		btnChangePassword = new Button("Change Password");
		btnChangePassword.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btnChangePassword.setIcon(VaadinIcons.CHECK);
		btnChangePassword.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnChangePassword.setWidth("100%");
		btnChangePassword.addClickListener(event->proccessForm());
		
		btnCancel = new Button("Cancel");
		btnCancel.setIcon(VaadinIcons.CLOSE);
		btnCancel.setWidth("100%");	
		btnCancel.addClickListener(event -> close());
	}
	
	private void addComponentsToUI() {
		windowLayout.addComponents(lblMandatoryFields, txtNewPassword, txtNewPasswordConfirmation, btnChangePassword, btnCancel);
		setContent(windowLayout);
	}
	
	private void proccessForm() {
		try {
			authService.changeUserPassword(this.username, txtNewPassword.getValue(), txtNewPasswordConfirmation.getValue());
			SystemNotificationUtil.showSuccessfulOperationNotification("Password changed.");
			close();
		}catch(Exception ex) {
			SystemNotificationUtil.showExceptionNotification(ex.getMessage());
		}
	}

}
