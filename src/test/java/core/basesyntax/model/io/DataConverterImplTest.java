package core.basesyntax.model.io;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DataConverterImplTest {

    private final DataConverter converter = new DataConverterImpl();

    @Test
    void convert_validLines_ok() {
        List<String> lines = List.of(
                "b,apple,100",
                "s,banana,50"
        );

        List<FruitTransaction> result = converter.convertToTransaction(lines);

        assertEquals(2, result.size());
        assertEquals(FruitTransaction.Operation.BALANCE, result.get(0).getOperation());
        assertEquals("apple", result.get(0).getFruit());
        assertEquals(100, result.get(0).getQuantity());
    }

    @Test
    void convert_invalidOperation_throwsException() {
        List<String> lines = List.of("x,apple,10");

        assertThrows(RuntimeException.class,
                () -> converter.convertToTransaction(lines));
    }
}
