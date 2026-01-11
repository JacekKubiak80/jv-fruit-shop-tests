package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final Map<String, Integer> stock = new HashMap<>();
    private final OperationStrategy strategy;

    public ShopServiceImpl(OperationStrategy strategy) {
        if (strategy == null) {
            throw new RuntimeException("OperationStrategy cannot be null");
        }
        this.strategy = strategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transaction list cannot be null");
        }

        for (FruitTransaction transaction : transactions) {
            if (transaction == null) {
                throw new RuntimeException("Transaction cannot be null");
            }

            FruitTransaction.Operation operation = transaction.getOperation();
            if (operation == null) {
                throw new RuntimeException("Operation cannot be null");
            }

            OperationHandler handler = strategy.getHandler(operation);

            handler.handle(transaction, stock);
        }
    }

    @Override
    public Map<String, Integer> getCurrentStock() {
        return stock;
    }
}

