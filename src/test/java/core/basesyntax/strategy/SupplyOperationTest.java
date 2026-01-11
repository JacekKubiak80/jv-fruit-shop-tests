package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @Test
    void handle_increasesStock() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("banana", 10);

        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 15);

        new SupplyOperation().handle(tx, stock);

        Assertions.assertEquals(25, stock.get("banana"));
    }

    @Test
    void handle_newFruit_addedToStock() {
        Map<String, Integer> stock = new HashMap<>();

        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 20);

        new SupplyOperation().handle(tx, stock);

        Assertions.assertEquals(20, stock.get("apple"));
    }

    @Test
    void handle_nullTransaction_throwsException() {
        Map<String, Integer> stock = new HashMap<>();
        Assertions.assertThrows(RuntimeException.class,
                () -> new SupplyOperation().handle(null, stock));
    }

    @Test
    void handle_nullStock_throwsException() {
        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        Assertions.assertThrows(RuntimeException.class,
                () -> new SupplyOperation().handle(tx, null));
    }
}

