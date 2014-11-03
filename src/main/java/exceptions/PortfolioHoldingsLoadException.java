/**
 * Created by joseph on 21/09/14.
 */
package exceptions;

public class PortfolioHoldingsLoadException extends Throwable {
    public PortfolioHoldingsLoadException(Exception innerException) {
        super(innerException);
    }
}
