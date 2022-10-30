import java.io.Serializable;
import java.util.ArrayList;

class Task implements Serializable {
    private int taskId;
    private ArrayList<ArrayList<Integer>> splitMatrixA;
    private ArrayList<ArrayList<Integer>> matrixB;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public ArrayList<ArrayList<Integer>> getSplitMatrixA() {
        return splitMatrixA;
    }

    public void setSplitMatrixA(ArrayList<ArrayList<Integer>> splitMatrixA) {
        this.splitMatrixA = splitMatrixA;
    }

    public ArrayList<ArrayList<Integer>> getMatrixB() {
        return matrixB;
    }

    public void setMatrixB(ArrayList<ArrayList<Integer>> matrixB) {
        this.matrixB = matrixB;
    }
}
