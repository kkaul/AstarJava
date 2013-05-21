//package Dasearch;

import java.util.*;

public class astar 
{
    public static final int OPEN = 0;
    public static final int FOREST = 1;  
    public static final int DESERT = 2;
    public static final int WATER = 3;
	public static final int MOUNTAIN = 4;

    public int ROWS, COLS; // size of map in array coords

    public int M[][];  // the map itself

    // constructor
    public astar(int r0, int c0)  // typically 32x44
    {
	M = new int[r0][c0];
	ROWS=r0;  COLS=c0;

	// generate random map  (initially all OPEN)
	int GENS = 12;  // number of generations
	double NFACTOR = 0.12;  
	double p, r;  // for random probability calculation
	int generation;  int i, j;
	for(generation=0;generation<GENS;generation++)
	    {
	     for(i=0;i<ROWS;i++) for(j=0;j<COLS;j++)
	     {
	       p = 0.005; // base probability factor
	       if (i>0 && M[i-1][j]==WATER) p += NFACTOR;
	       if (i<ROWS-1 && M[i+1][j]==WATER) p+=NFACTOR;
	       if (j>0 && M[i][j-1]==WATER) p+=NFACTOR;
	       if (j<COLS-1 && M[i][j+1]==WATER) p+=NFACTOR;
	       r = Math.random();
	       if (r<=p) M[i][j] = WATER;
			 
			 p = 0.003; // base probability factor
			 if (i>0 && M[i-1][j]==DESERT) p += NFACTOR;
			 if (i<ROWS-1 && M[i+1][j]==DESERT) p+=NFACTOR;
			 if (j>0 && M[i][j-1]==DESERT) p+=NFACTOR;
			 if (j<COLS-1 && M[i][j+1]==DESERT) p+=NFACTOR;
			 r = Math.random();
			 if (r<=p) M[i][j] = DESERT;
			 
			 p = 0.002; // base probability factor
			 if (i>0 && M[i-1][j]==FOREST) p += NFACTOR;
			 if (i<ROWS-1 && M[i+1][j]==FOREST) p+=NFACTOR;
			 if (j>0 && M[i][j-1]==FOREST) p+=NFACTOR;
			 if (j<COLS-1 && M[i][j+1]==FOREST) p+=NFACTOR;
			 r = Math.random();
			 if (r<=p) M[i][j] = FOREST;

			 
			 p = 0.001; // base probability factor
			 if (i>0 && M[i-1][j]==MOUNTAIN) p += NFACTOR;
			 if (i<ROWS-1 && M[i+1][j]==MOUNTAIN) p+=NFACTOR;
			 if (j>0 && M[i][j-1]==MOUNTAIN) p+=NFACTOR;
			 if (j<COLS-1 && M[i][j+1]==MOUNTAIN) p+=NFACTOR;
			 r = Math.random();
			 if (r<=p) M[i][j] = MOUNTAIN;
			 
	     } // for each cell
	    } // for each generation

    } //constructor

    // determines euclidean distance between y1,x1 and y2,x2, rounds off
    public static int distance(int y1, int x1, int y2, int x2)
    {
	int dy = (y1-y2);  int dx = (x1-x2);
	return (int) (0.5 + Math.sqrt((dx*dx) +(dy*dy)));
    }

    // determines discrete distance, assuming one moves in only 1/4 directions
    public static int ddist(int y1, int x1, int y2, int x2)
    {
	return (int)(Math.abs(y1-y2) + Math.abs(x1-x2));
	// the Math. functions all return doubles, so we need to typecast.
    }


    // determines if element c appears in A using "equals" method.  Use
    // this method instead of "contains".
    public boolean memb(ArrayList<coord> A, coord c)
    {
	for(coord d : A) {if (d.equals(c)) return true;}
	return false;
    }


    // create path from from source to target, return end of path coord.
    public coord search(int sy, int sx, int ty, int tx)
    {
	coord start = new coord(sy,sx);
    coord current = null;
	while (sy !=ty || sx!= tx)
	{
		if (sy<ty) sy++;
		else if (sy>ty) sy--;
		else if (sx<tx) sx++;
		else sx--;
		coord newc = new coord(sy,sx);
		newc.prev = current;
		current = newc;
	}
			return current;
	}//search


}//astar(overridden in alligator.java)