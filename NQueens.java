import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class NQueens {
	static int n;					//No. of queens
	static int random_restarts=-1;	//No. of random restarts
	static int state_changes=0;		//No.of state changes
	static int queens[];			//Column no. of each of the queens
	static int chess_board[][];		//Chess board
	static int temp_queens[];		//Used to get the h_cost of all the neighbors
	
	//A function to make a random restart as well as to make an initial state
	static void restart(){
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				chess_board[i][j]=0;
			}
		}
		
		for(int row=0;row<n;row++){
			int col=(int)(Math.random()*(n-1));
			
			queens[row]=col;
			
			chess_board[row][col]=1;
		}
		
		random_restarts++;
	}
	
	//A function to calculate the h-cost of a given state
	static int get_h_cost(int queens_positions[]){
		int h_cost=0;
		
		for(int i=0;i<n-1;i++){
			for(int j=i+1;j<n;j++){
				if(queens_positions[i]==queens_positions[j]){
					h_cost++;
					continue;
				}
				
				int row_diff=Math.abs(i-j);
				int col_diff=Math.abs(queens_positions[i]-queens_positions[j]);
				
				if(row_diff==col_diff){
					h_cost++;
				}
			}
		}
		
		return h_cost;
	}
	
	//A function to initialize the temp_queens array
	static void init_temp_queens(){
		for(int i=0;i<n;i++){
				temp_queens[i]=queens[i];
		}
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		System.out.println("Enter no. of queens:");
		n=Integer.parseInt(br.readLine());
		
		if(n==1){
			System.out.println("\nSolution:\nQ");
			return;
		}
		if(n<4){
			System.out.println("No solution");
			return;
		}
		
		queens=new int[n];
		chess_board=new int[n][n];
		
		//make an initial state
		restart();
		
		//Loop
		while(true){
			//get the h-cost of the current state
			int cur_h_cost=get_h_cost(queens);
			
			//if the h-cost of the current state is 0, print the current state (which is the solution) and return
			if(cur_h_cost==0){
				System.out.println("\nSolution:\n");
				
				for(int i=0;i<n;i++){
					for(int j=0;j<n;j++){
						if(queens[i]==j){
							System.out.print("Q ");
						}
						else{
							System.out.print("x ");
						}
					}
					System.out.println();
				}
				
				System.out.println("\nNo. of random restarts:" + random_restarts);
				System.out.println("No. of state changes:" + state_changes);
				
				return;
			}
			
			int h_costs[][]=new int[n][n];
			temp_queens=new int[n];
			
			//initialize the h_costs matrix by placing the h-cost of the current state in the current queens' positions
			for(int row=0;row<n;row++){
				h_costs[row][queens[row]]=cur_h_cost;
			}
			
			//get the h-costs of all the neighbor states in the h_costs matrix
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					init_temp_queens();
					temp_queens[i]=j;
					h_costs[i][j]=get_h_cost(temp_queens);
				}
			}
			
			int l_row=0, l_col=0;
			
			//position the l_row and l_col to the corresponding row and column number of the neighbor state with minimum h-cost in the h_costs matrix
			for(int i=0;i<n;i++){
				for(int j=0;j<n;j++){
					if(h_costs[i][j]<h_costs[l_row][l_col]){
						l_row=i;
						l_col=j;
					}
				}
			}
			
			//if the h-cost of the neighbor state(l_row, l_col) is less than the h-cost of the current state, change the current state to that neighbor state
			if(h_costs[l_row][l_col]<cur_h_cost){
				queens[l_row]=l_col;
				
				for(int i=0;i<n;i++){
					chess_board[l_row][i]=0;
				}
				
				chess_board[l_row][l_col]=1;
				
				state_changes++;
			}
			//else, make a random restart
			else{
				restart();
			}
		}
	}

}
