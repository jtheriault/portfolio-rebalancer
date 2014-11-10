/**
 * Created by joseph on 14/09/14.
 */
import services.FilePortfolioService;
import services.IPortfolioService;
import services.OrderService;
import ui.IUserInterface;
import ui.gui.PortfolioRebalancerGui;

public class main {
    private static IUserInterface userInterface;
    private static OrderService orders;
    private static IPortfolioService portfolios;
    private static IPriceService prices;

    public static void main(String[] args) {
    	prices = new FilePriceService();
        portfolios = new FilePortfolioService(prices);
        
        // TODO: new IPriceService for OrderService
        orders = new OrderService(prices);

        //userInterface = new PortfolioRebalancerCli(portfolios, orders);
        userInterface = new PortfolioRebalancerGui(portfolios, orders);

        userInterface.start();
    }
}
