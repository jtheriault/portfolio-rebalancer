/**
 * Created by joseph on 22/09/14.
 */
package ui.gui;

import services.IPortfolioService;
import services.OrderService;
import ui.IUserInterface;
import ui.gui.controllers.PortfolioController;

import javax.swing.*;

public class PortfolioRebalancerGui implements IUserInterface {
    private JFrame window;
    private OrderService orders;
    private IPortfolioService portfolios;

    public PortfolioRebalancerGui(IPortfolioService portfolioService, OrderService orderService) {
        this.portfolios = portfolioService;
        this.orders = orderService;
    }

    public void start () {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            // Fuck it
        }

        this.window = new JFrame("Portfolio Rebalancer");
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: Routing (handle response, build request, dispatch to controller
        IGuiController response = new PortfolioController(this.portfolios, this.orders);

        JPanel mainView = response.bind();

        response.process();

        window.add(mainView);
        window.pack();
        window.setVisible(true);
    }
}
