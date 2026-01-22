package LoggerSystem.model;

import LoggerSystem.enums.LogLevel;

import java.time.LocalTime;

public class InfoLogProcessor extends LogProcessor{
    public InfoLogProcessor(LogProcessor nextLogProcessor) {
        super(nextLogProcessor);
    }

    @Override
    public void log(LogLevel logLevel, String message){
        if (logLevel == LogLevel.INFO){
            System.out.print("["+ LocalTime.now()+"] "+"[INFO]: ");
            System.out.println(message);
        } else{
//            System.out.println("Not for INFO");
            super.log(logLevel, message);
        }
    }
}
