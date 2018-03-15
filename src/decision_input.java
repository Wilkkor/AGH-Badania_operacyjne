import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class decision_input {
    public static void main(String[] args) throws FileNotFoundException {
        Drzewo_kategorii drzewo=new Drzewo_kategorii();
        drzewo.dodajalternatywy();
        drzewo.porownaj();
        drzewo.wypisz(System.out);
        PrintStream zapis = new PrintStream("testowy.txt");
        drzewo.wypisz(zapis);
    }
}
