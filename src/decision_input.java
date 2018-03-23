import java.io.*;

public class decision_input {
    public static void main(String[] args) throws IOException {
        Drzewo_kategorii drzewo=new Drzewo_kategorii();
        drzewo.dodajalternatywy();
        drzewo.porownaj();
        drzewo.wypisz(System.out);
        File dane=new File("dane.json");
        if(!dane.exists()) {
            dane.createNewFile();
        }
        PrintStream zapis = new PrintStream(dane);
        drzewo.wypisz(zapis);
        System.out.println();
        Json wynik=Json.read("dane.json");
        wynik.print(System.out,0);
    }
}
