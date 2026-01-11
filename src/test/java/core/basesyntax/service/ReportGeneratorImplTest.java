package core.basesyntax.service;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @Test
    void getReport_sortedAndFormatted() {
        Map<String, Integer> stock = Map.of(
                "banana", 10,
                "apple", 20
        );

        ReportGenerator generator = new ReportGeneratorImpl(stock);
        String report = generator.getReport();

        Assertions.assertTrue(report.startsWith("fruit,quantity"));
        Assertions.assertTrue(report.contains("apple,20"));
        Assertions.assertTrue(report.contains("banana,10"));
    }

    @Test
    void constructor_nullStock_throwsException() {
        Assertions.assertThrows(RuntimeException.class,
                () -> new ReportGeneratorImpl(null));
    }
}

