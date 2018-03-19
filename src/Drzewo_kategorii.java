//import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
//import java.util.stream.Stream;

public class Drzewo_kategorii {
    private Scanner odczyt = new Scanner(System.in);
    private Json głowny=new Json();
    private Json alternatywy=new Json("alternatywy");
    public void dodajalternatywy() {
        głowny.addJson(alternatywy);
        int ilosc_alternatyw;
        System.out.println("podaj ile alternatyw");
        ilosc_alternatyw=odczyt.nextInt();
        alternatywy.setStringi(tworzenie_alternatyw(ilosc_alternatyw));
    }

    public void porownaj() {
        Json goal = new Json("Goal");
        głowny.addJson(goal);
        dodajpodkategorie(goal);
    }

    public void dodajpodkategorie(Json kategoria){
        System.out.printf("czy ta kategoria (%s) ma miec podkategorie ? (1-ma miec, inne-nie ma miec)",kategoria.nazwa);
        int wybor=odczyt.nextInt();
        if(wybor==1){
            System.out.println("ile ma miec podkategorii ?");
            int ilosc_podkategorii=odczyt.nextInt();
            dodajpodkategorieczesciowe(ilosc_podkategorii,kategoria);
        }
        else{
            dodajmacierz(kategoria);
        }
        //return null;
    }

    public void dodajpodkategorieczesciowe(int ilosc_podkategorii, Json kategoria){
        String nazwy[]=new String[ilosc_podkategorii];
        System.out.println("podaj nazwy podkategorii");
        for(int i=0;i<ilosc_podkategorii;i++){
            nazwy[i]=odczyt.next();
        }
        System.out.println("teraz zdecyduj ktore podkategorie są ważniejsze");
        dodajmacierzwag(kategoria,ilosc_podkategorii,nazwy);
        for(int i=0;i<ilosc_podkategorii;i++){
            Json tmp=new Json(nazwy[i]);
            dodajpodkategorie(tmp);
            kategoria.addJson(tmp);
        }
    }

    public Json dodajmacierzwag(Json kategoria,int wymiar,String[] nazwy){
        Json macierz = new Json("matrix");
        macierz.setLiczby(new double[wymiar*wymiar]);
        //double tab[];
        for(int i=0;i<wymiar;i++){
            //tab=new double[wymiar];
            for(int j=0;j<wymiar;j++){
                System.out.printf("ile razy ważniejsze jest %s ,od %s ",nazwy[i],nazwy[j]);
                macierz.getLiczby()[wymiar*i+j]=odczyt.nextDouble();
            }
            //macierz.json_tab[i]=new Json(tab);
        }
        kategoria.addJson(macierz);
        return macierz;
    }

    public Json dodajmacierz(Json kategoria){
        Json macierz = new Json();
        double tab[]=new double[alternatywy.getstringsize()*alternatywy.getstringsize()];
        //macierz.liczby=new double[alternatywy.stringi.length*alternatywy.stringi.length];
        for(int i=0;i<alternatywy.getstringsize();i++){
            //tab=new double[alternatywy.stringi.length];
            for(int j=0;j<alternatywy.getstringsize();j++){
                System.out.printf("ile razy w %s lepsze jest %s ,od %s ",kategoria.nazwa,alternatywy.getStringi()[i],alternatywy.getStringi()[j]);
                tab[i*alternatywy.getstringsize()+j]=odczyt.nextDouble();
            }
            //macierz.json_tab[i]=new Json(tab);
        }
        kategoria.setLiczby(tab);
        return kategoria;
    }

    public String[] tworzenie_alternatyw(int ilosc_alternatyw){
        String[] alternatywy = new String[ilosc_alternatyw];
        for (int i=0;i<ilosc_alternatyw;i++){
            System.out.println("podaj alternatywe");
            alternatywy[i]=odczyt.next();
        }
        return alternatywy;
    }
    public void wypisz(PrintStream out){
        //out.println(głowny.jsony.size());
        głowny.print(out,0);
    }
}
