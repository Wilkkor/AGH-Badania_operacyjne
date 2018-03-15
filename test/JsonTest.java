import org.junit.Test;

import static org.junit.Assert.*;

public class JsonTest {
    @Test
    public void wypisanie() throws Exception {
        Json główny=new Json();
        String[] tab ={"asd","sdf","dfg"};
        Json alternatywy = new Json("alternatywy");
        alternatywy.setStringi(tab);
        Json goal=new Json("Goal");
        Json[] macierzin=new Json[3];
        double[] liczby;
        for(int i=0;i<3;i++){
            liczby=new double[3];
            for (int j=0;j<3;j++) {
                liczby[j]=1;
            }
            macierzin[i]=new Json(liczby);
        }
        Json macierz=new Json(macierzin);
        macierz.nazwij("macierz");
        Json[] macierzinwag=new Json[2];
        for(int i=0;i<2;i++){
            liczby=new double[2];
            for (int j=0;j<2;j++) {
                liczby[j]=1;
            }
            macierzinwag[i]=new Json(liczby);
        }
        Json macierzwag=new Json(macierzinwag);
        macierzwag.nazwij("macierz_wag");


        główny.addJson(alternatywy);
        główny.addJson(goal);
        goal.addJson(macierzwag.cloneit());
        goal.addJson(new Json()).nazwij("a");
        goal.jsony.get(1).addJson(macierzwag.cloneit());
        goal.jsony.get(1).addJson(new Json("q")).addJson(macierz.cloneit());
        goal.jsony.get(1).addJson(new Json("w")).addJson(macierz.cloneit());
        goal.addJson(new Json()).nazwij("b").addJson(macierz.cloneit());




        główny.print(System.out,0);
    }

}