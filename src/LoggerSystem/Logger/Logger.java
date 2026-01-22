package LoggerSystem.Logger;

import LoggerSystem.enums.LogLevel;
import LoggerSystem.model.LogProcessor;

public class Logger {
    private static final Logger INSTANCE = new Logger();
    private final Object lock = new Object();
    private LogProcessor logProcessor;
    private LogLevel configuredLevel;


    private Logger(){}

    public static Logger getInstance(){
        return INSTANCE;
    }

    public void init(LogProcessor logProcessor, LogLevel configuredLevel) {
        this.logProcessor = logProcessor;
        this.configuredLevel = configuredLevel;
    }

    public void log(LogLevel level, String message) {
        if (level.ordinal() < configuredLevel.ordinal()) return;
        synchronized (lock) {
            logProcessor.log(level, message);
        }
    }
}
