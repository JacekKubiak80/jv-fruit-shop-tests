package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    @Test
    void handle_increasesStock() {
        Map<String, Integer> stock = new HashMap<>();
        stock.put("apple", 30);

        FruitTransaction tx = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 10);

        new ReturnOperation().handle(tx, stock);

        Assertions.assertEquals(40, stock.get("apple"));
    }
}

