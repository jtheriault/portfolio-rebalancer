/**
 * Created by joseph on 15/09/14.
 */
package models;

public class PricedPortfolio extends Portfolio {
    public double getPercentage(Holding holding) {
        return holding.getValue() / this.getValue();
    }

    public double getValue() {
        double value = 0;

        for (Holding holding : this.getHoldings()){
            value += holding.getValue();
        }

        return value;
    }
}
