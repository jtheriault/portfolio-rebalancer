/**
 * Created by joseph on 02/11/14.
 */
package ui.gui.controllers;

import models.Portfolio;
import services.IPortfolioService;
import ui.gui.IGuiController;
import ui.gui.models.PortfolioChooserModel;
import ui.gui.views.PortfolioChooserView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortfolioChooserController implements IGuiController {
    private ActionListener onChoose;
    private IPortfolioService portfolios;
    private Portfolio current;
    private PortfolioChooserModel model;
    private PortfolioChooserView view;

    public PortfolioChooserController(IPortfolioService portfolios, ActionListener setActivePortfolio) {
        this.portfolios = portfolios;
        this.onChoose = setActivePortfolio;
    }

    @Override
    public JPanel bind() {
        this.model = new PortfolioChooserModel();
        this.model.Choices = this.portfolios.getPortfolioList();
        this.model.Selection = this.model.Choices.getPrimary();

        ActionListener selectCurrent = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setCurrent(model.Selection);
            }
        };

        this.view = new PortfolioChooserView(this.model, selectCurrent);

        return this.view;
    }

    @Override
    public void process() {
        this.setCurrent(this.model.Selection);
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
