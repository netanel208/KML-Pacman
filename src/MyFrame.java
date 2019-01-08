
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Coords.MyCoords;
import File_format.Map;
import GIS.Fruit;
import GIS.Game;
import GIS.Packmen;
import GIS.Rec;
import Geom.Point3D;
import Robot.Play;

import javax.swing.JFileChooser;

/**
 * This class represents an application of the pacman game to the user.
 * @author Netanel
 * @author Carmel
 *
 */
public class MyFrame extends JFrame implements MouseListener,ActionListener,ItemListener {


	Image image;                                        //screen image
	Image packmenIcon;                                  //packmen icon
	Image ghostIcon;
	Image playerIcon;
	MenuBar menuBar;                                    
	Menu elementItem;
	Menu fileItem;
	Menu runItem;
	MenuItem myplayerItem;
	MenuItem addItem;
	MenuItem saveItem;
	MenuItem runProgramItem;
	MenuItem runProgramAutoItem;
	int x = -1;                                         //location of pixel(x,y)
	int y = -1;                                         //location of pixel(x,y)
	int width = -1;
	int height = -1;
	boolean myplayer_addable = false;
	boolean runTheGame = false;
	boolean runTheGameAuto = false;
	boolean saveDone = false;
	boolean threadTerminated = false;                   //An indication that thread has been terminated
	ArrayList<FruitLabel> fruitsLabels;                 //collection of Jlabel(any element represent fruit in screen)
	ArrayList<PacmanLabel> pacmansLabels;               //collection of Jlabel(any element represent pacman in screen)
	MyPlayerLabel myPlayerLabel;
	ArrayList<GhostLabel> ghostLabels;
	ArrayList<BoxLabel> boxs;

	private JTextField filename = new JTextField();


	final Point3D start = new Point3D(32.10571,35.20237,0);
	final Point3D rightup = new Point3D(32.10571,35.21239);
	final Point3D leftdown = new Point3D(32.10189,35.20237);
	final Point3D end = new Point3D(32.10189,35.21239,0);
	Map m;
	MyCoords c = new MyCoords();
	Game game;

	int goThere = 0;
	Play play1; 

	JPanel p;
	JLabel jLabel1;
	/**
	 * 
	 */
	private MyFrame()
	{
		initGUI();
		this.addMouseListener(this);
	}

	/**
	 * Initializes class variables
	 */
	private void initGUI() 
	{
		image = Toolkit.getDefaultToolkit().getImage("ariel3.png");
		packmenIcon = Toolkit.getDefaultToolkit().getImage("pacman.png");
		ghostIcon = Toolkit.getDefaultToolkit().getImage("Pacman_Ghost-512[1].png");
		playerIcon = Toolkit.getDefaultToolkit().getImage("robot.png");
		Container cp = getContentPane();
		filename.setEditable(false);
		p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(filename);
		cp.add(p, BorderLayout.NORTH);

		menuBar = new MenuBar();
		fileItem = new Menu("File");
		elementItem = new Menu("Element");
		runItem = new Menu("Start");

		myplayerItem = new MenuItem("Add player");
		myplayerItem.addActionListener(this);

		addItem = new MenuItem("Add Csv");
		addItem.addActionListener(this);


		saveItem = new MenuItem("Save");
		saveItem.addActionListener(this);

		runProgramItem = new MenuItem("Run");
		runProgramItem.addActionListener(this);
		
		runProgramAutoItem = new MenuItem("Auto Run...");
		runProgramAutoItem.addActionListener(this);

		menuBar.add(fileItem);
		fileItem.add(addItem);
		fileItem.add(saveItem);
		elementItem.add(myplayerItem);
		menuBar.add(elementItem);

		menuBar.add(runItem);
		runItem.add(runProgramItem);
		runItem.add(runProgramAutoItem);

		this.setMenuBar(menuBar);

		fruitsLabels = new ArrayList<FruitLabel>();
		pacmansLabels = new ArrayList<PacmanLabel>();
		ghostLabels = new ArrayList<GhostLabel>();
		boxs = new ArrayList<BoxLabel>();


		width = this.getWidth();
		height = this.getHeight();

	}

