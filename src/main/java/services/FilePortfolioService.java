/**
 * Created by joseph on 15/09/14.
 */
package services;

import com.google.gson.Gson;
import exceptions.PortfolioHoldingPricesLoadException;
import exceptions.PortfolioHoldingsLoadException;
import models.*;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Move Infrastructure concerns into a separate folder/package 
public class FilePortfolioService implements IPortfolioService {
    private String getPortfolioFolder () {
        return System.getProperty("user.home") + "/.portfolio-rebalancer";
    }

    @Override
    public PortfolioList getPortfolioList() {
        PortfolioList portfolios = new PortfolioList();
        File portfolioFolder;
        String[] portfolioFiles;

        // Get the list of files
        portfolioFolder = new File(this.getPortfolioFolder());
        portfolioFiles = portfolioFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return name.startsWith("portfolio-") && name.endsWith(".json");
            }
        });

        // Create a portfolio list out of the file names
        for (String portfolioFile : portfolioFiles) {
            Portfolio portfolio = new Portfolio();
            portfolio.name = portfolioFile.split("portfolio-")[1].split(".json")[0];
            portfolios.add(portfolio);
        }

        return portfolios;
    }

    @Override
    public Portfolio loadPortfolioHoldings(Portfolio portfolio) throws PortfolioHoldingsLoadException {
        Portfolio holdings;
        Path portfolioFilePath = FileSystems.getDefault().getPath(this.getPortfolioFolder(), "portfolio-" + portfolio.name + ".json");
        String portfolioJson;

        try {
            portfolioJson = new String(Files.readAllBytes(portfolioFilePath));

            Gson parser = new Gson();
            holdings = parser.fromJson(portfolioJson, Portfolio.class);
            // TODO: copy constructor (or better)
            holdings.name = portfolio.name;

            return holdings;
        }
        catch (IOException readError) {
            throw new PortfolioHoldingsLoadException(readError);
        }
    }

    @Override
    // TODO: Abstract out a price service which this implements
    public PricedPortfolio getPricedPortfolioHoldings(Portfolio holdings) throws PortfolioHoldingPricesLoadException {
        PricedPortfolio portfolio;

        // I'm pretty sure Java is a neurotic control freak with a nearly bigoted concern
        // for typing and class than in helping me write code that works.
        Path priceFilePath = FileSystems.getDefault().getPath(this.getPortfolioFolder(), "prices.json");
        String priceJson = "";

        try {
            priceJson = new String(Files.readAllBytes(priceFilePath));
        }
        catch (IOException readError) {
            throw new PortfolioHoldingPricesLoadException(
                    new Exception("Cannot read price database", readError)
            );
        }

        Pattern priceBySymbolPattern = Pattern.compile("\"(\\w+)\"\\s*?:\\s*?(\\d+\\.\\d+)");
        Matcher priceBySymbolMatches = priceBySymbolPattern.matcher(priceJson);

        HashMap<String, Double> priceBySymbol = new HashMap();
        while (priceBySymbolMatches.find()) {
            priceBySymbol.put(priceBySymbolMatches.group(1), Double.parseDouble(priceBySymbolMatches.group(2)));
        }

        ArrayList<Holding> pricedHoldings = new ArrayList<Holding>();
        for (Holding holding : holdings.getHoldings()) {
            if (!priceBySymbol.containsKey(holding.symbol)) {
                throw new PortfolioHoldingPricesLoadException(
                        new Exception("No price in database for symbol '" + holding.symbol + "'")
                );
            }

            holding.price = priceBySymbol.get(holding.symbol);

            pricedHoldings.add(holding);
        }

        // TODO: copy constructor (or better)
        portfolio = new PricedPortfolio();
        portfolio.name = holdings.name;
        portfolio.setHoldings(pricedHoldings.toArray(new Holding[0]));

        return portfolio;
    }
}
