/**
 * Created by joseph on 02/11/14.
 */
package ui.gui.controllers;

import models.Portfolio;
import models.PortfolioList;
import services.IPortfolioService;
import ui.gui.IGuiController;

import javax.swing.*;
import java.awt.event.ActionListener;

public class PortfolioChooserController implements IGuiController {
    private ActionListener onChoose;
    private IPortfolioService portfolios;
    private Portfolio current;

    public PortfolioChooserController(IPortfolioService portfolios, ActionListener setActivePortfolio) {
        this.portfolios = portfolios;
        this.onChoose = setActivePortfolio;
    }

    @Override
    public JPanel bind() {
        return new JPanel();
    }

    @Override
    public void process() {
        PortfolioList portfolios = this.portfolios.getPortfolioList();
        this.setCurrent(portfolios.getPrimary());
    }

    public Portfolio getCurrent() {
        return this.current;
    }

    public void setCurrent(Portfolio current) {
        this.current = current;
        // TODO: Should pass current
        this.onChoose.actionPerformed(null);
    }
}
