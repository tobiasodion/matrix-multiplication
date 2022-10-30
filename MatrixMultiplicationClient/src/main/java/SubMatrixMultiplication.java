import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SubMatrixMultiplication {
    private  ArrayList<ArrayList<Integer>> matrixA = new ArrayList<>();
    private  ArrayList<ArrayList<Integer>> matrixB = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> matrixC = new ArrayList<>();

    private int rowA, colA, rowB, colB;

    public SubMatrixMultiplication(){

    }

    public SubMatrixMultiplication(ArrayList<ArrayList<Integer>> matrixA, ArrayList<ArrayList<Integer>> matrixB) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
    }

    public void setMatrixA(ArrayList<ArrayList<Integer>> matrixA) {
        this.matrixA = matrixA;
        rowA = matrixA.size();
        colA = matrixA.get(0).size();
    }

    public void setMatrixB(ArrayList<ArrayList<Integer>> matrixB) {
        this.matrixB = matrixB;
        rowB = matrixB.size();
        colB = matrixB.get(0).size();
    }

    private ArrayList<ArrayList<Integer>> getMatrixC() {
        return matrixC;
    }

    public ArrayList<ArrayList<Integer>> multiplyMatrix (){
        int sum=0;
        for(int i=0; i<rowA; i++){
            ArrayList<Integer> rowC = new ArrayList<Integer>();
            for (int j=0; j < colB; j++){
                for(int k=0; k<rowB; k++){
                    sum += matrixA.get(i).get(k) * matrixB.get(k).get(j);
                }
                rowC.add(sum);
                sum=0;
            }
            matrixC.add(rowC);
        }

        return matrixC;
    }

    /*public void multiplyMatrix2 (ArrayList<ArrayList<Integer>> matrixA,
                                 ArrayList<ArrayList<Integer>> matrixB,
                                 int rowA, int colA, int rowB, int colB ){
        int sum=0;
        for(int i=0; i<rowA; i++){
            ArrayList<Integer> rowD = new ArrayList<Integer>();
            for (int j=0; j < colB; j++){
                for(int k=0; k<rowB; k++){
                    sum += matrixA.get(i).get(k) * matrixB.get(k).get(j);
                }
                rowD.add(sum);
                sum=0;
            }
            matrixD.add(rowD);
        }
    }*/

    /*public static void main(String[] args){
        ArrayList<ArrayList<Integer>> matrixA = new ArrayList<>();
        ArrayList<ArrayList<Integer>> matrixB = new ArrayList<>();
        ArrayList<ArrayList<Integer>> matrixC = new ArrayList<>();

        ArrayList<Integer> row1A = new ArrayList<Integer>(Arrays.asList(3,4,5,6));
        ArrayList<Integer> row2A = new ArrayList<Integer>(Arrays.asList(5,6,5,6));
        ArrayList<Integer> row3A = new ArrayList<Integer>(Arrays.asList(3,4,5,6));
        ArrayList<Integer> row4A = new ArrayList<Integer>(Arrays.asList(5,6,5,6));
        matrixA.add(row1A);
        matrixA.add(row2A);
        matrixA.add(row3A);
        matrixA.add(row4A);

        ArrayList<Integer> row1B = new ArrayList<Integer>(Arrays.asList(3,4,5,6));
        ArrayList<Integer> row2B = new ArrayList<Integer>(Arrays.asList(5,6,5,6));
        ArrayList<Integer> row3B = new ArrayList<Integer>(Arrays.asList(3,4,5,6));
        ArrayList<Integer> row4B = new ArrayList<Integer>(Arrays.asList(5,6,5,6));
        matrixB.add(row1B);
        matrixB.add(row2B);
        matrixB.add(row3B);
        matrixB.add(row4B);

        int rowA = matrixA.size();
        int colA = matrixA.get(0).size();

        int rowB = matrixB.size();
        int colB = matrixB.get(0).size();

        //split matrix A based on the clients
        ArrayList<ArrayList<Integer>> splitMatrixA1 = new ArrayList<>();
        ArrayList<ArrayList<Integer>> splitMatrixA2 = new ArrayList<>();

        //define clusters
        int clusters = 2;

        //define partitions
        int n = rowA+1/clusters;

        //split
        splitMatrixA1.add(matrixA.get(0));
        splitMatrixA1.add(matrixA.get(1));
        splitMatrixA2.add(matrixA.get(2));
        splitMatrixA2.add(matrixA.get(3));

        int rowA1 = splitMatrixA1.size();
        int colA1 = splitMatrixA1.get(0).size();

        int rowA2 = splitMatrixA2.size();
        int colA2 = splitMatrixA2.get(0).size();

        SubMatrixMultiplication mul = new SubMatrixMultiplication();

        mul.multiplyMatrix1(splitMatrixA1, matrixB, rowA1, colA1, rowB, colB);
        ArrayList<ArrayList<Integer>> result1 = new ArrayList<>();
        result1 = mul.getMatrixC();

        /*mul.multiplyMatrix2(splitMatrixA2, matrixB, rowA2, colA2, rowB, colB);
        ArrayList<ArrayList<Integer>> result2 = new ArrayList<>();
        result2 = mul.getMatrixC();

        //combine results
        result1.addAll(result2);
        System.out.println(result1);
    }*/
}
