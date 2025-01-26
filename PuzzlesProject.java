import java.util.Scanner;
import java.util.ArrayList;
public class PuzzlesProject {
    static Scanner in = new Scanner(System.in);
    static int[][] AStarboard= new int[4][4];
    static int[][] IDAStarboard= new int[4][4];
    static ArrayList<State> AStarFrontier=new ArrayList<State>();
    static ArrayList<State> IDAStarFrontier=new ArrayList<State>();
    public static void main(String[] args) {
        getInputs();
        AStar();
        IDAStar();
    }
    public static void getInputs(){
        System.out.println("Please inter the puzzle(4*4)");
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                AStarboard[i][j]=in.nextInt();
                IDAStarboard[i][j]=AStarboard[i][j];
            }
        }
    }
    public static void AStar(){
        System.out.println("_____________________A*______________________");
        State firstState=new State(AStarboard);
        AStarFrontier.add(firstState);
        State expendNode;
        while (true) {            
            expendNode=AStarFrontier.get(0);
            for(int i=1;i<AStarFrontier.size();i++){
                if(AStarFrontier.get(i).distance<expendNode.distance){
                    expendNode=AStarFrontier.get(i);
                }
            }  
            if(isGoalState(expendNode.board)){
                System.out.println("number of moves="+expendNode.distance);
                System.out.println("moves: ");
                for(int i=0;i<expendNode.path.size();i++){
                    System.out.print(expendNode.path.get(i)+" ");
                }
                System.out.println();
                break;
            }
            else{
                AStarFrontier.remove(expendNode);
                ArrayList<State> availableMoves=getAvailableStates(expendNode.board);
                for(int i=0;i<availableMoves.size();i++){
                    availableMoves.get(i).movesCount=expendNode.movesCount+1;
                    availableMoves.get(i).distance=heuristic(availableMoves.get(i).board)+(availableMoves.get(i).movesCount);
                    for(int j=0;j<expendNode.path.size();j++){
                        availableMoves.get(i).path.add(expendNode.path.get(j));
                    }
                    availableMoves.get(i).path.add(availableMoves.get(i).lastMove);
                    AStarFrontier.add(availableMoves.get(i));
                }
                
            }
        }
    }  
    
    public static void IDAStar(){
        System.out.println("_____________________IDA*______________________");
        State firstState=new State(IDAStarboard);
        int bound=1;
        while(true){
            if(IDAStarSearch(firstState.board,"", 0, bound)){
                break;
            }
            bound++;
        }
        
    }
    public static boolean IDAStarSearch(int[][] currentState,String path,int depth,int bound){
        boolean found=false;
        if(heuristic(currentState)+depth>bound){
            return false;
        }
        else if(isGoalState(currentState)){
            System.out.println("number of moves="+depth);
            System.out.println("moves:");
            System.out.println(path);
            return true;
        }
        else{
            ArrayList<State> availableMoves=getAvailableStates(currentState);
            for(int i=0;i<availableMoves.size();i++){
                if(!found){
                    found=found||IDAStarSearch(availableMoves.get(i).board,path+availableMoves.get(i).lastMove+" ", depth+1, bound);
                }
            }
        }
        return found;
    }
    public static int heuristic(int[][] board){
       int h=0;
       float sum=0;
       for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(i!=3||j!=3){
                    float x=Math.abs(j-((board[i][j]-1)%4));
                    float y=Math.abs(i-((board[i][j]-1)/4));
                    sum+=(Math.sqrt(Math.pow(x, 2)+Math.pow(y, 2)));
                }
            }
        }
       h=Math.round(sum);
       return h;
    }
    public static ArrayList<State> getAvailableStates(int[][] board){
        ArrayList<State> moves=new ArrayList<State>();
        int[][] newBoard=new int[4][4];
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(board[i][j]==0){
                    if(i-1>=0){
                        int temp=board[i][j];
                        board[i][j]=board[i-1][j];
                        board[i-1][j]=temp;
                        State s=new State(board);
                        s.lastMove="top";
                        moves.add(s);
                        temp=board[i][j];
                        board[i][j]=board[i-1][j];
                        board[i-1][j]=temp;
                    }
                    if(j-1>=0){
                        int temp=board[i][j];
                        board[i][j]=board[i][j-1];
                        board[i][j-1]=temp;
                        State s=new State(board);
                        s.lastMove="left";
                        moves.add(s);
                        temp=board[i][j];
                        board[i][j]=board[i][j-1];
                        board[i][j-1]=temp;
                    }
                    if(i+1<4){
                        int temp=board[i][j];
                        board[i][j]=board[i+1][j];
                        board[i+1][j]=temp;
                        State s=new State(board);
                        s.lastMove="down";
                        moves.add(s);
                        temp=board[i][j];
                        board[i][j]=board[i+1][j];
                        board[i+1][j]=temp;
                    }
                    if(j+1<4){
                        int temp=board[i][j];
                        board[i][j]=board[i][j+1];
                        board[i][j+1]=temp;
                        State s=new State(board);
                        s.lastMove="right";
                        moves.add(s);
                        temp=board[i][j];
                        board[i][j]=board[i][j+1];
                        board[i][j+1]=temp;
                    }
                    break;
                }
            }
        }
        
        return moves;
    }
    public static boolean isGoalState(int[][] board){
        boolean isGoal=true;
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                if(i==3&&j==3){
                    if(board[i][j]!=0){
                        isGoal=false;
                    }
                }else{
                    if(board[i][j]!=((i*4)+(j+1))){
                        isGoal=false;
                    }
                }
            }
        }
        return isGoal;
    }
    
    
    
}
