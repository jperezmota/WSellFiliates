package com.jperezmota.wsellfiliates.vaadin.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jperezmota.wsellfiliates.entity.wordpress.CouponSell;
import com.jperezmota.wsellfiliates.services.WordpressServiceImpl;
import com.jperezmota.wsellfiliates.utilities.SystemNotificationUtil;
import com.jperezmota.wsellfiliates.utilities.UserSession;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.components.grid.HeaderCell;
import com.vaadin.ui.components.grid.HeaderRow;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = DashboardView.VIEW_NAME)
public class DashboardView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "";
	
	@Autowired
	private WordpressServiceImpl wordpressService;
	@Autowired
	private UserSession userSession;
	
	private Label lblView;
	
	private CssLayout filter;
	private DateField txtInitialDate;
	private DateField txtFinalDate;
	private Button btnSearch;
	
	private List<CouponSell> couponsSells;
	private ListDataProvider<CouponSell> couponSellListDataProvider;
	private Label lblSellsGrid;
	private Grid<CouponSell> sellsGrid;

	@Override
	public void enter(ViewChangeEvent event) {
		createInterface();
		searchSalesByFilter();
    }
	
	private void createInterface() {
		configureRootUI();
		createComponents();
		addComponentsToUI();
	}
	
	private void configureRootUI() {
		setSizeFull();
	}
	
	private void createComponents() {
		couponsSells = new ArrayList<CouponSell>();
		
		lblView = new Label("Dashboard");
		lblView.addStyleName(ValoTheme.LABEL_H1);
		
		lblSellsGrid = new Label("Sales made by your Promo Code");
		lblSellsGrid.addStyleName(ValoTheme.LABEL_H3);
		
		filter = createFilter();
		sellsGrid = createGrid();
	}
	
	private void addComponentsToUI() {
		addComponent(lblView);
		addComponent(lblSellsGrid);
		addComponent(filter);
		addComponent(sellsGrid);
		setExpandRatio(sellsGrid, 1.0f);
	}
	
	private CssLayout createFilter() {
		LocalDate finalDate = LocalDate.now();
		LocalDate initialDate = LocalDate.of(finalDate.getYear(), finalDate.getMonthValue(), 1);
		
		CssLayout cssLayout = new CssLayout();
		txtInitialDate = new DateField();
		txtInitialDate.setPlaceholder("Initial Date");
		txtInitialDate.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtInitialDate.addStyleName("search-filter-elements");
		txtInitialDate.setValue(initialDate);
		
		txtFinalDate = new DateField();
		txtFinalDate.setPlaceholder("Final Date");
		txtFinalDate.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtFinalDate.addStyleName("search-filter-elements");
		txtFinalDate.setValue(finalDate);
		
		btnSearch = new Button("Search");
		btnSearch.addStyleName(ValoTheme.BUTTON_SMALL);
		btnSearch.addStyleName("search-filter-elements");
		
		btnSearch.addStyleName(ValoTheme.BUTTON_PRIMARY);
		btnSearch.setIcon(VaadinIcons.SEARCH);
		btnSearch.addClickListener(event -> searchSalesByFilter());
		
		cssLayout.addComponents(txtInitialDate, txtFinalDate, btnSearch);
	
		return cssLayout;
	}
	
	private void searchSalesByFilter() {
		try {
			validateFilterData();
			executeFilterSearch();
		}catch (Exception e) {
			SystemNotificationUtil.showExceptionNotification(e.getMessage());
		}
	}
	
	private void validateFilterData() {
		boolean validationHasErrors = false;
		String validationErrorsMessage = "\n\n";
		
		if(txtInitialDate.getValue() == null || txtFinalDate.getValue() == null) {
			validationHasErrors = true;
			validationErrorsMessage = "- Both date fields are mandatories. \n";
		}else {
			if(txtInitialDate.getValue().isAfter(txtFinalDate.getValue())){
				validationHasErrors = true;
				validationErrorsMessage = "- Initial Date field must have a value lower or equal than Final Date. \n";
			}
		}
		
		if(validationHasErrors) {
			throw new RuntimeException(validationErrorsMessage);
		}
	}
	
	private void executeFilterSearch() {
		LocalDate initialDate = txtInitialDate.getValue();
		LocalDate finalDate = txtFinalDate.getValue();
		
		String userCoupon = userSession.getCoupon();
		List<CouponSell> salesFound = wordpressService.getSellsByCoupon(userCoupon, initialDate, finalDate);
		if(salesFound.size() <= 0) {
			Notification.show("Sorry we haven't found sales by your Promo Code in this period of time :(.").setDelayMsec(Notification.DELAY_FOREVER);
		}
		
		couponsSells.clear();
		this.couponsSells.addAll(salesFound);
		
		couponSellListDataProvider.refreshAll();
	}

	private Grid createGrid() {
		Grid<CouponSell> sellsGrid = new Grid<CouponSell>();
		sellsGrid.setSizeFull();
		
		
		sellsGrid.addColumn(CouponSell::getOrderId).setId("Order Id").setCaption("Order Id");
		sellsGrid.addColumn(CouponSell::getPaidDate).setId("Date").setCaption("Date");
		sellsGrid.addColumn(CouponSell::getBillingCountry).setId("Country").setCaption("Country");
		sellsGrid.addColumn(CouponSell::getBillingCity).setId("City").setCaption("City");
		sellsGrid.addColumn(CouponSell::getBillingState).setId("State").setCaption("State");
		sellsGrid.addColumn(CouponSell::getOrderTotal).setId("Order Total").setCaption("Order Total").setStyleGenerator(item -> "v-align-right");
		
		FooterRow footerRow = sellsGrid.appendFooterRow();
		footerRow.getCell("State").setHtml("<strong>Total:</strong> ");
		footerRow.getCell("Order Total").setStyleName("total-footer-grid");
		couponSellListDataProvider = DataProvider.ofCollection(couponsSells);
		sellsGrid.setDataProvider(couponSellListDataProvider);
		couponSellListDataProvider.addDataProviderListener(event -> setTotalFooter(footerRow));
	
		
		return sellsGrid;
	}
	
	private void setTotalFooter(FooterRow footerRow) {
		footerRow.getCell("Order Total").setHtml(calculateTotalInSales());
	}
	
	private String calculateTotalInSales() {
		double totalInSales = couponSellListDataProvider.fetch(new Query<>()).mapToDouble(CouponSell::getOrderTotal).sum();
		return "<b> $ " + totalInSales + "</b>";
	}

}
