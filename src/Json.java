import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Json{
    String nazwa= "";
    String type="";
    private Object zawartosc =new Object();

//    double liczby[]=new double[0];
//    String stringi[]=new String[0];
//    Json json_tab[]= new Json[0];
//    List<Json> jsony=new ArrayList<>();
    public Json clone(){
        Json wynik=new Json();
        wynik.nazwa=nazwa;
        wynik.type=type;
        switch (type) {
            case "string": {
                String tmp[] = (String[]) zawartosc;
                wynik.zawartosc = tmp.clone();
                break;
            }
            case "double": {
                double tmp[] = (double[]) zawartosc;
                wynik.zawartosc = tmp.clone();
                break;
            }
            case "jsontab": {
                Json tmp[] = (Json[]) zawartosc;
                Json do_przypisania[] = new Json[tmp.length];
                for (int i = 0; i < tmp.length; i++) {
                    do_przypisania[i] = tmp[i].clone();
                }
                wynik.zawartosc = do_przypisania;
                break;
            }
            case "jsonlist": {
                List<Json> tmp = (ArrayList) zawartosc;
                List<Json> do_przypisania = new ArrayList<>();
                for (Json x : tmp) {
                    do_przypisania.add(x.clone());
                }
                wynik.zawartosc = do_przypisania;
                break;
            }
        }
        return wynik;
    }
    public int getstringsize(){
        if(type.equals("string")){
            String tmp[]=(String[]) zawartosc;
            return tmp.length;
        }
        else return 0;
    }
    public int getliczbysize(){
        if(type.equals("double")){
            double tmp[]=(double[]) zawartosc;
            return tmp.length;
        }
        else return 0;
    }
    public int getjsonssize(){
        if(type.equals("jsontab")){
            Json tmp[]=(Json[]) zawartosc;
            return tmp.length;
        }
        else return 0;
    }

    public void setLiczby(double[] liczby) {
        this.zawartosc = liczby.clone();
        type="double";
    }

    public void setStringi(String[] stringi) {
        this.zawartosc = stringi.clone();
        type="string";
    }

    public void setJson_tab(Json[] json_tab) {
        this.zawartosc = json_tab.clone();
        type="jsontab";
    }

    public double[] getLiczby() {
        return type.equals("double")?(double[]) zawartosc :null;
    }

    public String[] getStringi() {
        return (String[]) zawartosc;
    }

    public Json[] getJson_tab() {
        return (Json[]) zawartosc;
    }

    public List<Json> getJson_list() {
        return (ArrayList) zawartosc;
    }

    public Json() {}

    public Json(String nazwa) {
        this.nazwa = nazwa;
    }

    public Json(double[] liczby) {
        this.zawartosc = liczby.clone();
        type="double";
    }

    public Json(String[] stringi) {
        this.zawartosc = stringi.clone();
        type="string";
    }

    public Json(Json[] json_tab) {
        this.zawartosc = json_tab.clone();
        type="jsontab";
    }

    public Json addJson(Json json) {
        if(!type.equals("jsonlist")){
            zawartosc =new ArrayList<Json>();
            type="jsonlist";
        }
        List<Json> tmp = (ArrayList<Json>) zawartosc;
        tmp.add(json.clone());
        return tmp.get(tmp.size()-1);
//        return null;
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
//        Scanner odczyt=new Scanner(System.in);
        //odczyt.next();
        Locale.setDefault(Locale.US);
        if(!nazwa.isEmpty()){
            tabulatory(out,tabulatory);
            out.printf("\"%s\" : ",nazwa);
        }
//        System.out.printf("to %s typ ",type);
        switch (type) {
            case "jsonlist":
                List<Json> jsony = getJson_list();
                out.printf("{\n");
                tabulatory(out, tabulatory);
                for (int i = 0; i < jsony.size() - 1; i++) {
                    jsony.get(i).print(out, tabulatory + 1);
                    out.printf(",\n");
                    tabulatory(out, tabulatory);
                }
                jsony.get(jsony.size() - 1).print(out, tabulatory + 1);
                out.printf("\n");
                tabulatory(out, tabulatory + 2);
                out.printf("}");
                break;
            case "string":
                String stringi[] = (String[]) zawartosc;
                out.printf("[ ");
                for (int i = 0; i < stringi.length - 1; i++) {
                    out.printf("\"%s\",", stringi[i]);
                }
                out.printf("\"%s\"", stringi[stringi.length - 1]);
                out.printf(" ]");
                break;
            case "jsontab":
                Json json_tab[] = (Json[]) zawartosc;
                out.printf("[ ");
                for (int i = 0; i < json_tab.length - 1; i++) {
                    json_tab[i].print(out, tabulatory + 1);
                    out.printf(",");
                    //tabulatory(out,tabulatory);
                }
                json_tab[json_tab.length - 1].print(out, tabulatory + 1);
                out.printf(" ]");
                break;
            case "double":
                double liczby[] = (double[]) zawartosc;
                out.printf("[ ");
                for (int i = 0; i < liczby.length - 1; i++) {
                    out.printf("%2f,", liczby[i]);
                }
                out.printf("%f", liczby[liczby.length - 1]);
                out.printf(" ]");
                break;
            default:
                System.out.println("nieee nie tak");
        }
    }
    static Json read(String nazwa_wejscia) throws IOException {
        FileInputStream wejscie=new FileInputStream(nazwa_wejscia);
        byte[] buf=new byte[100];
        String w_stringu;
        StringBuilder do_wczytywania=new StringBuilder();
        while(wejscie.read(buf,0,100)>0){
            do_wczytywania.append(buf);
        }
        w_stringu=usun_biale_znaki(do_wczytywania);
        return Json_creaton(w_stringu);
    }
    static String usun_biale_znaki(StringBuilder wynik){
        boolean w_cudzysłowiu=false;
        int i=0;
        while(i<wynik.length()){
            if(wynik.charAt(i)<33){
                wynik.delete(i,i+1);
            }
        }
        return wynik.toString();
    }
    static Json Json_creaton(String w_stringu){
        Json wynik =new Json();
        return wynik;
    }

}
