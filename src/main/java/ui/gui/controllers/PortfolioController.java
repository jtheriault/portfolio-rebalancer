/**
 * Created by joseph on 02/10/14.
 */
package ui.gui.controllers;

import exceptions.PortfolioHoldingPricesLoadException;
import exceptions.PortfolioHoldingsLoadException;
import models.Portfolio;
import models.PortfolioList;
import models.PricedPortfolio;
import services.IPortfolioService;
import services.OrderService;
import ui.gui.IGuiController;
import ui.gui.models.PortfolioModel;
import ui.gui.views.PortfolioView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortfolioController implements IGuiController {
    PortfolioModel model;
    PortfolioView view;
    private OrderService orders;
    private IPortfolioService portfolios;

    // TODO: Enhancement - dependency injection
    public PortfolioController(IPortfolioService portfolioService, OrderService orderService) {
        this.portfolios = portfolioService;
        this.orders = orderService;
    }

    @Override
    public JPanel bind() {
        this.model = new PortfolioModel();

        // Build up base model data
        this.model.Cash = 0.0;
        this.model.Orders = null;

        // Establish view events
        ActionListener createOrders = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                model.Orders = orders.getBiggestOrders(model.Portfolio, model.Cash);

                view.render();
            }
        };

        // Create and show view
        // TODO: Sort out constructor pattern for models -- I'm pretty sure this should all be DI-appropriate parameters
        this.view = new PortfolioView(this.model, createOrders);
        return this.view;
    }

    @Override
    public void process() {
        view.render();
    }

    public void setPortfolio(Portfolio portfolio) {
        models.Portfolio holdings;

        if (portfolio == null) {
            this.model.Portfolio = null;
        }
        else {
            try {
                holdings = this.portfolios.loadPortfolioHoldings(portfolio);

                this.model.Portfolio =
                        holdings != null
                        ? this.portfolios.getPricedPortfolioHoldings(holdings)
                        : null;
            }
            catch (PortfolioHoldingsLoadException e) {
                this.model.ErrorContextSummary = "Loading Portfolio";
                this.model.ErrorMessage = "Could not load portfolio holdings.";

                System.out.print(e);
            }
            catch (PortfolioHoldingPricesLoadException e) {
                this.model.ErrorContextSummary = "Loading Portfolio";
                this.model.ErrorMessage = "Could not load prices for portfolio holdings.";

                System.out.print(e);
            }
        }

        this.process();
    }
}
