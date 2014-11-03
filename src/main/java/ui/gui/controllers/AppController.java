/**
 * Created by joseph on 02/11/14.
 */
package ui.gui.controllers;

import services.IPortfolioService;
import services.OrderService;
import ui.gui.IGuiController;
import ui.gui.views.AppView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController implements IGuiController {
    private OrderService orders;
    private IPortfolioService portfolios;
    PortfolioChooserController portfolioChooser;
    PortfolioController portfolio;

    // TODO: Enhancement - dependency injection
    public AppController(IPortfolioService portfolioService, OrderService orderService) {
        this.portfolios = portfolioService;
        this.orders = orderService;
    }

    public JPanel bind() {
        ActionListener setActivePortfolio = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                portfolio.setPortfolio(portfolioChooser.getCurrent());
                portfolio.process();
            }
        };

        this.portfolioChooser = new PortfolioChooserController(this.portfolios, setActivePortfolio);
        JPanel chooserView = portfolioChooser.bind();

        this.portfolio = new PortfolioController(this.portfolios, this.orders);
        JPanel portfolioView = portfolio.bind();

        JPanel view = new AppView(chooserView, portfolioView);

        return view;
    }

    public void process() {
        this.portfolioChooser.process();
        this.portfolio.process();
    }
}
