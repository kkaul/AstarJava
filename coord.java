//package Dasearch;

public class coord implements Comparable<coord>
{
    int y, x;
    int cost;    // estimated cost, including heuristic
    int dist;    // distance from source node
    coord prev; // pointer to previous coordinate on path.
    coord(int a, int b) {y=a; x=b;}

    public boolean equals(Object oc) // conforms to old java specs
    {
	coord c = (coord)oc;
	return (x==c.x && y==c.y);
    }

    public int compareTo(coord c) // compares cost
    {
	return c.cost - cost;   //reverses relationship
    }

} // coord

