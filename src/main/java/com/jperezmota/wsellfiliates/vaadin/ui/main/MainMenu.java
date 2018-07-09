package com.jperezmota.wsellfiliates.vaadin.ui.main;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.jperezmota.wsellfiliates.utilities.ApplicationProperties;
import com.jperezmota.wsellfiliates.utilities.UserSession;
import com.jperezmota.wsellfiliates.vaadin.views.DashboardView;
import com.jperezmota.wsellfiliates.vaadin.views.TrackPromoCodeView;
import com.jperezmota.wsellfiliates.vaadin.views.affiliates.AffiliatesView;
import com.jperezmota.wsellfiliates.vaadin.views.shared.ChangePasswordWindow;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class MainMenu extends CssLayout{
	
	private String appName;
	private UserSession userSession;

	private HorizontalLayout topBar;
	private Button btnShowMenu;
	private MenuBar userSettingMenu;
	CssLayout mainMenuOptions;
	
	public final LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>();
	
	public MainMenu(String appName, UserSession userSession) {	
		this.appName = appName;
		this.userSession = userSession;
		
		createInterface();
	}
	
	private void createInterface() {
		configureRootUI();
		createUIComponents();
		addComponentsToUI();
	}
	
	private void configureRootUI() {
		setId("testMenu");
		addStyleName("valo-menu-part");		
	}
	
	private void createUIComponents() {
		topBar = createTopBar();
		btnShowMenu = createShowMenuButton();
        userSettingMenu = createUserSettingMenu();
        mainMenuOptions = createMainMenuOptions();
	}
	
	private void addComponentsToUI() {
		addComponent(topBar);
		addComponent(btnShowMenu);
		addComponent(userSettingMenu);
        addComponent(mainMenuOptions);
	}

	private HorizontalLayout createTopBar() {
		Label lblAppName = new Label("<h3><strong>"+appName+"</strong></h3>", ContentMode.HTML);
		lblAppName.setSizeUndefined();
		HorizontalLayout topBarLayout = new HorizontalLayout();
		
		topBarLayout.setWidth("100%");
		topBarLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		topBarLayout.addStyleName("valo-menu-title");
   
		topBarLayout.addComponent(lblAppName);
		topBarLayout.setExpandRatio(lblAppName, 1);
        
        return topBarLayout;
	}
	
	private Button createShowMenuButton() {
		 Button btnShowMenu = new Button("Menu", new Button.ClickListener() {
	            @Override
	            public void buttonClick(final Button.ClickEvent event) {
	                if (getStyleName().contains("valo-menu-visible")) {
	                    removeStyleName("valo-menu-visible");
	                } else {
	                    addStyleName("valo-menu-visible");
	                }
	            }
	        });
       btnShowMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
       btnShowMenu.addStyleName(ValoTheme.BUTTON_SMALL);
       btnShowMenu.addStyleName("valo-menu-toggle");
       btnShowMenu.setIcon(FontAwesome.LIST);
       
       return btnShowMenu;
	}
	
	private MenuBar createUserSettingMenu() {
		MenuBar userSettingMenu = new MenuBar();
        userSettingMenu.addStyleName("user-menu");
        final MenuBar.MenuItem settingsItem = userSettingMenu.addItem(
                userSession.getUsername(),
                new ThemeResource("img/profile-pic-300px.jpg"),
                null
        );
        
        settingsItem.addItem("Change Password", VaadinIcons.LOCK, e -> openChangePasswordWindow());
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", VaadinIcons.SIGN_OUT, e -> {
        		userSession.invalidateSessionData(); UI.getCurrent().getPage().setLocation("/login");
        	});
        
        return userSettingMenu;
	}
	
	private CssLayout createMainMenuOptions() {
		CssLayout mainMenuOptionsLayout = new CssLayout();
		mainMenuOptionsLayout.setPrimaryStyleName("valo-menuitems");
		
        menuItems.put(DashboardView.VIEW_NAME, "Dashboard");
        menuItems.put(TrackPromoCodeView.VIEW_NAME, "Track Promo Code");
        menuItems.put(AffiliatesView.VIEW_NAME, "Affiliates");

        for (final Map.Entry<String, String> item : menuItems.entrySet()) {
          String viewNameToOpen = item.getKey();
          String menuOptionText = item.getValue();
          final Button b = new Button(menuOptionText, e -> getUI().getNavigator().navigateTo(viewNameToOpen));
          b.setPrimaryStyleName("valo-menu-item");
          mainMenuOptionsLayout.addComponent(b);
          
          if(menuOptionText.equals("Dashboard")) {
        	    Label lblAdminSeparator = new Label("Administration", ContentMode.HTML);
        	    lblAdminSeparator.setPrimaryStyleName("valo-menu-subtitle");
        	    lblAdminSeparator.addStyleName("h4");
        	    lblAdminSeparator.setSizeUndefined();
  	        mainMenuOptionsLayout.addComponent(lblAdminSeparator);
          }
          
        }
        
        return mainMenuOptionsLayout;
	}
	
	private void openChangePasswordWindow() {
		ChangePasswordWindow changePasswordWindow = new ChangePasswordWindow(userSession.getUsername());
		UI.getCurrent().addWindow(changePasswordWindow);
	}

}
