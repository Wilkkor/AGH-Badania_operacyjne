import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Json{
    String nazwa= "";
    String type="";
    private Object zawartosc =new Object();

//    double liczby[]=new double[0];
//    String stringi[]=new String[0];
//    Json json_tab[]= new Json[0];
//    List<Json> jsony=new ArrayList<>();
    public boolean equal(Json drugi){
        if(nazwa.length()>1&&drugi.nazwa.length()>1&&!nazwa.equals(drugi.nazwa)||!type.equals(drugi.type)){
            return false;
        }
        switch (type) {
            case "string": {
                String tmp[] = (String[]) zawartosc;
                String tmp2[] = (String[]) drugi.zawartosc;
                for(int i=0;i<tmp.length;i++){
                    if(!tmp[i].equals(tmp2[i])){
                        return false;
                    }
                }
                break;
            }
            case "double": {
                double tmp[] = (double[]) zawartosc;
                double tmp2[] = (double[]) drugi.zawartosc;
                for(int i=0;i<tmp.length;i++){
                    if(tmp[i]!=tmp2[i]){
                        return false;
                    }
                }
                break;
            }
            case "jsontab": {
                Json tmp[] = (Json[]) zawartosc;
                Json tmp2[] = (Json[]) drugi.zawartosc;
                for(int i=0;i<tmp.length;i++){
                    if(!tmp[i].equal(tmp2[i])){
                        return false;
                    }
                }
                break;
            }
            case "jsonlist": {
                List<Json> tmp = (ArrayList) zawartosc;
                List<Json> tmp2 = (ArrayList) drugi.zawartosc;
                for(int i=0;i<tmp.size();i++){
                    if(!tmp.get(i).equal(tmp2.get(i))){
                        System.out.printf("sss %s sss %s ",nazwa,drugi.nazwa);
                        return false;
                    }
                }
                break;
            }
        }
        return true;
    }
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
        Scanner odczyt = new Scanner(new File(nazwa_wejscia));
        String w_stringu;
        StringBuilder do_wczytywania=new StringBuilder();
        while(odczyt.hasNext()){
            do_wczytywania.append(odczyt.nextLine());
        }
        w_stringu=usun_biale_znaki(do_wczytywania);
        return Json_creaton(w_stringu);
        //return null;
    }
    static String usun_biale_znaki(StringBuilder wynik){
        boolean w_cudzysłowiu=false;
        int i=0;
        while(i<wynik.length()){
            if(wynik.charAt(i)<33&&w_cudzysłowiu==false){
                wynik.delete(i,i+1);
                i=i-1;
            }
            else if(wynik.charAt(i)=='\"'){
                w_cudzysłowiu=!w_cudzysłowiu;
            }
            i++;
        }
        return wynik.toString();
    }
    static private Json Json_creaton(String w_stringu){
        Json wynik =new Json();
        int i=0,w_klamrze=0,w_tablicy=0;//tablica/lista
        StringBuilder nazwa=new StringBuilder();
        if(w_stringu.charAt(i)=='"'){
            i++;
            while(w_stringu.charAt(i)!='"'){
                nazwa.append(w_stringu.charAt(i));
                i++;
            }
            wynik.nazwij(nazwa.toString());
            i=i+2;
        }
        if(w_stringu.charAt(i)=='{'){
            tworzenie_listy_jsonow(w_stringu, wynik, i);
        }else if(w_stringu.charAt(i)=='['){//tablica stringów double-ów i jsonów
            i++;
            if(w_stringu.charAt(i)=='"'){
                tworzenie_tablicy_stringów(w_stringu, wynik, i);
            }else if(w_stringu.charAt(i)<='9'&&w_stringu.charAt(i)>='0'){
                tworzenie_tablicy_doubli(w_stringu, wynik, i);
            }else{
                tworzenie_tablicy_jsonow(w_stringu, wynik, i);
            }
        }

        return wynik;
    }

    private static void tworzenie_tablicy_jsonow(String w_stringu, Json wynik, int i) {
        System.err.println("nie zaimplementowane");
    }

    private static void tworzenie_listy_jsonow(String w_stringu, Json wynik, int i) {
        StringBuilder zawartosc=new StringBuilder();
        int w_klamrze=1;
        int w_tablicy=0;
        i++;
        while(w_klamrze!=1||w_stringu.charAt(i)!='}'){//to nie działa z tabliczmi sprawdzać w_tablicy
            if(w_stringu.charAt(i)=='{'){
                w_klamrze++;
            }else if(w_stringu.charAt(i)=='}'){
                w_klamrze--;
            }else if(w_stringu.charAt(i)=='['){
                w_tablicy++;
            }else if(w_stringu.charAt(i)==']'){
                w_tablicy--;
            }
            if(w_stringu.charAt(i)==','&&w_klamrze==1&&w_tablicy==0){
                wynik.addJson(Json_creaton(zawartosc.toString()));
                zawartosc.delete(0,zawartosc.length());
            }else{
                zawartosc.append(w_stringu.charAt(i));
            }
            i++;
        }
        wynik.addJson(Json_creaton(zawartosc.toString()));
        zawartosc.delete(0,zawartosc.length());
    }

    private static void tworzenie_tablicy_doubli(String w_stringu, Json wynik, int i) {
        int w_tablicy=1;
        int w_klamrze=0;
        StringBuilder zawartosc=new StringBuilder();
        int j=i,s=1;
        int w_tablicypom=1;
        while(w_tablicypom>0&&j<w_stringu.length()){
            if(w_stringu.charAt(j)==','&&w_tablicypom==1){
                s++;
            }else if(w_stringu.charAt(i)=='['){
                w_tablicypom++;
            }else if(w_stringu.charAt(i)==']'){
                w_tablicypom--;
            }
            j++;
        }
        double tablica_doubli[]=new double[s];
        int indeks_tablicy=0,indeks_do_parsowania_liczby,liczba_liczb_po_przecinku;
        String liczba_w_stringu;
        while(w_tablicy!=1||w_stringu.charAt(i)!=']'){
            if(w_stringu.charAt(i)=='['){
                w_tablicy++;
            }else if(w_stringu.charAt(i)==']'){
                w_tablicy--;
            }else if(w_stringu.charAt(i)==','&&w_tablicy==1){
                tablica_doubli[indeks_tablicy]=zliczbuj(zawartosc);
                indeks_tablicy++;
                zawartosc.delete(0,zawartosc.length());
            }else{
                zawartosc.append(w_stringu.charAt(i));
            }
            i++;
        }
        tablica_doubli[indeks_tablicy]=zliczbuj(zawartosc);
        wynik.setLiczby(tablica_doubli);
    }

    private static void tworzenie_tablicy_stringów(String w_stringu, Json wynik, int i) {
        StringBuilder zawartosc=new StringBuilder();
        int w_tablicy=1;
        int w_klamrze=0;
        int j=i,s=1;
        int w_tablicypom=1;
        while(w_tablicypom>0&&j<w_stringu.length()){
            if(w_stringu.charAt(j)==','&&w_tablicypom==1){
                s++;
            }else if(w_stringu.charAt(i)=='['){
                w_tablicypom++;
            }else if(w_stringu.charAt(i)==']'){
                w_tablicypom--;
            }else if(w_stringu.charAt(i)=='{'){
                w_klamrze++;
            }else if(w_stringu.charAt(i)=='}'){
                w_klamrze--;
            }
            j++;
        }
        String tablica_stringow[]=new String[s];
        int indeks_tablicy=0;
        while(w_tablicy!=1||w_stringu.charAt(i)!=']'){
            if(w_stringu.charAt(i)=='['){
                w_tablicy++;
            }else if(w_stringu.charAt(i)==']'){
                w_tablicy--;
            }else if(w_stringu.charAt(i)=='{'){
                w_klamrze++;
            }else if(w_stringu.charAt(i)=='}'){
                w_klamrze--;
            }else if(w_stringu.charAt(i)==','&&w_tablicy==1){
                tablica_stringow[indeks_tablicy]=zawartosc.toString();
                indeks_tablicy++;
                zawartosc.delete(0,zawartosc.length());
            }else if(w_stringu.charAt(i)!='"'){
                zawartosc.append(w_stringu.charAt(i));
            }
            i++;
        }
        tablica_stringow[indeks_tablicy]=zawartosc.toString();
        zawartosc.delete(0,zawartosc.length());
        wynik.setStringi(tablica_stringow);
    }

    private static double zliczbuj(StringBuilder zawartosc) {
        String liczba_w_stringu=zawartosc.toString();
        double liczba=0;
        boolean po_kropce=false;
        int cyfry_po_kropce=0;
        for(int i=0;i<liczba_w_stringu.length();i++){
            if(liczba_w_stringu.charAt(i)=='.'){
                po_kropce=true;
                continue;
            }
            liczba=10*liczba+liczba_w_stringu.charAt(i)-'0';
            if(po_kropce){
                cyfry_po_kropce++;
            }
        }
        for (int i=0;i<cyfry_po_kropce;i++){
            liczba/=10;
        }
        return liczba;
    }

}
