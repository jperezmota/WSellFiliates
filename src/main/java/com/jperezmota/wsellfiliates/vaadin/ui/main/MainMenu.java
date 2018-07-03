package com.jperezmota.wsellfiliates.vaadin.ui.main;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.jperezmota.wsellfiliates.config.ApplicationProperties;
import com.jperezmota.wsellfiliates.services.UserSession;
import com.jperezmota.wsellfiliates.vaadin.views.LoginView;
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
	
	private Label title;
	private HorizontalLayout top;
	private Button showMenu;
	private MenuBar settings;
	
	CssLayout menuItemsLayout = new CssLayout();
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
		
		top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");

        showMenu = new Button("Menu", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                if (getStyleName().contains("valo-menu-visible")) {
                    removeStyleName("valo-menu-visible");
                } else {
                    addStyleName("valo-menu-visible");
                }
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(FontAwesome.LIST);
        
        title = new Label("<h3><strong>"+appName+"</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        
        settings = new MenuBar();
        settings.addStyleName("user-menu");
        final MenuBar.MenuItem settingsItem = settings.addItem(
                userSession.getUsername(),
                new ThemeResource("../tests-valo/img/profile-pic-300px.jpg"),
                null
        );
       
        
        settingsItem.addItem("Change Password", null);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", VaadinIcons.SIGN_OUT, e -> {
        		userSession.invalidateSessionData(); UI.getCurrent().getPage().setLocation("/login");
        	});

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        
        menuItems.put(LoginView.VIEW_NAME, "Todo View");
      Label label = null;
      int count = -1;
      for (final Map.Entry<String, String> item : menuItems.entrySet()) {
          count = 0;
          label = new Label("Other", ContentMode.HTML);
          label.setPrimaryStyleName("valo-menu-subtitle");
          label.addStyleName("h4");
          label.setSizeUndefined();
          menuItemsLayout.addComponent(label);
          final Button b = new Button(item.getValue(), e -> getUI().getNavigator().navigateTo(item.getKey()));
          b.setCaption(b.getCaption() + " <span class=\"valo-menu-badge\">123</span>");
          b.setHtmlContentAllowed(true);
          b.setPrimaryStyleName("valo-menu-item");
//          b.setIcon(new TestIcon(100).get());
          menuItemsLayout.addComponent(b);
          count++;
      }
      label.setValue(label.getValue() + " <span class=\"valo-menu-badge\">" + count + "</span>");
		
	}

	private void addComponentsToUI() {
		top.addComponent(title);
        top.setExpandRatio(title, 1);

		addComponent(top);
		addComponent(showMenu);
		addComponent(settings);
        addComponent(menuItemsLayout);
	}

}
