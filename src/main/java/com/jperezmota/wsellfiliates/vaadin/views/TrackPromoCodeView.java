package com.jperezmota.wsellfiliates.vaadin.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jperezmota.wsellfiliates.entity.AsignedCoupon;
import com.jperezmota.wsellfiliates.entity.wordpress.CouponSale;
import com.jperezmota.wsellfiliates.services.AsignedCouponServiceImpl;
import com.jperezmota.wsellfiliates.services.WordpressServiceImpl;
import com.jperezmota.wsellfiliates.utilities.SystemNotificationUtil;
import com.jperezmota.wsellfiliates.utilities.UserSession;
import com.jperezmota.wsellfiliates.vaadin.views.affiliates.AffiliatesView;
import com.jperezmota.wsellfiliates.vaadin.views.affiliates.CreateAffiliateWindow;
import com.jperezmota.wsellfiliates.vaadin.views.shared.ChangePasswordWindow;
import com.jperezmota.wsellfiliates.vaadin.views.shared.ConfirmationWindow;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.data.provider.Query;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.components.grid.FooterRow;
import com.vaadin.ui.themes.ValoTheme;

@SpringView(name = TrackPromoCodeView.VIEW_NAME)
public class TrackPromoCodeView extends VerticalLayout implements View{
	
	public static final String VIEW_NAME = "track-promo-code";
	
	@Autowired
	private WordpressServiceImpl wordpressService;
	@Autowired
	private UserSession userSession;
	
	private Label lblView;
	
	private CssLayout filter;
	private TextField txtPromoCode;
	private DateField txtInitialDate;
	private DateField txtFinalDate;
	private Button btnSearch;
	
	private List<CouponSale> couponsSells;
	private ListDataProvider<CouponSale> couponSellListDataProvider;
	private Grid<CouponSale> sellsGrid;

	@Override
	public void enter(ViewChangeEvent event) {
		createInterface();
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
		couponsSells = new ArrayList<CouponSale>();
		
		lblView = new Label("Track Promo Code");
		lblView.addStyleName(ValoTheme.LABEL_H1);
		
		filter = createFilter();
		sellsGrid = createGrid();
	}
	
	private void addComponentsToUI() {
		addComponent(lblView);
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
		
		txtPromoCode = new TextField();
		txtPromoCode.setPlaceholder("Promo Code");
		txtPromoCode.addStyleName(ValoTheme.TEXTFIELD_TINY);
		txtPromoCode.addStyleName("search-filter-elements");
		
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
		
		cssLayout.addComponents(txtPromoCode, txtInitialDate, txtFinalDate, btnSearch);
	
		return cssLayout;
	}
	
	private void searchSalesByFilter() {
		try {
			executeFilterSearch();
		}catch (Exception e) {
			couponsSells.clear();
			couponSellListDataProvider.refreshAll();
			SystemNotificationUtil.showExceptionNotification(e.getMessage());
		}
	}

	private void executeFilterSearch() {
		String promoCode = txtPromoCode.getValue();
		LocalDate initialDate = txtInitialDate.getValue();
		LocalDate finalDate = txtFinalDate.getValue();
		
		List<CouponSale> salesFound = wordpressService.getSalesByCoupon(promoCode, initialDate, finalDate);
		
		couponsSells.clear();
		this.couponsSells.addAll(salesFound);
		couponSellListDataProvider.refreshAll();
	}

	private Grid createGrid() {
		Grid<CouponSale> sellsGrid = new Grid<CouponSale>();
		sellsGrid.setSizeFull();
		
		
		sellsGrid.addColumn(CouponSale::getOrderId).setId("Order Id").setCaption("Order Id");
		sellsGrid.addColumn(CouponSale::getPaidDate).setId("Date").setCaption("Date");
		sellsGrid.addColumn(CouponSale::getBillingCountry).setId("Country").setCaption("Country");
		sellsGrid.addColumn(CouponSale::getBillingCity).setId("City").setCaption("City");
		sellsGrid.addColumn(CouponSale::getBillingState).setId("State").setCaption("State");
		sellsGrid.addColumn(CouponSale::getOrderTotal).setId("Order Total").setCaption("Order Total").setStyleGenerator(item -> "v-align-right");
		
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
		double totalInSales = couponSellListDataProvider.fetch(new Query<>()).mapToDouble(CouponSale::getOrderTotal).sum();
		return "<b> $ " + totalInSales + "</b>";
	}
	
}