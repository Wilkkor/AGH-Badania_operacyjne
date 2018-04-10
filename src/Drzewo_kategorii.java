//import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;
import Jama.*;
//import java.util.stream.Stream;

public class Drzewo_kategorii {
    private Scanner odczyt = new Scanner(System.in);
    Json glowny =new Json();
    private Json alternatywy=new Json("alternatives");

    public Drzewo_kategorii(){}

    public Drzewo_kategorii(Json glowny){
        this.glowny =glowny;
        this.alternatywy=glowny.getJson_list().get(0);
    }

    public void dodajalternatywy() {
        int ilosc_alternatyw;
        System.out.println("podaj ile alternatyw");
        ilosc_alternatyw=odczyt.nextInt();
        alternatywy.setStringi(tworzenie_alternatyw(ilosc_alternatyw));
        glowny.addJson(alternatywy);
    }

    public void porownaj() {
        Json goal = new Json("Goal");
        glowny.addJson(goal);
        dodajpodkategorie(glowny.getJson_list().get(glowny.getJson_list().size()-1));
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
                if(i==j){
                    macierz.getLiczby()[wymiar * i + j] = 1;
                }else {
                    System.out.printf("ile razy ważniejsze jest %s ,od %s ", nazwy[i], nazwy[j]);
                    macierz.getLiczby()[wymiar * i + j] = odczyt.nextDouble();
                }
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
                if(i==j){
                    tab[i*alternatywy.getstringsize()+j]=1;
                }
                else {
                    System.out.printf("ile razy w %s lepsze jest %s ,od %s ", kategoria.nazwa, alternatywy.getStringi()[i], alternatywy.getStringi()[j]);
                    tab[i * alternatywy.getstringsize() + j] = odczyt.nextDouble();
                }
            }
            //macierz.json_tab[i]=new Json(tab);
        }
        kategoria.setLiczby(tab);
        return kategoria;
    }

    public String[] tworzenie_alternatyw(int ilosc_alternatyw){
        String[] alternatywy = new String[ilosc_alternatyw];
        for (int i=0;i<ilosc_alternatyw;i++){
            System.out.println("podaj alternatywy");
            alternatywy[i]=odczyt.next();
        }
        return alternatywy;
    }

    public void wypisz(PrintStream out){
        glowny.print(out,0);
    }

    public double[] evaluate_geometric_mean(){
        return policz_wektor(glowny.getJson_list().get(1),1);
    }
    public double[] evaluate_eigenvalue(){
        return policz_wektor(glowny.getJson_list().get(1),2);
    }

    private double[] policz_wektor(Json element, int typ_obliczania){
        switch (element.type) {
            case "double":
//                System.out.println("Jest double");
                if (typ_obliczania == 1) {
                    return geometric_mean_count(element);
                } else {
                    return eigenvalue_count(element);
                }
            case "jsonlist":
                List<Json> list = element.getJson_list();
                double[] w = new double[glowny.getJson_list().get(0).getStringi().length];
                double[] w_podkategorii = new double[list.size() - 1];
                double[][] w_z_podkategorii = new double[list.size() - 1][];
                w_podkategorii = policz_wektor(list.get(0),typ_obliczania);
                for (int i = 1; i < list.size(); i++) {
                    w_z_podkategorii[i - 1] = policz_wektor(list.get(i),typ_obliczania);
                }
                for (int i = 0; i < w.length; i++) {
                    w[i] = 0;
                    for (int j = 0; j < w_podkategorii.length; j++) {
                        w[i] += w_podkategorii[j] * w_z_podkategorii[j][i];
                    }
                }
//                for (int i = 0; i < w.length; i++) {
//                    System.out.printf(" %f ",w[i]);
//                }
//                System.out.println();
                return w;
            default:
                System.err.println("nieeee");
                return null;
        }
    }

    private double[] geometric_mean_count(Json element) {
        double[] macierz=element.getLiczby();
        double[] w=new double[(int)Math.sqrt(macierz.length)];
        for(int i=0;i<w.length;i++){
            w[i]=1;
            for (int j=0;j<w.length;j++){
                w[i]*=macierz[i*w.length+j];
            }
            w[i]=Math.pow(w[i],(double)1/w.length);//to nie działa
        }
        double s=0;
        for (double wartosc : w) {
            s += wartosc;
        }
        for(int i=0;i<w.length;i++){
            w[i]/=s;
        }
        return w;
    }

    private double[] eigenvalue_count(Json element) {
        Matrix matrix=new Matrix(element.getLiczby(),(int)Math.sqrt(element.getLiczby().length));
        double[] eigenvalues=matrix.eig().getRealEigenvalues();
        int max=0;
        for(int i=0;i<eigenvalues.length;i++){
            if(eigenvalues[i]>eigenvalues[max]){
                max=i;
            }
        }
        double[] w=new double[matrix.eig().getRealEigenvalues().length];
        Matrix a=matrix.eig().getV();
        double s=0;
        for(int i=0;i<w.length;i++){
            w[i]=1/a.get(i,max);
            s+=w[i];
        }
        for (int i=0;i<w.length;i++) {
            w[i]/=s;
        }
        return w;
    }
}