	public void paint(Graphics g){
		width = this.getWidth(); 
		height = this.getHeight();
		g.drawImage(image, 0, 0, width, height, this);
		if(myPlayerLabel!=null) {
			myPlayerLabel.update(width, height);
			int x_player= (int)(((double)width)*((double)myPlayerLabel.x_factor));
			int y_player= (int)(((double)height)*((double)myPlayerLabel.y_factor));
			g.drawImage(playerIcon,x_player, y_player, 45, 45,this);
		}
		if(!boxs.isEmpty())
		{
			for(int i=0 ; i<boxs.size() ; i++)
			{
				BoxLabel box = boxs.get(i);
				int x_box = box.getX();
				int y_box = box.getY();
				x_box = (int)(((double)width)*((double)box.x_factor));
				y_box = (int)(((double)height)*((double)box.y_factor));
				box.update(width, height);
				box.setLocation(x_box, y_box);
				g.setColor(Color.BLACK);
				g.fillRect(x_box, y_box, box.x_dis, box.y_dis-10);
			}
		}
		if(!fruitsLabels.isEmpty())
		{
			for(int i=0 ; i<fruitsLabels.size() ; i++)//draw all fruits label if there is a change or is said so(by repaint) 
			{
				FruitLabel fruit = fruitsLabels.get(i);
				fruit.update();//if deleted -> isEaten = true

				int x_fruit = fruitsLabels.get(i).getX();
				int y_fruit = fruitsLabels.get(i).getY();
				x_fruit = (int)(((double)width)*((double)fruit.x_factor));//The position X of the fruit relative to the screen size
				y_fruit = (int)(((double)height)*((double)fruit.y_factor));//The position Y of the fruit relative to the screen size
				fruit.setLocation(x_fruit, y_fruit);
				if(!fruit.isEaten)//If the fruit is eaten by the pacman on you will paint it more
				{
					g.setColor(Color.WHITE);
					g.fillOval(x_fruit, y_fruit, 20, 20);
				}
			}
		}
		if(!pacmansLabels.isEmpty())
		{
			for(int i=0 ; i<pacmansLabels.size() ;i++)//draw all pacman label if there is a change or is said so(by repaint)
			{
				PacmanLabel pacman = pacmansLabels.get(i);
				pacman.update(width, height);

				int x_pacman = pacmansLabels.get(i).getX();
				int y_pacman = pacmansLabels.get(i).getY();
				x_pacman = (int)(((double)width)*((double)pacman.x_factor));//The position X of the pacman relative to the screen size
				y_pacman = (int)(((double)height)*((double)pacman.y_factor));//The position Y of the pacman relative to the screen size
				pacman.setLocation(x_pacman, y_pacman);
				if(!pacman.isEaten)
				{
					g.drawImage(packmenIcon,x_pacman, y_pacman, 28, 28,this);
				}
			}
		}

		if(!ghostLabels.isEmpty())
		{
			for(int i=0 ; i<ghostLabels.size() ;i++)//draw all pacman label if there is a change or is said so(by repaint)
			{
				GhostLabel ghost = ghostLabels.get(i);
				ghost.update(width, height);
				int x_ghost = ghostLabels.get(i).getX();
				int y_ghost = ghostLabels.get(i).getY();
				x_ghost = (int)(((double)width)*((double)ghost.x_factor));//The position X of the pacman relative to the screen size
				y_ghost = (int)(((double)height)*((double)ghost.y_factor));//The position Y of the pacman relative to the screen size
				ghost.setLocation(x_ghost, y_ghost);
				g.drawImage(ghostIcon,x_ghost, y_ghost, 28, 28,this);
			}
		}


	}

	@Override
	public void itemStateChanged(ItemEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent event) {

		if(event.getSource().equals(myplayerItem))//If pressed on Pacman, you will avoid adding Fruit
		{
			myplayer_addable = true;
		}


		if(event.getSource().equals(addItem))//If pressed on Add csv file
		{
			JFileChooser c = new JFileChooser();

			// Demonstrate "Open" dialog:
			int rVal = c.showOpenDialog(MyFrame.this);
			if (rVal == JFileChooser.APPROVE_OPTION) {
				String diraction = c.getCurrentDirectory().toString()+"\\"+c.getSelectedFile().getName();
				filename.setText(diraction);
				play1 = new Play(diraction);
				game = new Game (diraction);
				loadGame ();

			}
			if (rVal == JFileChooser.CANCEL_OPTION) {
				filename.setText("You pressed cancel");
			}	
		}


		if(event.getSource().equals(runProgramItem))//If press on Run (after saving the game)
		{


			runGame();
			runTheGame = true;

		}


	}

