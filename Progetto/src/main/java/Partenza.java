import java.util.Random;

public class Partenza {
    public static Long startTime;
    public static Random r;

    public static Integer getOpt() {
        return opt;
    }

    public static void setOpt(Integer opt) {
        Partenza.opt = opt;
    }

    public static Integer opt;


    public static void setR(Long seed){
        r=new Random(seed);
    }
    public static void setStartTime(){
        startTime=System.currentTimeMillis();
    }
}
