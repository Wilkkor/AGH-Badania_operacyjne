import java.util.ArrayList;

public class próby {
    public static void main(String[] args) {
        Object a=new String[3];
        System.out.println(a.getClass().toString());
        a=new double[3];
        System.out.println(a.getClass().toString());
        a= new ArrayList<>();
        System.out.println(a.getClass().toString());
        a=new Json[3];
        System.out.println(a.getClass().toString());
        Json macierzwag=new Json();
        macierzwag.setLiczby(new double[5]);
        System.out.println(macierzwag.getliczbysize());
    }
}
