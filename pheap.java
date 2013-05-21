class pheap<TY extends Comparable<TY>> // heap of type TY
{
    TY[] H; // the heap
    public int size = 0; // current size
    public final int maxsize;

    public pheap(TY[] h)  //constructor
	{
	    H = h;
	    maxsize = H.length;
	}//pheap
    
    public pheap(TY[] h, int size) 
    {
	this.size = size;
	this.maxsize = h.length;
	this.H = h;
	heapify(); // call heapify to convert into heap
    }//

    // functions to compute indexes of left, right, and parent, 0 is root.
    // returns -1 if input is invalid
    int left(int n) 
    { int l = 2*n+1;
      if (l>=size || l<0) return -1; else return l;
    }
    int right(int n) 
    { int r = 2*n+2;
      if (r>=size || r<0) return -1; else return r;
    }	
    int parent(int n)  // returns -1 if n is already the root
    {	int p = (n-1)/2;
	if (n<=0 || p>=size ) return -1; // parent does not exist
	else return p;
    }
    boolean leaf(int n) // determines if n is a leaf node
    { return left(n)<0; }

	
	
    public int size() {return size;}

    public boolean find(TY x)
    {
	if (x==null) return false;
	boolean ax = false;
	for(int i=0;i<size && !ax;i++) if (x.compareTo(H[i])==0) ax=true;
	return ax;
    }

    // insert new TY x into heap, returns index of where it was inserted,
    // -1 if insertion failed.
    public int insert(TY x)
    {
	if (size>=maxsize) return -1;
	int answer = -1;
	// attach to end of tree first:
	int i = size;  // current position of x
	H[i] = x;
	size++; // record increase in size
	// propagate upwards until it satisfies heap invariant.
	while (answer<0)
	    {
		int p = parent(i);
		if (p==-1 || H[p].compareTo(H[i])>=0) answer = i; // < parent, or at root
		else // swap with parent
		    {
			TY temp = H[i];
			H[i] = H[p];
			H[p] = temp;
			i = p; // move upwards
		    }
	    }//while
	return answer;
    }//insert

    public TY gettop() { return H[0];}

    public TY deletetop() // deletes and returns the largest number
    {
	if (size==0) throw new Error("delete from empty heap");
	TY ax = H[0]; // answer always at root.
	H[0] = H[--size]; // take from end of heap, after decreasing size
	// propagate downwards until heap invariant is satisfied.
	int i = 0; // current position of value to be propagated downward.
	int s = 0; // index of value to swap H[i] with, -1 means done
	while (s>=0)
	{
	    int l = left(i), r = right(i);
	    s = -1;  // index of value to swap H[i] with.
	    // exchange with larger of two children.  (tricky)
	    if (l>=0 && H[i].compareTo(H[l])<0) s=l;
	    if (r>=0 && H[i].compareTo(H[r])<0 && H[r].compareTo(H[l])>0) s=r;
	    if (s>=0) // time to swap:
		{
		    TY temp = H[i];
		    H[i] = H[s];
		    H[s] = temp;
		    i = s; // move i downwards
		}
	}//while
	return ax;
    }//deletetop
    
    
    public TY delete(int n) // delete node at index n, return value
    {
	if (n<0 || n>=size) throw new Error("invalid delete index: "+n);
	TY ax = H[n];
	H[n] = H[--size]; // move last node into position
	int i = n; // current position of node to propagate
	// more generic propagation
	int s = 0; // value of index to swap with
	while (s>=0)
        {
	   s = -1;
	   int p = parent(i), l = left(i), r = right(i);
	   // first check with parent
	   if (p>=0 && H[p].compareTo(H[i])<0) s = p; // upward propagation
	   // check against children
	   if (s<0) // do nothing if s already set
	     {
		 if (l>=0 && H[i].compareTo(H[l])<0) s=l;
		 if (r>=0 && H[i].compareTo(H[r])<0 && H[r].compareTo(H[l])>0) s=r;

	     }// downward propagation
	    if (s>=0) //swap
		{
		    TY temp = H[i];
		    H[i] = H[s];
		    H[s] = temp;
		    i = s; // move i downwards
		}	        
        }// generic propagation while loop
	return ax;
    }// arbitrary delete.

    private void percdown(int n)// percolate node n downwards
    {
	if (n<0 || n>=size) return;
	int i = n; // current position of value to be propagated downward.
	int s = 0; // index of value to swap H[i] with, -1 means done
	while (s>=0)
	{
	    int l = left(i), r = right(i);
	    s = -1;  // index of value to swap H[i] with.
	    // exchange with larger of two children.
	    if (l>=0 && H[i].compareTo(H[l])<0) s=l;
	    if (r>=0 && H[i].compareTo(H[r])<0 && H[r].compareTo(H[l])>0) s=r;
	    if (s>=0) //swap
		{
		    TY temp = H[i];
		    H[i] = H[s];
		    H[s] = temp;
		    i = s; // move i downwards
		}
	}//while
    }// percdown utility function

    public void heapify()
    {
	for(int i = size-1;i>=0;i--)
        {
	    percdown(i);
        }
    }// convert any array into heap
  
}//pheap
