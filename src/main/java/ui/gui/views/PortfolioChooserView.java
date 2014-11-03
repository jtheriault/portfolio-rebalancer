/**
 * Created by joseph on 02/11/14.
 */
package ui.gui.views;

import models.Portfolio;
import ui.gui.models.PortfolioChooserModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PortfolioChooserView extends JPanel {
    public PortfolioChooserView(final PortfolioChooserModel model, final ActionListener onSelect) {
        final HashMap<String, Portfolio> choices = new HashMap<String, Portfolio>();
        ActionListener onClick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selectionName = ((JButton)actionEvent.getSource()).getText();

                model.Selection = choices.get(selectionName);
                onSelect.actionPerformed(actionEvent);
            }
        };

        for(Portfolio choice : model.Choices) {
            choices.put(choice.name, choice);

            JButton choose = new JButton();
            choose.setText(choice.name);
            choose.addActionListener(onClick);

            this.add(choose);
        }
    }
}
