import org.junit.Test;

import java.io.File;
import static org.junit.Assert.*;
import java.io.PrintStream;

public class JsonTest {
    Json create(){Json glowny=new Json();
        String[] tab ={"asd","sdf","dfg"};
        Json alternatywy = new Json("alternatives");
        alternatywy.setStringi(tab);
        Json goal=new Json("Goal");
        double[] macierz=new double[9];
        //macierz.nazwij("macierz");
        //ten.typ=="double"
        for(int i=0;i<3;i++){
            for (int j=0;j<3;j++) {
                //macierz.liczby[3*i+j]=1;
                macierz[3*i+j]=1;
            }
        }
        Json macierzwag=new Json(new double[4]);
        macierzwag.nazwij("matrix");
        for(int i=0;i<2;i++){
            for (int j=0;j<2;j++) {
                macierzwag.getLiczby()[j]=1;
            }
        }
        glowny.addJson(alternatywy);
        glowny.addJson(goal);
        glowny.getJson_list().get(1).addJson(macierzwag);
        glowny.getJson_list().get(1).addJson(new Json()).nazwij("a");
        glowny.getJson_list().get(1).getJson_list().get(1).addJson(macierzwag);
        glowny.getJson_list().get(1).getJson_list().get(1).addJson(new Json("q")).setLiczby(macierz);
        glowny.getJson_list().get(1).getJson_list().get(1).addJson(new Json("w")).setLiczby(macierz);
        glowny.getJson_list().get(1).addJson(new Json()).nazwij("b").setLiczby(macierz);
        return glowny;
    }
    @Test
    public void wypisanie() throws Exception {
        Json glowny=create();
        glowny.print(System.out,0);
        File dane=new File("dane.json");
        if(!dane.exists()) {
            dane.createNewFile();
        }
        PrintStream zapis = new PrintStream(dane);
//        glowny.print(zapis,0);
    }
    @Test
    public void equal() throws Exception {
        Json glowny=create();
        assert glowny.equal(glowny);
    }
    @Test
    public void wczytanie() throws Exception {
        Json glowny=create();
        File dane=new File("dane.json");
        if(!dane.exists()) {
            dane.createNewFile();
        }
        PrintStream zapis = new PrintStream(dane);
        glowny.print(zapis,0);
        Json wynik=Json.read("dane.json");
//        glowny.print(System.out,0);
//        wynik.print(System.out,0);
        assert wynik.equal(glowny);
    }

}