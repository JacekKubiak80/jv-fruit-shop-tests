package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @Test
    void handle_enoughStock_ok() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("apple", 50);

        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);

        new PurchaseOperation().handle(tx, stock);

        Assertions.assertEquals(30, stock.get("apple"));
    }

    @Test
    void handle_notEnoughStock_throwsException() {
        Map<String, Integer> stock = Map.of("apple", 10);

        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 20);

        Assertions.assertThrows(RuntimeException.class,
                () -> new PurchaseOperation().handle(tx, new HashMap<>(stock)));
    }
}

