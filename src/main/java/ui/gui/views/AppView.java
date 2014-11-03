/**
 * Created by joseph on 02/11/14.
 */
package ui.gui.views;

import javax.swing.*;
import java.awt.*;

public class AppView extends JPanel {
    public AppView(JPanel chooser, JPanel portfolio) {
        this.add(chooser, BorderLayout.PAGE_START);
        this.add(portfolio, BorderLayout.CENTER);
    }
}
