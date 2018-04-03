import java.io.*;
import java.util.Scanner;

public class decision_input {
    public static void main(String[] args) throws IOException {
        System.out.println("podanie i zapis -1, wczytaj z dane.json - 2 z podanego pliku - inne");
        Scanner in=new Scanner(System.in);
        int wybor=in.nextInt();
        switch (wybor) {
            case 1: {
                Drzewo_kategorii drzewo = new Drzewo_kategorii();
                drzewo.dodajalternatywy();
                drzewo.porownaj();
//            drzewo.wypisz(System.out);
                File dane = new File("dane.json");
                if (!dane.exists()) {
                    dane.createNewFile();
                }
                PrintStream zapis = new PrintStream(dane);
                drzewo.wypisz(zapis);
                System.out.println();
                break;
            }
            case 2: {
                Drzewo_kategorii drzewo;
                Json wynik = Json.read("dane.json");
                drzewo = new Drzewo_kategorii(wynik);
                wynik.print(System.out, 0);
                System.out.println();
                double[] w = drzewo.evaluate_geometric_mean();
                System.out.println("geometric mean :");
                int index_max = 0;
                for (int i = 0; i < w.length; i++) {
                    System.out.printf(" %f ", w[i]);
                    if (Math.abs(w[i]) > Math.abs(w[index_max])) {
                        index_max = i;
                    }
                }
                System.out.println();
                System.out.printf("%s jest najlepsze\n", drzewo.glowny.getJson_list().get(0).getStringi()[index_max]);
                w = drzewo.evaluate_eigenvalue();
                System.out.println("eigenvalue :");
                index_max = 0;
                for (int i = 0; i < w.length; i++) {
                    System.out.printf(" %f ", w[i]);
                    if (Math.abs(w[i]) > Math.abs(w[index_max])) {
                        index_max = i;
                    }
                }
                System.out.println();
                System.out.printf("%s jest najlepsze\n", drzewo.glowny.getJson_list().get(0).getStringi()[index_max]);
                break;
            }
            default: {
                Drzewo_kategorii drzewo;
                System.out.println("podaj nazwę lub ścieżkę do pliku");
                Json wynik = Json.read(in.next());
                drzewo = new Drzewo_kategorii(wynik);
                wynik.print(System.out, 0);
                System.out.println();
                double[] w = drzewo.evaluate_geometric_mean();
                System.out.println("geometric mean :");
                int index_max = 0;
                for (int i = 0; i < w.length; i++) {
                    System.out.printf(" %f ", w[i]);
                    if (Math.abs(w[i]) > Math.abs(w[index_max])) {
                        index_max = i;
                    }
                }
                System.out.println();
                System.out.printf("%s jest najlepsze\n", drzewo.glowny.getJson_list().get(0).getStringi()[index_max]);
                w = drzewo.evaluate_eigenvalue();
                System.out.println("eigenvalue :");
                index_max = 0;
                for (int i = 0; i < w.length; i++) {
                    System.out.printf(" %f ", w[i]);
                    if (Math.abs(w[i]) > Math.abs(w[index_max])) {
                        index_max = i;
                    }
                }
                System.out.println();
                System.out.printf("%s jest najlepsze\n", drzewo.glowny.getJson_list().get(0).getStringi()[index_max]);
                break;
            }
        }


    }
}
