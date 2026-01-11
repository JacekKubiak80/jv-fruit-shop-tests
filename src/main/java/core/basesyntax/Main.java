package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.io.DataConverter;
import core.basesyntax.model.io.DataConverterImpl;
import core.basesyntax.model.io.FileReader;
import core.basesyntax.model.io.FileReaderImpl;
import core.basesyntax.model.io.FileWriter;
import core.basesyntax.model.io.FileWriterImpl;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.ReportGeneratorImpl;
import core.basesyntax.service.ShopService;
import core.basesyntax.service.ShopServiceImpl;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String inputFileName = "reportToRead.csv";

        File inputFile = new File(inputFileName);
        if (!inputFile.exists()) {
            try {
                createSampleInputFile(inputFileName);
                System.out.println("Utworzono przykładowy plik " + inputFileName);
            } catch (IOException e) {
                throw new RuntimeException("Nie udało się utworzyć przykładowego pliku", e);
            }
        }

        FileReader fileReader = new FileReaderImpl();
        List<String> inputLines = fileReader.read(inputFileName);

        DataConverter converter = new DataConverterImpl();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        OperationStrategy strategy = new OperationStrategyImpl(handlers);

        List<FruitTransaction> transactions = converter.convertToTransaction(inputLines);
        ShopService shopService = new ShopServiceImpl(strategy);
        shopService.process(transactions);

        ReportGenerator reportGenerator = new ReportGeneratorImpl(shopService.getCurrentStock());
        String report = reportGenerator.getReport();

        String outputFileName = "finalReport.csv";

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(report, outputFileName);

        System.out.println("Raport został wygenerowany i zapisany w pliku " + outputFileName);
    }

    private static void createSampleInputFile(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("operation,fruit,quantity\n"); // nagłówek
        sb.append("b,apple,100\n");
        sb.append("s,banana,50\n");
        sb.append("p,apple,30\n");
        sb.append("r,banana,10\n");
        sb.append("s,apple,20\n");

        FileWriter fileWriter = new FileWriterImpl();
        fileWriter.write(sb.toString(), fileName);
    }
}
