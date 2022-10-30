import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

interface IMatrixMultiplication extends Remote {
    //method to receive to workload from client
    //information about the available slave clients
    //performs the array split according to the number of slave clients
    //returns an int
    void initialize(int slaves,
                   ArrayList<ArrayList<Integer>> matrixA,
                   ArrayList<ArrayList<Integer>> matrixB) throws RemoteException;

    //method receive sends a task to a particular slave client
    //method receives the slaveId of the client
    //sends a corresponding task to the client
    //the task consist of the slaveId,
        // a certain number of rows from matrixA
        // the entire matrixB
    Task getTask(int slaveId) throws RemoteException;

    //returns the status of the server task split operation
    //gives the client an indication that the task split has been completed
    int getStatus() throws RemoteException;

    //utility method to ensure that the server is in the initial state when the client is restarted
    void resetServer() throws RemoteException;

    //method returns updates the submatrix results on the server
    void updateResult(int taskId, ArrayList<ArrayList<Integer>> matrix) throws RemoteException;

    //method checks if the result is ready on the server. i.e all the results from the clients have been combined
    boolean resultStatus() throws RemoteException;

    //method gets the final result matrixC from the server
    ArrayList<ArrayList<Integer>> getMatrixC() throws RemoteException;
}
