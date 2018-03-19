import org.junit.Test;

import java.io.File;
import java.io.PrintStream;

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
                macierzwag.getLiczby()[j]=1;
            }
        }


        główny.addJson(alternatywy);
        główny.addJson(goal);
        główny.getJson_list().get(1).addJson(macierzwag);
        główny.getJson_list().get(1).addJson(new Json()).nazwij("a");
        główny.getJson_list().get(1).getJson_list().get(1).addJson(macierzwag);
        główny.getJson_list().get(1).getJson_list().get(1).addJson(new Json("q")).setLiczby(macierz);
        główny.getJson_list().get(1).getJson_list().get(1).addJson(new Json("w")).setLiczby(macierz);
        główny.getJson_list().get(1).addJson(new Json()).nazwij("b").setLiczby(macierz);

        System.out.println(główny.getJson_list().get(1).getJson_list().get(0).nazwa);
        System.out.println(główny.getJson_list().get(1).getJson_list().size());
        System.out.println(główny.getJson_list().get(1).type.equals("jsonlist"));


        główny.print(System.out,0);
        File dane=new File("dane.json");
        if(!dane.exists()) {
            dane.createNewFile();
        }
        PrintStream zapis = new PrintStream(dane);
        główny.print(zapis,0);
    }

}