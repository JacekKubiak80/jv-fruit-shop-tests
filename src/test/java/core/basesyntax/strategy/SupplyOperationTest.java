package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class SupplyOperationTest {

    @Test
    void handle_increasesStock() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("banana", 10);

        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 15);

        new SupplyOperation().handle(tx, stock);

        assertEquals(25, stock.get("banana"));
    }
}
