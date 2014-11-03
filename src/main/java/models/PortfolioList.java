/**
 * Created by joseph on 15/09/14.
 */
package models;

import java.util.ArrayList;

public class PortfolioList {
    private ArrayList<Portfolio> portfolios = new ArrayList<Portfolio>();
    private Portfolio primary;

    public Portfolio getPrimary() {
        if (this.primary == null) {
            this.primary = this.portfolios.size() > 0 ? this.portfolios.get(0) : null;
        }

        return this.primary;
    }

    public void add(Portfolio portfolio) {
        this.portfolios.add(portfolio);
    }

//    public void setPrimary(Portfolio portfolio) {
//        if (this.portfolios.contains(portfolio)) {
//            this.primary = portfolio;
//        }
//    }
}
