import java.util.ArrayList;
import java.util.List;

public class Json {

    private String nazwa;
    private double liczby[];
    private String stringi[];
    private Json json_tab[];
    private List<Json> jsony=new ArrayList<>();

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

    public void addJsony(Json jsony) {
        this.jsony.add(jsony);
    }


    public void nazwij(String nazwa) {
        this.nazwa = nazwa;
    }

}
