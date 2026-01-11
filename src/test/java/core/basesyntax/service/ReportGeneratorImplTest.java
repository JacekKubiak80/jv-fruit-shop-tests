package core.basesyntax.service;

import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorImplTest {

    @Test
    void getReport_sortedAndFormatted() {
        Map<String, Integer> stock = Map.of(
                "banana", 10,
                "apple", 20
        );

        ReportGenerator generator = new ReportGeneratorImpl(stock);
        String report = generator.getReport();

        assertTrue(report.startsWith("fruit,quantity"));
        assertTrue(report.contains("apple,20"));
        assertTrue(report.contains("banana,10"));
    }

    @Test
    void constructor_nullStock_throwsException() {
        assertThrows(RuntimeException.class,
                () -> new ReportGeneratorImpl(null));
    }
}
