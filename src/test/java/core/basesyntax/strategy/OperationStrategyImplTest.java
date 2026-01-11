package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void getHandler_existingOperation_ok() {
        OperationHandler handler = new BalanceOperation();
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, handler)
        );

        Assertions.assertEquals(handler,
                strategy.getHandler(FruitTransaction.Operation.BALANCE));
    }

    @Test
    void getHandler_nullOperation_throwsException() {
        OperationStrategy strategy = new OperationStrategyImpl(
                Map.of(FruitTransaction.Operation.BALANCE, new BalanceOperation())
        );

        Assertions.assertThrows(RuntimeException.class,
                () -> strategy.getHandler(null));
    }
}

