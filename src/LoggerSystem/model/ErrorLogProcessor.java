package LoggerSystem.model;

import LoggerSystem.enums.LogLevel;

import java.time.LocalTime;

public class ErrorLogProcessor extends LogProcessor{
    public ErrorLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(LogLevel logLevel, String message){
        if (logLevel == LogLevel.ERROR){
            System.out.print("["+ LocalTime.now()+"] "+"[ERROR]: ");
            System.out.println(message);
        } else{
//            System.out.println("Not for ERROR");
            super.log(logLevel, message);
        }
    }
}
