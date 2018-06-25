package com.jperezmota.wsellfiliates.vaadin.main_ui;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.themes.ValoTheme;

public class MainMenu extends CssLayout implements ViewChangeListener{
	
	@Autowired
    private SpringViewProvider viewProvider;
	private Navigator navigator;
	private ValoThemeUI ui;
	private ComponentContainer viewDisplayArea;
	
	CssLayout menuItemsLayout = new CssLayout();
	private final LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>();
	
	public MainMenu(ValoThemeUI ui, ComponentContainer viewDisplayArea) {
		
		this.navigator = new Navigator(ui, viewDisplayArea);
		
		this.ui = ui;
		this.viewDisplayArea = viewDisplayArea;
		
		navigator.addViewChangeListener(this);
		
		setId("testMenu");
		
		addStyleName("valo-menu-part");

        final HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");
        addComponent(top);

        final Button showMenu = new Button("Menu", new Button.ClickListener() {
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
        addComponent(showMenu);

        final Label title = new Label("<h3>Vaadin <strong>Valo Theme</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);

        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        final MenuBar.MenuItem settingsItem = settings.addItem(
                "Michal_Hajek",
                new ThemeResource("../tests-valo/img/profile-pic-300px.jpg"),
                null
        );
        settingsItem.addItem("Edit Profile", null);
        settingsItem.addItem("Preferences", null);
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", FontAwesome.SIGN_OUT, e -> navigator.navigateTo(LoginView.VIEW_NAME));
        addComponent(settings);

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        addComponent(menuItemsLayout);
	}
	
	@Override
    public boolean beforeViewChange(ViewChangeEvent event) {
        return true;
    }

    @Override
    public void afterViewChange(ViewChangeEvent event) {

        for (final Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
            it.next().removeStyleName("selected");
        }
        for (final Map.Entry<String, String> item : menuItems.entrySet()) {
            if (event.getViewName().equals(item.getKey())) {
                for (final Iterator<Component> it = menuItemsLayout.iterator(); it.hasNext();) {
                    final Component c = it.next();
                    if (c.getCaption() != null && c.getCaption().startsWith(item.getValue())) {
                        c.addStyleName("selected");
                        break;
                    }
                }
                break;
            }
        }
        removeStyleName("valo-menu-visible");

    }
    
    private void configureNavigator() {
		navigator = new Navigator(ui, viewDisplayArea);
	    navigator.addProvider(viewProvider);
	    navigator.setErrorView(DefaultView.class);
	}   

}
