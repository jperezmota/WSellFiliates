package com.jperezmota.wsellfiliates.vaadin.views.affiliates;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.Validator;

import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.User;
import com.jperezmota.wsellfiliates.services.AsignedCouponServiceImpl;
import com.jperezmota.wsellfiliates.services.SecurityServiceImpl;
import com.jperezmota.wsellfiliates.services.WordpressServiceImpl;
import com.jperezmota.wsellfiliates.utilities.BeanUtil;
import com.jperezmota.wsellfiliates.utilities.SystemNotificationUtil;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@Scope(value="prototype")
public class CreateAffiliateWindow extends Window{
	
	private VerticalLayout windowLayout;
	private Label lblMandatoryFields;
	private TextField txtAffiliateUsername;
	private PasswordField txtPassword;
	private TextField txtCoupon;
	private Button btnCancel;
	private Button btnCreate;
	
	private AsignedCoupon asignedCoupon;
	private AsignedCouponServiceImpl asignedCouponService;
	
	public CreateAffiliateWindow() {
		this.asignedCouponService = BeanUtil.getBean(AsignedCouponServiceImpl.class);
		createInterface();
	}
	
	private void createInterface() {
		configureRootLayout();
		createComponents();
		addComponentsToUI();
	}
	
	private void configureRootLayout() {
		Responsive.makeResponsive(this);
		addStyleName("create-affiliate-window");
		center();
		setModal(true);
		setResizable(false);
		setCaption("Creating an Affiliate");
	}
	
	private void createComponents() {
		windowLayout = new VerticalLayout();
		windowLayout.setMargin(true);
		
		lblMandatoryFields = new Label("All fields are mandatories.");
		
		txtAffiliateUsername = new TextField("Affiliate Username");
		txtAffiliateUsername.setWidth("100%");
		
		txtPassword = new PasswordField("Password");
		txtPassword.setWidth("100%");
		
		
		txtCoupon = new TextField("Asigned Coupon");
		txtCoupon.setWidth("100%");
		
		btnCreate = new Button("Create");
		btnCreate.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		btnCreate.setIcon(VaadinIcons.CHECK);
		btnCreate.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnCreate.setWidth("100%");
		btnCreate.addClickListener(event->proccessForm());
		
		btnCancel = new Button("Cancel");
		btnCancel.setIcon(VaadinIcons.CLOSE);
		btnCancel.setWidth("100%");	
		btnCancel.addClickListener(event -> close());
	}
	
	private void addComponentsToUI() {
		windowLayout.addComponents(lblMandatoryFields, txtAffiliateUsername, txtPassword, txtCoupon, btnCreate, btnCancel);
		setContent(windowLayout);
	}
	
	private void proccessForm() {
		try {
			AsignedCoupon asignedCoupon = bindFormDataToAsignedCoupon();
			this.asignedCoupon = asignedCouponService.createAsignedCoupon(asignedCoupon);
			SystemNotificationUtil.showSuccessfulOperationNotification("Affiliate created.");
			close();
		}catch(Exception ex) {
			SystemNotificationUtil.showExceptionNotification(ex.getMessage());
		}
	}
	
	private AsignedCoupon bindFormDataToAsignedCoupon() {
		String username = txtAffiliateUsername.getValue();
		String password = txtPassword.getValue();
		String coupon = txtCoupon.getValue();
		
		HashFunction hashFunction = Hashing.sha256();
		String hashedPassword = hashFunction.hashString(password, Charsets.UTF_8).toString();
		
		User newUser = new User(username, hashedPassword, true);
		AsignedCoupon asignedCoupon = new AsignedCoupon(coupon, newUser);
		
		return asignedCoupon;
	}
	
	public AsignedCoupon getAsignedCoupon() {
		return asignedCoupon;
	}

}
