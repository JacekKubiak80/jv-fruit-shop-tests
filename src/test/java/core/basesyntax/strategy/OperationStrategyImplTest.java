package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImplTest {

    @Test
    void getHandler_existingOperation_ok() {
        OperationHandler handler = new BalanceOperation();
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, handler)
        );

        assertEquals(handler,
                strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_nullOperation_throwsException() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation())
        );

        assertThrows(RuntimeException.class,
                () -> strategy.getHandler(null));
    }
}
