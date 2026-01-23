//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import jdk.nashorn.api.scripting.AbstractJSObject;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;
	public Image playerpic;
    public Image Futbol;
    public Image backgroundPic;

   //Declare the objects used in the program
   //These are things that are made up of more than one variable type
	public Astronaut ball;
    public Astronaut player;
    public Astroid  astroid2;
    public Astroid  astroid1;

   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();

      //randomness
        //range 0-9
        int randx=(int)(Math.random() * 10);
        // range 1-10
        randx = (int)(Math.random() * 10) + 1;

        //range 1-1000
        randx = (int)(Math.random() * 150) + 1;

        //range 1-700
        int randy = (int)(Math.random() * 250) + 1;
       
      //variable and objects
      //create (construct) the objects needed for the game and load up 
		 playerpic= Toolkit.getDefaultToolkit().getImage("Runner.png"); //load the picture
        Futbol = Toolkit.getDefaultToolkit().getImage("Subject.png"); //load the picture
        backgroundPic = Toolkit.getDefaultToolkit().getImage("pitch.jpg"); //load the picture

       ball = new Astronaut(randy,randx);
        ball.dy=-2;
        ball.height=30;
        ball.width=30;

        player = new Astronaut(102,50);
        player.dy=-2;
        player.height=150;
        player.width=150;
astroid2= new Astroid(100,30);
        astroid1 = new Astroid(100,800);
        astroid1.dx= -astroid1.dx;
        //astroid2 = new Astroid(randy,467);
	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
         render();  // paint the graphics
         pause(10); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
        System.out.println(ball.ypos);
      //calls the move( ) code in the objects
		ball.move();
        player.move();
        astroid1.move();

crashing();

    }
    public void crashing() {
        if (ball.hitbox.intersects(player.hitbox)) {
            System.out.println("TOUCHDOWN!");
            ball.dx = -ball.dx;
            ball.dy = -ball.dy;


        }
        if (astroid1.hitbox.intersects(astroid2.hitbox)) {

            System.out.println(" astroid collison");
            astroid2.height = astroid2.height + 100;
            astroid2.isCrashing = true;

        }
        if (!astroid1.hitbox.intersects(astroid2.hitbox)) {
            astroid2.isCrashing = false;
        }

    }

   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);
      //draw the image of the astronaut

		g.drawImage(Futbol,ball.xpos, ball.ypos, ball.width, ball.height, null);
        if(player.isAlive == true){
		g.drawImage(playerpic, player.xpos, player.ypos, player.width, player.height, null);
        }
       // g.drawImage(Futbol, astroid1.xpos, astroid1.ypos,astroid1.width,astroid1.height, null);
        //g.drawImage(Futbol, astroid2.xpos, astroid2.ypos,astroid2.width,astroid2.height, null);

        g.drawRect(ball.hitbox.x, ball.hitbox.y, ball.hitbox.width, ball.hitbox.height);


        bufferStrategy.show();
	}
}