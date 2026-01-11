package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation()
        );
        shopService = new ShopServiceImpl(new OperationStrategyImpl(handlers));
    }

    @Test
    void process_validTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)
        );

        shopService.process(transactions);

        Assertions.assertEquals(80, shopService.getCurrentStock().get("apple"));
    }

    @Test
    void process_nullList_throwsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(null));
    }

    @Test
    void process_nullTransactionInList_throwsException() {
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 50));
        transactions.add(null);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
        Assertions.assertEquals("Transaction cannot be null", exception.getMessage());
    }

    @Test
    void getCurrentStock_emptyInitially_ok() {
        Assertions.assertTrue(shopService.getCurrentStock().isEmpty());
    }

    @Test
    void process_transactionWithNullOperation_throwsException() {
        FruitTransaction transaction = new FruitTransaction(null, "apple", 10);
        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
        Assertions.assertEquals("Operation cannot be null", exception.getMessage());
    }

    @Test
    void process_transactionWithInvalidFruitOrQuantity_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, null, 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", -5)
        );

        for (FruitTransaction t : transactions) {
            if (t != null) {
                RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                        () -> shopService.process(List.of(t)));
                System.out.println(exception.getMessage());
            }
        }
    }

    @Test
    void purchaseWithInsufficientStock_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10)
        );

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
        Assertions.assertTrue(exception.getMessage().contains("Insufficient stock"));
    }

    @Test
    void process_purchaseMoreThanStock_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 10)
        );

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
        Assertions.assertTrue(exception.getMessage().contains("Insufficient stock"));
    }

    @Test
    void process_nullFruitName_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, 10)
        );

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
        Assertions.assertEquals("Fruit name cannot be null or empty", exception.getMessage());
    }

    @Test
    void process_negativeQuantity_throwsException() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", -5)
        );

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> shopService.process(transactions));
        Assertions.assertEquals("Quantity must be a non-negative integer", exception.getMessage());
    }
}




