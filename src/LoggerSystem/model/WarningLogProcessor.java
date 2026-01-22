package LoggerSystem.model;

import LoggerSystem.enums.LogLevel;

import java.time.LocalTime;

public class WarningLogProcessor extends LogProcessor{
    public WarningLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(LogLevel logLevel, String message){
        if (logLevel == LogLevel.WARNING){
            System.out.print("["+ LocalTime.now()+"] "+"[WARNING]: ");
            System.out.println(message);
        } else{
//            System.out.println("Not for WARNING");
            super.log(logLevel, message);
        }
    }
}
