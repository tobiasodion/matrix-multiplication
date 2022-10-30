import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class MatrixMultiplicationMaster extends UnicastRemoteObject implements IMatrixMultiplication {
    private int slaves;
    private int returnedResultCount = 0;
    private boolean resultReady = false;

    private ArrayList<ArrayList<Integer>> matrixA = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> matrixB = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> matrixC = new ArrayList<>();

    private Hashtable<Integer, ArrayList<ArrayList<Integer>>> tasks = new Hashtable<Integer, ArrayList<ArrayList<Integer>>>();
    private int status = 0;
    private int rowA, colA, rowB, colB;

    private Hashtable<Integer, ArrayList<ArrayList<Integer>>> clientResults = new Hashtable<Integer, ArrayList<ArrayList<Integer>>>();

    public MatrixMultiplicationMaster() throws RemoteException {
        super();
        generateMatrix();
    }

    @Override
    public int getStatus() throws RemoteException {
        return status;
    }

    @Override
    public void initialize(int slaves, ArrayList<ArrayList<Integer>> matrixA, ArrayList<ArrayList<Integer>> matrixB) throws RemoteException {
        this.slaves = slaves;
        splitMatrix();
        status = 1;
    }

    @Override
    public void updateResult(int taskId, ArrayList<ArrayList<Integer>> matrix) throws RemoteException {
        clientResults.put(taskId, matrix);
        returnedResultCount++;
        if(returnedResultCount == slaves){
            combineResult();
            resultReady = true;
        }
    }

    @Override
    public boolean resultStatus() throws RemoteException {
        return resultReady;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getMatrixC() throws RemoteException {
        return matrixC;
    }

    @Override
    public Task getTask(int slaveId) throws RemoteException{
        Task task = new Task();
        task.setTaskId(slaveId);
        task.setMatrixB(matrixB);
        task.setSplitMatrixA(tasks.get(slaveId));

        return task;
    }

    @Override
    public void resetServer() throws RemoteException{
        matrixA = new ArrayList<>();
        matrixB = new ArrayList<>();
        generateMatrix();
    }

    private void combineResult(){
        for(int i=1; i<=slaves; i++){
            matrixC.addAll(clientResults.get(i));
        }
    }

    private void splitMatrix(){
        //split matrix A based on the clients
        ArrayList<ArrayList<Integer>> splitMatrixA1 = new ArrayList<>();
        splitMatrixA1.add(matrixA.get(0));
        splitMatrixA1.add(matrixA.get(1));
        tasks.put(1,splitMatrixA1);

        ArrayList<ArrayList<Integer>> splitMatrixA2 = new ArrayList<>();
        splitMatrixA2.add(matrixA.get(2));
        splitMatrixA2.add(matrixA.get(3));
        tasks.put(2,splitMatrixA2);
    }

    private void generateMatrix(){
        ArrayList<Integer> row1A = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
        ArrayList<Integer> row2A = new ArrayList<Integer>(Arrays.asList(5,6,7,8));
        ArrayList<Integer> row3A = new ArrayList<Integer>(Arrays.asList(9,10,11,12));
        ArrayList<Integer> row4A = new ArrayList<Integer>(Arrays.asList(13,14,15,16));
        matrixA.add(row1A);
        matrixA.add(row2A);
        matrixA.add(row3A);
        matrixA.add(row4A);

        ArrayList<Integer> row1B = new ArrayList<Integer>(Arrays.asList(17,18,19,20));
        ArrayList<Integer> row2B = new ArrayList<Integer>(Arrays.asList(21,22,23,24));
        ArrayList<Integer> row3B = new ArrayList<Integer>(Arrays.asList(25,26,27,28));
        ArrayList<Integer> row4B = new ArrayList<Integer>(Arrays.asList(29,30,31,32));
        matrixB.add(row1B);
        matrixB.add(row2B);
        matrixB.add(row3B);
        matrixB.add(row4B);

        rowA = matrixA.size();
        colA = matrixA.get(0).size();

        rowB = matrixB.size();
        colB = matrixB.get(0).size();
    }

    public static void main(String args[]) {
        try {
            // new instance MatrixMultiplicationMaster
            MatrixMultiplicationMaster obj = new MatrixMultiplicationMaster();

            // start RMIRegistry: port 12345
            // Alternative: start rmiregistry in terminal !
            java.rmi.registry.LocateRegistry.createRegistry(12345);

            // register the object
            Naming.rebind("rmi://localhost:12345/master", obj);

            System.out.println("Master is bound in registry");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
