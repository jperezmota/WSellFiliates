package com.jperezmota.wsellfiliates.vaadin.views.shared;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class ConfirmationWindow extends Window{
	
	private VerticalLayout contentLayout;
	private String message;
	private String cancelMessage;
	private String proceedMessage;
	private Label lblMessage;
	private Button btnCancel;
	private Button btnProceed;
	
	private boolean confirmed = false;
	
	public ConfirmationWindow(String message, String cancelMessage, String proceedMessage){
		this.message = message;
		this.cancelMessage = cancelMessage;
		this.proceedMessage = proceedMessage;
		createInterface();
	}
	
	private void createInterface() {
		configureRootLayout();
		createComponents();
		addComponentsToUI();
	}
	
	private void configureRootLayout() {
		setModal(true);
		center();
		setResizable(false);
		setClosable(false);
	}
	
	private void createComponents() {
		contentLayout = new VerticalLayout();
		contentLayout.setMargin(true);
		
		lblMessage = new Label("<strong>"+message+"</strong>");
		lblMessage.setContentMode(ContentMode.HTML);
		
		btnCancel = new Button(cancelMessage);
		btnCancel.setWidth("100%");
		btnCancel.setIcon(VaadinIcons.CLOSE);
		btnCancel.addClickListener( event-> close() );
		
		btnProceed = new Button(proceedMessage);
		btnProceed.setWidth("100%");
		btnProceed.setIcon(VaadinIcons.CHECK);
		btnProceed.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnProceed.addClickListener(event -> {
			confirmed = true; 
			close();
			});
	}

	private void addComponentsToUI() {
		contentLayout.addComponents(lblMessage, btnCancel, btnProceed);
		setContent(contentLayout);
	}

	public boolean isConfirmed() {
		return confirmed;
	}
	
}
