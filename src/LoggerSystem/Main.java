package LoggerSystem;

import LoggerSystem.Logger.Logger;
import LoggerSystem.enums.LogLevel;
import LoggerSystem.model.*;

public class Main {
    public static void main(String[] args) {
//        LogProcessor logProcessor = new InfoLogProcessor(new DebugLogProcessor(new WarningLogProcessor(new ErrorLogProcessor(null))));
//        logProcessor.log(LogLevel.ERROR, "ERROR");
//        logProcessor.log(LogLevel.DEBUG, "DEBUG");
//        logProcessor.log(LogLevel.INFO, "INFO");
//        logProcessor.log(LogLevel.WARNING, "WARNING");

        LogProcessor loggerChain1 =
                new DebugLogProcessor(
                        new InfoLogProcessor(
                                new WarningLogProcessor(
                                        new ErrorLogProcessor(null)
                                )
                        )
                );
        LogProcessor loggerChain2 =
                new InfoLogProcessor(
                        new DebugLogProcessor(
                                new WarningLogProcessor(
                                        new ErrorLogProcessor(null)
                                )
                        )
                );

        Logger logger = Logger.getInstance();
        new Thread(() -> {
            logger.init(loggerChain1, LogLevel.INFO);
            for (int i = 1; i <= 100; i++) {
                logger.log(LogLevel.DEBUG, "[THREAD-1] " + i + " This will NOT be printed");
                logger.log(LogLevel.INFO, "[THREAD-1] " + i + " Application started");
                logger.log(LogLevel.ERROR, "[THREAD-1] " + i + " Something went wrong");
            }
        }, "Thread-1").start();
        new Thread(() -> {
            logger.init(loggerChain2, LogLevel.DEBUG);
            for (int i = 1; i <= 100; i++) {
                logger.log(LogLevel.DEBUG, "[THREAD-2] " + i + " This will NOT be printed");
                logger.log(LogLevel.INFO, "[THREAD-2] " + i + " Application started");
                logger.log(LogLevel.ERROR, "[THREAD-2] " + i + " Something went wrong");
            }
        }, "Thread-2").start();

    }
}
