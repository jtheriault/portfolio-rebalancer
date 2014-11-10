/**
 * Created by joseph on 15/09/14.
 */
package services;
 
import models.Holding;
import models.Order;
import models.PricedPortfolio;
 
import java.util.*;
 
public class OrderService {
	private IPriceService priceService;
	
	public OrderService (IPriceService priceService) {
		this.priceService = priceService;
	}
	
    public Order[] getBiggestOrders(PricedPortfolio portfolio, double cash) {
        ArrayList<Order> orders = new ArrayList<Order>();
        HashMap<String, Order> ordersBySymbol = new HashMap<String, Order>();
        double balance = cash;
        double previousBalance = 0;
 
        ArrayList<Holding> holdingsUnderTarget = new ArrayList<Holding>();
 
        for (Holding holding : portfolio.getHoldings()) {
            if (portfolio.getPercentage(holding) < holding.target) {
                holdingsUnderTarget.add(holding);
            }
        }
 
        Arrays.sort(holdingsUnderTarget.toArray(new Holding[0]), new Comparator<Holding>() {
            @Override
            public int compare(Holding left, Holding right) {
                return left.price > right.price ? -1 : 1;
            }
        });
 
        while (previousBalance != balance) {
            previousBalance = balance;
 
            for(Holding holding : portfolio.getHoldings()) {
                // Purchase price is price of symbol plus commission if this is a new order
                // TODO: Commission possibly only waived for orders of a particular size (other conditions?) - Build price via trading house
                double purchasePrice = holding.price + (ordersBySymbol.containsKey(holding.symbol) ? 0 : holding.commission);
 
                if (balance >= purchasePrice) {
                    Order order;
 
                    if (!ordersBySymbol.containsKey(holding.symbol)) {
                        order = new Order(this.priceService, holding.symbol);
                        order.isBuy = true;
 
                        ordersBySymbol.put(holding.symbol, order);
                    }
                    else {
                        order = ordersBySymbol.get(holding.symbol);
                    }
 
 					order.setQuantity(order.getQuantity() + 1)
                    
                    // TODO: Total should come from the order, not set in it
                    order.total += purchasePrice;
 
                    balance = balance - purchasePrice;
                }
            }
        }
 
        for(Order order : ordersBySymbol.values()) {
            orders.add(order);
        }
 
        return orders.toArray(new Order[0]);
    }
}