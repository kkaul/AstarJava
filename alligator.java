//package Dasearch;
// Pathfinder with alligator lurking at the water's edge

import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import javax.swing.*;

public class alligator extends JFrame
{
    private Image diamondgif, mangif;  // animated gif images
    private Image gatorgif;
    private Graphics display;
    private int gap = 24;  // size of each square/cell of map
    private int yoff = 24;
    private astar PG;
    int rows, cols;
    int XDIM, YDIM; // window dimensions
    int gobx, goby, profx, profy;
    private Image[] imageof; // image vector for terrain.
    private Image[] imagechar; //image vector for character based on terrain.

    // animation buffer
    Image clip, dbuf;
    Graphics cg, wg;


    /* Chance of getting eaten by alligator at water's edge */
    public static double gatorchance = 0.1;



    public void paint(Graphics g) {} // override automatic repaint

    public alligator(int r, int c)
    {   rows = r; cols = c;
	PG = new myastar(r,c);
	XDIM = cols*gap;  YDIM = rows*gap;
	this.setBounds(0,0,XDIM,YDIM+yoff+5);
	this.setVisible(true); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	//	display = this.getGraphics();
	clip = createImage(XDIM,YDIM+yoff+5);
	cg = clip.getGraphics();
	dbuf = createImage(XDIM,YDIM+yoff+5);
	display = dbuf.getGraphics();
	wg = this.getGraphics();

	diamondgif = Toolkit.getDefaultToolkit().getImage("gem1.gif");
	prepareImage(diamondgif,this);
	mangif = Toolkit.getDefaultToolkit().getImage("man15.gif");
	prepareImage(mangif,this);
	gatorgif = Toolkit.getDefaultToolkit().getImage("gator.gif");
	prepareImage(mangif,this);
	
	imageof = new Image[5];
	imagechar = new Image[5];
	imageof[astar.WATER]= 
	    Toolkit.getDefaultToolkit().getImage("Water.gif");
	prepareImage(imageof[astar.WATER],this);
		imageof[astar.MOUNTAIN]= 
	    Toolkit.getDefaultToolkit().getImage("Mountain.gif");
		prepareImage(imageof[astar.MOUNTAIN],this);		
	imageof[astar.OPEN] = 
	    Toolkit.getDefaultToolkit().getImage("grass1.gif");
	prepareImage(imageof[astar.OPEN],this);
	imagechar[astar.OPEN] = mangif;
	imagechar[astar.WATER] = 	    
	    Toolkit.getDefaultToolkit().getImage("boat.gif");
	prepareImage(imagechar[astar.WATER],this);
		imageof[astar.DESERT]= 
	    Toolkit.getDefaultToolkit().getImage("Desert.gif");
		prepareImage(imageof[astar.DESERT],this);
		
		imageof[astar.FOREST]= 
	    Toolkit.getDefaultToolkit().getImage("Forest.gif");
		prepareImage(imageof[astar.FOREST],this);
	
		imagechar[astar.DESERT] = 	    
	    Toolkit.getDefaultToolkit().getImage("camel.gif");
		prepareImage(imagechar[astar.DESERT],this);
		
		imagechar[astar.FOREST] = 	    
	    Toolkit.getDefaultToolkit().getImage("lumberjack.gif");
		prepareImage(imagechar[astar.FOREST],this);
		
		imagechar[astar.MOUNTAIN] = 	    
	    Toolkit.getDefaultToolkit().getImage("climber.gif");
		prepareImage(imagechar[astar.MOUNTAIN],this);
	try{Thread.sleep(500);} catch(Exception e) {} // Synch with system

	// generate random starting positions.
	  // generate initial positions of professor and diamond
	do 
	    {
		gobx = (int)(Math.random() * PG.COLS);  
		goby = (int)(Math.random() * PG.ROWS);
	    }
	while (PG.M[goby][gobx]!=PG.OPEN);
	do 
	    {
		profx = (int)(Math.random() * PG.COLS);  
                profy = (int)(Math.random() * PG.ROWS);
	    }
	while (PG.M[profy][profx]!=PG.OPEN);

	// draw map
	drawmap();
	display.drawImage(clip,0,0,this);
	// draw professor and diamond, initial position
	display.drawImage(imagechar[PG.M[profy][profx]],
			  (profx*gap),(profy*gap)+yoff,gap,gap,null);
	display.drawImage(diamondgif,gobx*gap,goby*gap+yoff,gap,gap,null);
	wg.drawImage(dbuf,0,0,this);
	animate();
	
    } // constructor


