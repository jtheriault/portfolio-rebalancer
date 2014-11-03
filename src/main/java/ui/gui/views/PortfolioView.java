/**
 * Created by joseph on 02/10/14.
 */
package ui.gui.views;

import models.Holding;
import models.Order;
import models.PricedPortfolio;
import ui.gui.models.PortfolioModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PortfolioView extends JPanel {
    private PortfolioModel model;
    private ActionListener onCreateOrders;

    public PortfolioView(PortfolioModel model, ActionListener onCreateOrders) {
        this.model = model;
        this.onCreateOrders = onCreateOrders;
    }

    public void render() {
        // TODO: Bug - showing cashPanel twice. Something like window.removeAll?
        this.removeAll();
        this.setLayout(new BorderLayout());

        if (this.model.ErrorMessage != null) {
            JOptionPane.showMessageDialog(
                    this,
                    this.model.ErrorMessage,
                    this.model.ErrorContextSummary,
                    JOptionPane.ERROR_MESSAGE
            );
        }

        JPanel holdingsPanel = this.getHoldingsPanel();
        this.add(holdingsPanel, BorderLayout.PAGE_START);

        JPanel cashPanel = this.getCashEntryPanel();
        this.add(cashPanel, BorderLayout.CENTER);

        JPanel ordersPanel = this.getOrdersPanel();
        this.add(ordersPanel, BorderLayout.PAGE_END);

        //this.repaint();
    }

    private JPanel getHoldingsPanel() {
        JPanel panel = new JPanel();

        JLabel pleaseWait = new JLabel("Loading portfolio...");
        panel.add(pleaseWait, BorderLayout.CENTER);

        if (this.model.Portfolio != null) {
            String[] tableColumns = {
                    "Symbol",
                    "Segment",
                    "Shares",
                    "Commission",
                    "Price",
                    "Target",
                    "Percentage"
            };

            Object[][] tableData = this.getPortfolioTableData(this.model.Portfolio);
            JTable holdings = new JTable(
                    tableData,
                    tableColumns
            );

            // HACK: Magic Number "18" is guesstimate of line height
            holdings.setPreferredScrollableViewportSize(new Dimension(500, tableData.length * 18));
            holdings.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(holdings);

            panel.remove(pleaseWait);
            panel.add(scrollPane, BorderLayout.CENTER);
        }

        return panel;
    }

    private JPanel getCashEntryPanel() {
        JPanel panel = new JPanel();

        JLabel cashLabel = new JLabel("Cash: ");
        panel.add(cashLabel, BorderLayout.LINE_START);

        final JTextField cash = new JTextField(Double.toString(this.model.Cash));
        panel.add(cash, BorderLayout.CENTER);

        JButton createOrder = new JButton("Order");
        createOrder.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                model.Cash = Double.parseDouble(cash.getText());
                onCreateOrders.actionPerformed(e);
            }
        });
        panel.add(createOrder,  BorderLayout.LINE_END);

        return panel;
    }

    private JPanel getOrdersPanel() {
        JPanel container = new JPanel();

        JLabel placeholder = new JLabel("Loading orders...");
        container.add(placeholder, BorderLayout.CENTER);

        if (this.model.Orders != null) {
            // Set column headers
            String[] tableColumns = {
                    "Buy/Sell",
                    "Symbol",
                    "Quantity",
                    "Price",
                    "Total"
            };

            // TODO: Get order table data
            Object[][] tableData = this.getOrderTableData(this.model.Orders);

            // Add table to new scroll pane
            JTable orders = new JTable(
                    tableData,
                    tableColumns
            );

            // Remove placeholder
            container.remove(placeholder);

            // Add scroll pane
            // HACK: Magic Number "18" is guesstimate of line height
            orders.setPreferredScrollableViewportSize(new Dimension(500, tableData.length * 18));
            orders.setFillsViewportHeight(true);

            JScrollPane scrollPane = new JScrollPane(orders);
            container.add(scrollPane, BorderLayout.CENTER);
        }
        else if (this.model.Cash == 0) {
            placeholder.setText("Please set your cash buying power to see your possible orders.");
        }

        return container;
    }

    private Object[][] getPortfolioTableData (PricedPortfolio portfolio) {
        ArrayList<Object[]> tableData = new ArrayList<Object[]>();

        for (Holding holding : portfolio.getHoldings()) {
            // TODO: Enhancement - Piece table data together based on the column list
            tableData.add(new Object[] {
                    holding.symbol,
                    holding.segment,
                    holding.shares,
                    holding.commission,
                    holding.price,
                    holding.target * 100,
                    portfolio.getPercentage(holding) * 100
            });
        }

        return tableData.toArray(new Object[0][0]);
    }

    private Object[][] getOrderTableData (Order[] orders) {
        ArrayList<Object[]> tableData = new ArrayList<Object[]>();

        for (Order order : orders) {
            // TODO: Enhancement - Piece table data together based on the column list
            tableData.add(new Object[] {
                    order.isBuy ? "Buy" : "Sell",
                    order.symbol,
                    order.quantity, // TODO: Bug - why is this always the same number?
                    order.price,
                    order.total
            });
        }

        return tableData.toArray(new Object[0][0]);
    }
}
