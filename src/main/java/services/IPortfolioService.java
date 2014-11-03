/**
 * Created by joseph on 15/09/14.
 */
package services;

import exceptions.PortfolioHoldingPricesLoadException;
import exceptions.PortfolioHoldingsLoadException;
import models.*;

public interface IPortfolioService {
    Portfolio loadPortfolioHoldings(Portfolio primary) throws PortfolioHoldingsLoadException;

    PortfolioList getPortfolioList();

    PricedPortfolio getPricedPortfolioHoldings(Portfolio holdings) throws PortfolioHoldingPricesLoadException;
}
