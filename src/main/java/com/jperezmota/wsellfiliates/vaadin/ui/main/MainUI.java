package com.jperezmota.wsellfiliates.vaadin.ui.main;


import com.jperezmota.wsellfiliates.services.UserSession;
import com.jperezmota.wsellfiliates.vaadin.views.DefaultView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.*;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@SpringUI(path = "/")
@PreserveOnRefresh
@Theme("wsellfiliates")
@Title("Demo Valo Theme")
public class MainUI extends UI implements ViewChangeListener{

	@Autowired
    private SpringViewProvider viewProvider;
	private Navigator navigator;
	
	private MainMenu mainMenu;
    private ValoLayout rootLayout;
    private CssLayout menuItemsLayout = new CssLayout();
    private CssLayout menu;
    
    @Override
    protected void init(VaadinRequest request) {
    		createInterface();
    		configureNavigator();
    }
    
    private void createInterface() {
    		configureRootUI();
		createUIComponents();
		addComponentsToUI();
    }

    private void configureRootUI() {
		Responsive.makeResponsive(this);
	    addStyleName(ValoTheme.UI_WITH_MENU);
	}
    
    private void createUIComponents() {
    	 	createMainLayout();
    	 	createMainMenu();
    }
    
    private void createMainLayout() {
    		rootLayout = new ValoLayout();
		rootLayout.setWidth("100%");		
    }
    
    private void createMainMenu() {
    		mainMenu = new MainMenu();
		rootLayout.addMenu(mainMenu);	
    }
    
    private void addComponentsToUI() {
    		setContent(rootLayout);
    }
    
    private void configureNavigator() {
    		ComponentContainer viewDisplayArea = rootLayout.getContentContainer();
		navigator = new Navigator(this, viewDisplayArea);
	    navigator.addProvider(this.viewProvider);
	    navigator.setErrorView(DefaultView.class);
	    navigator.addViewChangeListener(this);
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
        for (final Map.Entry<String, String> item : this.mainMenu.menuItems.entrySet()) {
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
   
}