	private void loadGame() {


		int Width =this.getWidth();
		int Height= this.getHeight();
		m = new Map(image,Width,Height,start,end);

		ArrayList<Fruit> fruits = game.getFruits();
		ArrayList<Packmen> pacmans = game.getPackmens();
		ArrayList<Packmen> ghosts = game.getGhosts();
		ArrayList<Rec> _boxs = game.getBoxs();

		/// player
		//		Packmen MyPlayer = game.getMyPlayer();
		//		Point3D playerGps = (Point3D)MyPlayer.getGeom();
		//		Point3D playerPixel = m.toPixel(playerGps);
		//		myPlayerLabel=  new MyPlayerLabel(Width,Height,(int)playerPixel.x(),(int)playerPixel.y() );


		for (int i = 0; i < fruits.size(); i++) {
			//add fruit in right pixel
			FruitLabel fru = new FruitLabel(Width,Height,fruits.get(i) );
			fruitsLabels.add(fru);
			repaint();

		}

		for (int i = 0; i < pacmans.size(); i++) {
			//add pacman in right pixel
			PacmanLabel pac = new PacmanLabel(Width,Height,pacmans.get(i));
			pacmansLabels.add(pac);
			repaint();

		}
		for (int i = 0; i < ghosts.size(); i++) {
			//add ghosts in right pixel
			GhostLabel ghost = new GhostLabel(Width,Height,ghosts.get(i));
			ghostLabels.add(ghost);
			repaint();
		}
		for(int i = 0; i < _boxs.size(); i++){
			//add boxs in right pixel
			BoxLabel box = new BoxLabel(_boxs.get(i));
			boxs.add(box);
			repaint();
		}

	}


