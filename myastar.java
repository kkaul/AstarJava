import java.util.*;
public class myastar extends astar
{
    public myastar(int r, int c) { super(r,c); }


	static final int NEW =0;
	static final int INTERIOR =1;
	static final int FRONTIER =2;
	int[][] Status = new int[ROWS][COLS]; //initialize a new matrix of all 0s--keep track of status of node

	public coord search(int sy, int sx, int ty, int tx)
	{
		coord start = new coord (sy, sx);
		start.dist = 0;
		start.cost=0;
		coord current = null;
        int aall = 3000;
		
		//heapstuff
		pheap<coord> Frontier = new pheap<coord>(new coord[1000]);
		Frontier.insert(start);
		Status [sy][sx] = FRONTIER;
		boolean stop = false;
		int allcost = 0;
		int []cost = {1, 3, 5, 10, 7};
		while(!stop && Frontier.size() != 0)
		{			
			current = Frontier.deletetop();
			Status[current.y][current.x] = INTERIOR;
			int i = current.y;
			int j = current.x;
			coord nn = new coord(i-1, j); //NORTH
			coord nw = new coord(i, j-1); //WEST
			coord ne = new coord(i, j+1); //EAST
			coord ns = new coord(i+1, j); //SOUTH
			
			
			//alligatorcost
			if (i>0 && j>0 && i<ROWS-1 && j<COLS-1){
				if (M[i-1][j] == WATER){allcost = aall;}
				if (M[i][j-1] == WATER){allcost = aall;}
				if (M[i][j+1] == WATER){allcost = aall;}
				if (M[i+1][j] == WATER){allcost = aall;}
			}
				
				
			
			if(i>0  && Status[i-1][j] == NEW)		
			{
				nn.dist = current.dist + 1;
				nn.cost = nn.dist + cost[M[i-1][j]] + allcost;
				nn.prev = current;
				Frontier.insert(nn);
				Status[i-1][j] = FRONTIER;
			}
			if(j>0 && Status[i][j-1]==NEW)
			{
				nw.dist = current.dist + 1;
				nw.cost = nw.dist + cost[M[i][j-1]] + allcost;
				nw.prev = current;
				Frontier.insert(nw);
				Status[i][j-1] = FRONTIER;
			}
			if(j<COLS-1 && Status[i][j+1] == NEW)
			{
				ne.dist = current.dist + 1;
				ne.cost = ne.dist+ cost[M[i][j+1]] + allcost;
				ne.prev = current;
				Frontier.insert(ne);
				Status[i][j+1] = FRONTIER;
			}
			if(i<ROWS-1 && Status[i+1][j] == NEW)
			{
				ns.dist = current.dist + 1;
				ns.cost = ns.dist+ cost[M[i+1][j]] + allcost;
				ns.prev = current;
				Frontier.insert(ns);
				Status[i+1][j] = FRONTIER;
			}
			//if(i==ty && j == tx){stop = true;}
			if (Status[ty][tx] == 2){stop=true;}
			allcost = 0;
		}
		return current;
	}
}
