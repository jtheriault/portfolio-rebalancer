/**
 * Created by joseph on 21/09/14.
 */
package exceptions;

public class PortfolioHoldingPricesLoadException extends Throwable {
    public PortfolioHoldingPricesLoadException(Exception innerException) {
        super(innerException);
    }
}
