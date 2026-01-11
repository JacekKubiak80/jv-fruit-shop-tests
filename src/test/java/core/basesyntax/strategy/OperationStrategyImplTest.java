package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {

    @Test
    void constructor_nullMap_throwsException() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> new OperationStrategyImpl(null));
        Assertions.assertEquals("Handlers map cannot be null", exception.getMessage());
    }

    @Test
    void constructor_nullKey_throwsException() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(null, new BalanceOperation()); // teraz można dać null
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> new OperationStrategyImpl(handlers));
        Assertions.assertEquals("Operation key cannot be null", exception.getMessage());
    }

    @Test
    void constructor_nullValue_throwsException() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, null); // null wartość
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> new OperationStrategyImpl(handlers));
        Assertions.assertEquals("Handler for operation BALANCE cannot be null",
                exception.getMessage());
    }

    @Test
    void getHandler_nullOperation_throwsException() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        );
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> strategy.getHandler(null));
        Assertions.assertEquals("Operation cannot be null", exception.getMessage());
    }

    @Test
    void getHandler_missingHandler_throwsException() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        );
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> strategy.getHandler(FruitTransaction.Operation.PURCHASE));
        Assertions.assertEquals(
                "No handler found for operation: PURCHASE", exception.getMessage());
    }

    @Test
    void getHandler_existingHandler_ok() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation()
        );
        OperationStrategyImpl strategy = new OperationStrategyImpl(handlers);

        OperationHandler handler = strategy.getHandler(FruitTransaction.Operation.BALANCE);
        Assertions.assertNotNull(handler);
        Assertions.assertTrue(handler instanceof BalanceOperation);
    }
}

