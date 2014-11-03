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

    public static void main(String[] args) {
        portfolios = new FilePortfolioService();
        orders = new OrderService();

        //userInterface = new PortfolioRebalancerCli(portfolios, orders);
        userInterface = new PortfolioRebalancerGui(portfolios, orders);

        userInterface.start();
    }
}
