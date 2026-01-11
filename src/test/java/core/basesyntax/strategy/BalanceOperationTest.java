package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    @Test
    void handle_validTransaction_updatesStock() {
        BalanceOperation operation = new BalanceOperation();
        Map<String, Integer> stock = new HashMap<>();
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10);

        operation.handle(transaction, stock);

        assert stock.get("apple") == 10;
    }

    @Test
    void handle_nullTransaction_throwsException() {
        BalanceOperation operation = new BalanceOperation();
        Map<String, Integer> stock = new HashMap<>();
        boolean thrown = false;
        try {
            operation.handle(null, stock);
        } catch (RuntimeException e) {
            thrown = true;
        }
        assert thrown;
    }

    @Test
    void handle_nullStock_throwsException() {
        BalanceOperation operation = new BalanceOperation();
        FruitTransaction transaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5);
        boolean thrown = false;
        try {
            operation.handle(transaction, null);
        } catch (RuntimeException e) {
            thrown = true;
        }
        assert thrown;
    }

    @Test
    void handle_nullFruitOrEmpty_throwsException() {
        BalanceOperation operation = new BalanceOperation();
        Map<String, Integer> stock = new HashMap<>();
        boolean thrown1 = false;
        boolean thrown2 = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    null, 5), stock);
        } catch (RuntimeException e) {
            thrown1 = true;
        }
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    " ", 5), stock);
        } catch (RuntimeException e) {
            thrown2 = true;
        }
        assert thrown1 && thrown2;
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        BalanceOperation operation = new BalanceOperation();
        Map<String, Integer> stock = new HashMap<>();
        boolean thrown = false;
        try {
            operation.handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                    "apple", -1), stock);
        } catch (RuntimeException e) {
            thrown = true;
        }
        assert thrown;
    }
}