	public void runGame()
	{
		runTheGame = true;
		Thread th = new Thread(new Runnable() {
			public void run() {

				play1.start();
				ArrayList<String> board_data;

				while(play1.isRuning()) {
					play1.rotate(goThere);
					board_data = play1.getBoard();
					game.update(board_data);
					System.out.println(play1.getStatistics());
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();		
				}
			}
		});
		th.start();
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {

		x = arg0.getX();
		y = arg0.getY();
		Point3D pointPixel =new Point3D(x,y,0);
		Point3D gpsPixel = m.toGPS(pointPixel);

		//if select first Fruit and pressed on screen 
		if(myplayer_addable)
		{
			m.setMap(width, height);
			myPlayerLabel= new MyPlayerLabel(this.getWidth(), this.getHeight(),gpsPixel.x(),gpsPixel.y());
			myplayer_addable= false;
			play1.setIDs(315858340,208252684);


			play1.setInitLocation(gpsPixel.x(),gpsPixel.y());	
		}
		repaint();

		if(runTheGame)
		{
			double azimuth = c.azimuth_elevation_dist((Point3D)game.getMyPlayer().getGeom(), gpsPixel)[0];
			goThere = (int)azimuth;
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	public static void main(String[] args) {
		
		MyFrame window = new MyFrame();
		window.setVisible(true);
		window.setSize(1371, 594);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	/**
	 * This class represents fruit as a frame label with height and width data. 
	 * @author Netanel
	 * @author Carmel
	 *
	 */
	class FruitLabel extends JLabel{

		double x_factor;//The relative portion of a point from the width of the frame
		double y_factor;//The relative part of a point from the height of the frame
		boolean isEaten;//if the pacman eat this fruit or not
		Fruit fruit;
		/**
		 * The constructor placed the point in the expected place 
		 * and calculated the position ratio of the point in the frame.
		 * @param width
		 * @param height
		 * @param x
		 * @param y
		 */
		public FruitLabel(int width,int height,Fruit fruit)
		{
			//isEaten = false;
			this.fruit=fruit;
			m.setMap(width, height);
			Point3D pointGps = (Point3D) fruit.getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			this.setLocation((int)pointPixel.x(),(int)pointPixel.y());
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}

		public void update() {
			if (this.fruit.isEaten()==true) {
				isEaten=true;
			}
		}


	}

	/**
	 * This class represents pacman as a frame label with height and width data.
	 * @author Netanel
	 * @author Carmel
	 *
	 */
	class PacmanLabel extends JLabel{

		double x_factor;
		double y_factor;
		boolean isEaten;//		boolean isEaten;
		Packmen packmen;


		public PacmanLabel(int width,int height,Packmen packmen)
		{
			this.packmen= packmen;
			m.setMap(width, height);
			Point3D pointGps = (Point3D) packmen.getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			this.setLocation((int)pointPixel.x(),(int)pointPixel.y());
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}

		/**
		 * This method placed the point in the expected place 
		 * and calculated the position ratio of the point in the frame.
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 */
		public void update( int width, int height) {

			if (this.packmen.isEaten()==true) {
				isEaten=true;
			}

			else {
				m.setMap(width, height);
				Point3D pointGps = (Point3D) this.packmen.getGeom();
				Point3D pointPixel = m.toPixel(pointGps);
				this.setLocation((int)pointPixel.x(),(int)pointPixel.y());
				x_factor = ((double)this.getX())/((double)width);
				y_factor = ((double)this.getY())/((double)height);
			}
		}
	}

	class GhostLabel extends JLabel{

		double x_factor;
		double y_factor;
		Packmen ghost;

		public GhostLabel(int width,int height,Packmen ghost)
		{
			this.ghost=ghost;
			m.setMap(width, height);
			Point3D pointGps = (Point3D) ghost.getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			this.setLocation((int)pointPixel.x(),(int)pointPixel.y());
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);

		}

		/**
		 * This method placed the point in the expected place 
		 * and calculated the position ratio of the point in the frame.
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 */
		public void update( int width, int height) {
			m.setMap(width, height);
			Point3D pointGps = (Point3D) this.ghost.getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			this.setLocation((int)pointPixel.x(),(int)pointPixel.y());
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}
	}


	class MyPlayerLabel extends JLabel{

		double x_factor;
		double y_factor;
		boolean isEaten;
		Packmen MyPlayer;

		public MyPlayerLabel(int width,int height,double lat,double lon)
		{
			String []  str= {"M","0",Double.toString(lat),Double.toString(lon),"0.0","20.0","1.0"};
			MyPlayer= new Packmen(str);
			game.setMyPlayer(MyPlayer);
			Point3D pix = m.toPixel(new Point3D(lat,lon,0));
			this.setLocation((int)pix.x(), (int)pix.y());
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);

		}

		/**
		 * This method placed the point in the expected place 
		 * and calculated the position ratio of the point in the frame.
		 * @param x
		 * @param y
		 * @param width
		 * @param height
		 */
		public void update( int width, int height) {
			m.setMap(width, height);
			Point3D pointGps = (Point3D) this.MyPlayer.getGeom();
			Point3D pointPixel = m.toPixel(pointGps);
			this.setLocation((int)pointPixel.x(),(int)pointPixel.y());
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}
	}

	class BoxLabel extends JLabel{
		double x_factor;
		double y_factor;
		int _x;
		int _y;
		int x_dis;
		int y_dis;
		Rec rec;
		
		public BoxLabel(Rec rec)
		{
			this.rec = rec;
			Point3D pixUL = m.toPixel(rec.getUpLeftP());
			System.out.println("paint UL:"+pixUL);
			Point3D pixUR = m.toPixel(rec.getUpRightP());
			Point3D pixDL = m.toPixel(rec.getDownLeftP());
			_x = (int)pixUL.x();
			_y = (int)pixUL.y();
			x_dis = (int)(pixUR.x() - pixDL.x());
			y_dis = (int)(pixDL.y() - pixUR.y());
			this.setLocation(_x, _y);
			x_factor = ((double)this.getX())/((double)width);
			y_factor = ((double)this.getY())/((double)height);
		}
		
		public void update(int width, int height) {
			m.setMap(width, height);
			Point3D pixUL = m.toPixel(rec.getUpLeftP());
			Point3D pixUR = m.toPixel(rec.getUpRightP());
			Point3D pixDL = m.toPixel(rec.getDownLeftP());
			x_dis = (int)(pixUR.x() - pixDL.x());
			y_dis = (int)(pixDL.y() - pixUR.y());
		}
	}

}
