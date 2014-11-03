/**
 * Created by joseph on 16/09/14.
 */
package models;

public class Holding {
    public double commission;
    public double price;
    public String segment;
    public long shares;
    public String symbol;
    public double target;

    public double getValue() {
        return this.price * this.shares;
    }
}
