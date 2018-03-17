import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Stream;

public class Json implements Cloneable{

    String nazwa=new String("");
    double liczby[]=new double[0];
    String stringi[]=new String[0];
    Json json_tab[]= new Json[0];
    List<Json> jsony=new ArrayList<>();
    public Json cloneit(){
        Json wynik=new Json();
        wynik.nazwa=nazwa;
        wynik.liczby=liczby.clone();
        wynik.stringi=stringi.clone();
        //System.out.println(json_tab.length);
        //System.out.println(jsony.size());
        wynik.json_tab=new Json[json_tab.length];
        for(int i=0;i<json_tab.length;i++){
            wynik.json_tab[i]=json_tab[i].cloneit();
        }
        for(int i=0;i<jsony.size();i++){
            wynik.jsony.add(jsony.get(i).cloneit());
        }
        return wynik;
    }
    public int getstringsize(){
        return stringi.length;
    }
    public int getliczbysize(){
        return liczby.length;
    }
    public int getjsonssize(){
        return json_tab.length;
    }

    public void setLiczby(double[] liczby) {
        this.liczby = liczby;
    }

    public void setStringi(String[] stringi) {
        this.stringi = stringi;
    }

    public void setJson_tab(Json[] json_tab) {
        this.json_tab = json_tab;
    }

    public Json() {}

    public Json(String nazwa) {
        this.nazwa = nazwa;
    }

    public Json(double[] liczby) {
        this.liczby = liczby;
    }


    public Json(String[] stringi) {
        this.stringi = stringi;
    }

    public Json(Json[] json_tab) {
        this.json_tab = json_tab;
    }

    public Json addJson(Json jsony) {
        this.jsony.add(jsony);
        return jsony;
    }


    public Json nazwij(String nazwa) {
        this.nazwa = nazwa;
        return this;
    }
    private static void tabulatory(PrintStream out,int tabulatory){
        for(int j=0;j<tabulatory;j++){
            out.printf("\t");
        }
    }
    public void print(PrintStream out,int tabulatory){
        Scanner odczyt=new Scanner(System.in);
        //odczyt.next();
        Locale.setDefault(Locale.US);
        if(!nazwa.isEmpty()){
            tabulatory(out,tabulatory);
            out.printf("\"%s\" : ",nazwa);
        }
        if(jsony.size()>0){
//            System.out.printf("%d",jsony.size());
            out.printf("{\n");
            tabulatory(out,tabulatory);
            for(int i=0;i<jsony.size()-1;i++){
                jsony.get(i).print(out,tabulatory+1);
                out.printf(",\n");
                tabulatory(out,tabulatory);
            }
            jsony.get(jsony.size()-1).print(out,tabulatory+1);
            out.printf("\n");
            tabulatory(out,tabulatory+2);
            out.printf("}");
        }else if(stringi.length>0){
            out.printf("[ ");
            for(int i=0;i<stringi.length-1;i++){
                out.printf("\"%s\",",stringi[i]);
            }
            out.printf("\"%s\"",stringi[stringi.length-1]);
            out.printf(" ]");
        }else if(json_tab.length>0){
            out.printf("[ ");
            for(int i=0;i<json_tab.length-1;i++){
                json_tab[i].print(out,tabulatory+1);
                out.printf(",");
                //tabulatory(out,tabulatory);
            }
            json_tab[json_tab.length-1].print(out,tabulatory+1);
            out.printf(" ]");
        }else if(liczby.length>0){
            out.printf("[ ");
            for(int i=0;i<liczby.length-1;i++){
                out.printf("%2f,",liczby[i]);
            }
            out.printf("%f",liczby[liczby.length-1]);
            out.printf(" ]");
        }
    }
}
