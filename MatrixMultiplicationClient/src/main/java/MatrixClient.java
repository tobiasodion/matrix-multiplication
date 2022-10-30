import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class MatrixClient extends Thread{
    private int slaveId;
    private int taskId;
    private SubMatrixMultiplication _subMatrixMultiplication;

    private  ArrayList<ArrayList<Integer>> matrixA = new ArrayList<>();
    private  ArrayList<ArrayList<Integer>> matrixB = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> matrixC = new ArrayList<>();

    private IMatrixMultiplication obj;

    public MatrixClient(int slaveId, int slaves, ArrayList<ArrayList<Integer>> userMatrixA,
                        ArrayList<ArrayList<Integer>> userMatrixB, SubMatrixMultiplication subMatrixMultiplication){
        try{
            this._subMatrixMultiplication = subMatrixMultiplication;
            this.slaveId = slaveId;
            this.obj = (IMatrixMultiplication) Naming.lookup("rmi://localhost:12345/master");

            //initialize server with user input
            //in our case it is generated automatically on the server
            obj.initialize(slaves, userMatrixA, userMatrixB);

            //wait for server to complete initialization
            boolean wait = true;
            while(wait){
                if(obj.getStatus() == 1){
                    getSubTask(slaveId);
                    computeSubTask();
                    wait = false;
                }
            }
        }
        catch (Exception e){

        }
    }


    public MatrixClient(int slaveId, SubMatrixMultiplication subMatrixMultiplication){
        try{
            this._subMatrixMultiplication = subMatrixMultiplication;
            this.slaveId = slaveId;
            this.obj = (IMatrixMultiplication) Naming.lookup("rmi://localhost:12345/master");

            //wait for server to complete initialization
            boolean wait = true;
            while(wait){
                if(obj.getStatus() == 1){
                    getSubTask(slaveId);
                    computeSubTask();
                    wait = false;
                }
            }
        }

        catch (Exception e){

        }
    }

    private void getSubTask(int slaveId){
        try{
            //get task from master
            Task slaveTask = obj.getTask(slaveId);
            taskId = slaveTask.getTaskId();
            matrixA = slaveTask.getSplitMatrixA();
            matrixB = slaveTask.getMatrixB();
        }catch(Exception e){

        }
    }

    private void computeSubTask() throws RemoteException{
        _subMatrixMultiplication.setMatrixA(matrixA);
        _subMatrixMultiplication.setMatrixB(matrixB);
        //compute Sub matrix
        matrixC = _subMatrixMultiplication.multiplyMatrix();
        //subtask result
        System.out.println("Rows from slave client " + slaveId + ": " + matrixC);
        obj.updateResult(taskId, matrixC);
    }



    public static void main(String[] arg){
        try{
            //connect driver program to server
            IMatrixMultiplication obj = (IMatrixMultiplication) Naming.lookup("rmi://localhost:12345/master");
            //reset server
            obj.resetServer();

            //get number of slaves n
            int slaves = 2;

            //Get user inputs i.e the matrices to be multiplied
            //matrixA = getMatrix()
            //matrixB = getMatrix()
            //However, in our case it is generated automatically on the server

            //create n MatrixClient threads
            SubMatrixMultiplication subMatrixMultiplication1 = new SubMatrixMultiplication();
            //first client connected initializes the server
            MatrixClient slave1 = new MatrixClient(1,slaves, new ArrayList<>(), new ArrayList<>(), subMatrixMultiplication1);
            slave1.start();

            SubMatrixMultiplication subMatrixMultiplication2 = new SubMatrixMultiplication();
            MatrixClient slave2 = new MatrixClient(2,subMatrixMultiplication2);
            slave2.start();

            boolean wait=true;
            //wait for server to finish computation
            while(wait){
                if(obj.resultStatus()){
                    //get and display final result from server
                    ArrayList<ArrayList<Integer>> matrixC = obj.getMatrixC();
                    System.out.println("final Result: " + matrixC);
                    wait=false;
                }
            }
        }
        catch(Exception e){

        }
    }
}


