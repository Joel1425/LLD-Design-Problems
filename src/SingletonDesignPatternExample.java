class Logger{
    private static volatile Logger logger;
    private Logger(){}
    public static Logger getLogger(){
        if (logger == null){
            synchronized (Logger.class){
                if (logger == null) {
                    logger = new Logger();
                }
            }
        }
        return logger;
    }
    public void Log(String log){
        System.out.println("[LOG] "+log);
    }

}
public class SingletonDesignPatternExample {
    public static void main(String[] args) {
        Logger logger1 = Logger.getLogger();
        logger1.Log("Sample Case 1");
        Logger logger2 = Logger.getLogger();
        logger2.Log("Sample Case 2");
        System.out.println(logger1==logger2);
    }
}
