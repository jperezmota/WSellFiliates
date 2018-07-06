package com.jperezmota.wsellfiliates.vaadin.views.affiliates;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.User;
import com.jperezmota.wsellfiliates.services.AsignedCouponImplService;
import com.jperezmota.wsellfiliates.utilities.SystemNotificationUtil;
import com.jperezmota.wsellfiliates.utilities.UserSession;
import com.jperezmota.wsellfiliates.vaadin.views.shared.ChangePasswordWindow;
import com.jperezmota.wsellfiliates.vaadin.views.shared.ConfirmationWindow;
import com.vaadin.data.ValueProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = AffiliatesView.VIEW_NAME)
public class AffiliatesView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "affiliates";
	
	@Autowired
	private AsignedCouponImplService asignedCouponService;
	@Autowired
	private UserSession userSession;
	
	private Label lblView;
	private Button btnCreateAffiliate;
	private List<AsignedCoupon> asignedCoupons;
	private Grid<AsignedCoupon> grid;
	
	private CreateAffiliateWindow createAffiliateWindow;
	private ChangePasswordWindow changePasswordWindow;
	
	@Override
	public void enter(ViewChangeEvent event) {
		createInterface();
    }
	
	public void createInterface() {
		configureRootUI();
		createComponents();
		addComponentesToUI();
	}
	
	private void configureRootUI() {
		setSizeFull();
	}
	
	private void createComponents() {
		lblView = new Label("Affiliates");
		lblView.addStyleName(ValoTheme.LABEL_H1);
		
		asignedCoupons = new ArrayList<AsignedCoupon>();
		asignedCoupons = asignedCouponService.getAllAsignedCoupons();
		grid = new Grid<AsignedCoupon>();
		grid.setSizeFull();
		ListDataProvider<AsignedCoupon> asignedCouponDataProvider = DataProvider.ofCollection(asignedCoupons);
		grid.setDataProvider(asignedCouponDataProvider);
		grid.addColumn(AsignedCoupon::getUsernameOfAsignedTo).setCaption("Affiliate");
		grid.addColumn(AsignedCoupon::getCoupon).setCaption("Coupon");
		grid.addComponentColumn( asignedCoupon -> createDeleteButton(asignedCoupon) ).setWidth(60);
		grid.addComponentColumn( asignedCoupon -> createChangePasswordButton(asignedCoupon.getUsernameOfAsignedTo()) ).setWidth(60);

		btnCreateAffiliate = new Button("Create");
		btnCreateAffiliate.setDescription("Create a new Affiliate");
		btnCreateAffiliate.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnCreateAffiliate.setIcon(VaadinIcons.PLUS);
		btnCreateAffiliate.addClickListener(event ->{
			openCreateAffiliateWindow();
		});
		
	}

	private void addComponentesToUI() {
		addComponent(lblView);
		addComponent(btnCreateAffiliate);
		addComponent(grid);
		setExpandRatio(grid, 1.0f);
	}
	
	private void addNewAsignedCouponToGrid(AsignedCoupon newAsignedCoupon) {
		asignedCoupons.add(newAsignedCoupon);
		regreshGrid();
	}
	
	private void regreshGrid() {
		grid.getDataProvider().refreshAll();
	}
	
	private Button createDeleteButton(AsignedCoupon asignedCoupon) {
		 Button btnDelete = new Button();
		 btnDelete.setDescription("Delete");
		 btnDelete.setIcon(VaadinIcons.TRASH);
		 btnDelete.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		 btnDelete.addStyleName(ValoTheme.BUTTON_TINY);
		 btnDelete.addClickListener( event -> openConfirmationWindow(asignedCoupon) );
		 
	     return btnDelete;
	}
	
	private Button createChangePasswordButton(String username) {
		Button btnChangePassword = new Button();
		btnChangePassword.setDescription("Change Password");
		btnChangePassword.setIcon(VaadinIcons.LOCK);
		btnChangePassword.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnChangePassword.addStyleName(ValoTheme.BUTTON_TINY);
		btnChangePassword.addClickListener( event -> openChangePasswordWindow(username) );
		
	    return btnChangePassword;
	}
	
	private void openCreateAffiliateWindow() {
		createAffiliateWindow = new CreateAffiliateWindow();
		UI.getCurrent().addWindow(createAffiliateWindow);
		createAffiliateWindow.addCloseListener(event -> {
			boolean asignedCouponCreated = createAffiliateWindow.getAsignedCoupon() != null;
			if(asignedCouponCreated) {
				addNewAsignedCouponToGrid(createAffiliateWindow.getAsignedCoupon());
			}
		});
	}
	
	private void openChangePasswordWindow(String username) {
		changePasswordWindow = new ChangePasswordWindow(username);
		UI.getCurrent().addWindow(changePasswordWindow);
	}
	
	private void openConfirmationWindow(AsignedCoupon asignedCoupon) {
		ConfirmationWindow confirmationWindow = new ConfirmationWindow("This deletion will be permanent. You still want to proceed?", "Cancel", "Yes, Delete Affiliate");
		confirmationWindow.addCloseListener( event-> {
			if(confirmationWindow.isConfirmed()) {
				deleteAsignedCoupon(asignedCoupon);
			}
		});
		UI.getCurrent().addWindow(confirmationWindow);
	}
	private void deleteAsignedCoupon(AsignedCoupon asignedCoupon) {
		try {
			asignedCouponService.deleteAsignedCoupon(asignedCoupon, userSession.getUsername());
			asignedCoupons.remove(asignedCoupon);
			regreshGrid();
		}catch(Exception ex) {
			SystemNotificationUtil.showExceptionNotification(ex.getMessage());
		}	
	}
	

}
