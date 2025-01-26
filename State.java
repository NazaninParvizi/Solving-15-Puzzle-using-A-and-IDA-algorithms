import java.util.ArrayList;
public class State {
    int[][] board= new int[4][4];
    int movesCount=0;
    int distance;
    String lastMove;
    ArrayList<String> path = new ArrayList<>();
    public State(int[][] board){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                this.board[i][j]=board[i][j];
            }
        }
    }
}
