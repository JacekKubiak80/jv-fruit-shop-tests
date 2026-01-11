package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private PurchaseOperation purchase;
    private Map<String, Integer> stock;

    @BeforeEach
    void setUp() {
        purchase = new PurchaseOperation();
        stock = new HashMap<>();
        stock.put("apple", 10);
    }

    @Test
    void handle_purchaseReducesStock() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5);
        purchase.handle(transaction, stock);
        Assertions.assertEquals(5, stock.get("apple"));
    }

    @Test
    void handle_purchaseMoreThanStock_throwsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 15);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> purchase.handle(transaction, stock));

        Assertions.assertEquals(
                "Insufficient stock for purchase: fruit=apple, requested=15, available=10",
                exception.getMessage());
    }

    @Test
    void handle_nullTransaction_throwsException() {
        Assertions.assertThrows(RuntimeException.class, () -> purchase.handle(null, stock));
    }

    @Test
    void handle_nullStock_throwsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", 5);
        Assertions.assertThrows(RuntimeException.class, () -> purchase.handle(transaction, null));
    }

    @Test
    void handle_nullFruit_throwsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                null, 5);
        Assertions.assertThrows(RuntimeException.class, () -> purchase.handle(transaction, stock));
    }

    @Test
    void handle_negativeQuantity_throwsException() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "apple", -5);
        Assertions.assertThrows(RuntimeException.class, () -> purchase.handle(transaction, stock));
    }
}



