/**
 * Created by joseph on 15/09/14.
 */
package models;

import java.util.ArrayList;

public class PortfolioList extends ArrayList<Portfolio> {
    private Portfolio primary;

    public Portfolio getPrimary() {
        if (this.primary == null) {
            this.primary = this.size() > 0 ? this.get(0) : null;
        }

        return this.primary;
    }
}