    boolean wedge(int y, int x) // check if at water's edge
    {
	if (y>0 && PG.M[y-1][x]==PG.WATER) return true;
	if (x>0 && PG.M[y][x-1]==PG.WATER) return true;
	if (y<rows-1 && PG.M[y+1][x]==PG.WATER) return true;
	if (x<cols-1 && PG.M[y][x+1]==PG.WATER) return true;
	return false;
    }


    public void animate()
    {
	// invert path.
	int i =0, j=0;
	coord path = PG.search(goby,gobx, profy,profx);
	if (path==null) 
	    {   display.setColor(Color.red);
		display.drawString("NO PATH TO TARGET!",50,100); 
		wg.drawImage(dbuf,0,0,this);
		System.out.println("no path"); return;
	    }

	boolean eaten = false;
	while (path!=null && !eaten)
	    {

	      display.drawImage(clip,0,0,this);  //draw static map
	      display.drawImage(imagechar[PG.M[path.y][path.x]],
				(path.x*gap),(path.y*gap)+yoff,gap,gap,null);
	      //	      display.drawImage(imageof[PG.M[path.y][path.x]],
	      //				(path.x*gap),(path.y*gap)+yoff,gap,gap,null);

	      try{Thread.sleep(250);} catch(Exception se) {}
	      cg.setColor(Color.red);
      	      cg.fillOval((path.x*gap)+8,(path.y*gap)+yoff+8,4,4);
	      // for animation:
	      display.drawImage(diamondgif,gobx*gap,goby*gap+yoff,gap,gap,null);	      
	      wg.drawImage(dbuf,0,0,this);

	      if (wedge(path.y,path.x) && PG.M[path.y][path.x]!=PG.WATER)
		  {
		      if (Math.random()<gatorchance)
			  {
			      for(j=0;j<500;j++)
				  {
				      display.drawImage(clip,0,0,this);
				      display.drawImage(gatorgif,(path.x-2)*gap,(path.y-2)*gap,5*gap,5*gap,this);
	      display.drawImage(diamondgif,gobx*gap,goby*gap+yoff,gap,gap,null);	      
				      wg.drawImage(dbuf,0,0,this);
				      
				      try {Thread.sleep(40);}
				      catch (Exception ge) {}
				  }
			     
			      eaten = true;
			  }
		  }
	      
	      path = path.prev;
	    }
	//display.drawImage(diamondgif,gobx*gap,goby*gap+yoff,gap,gap,null);
	if (!eaten)
	display.drawImage(imagechar[PG.M[goby][gobx]],gobx*gap,goby*gap+yoff,gap,gap,null);
	wg.drawImage(dbuf,0,0,this);
    }

    public void drawmap()
    {   
	int i, j;
	// draw static background as a green rectangle
	cg.setColor(Color.green);
	cg.fillRect(0,0,XDIM,YDIM+yoff+5);

	for(i=0;i<PG.ROWS;i++)
	    for(j=0;j<PG.COLS;j++)
		{
		    //  if (PG.M[i][j]==PG.OPEN) cg.setColor(Color.green);
		    //    else cg.setColor(Color.blue);
		    //  cg.fillRect(j*gap,(i*gap)+yoff,gap,gap);
		    if (imageof[PG.M[i][j]]==null)
			{
			    cg.setColor(Color.gray);
			    cg.fillRect(j*gap,(i*gap)+yoff,gap,gap);
			}
		    cg.drawImage(imageof[PG.M[i][j]],j*gap,(i*gap)+yoff,gap,gap,null);
		} 
	//try{Thread.sleep(1000);} catch(Exception e) {} 
    } // drawmap



    public static void main(String[] args)
    {
	int r = 32; int c = 44;
	alligator pf = new alligator(r,c);
    }

} // class alligator

