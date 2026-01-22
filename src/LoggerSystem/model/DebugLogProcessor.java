package LoggerSystem.model;

import LoggerSystem.enums.LogLevel;

import java.time.LocalTime;

public class DebugLogProcessor extends LogProcessor{
    public DebugLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(LogLevel logLevel, String message){
        if (logLevel == LogLevel.DEBUG){
            System.out.print("["+ LocalTime.now()+"] "+"[DEBUG]: ");
            System.out.println(message);
        } else{
//            System.out.println("Not for DEBUG");
            super.log(logLevel, message);
        }
    }
}