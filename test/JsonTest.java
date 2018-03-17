import org.junit.Test;

import java.io.File;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class JsonTest {
    @Test
    public void wypisanie() throws Exception {
        Json główny=new Json();
        String[] tab ={"asd","sdf","dfg"};
        Json alternatywy = new Json("alternatywy");
        alternatywy.setStringi(tab);
        Json goal=new Json("Goal");
        double[] macierz=new double[9];
        //macierz.nazwij("macierz");
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++) {
                //macierz.liczby[3*i+j]=1;
                macierz[3*i+j]=1;
            }
        }
        Json macierzwag=new Json(new double[4]);
        macierzwag.nazwij("macierz_wag");
        for(int i=0;i<2;i++){
            for (int j=0;j<2;j++) {
                macierzwag.liczby[j]=1;
            }
        }


        główny.addJson(alternatywy);
        główny.addJson(goal);
        goal.addJson(macierzwag.cloneit());
        goal.addJson(new Json()).nazwij("a");
        goal.jsony.get(1).addJson(macierzwag.cloneit());
        goal.jsony.get(1).addJson(new Json("q")).setLiczby(macierz.clone());
        goal.jsony.get(1).addJson(new Json("w")).setLiczby(macierz.clone());
        goal.addJson(new Json()).nazwij("b").setLiczby(macierz.clone());




        główny.print(System.out,0);
        File dane=new File("dane.json");
        if(!dane.exists()) {
            dane.createNewFile();
        }
        PrintStream zapis = new PrintStream(dane);
        główny.print(zapis,0);
    }

}