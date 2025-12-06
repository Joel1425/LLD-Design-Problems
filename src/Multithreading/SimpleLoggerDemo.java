package Multithreading;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// 1. Logging Levels
enum LogLevel {
    DEBUG, INFO, WARN, ERROR, FATAL
}

// 2. Formatter
class LogFormatter {
    private final String pattern;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public LogFormatter(String pattern) {
        this.pattern = pattern != null ? pattern : "[%d] [%p] [%t] - %m";
    }

    public String format(LogLevel level, String message) {
        String time = sdf.format(new Date());
        String thread = Thread.currentThread().getName();
        return pattern
                .replace("%d", time)
                .replace("%p", level.name())
                .replace("%t", thread)
                .replace("%m", message);
    }
}

// 3. Appender interface
interface Appender {
    void append(String log);
    void close() throws IOException;
}

// Console appender
class ConsoleAppender implements Appender {
    @Override
    public synchronized void append(String log) {
        System.out.println(log);
    }
    @Override public void close() {}
}

// File appender
class FileAppender implements Appender {
    private final BufferedWriter writer;

    public FileAppender(String filePath) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath, true));
    }

    @Override
    public synchronized void append(String log) {
        try {
            writer.write(log);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}

// 4. Logger (thread-safe)
class Logger {
    private final Appender[] appenders;
    private final LogFormatter formatter;
    private LogLevel level = LogLevel.DEBUG;

    public Logger(LogFormatter formatter, Appender... appenders) {
        this.formatter = formatter;
        this.appenders = appenders;
    }

    public synchronized void setLevel(LogLevel level) {
        this.level = level;
    }

    private void log(LogLevel logLevel, String message) {
        if (logLevel.ordinal() < level.ordinal()) return;

        String formatted = formatter.format(logLevel, message);
        for (Appender appender : appenders) {
            appender.append(formatted);
        }
    }

    // convenience methods
    public void debug(String msg) { log(LogLevel.DEBUG, msg); }
    public void info(String msg)  { log(LogLevel.INFO, msg); }
    public void warn(String msg)  { log(LogLevel.WARN, msg); }
    public void error(String msg) { log(LogLevel.ERROR, msg); }
    public void fatal(String msg) { log(LogLevel.FATAL, msg); }
}

// 5. Demo
public class SimpleLoggerDemo {
    public static void main(String[] args) throws Exception {
        LogFormatter formatter = new LogFormatter("[%d] [%p] [%t] - %m");
        Appender console = new ConsoleAppender();
        Appender file = new FileAppender("app.log");

        Logger logger = new Logger(formatter, console, file);

        // Example logs from multiple threads
        Runnable task = () -> {
            for (int i = 0; i < 5; i++) {
                logger.info("Message " + i + " from " + Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(task, "Worker-1");
        Thread t2 = new Thread(task, "Worker-2");

        t1.start(); t2.start();
        t1.join(); t2.join();

        logger.error("An error occurred!");
        logger.fatal("System shutting down...");

        file.close();
    }
}
