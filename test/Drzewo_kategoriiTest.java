import Jama.Matrix;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Random;

public class Drzewo_kategoriiTest {
    @Test
    public void macierze() throws  Exception{
        double[] a={1,19,0.9,1};
        Matrix matrix=new Matrix(a,2);
        double[] eigenvalues=matrix.eig().getRealEigenvalues();
        int max=0;
        for(int i=0;i<eigenvalues.length;i++){
            if(eigenvalues[i]>eigenvalues[max]){
                max=i;
            }
        }
        double[] w=new double[matrix.eig().getRealEigenvalues().length];
        Matrix V=matrix.eig().getV();
        for(int i=0;i<w.length;i++){
            w[i]=V.get(i,max);
        }
        for (double[] x:V.getArrayCopy()) {
            for (double y:x) {
                System.out.printf(" %f ",y);
            }
            System.out.println();
        }
        System.out.println();
        for (double x:eigenvalues) {
            System.out.printf(" %f ",x);
        }
        System.out.println();
        System.out.println();
        for (double x:w) {
            System.out.printf(" %f ",x);
        }
    }

}