/**
 * Created by joseph on 15/09/14.
 */
package models;

public class Portfolio {
    private Holding[] holdings;

    public String name;

    public Holding[] getHoldings() {
        return this.holdings;
    }

    public void setHoldings(Holding[] holdings) {
        this.holdings = holdings;
    }
}
