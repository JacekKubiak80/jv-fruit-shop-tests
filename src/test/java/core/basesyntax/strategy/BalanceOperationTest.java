package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BalanceOperationTest {

    @Test
    void handle_setsExactBalance() {
        Map<String, Integer> stock = new HashMap<>();
        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100);

        new BalanceOperation().handle(tx, stock);

        assertEquals(100, stock.get("apple"));
    }
}
