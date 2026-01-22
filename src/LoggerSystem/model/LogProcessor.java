package LoggerSystem.model;

import LoggerSystem.enums.LogLevel;

public abstract class LogProcessor {
    LogProcessor nextLogProcessor;

    public LogProcessor(LogProcessor nextLogProcessor) {
        this.nextLogProcessor = nextLogProcessor;
    }

    public void log(LogLevel logLevel, String message){
        if (nextLogProcessor != null){
            nextLogProcessor.log(logLevel, message);
        }
    }
}
