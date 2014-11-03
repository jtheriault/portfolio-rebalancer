package ui.cli; /**
 * Created by joseph on 14/09/14.
 */
import exceptions.PortfolioHoldingPricesLoadException;
import exceptions.PortfolioHoldingsLoadException;
import models.*;
import services.IPortfolioService;
import services.OrderService;
import ui.IUserInterface;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class PortfolioRebalancerCli implements IUserInterface {
    private InputStream in;
    private PrintStream out;
    private OrderService orders;
    private IPortfolioService portfolios;

    public PortfolioRebalancerCli(IPortfolioService porfolioService, OrderService orderService) {
        this.portfolios = porfolioService;
        this.orders = orderService;

        // Don"t tie us to System.in/out throughout the class
        this.in = System.in;
        this.out = System.out;
    }

    @Override
    public void start() {
        PortfolioList portfolios = this.portfolios.getPortfolioList();
        Portfolio holdings;

        try {
            holdings = this.portfolios.loadPortfolioHoldings(portfolios.getPrimary());
        } catch (PortfolioHoldingsLoadException e) {
            this.out.println("Count not load portfolio holdings.");
            e.printStackTrace();

            // Get out!
            System.exit(1);
            return;
        }

        double cash = getCash();

        PricedPortfolio pricedHoldings;

        try {
            pricedHoldings = this.portfolios.getPricedPortfolioHoldings(holdings);
        }
        catch (PortfolioHoldingPricesLoadException e) {
            this.out.println("Count not load prices for portfolio holdings.");
            e.printStackTrace();

            // Get out!
            System.exit(1);
            return;
        }

        print(pricedHoldings);

        Order[] biggestOrders = this.orders.getBiggestOrders(pricedHoldings, cash);

        print(biggestOrders);
    }

    private double getCash() {
        Scanner input = new Scanner(this.in);

        this.out.print("What is the current cash buying power? $");

        // WARN: Explosive!
        return input.nextDouble();
    }

    private void print(PricedPortfolio portfolio) {
        for (Holding holding : portfolio.getHoldings()) {
            this.out.println("=== " + holding.symbol + " ===");
            this.out.println("SEGMENT: " + holding.segment);
            this.out.println("COMMISSION: " + holding.commission);
            this.out.println("SHARES: " + holding.shares);
            this.out.println("TARGET: " + holding.target * 100 + "%");

            this.out.println("PRICE: " + holding.price);
            this.out.println("VALUE: " + holding.getValue());
            this.out.println("PERCENTAGE: " + portfolio.getPercentage(holding) * 100 + "%");
        }

        this.out.println("");
        this.out.println("TOTAL: " + portfolio.getValue());
    }

    private void print (Order[] orders) {
        for(Order order : orders) {
            this.out.println(order.symbol + ": " + (order.isBuy ? "BUY " : "SELL ") + order.quantity + " (@ $" + order.price + " for $" + order.total + " total)");
        }
    }
}
