/**
 * Created by joseph on 15/09/14.
 */
package models;

public class Order {
	private IPriceService priceService;
    private int quantity;
    // TODO: Can of worms: real-time
	private double price;
    private String symbol;
    
    // TODO: Service?
    public Order (IPriceService priceService, String symbol) {
    	this.priceService = priceService;
    	this.setSymbol(symbol);
    }

    public boolean isBuy;
    
    public double getQuantity () {
    	return this.quantity;
    }
    
    public double getSubtotal () {
    	return this.price * this.quantity;
    }
    
    public void setQuantity (int quantity) {
    	this.quantity = quantity;
    }
    
    public void setSymbol (String symbol) {
    	this.symbol = symbol;
    	this.price = this.priceService.getPrice(this.symbol);
    }
}
