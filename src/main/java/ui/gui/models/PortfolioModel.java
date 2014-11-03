/**
 * Created by joseph on 02/10/14.
 */
package ui.gui.models;

import models.Order;
import models.PricedPortfolio;

public class PortfolioModel {
    public PricedPortfolio Portfolio;
    public double Cash;
    public Order[] Orders;
    public String ErrorContextSummary;
    public String ErrorMessage;
}
