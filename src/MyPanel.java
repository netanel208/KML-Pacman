import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFileChooser;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Netanel
 * @author Carmel
 */
public class MyPanel extends javax.swing.JFrame {

	Image packmenIcon;                                  //packmen icon
	Image ghostIcon;
	Image playerIcon;
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

	/**
	 * Creates new form MyPanel
	 */
	public MyPanel() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
	private void initComponents() {

		jPanel1 = new PanelComp(this);
		jMenuBar1 = new javax.swing.JMenuBar();
		fileMenu = new javax.swing.JMenu();
		addFileItem = new javax.swing.JMenuItem();
		elementMenu = new javax.swing.JMenu();
		addPlayerItem = new javax.swing.JMenuItem();
		startMenu = new javax.swing.JMenu();
		runItem = new javax.swing.JMenuItem();
		autoRunItem = new javax.swing.JMenuItem();

		jPanel1.setOpaque(false);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(1378, 620));
		 jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
	            public void mouseClicked(java.awt.event.MouseEvent evt) {
	                jPanel1MouseClicked(evt);
	            }
	        });
		
		jPanel1.setPreferredSize(new java.awt.Dimension(1368, 556));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 1368, Short.MAX_VALUE)
				);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 556, Short.MAX_VALUE)
				);

		fileMenu.setText("File");

		addFileItem.setText("Add Csv");
		addFileItem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				addFileItemMouseClicked(evt);
			}
		});
		addFileItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addFileItemActionPerformed(evt);
			}
		});
		fileMenu.add(addFileItem);

		jMenuBar1.add(fileMenu);

		elementMenu.setText("Element");

		addPlayerItem.setText("Add Player");
		addPlayerItem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				addPlayerItemMouseClicked(evt);
			}
		});
		addPlayerItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				addPlayerItemActionPerformed(evt);
			}
		});
		elementMenu.add(addPlayerItem);

		jMenuBar1.add(elementMenu);

		startMenu.setText("Start");

		runItem.setText("Run");
		runItem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				runItemMouseClicked(evt);
			}
		});
		runItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runItemActionPerformed(evt);
			}
		});
		startMenu.add(runItem);

		autoRunItem.setText("Auto Run");
		autoRunItem.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				autoRunItemMouseClicked(evt);
			}
		});
		autoRunItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				autoRunItemActionPerformed(evt);
			}
		});
		startMenu.add(autoRunItem);

		jMenuBar1.add(startMenu);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				);

		pack();

		packmenIcon = Toolkit.getDefaultToolkit().getImage("pacman.png");
		ghostIcon = Toolkit.getDefaultToolkit().getImage("Pacman_Ghost-512[1].png");
		playerIcon = Toolkit.getDefaultToolkit().getImage("robot.png");
		Container cp = getContentPane();
		filename.setEditable(false);
		p = new JPanel();
		p.setLayout(new GridLayout(1, 1));
		p.add(filename);
		cp.add(p, BorderLayout.NORTH);

		fruitsLabels = new ArrayList<FruitLabel>();
		pacmansLabels = new ArrayList<PacmanLabel>();
		ghostLabels = new ArrayList<GhostLabel>();
		boxs = new ArrayList<BoxLabel>();


		width = this.getWidth();
		height = this.getHeight();
	}// </editor-fold>                        
	
	private void runItemActionPerformed(java.awt.event.ActionEvent evt) {                                        
		// TODO add your handling code here:
		runGame();
		runTheGame = true;
	}                                       

	private void addFileItemMouseClicked(java.awt.event.MouseEvent evt) {                                         
		// TODO add your handling code here:
	}                                        

	private void addFileItemActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
		JFileChooser c = new JFileChooser();

		// Demonstrate "Open" dialog:
		int rVal = c.showOpenDialog(MyPanel.this);
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

	private void addPlayerItemActionPerformed(java.awt.event.ActionEvent evt) {                                              
		// TODO add your handling code here:
		myplayer_addable = true;
	}                                             

	private void addPlayerItemMouseClicked(java.awt.event.MouseEvent evt) {                                           
		// TODO add your handling code here:
	}                                          

	private void runItemMouseClicked(java.awt.event.MouseEvent evt) {                                     
		// TODO add your handling code here:
	}                                    

	private void autoRunItemActionPerformed(java.awt.event.ActionEvent evt) {                                            
		// TODO add your handling code here:
	}                                           

	private void autoRunItemMouseClicked(java.awt.event.MouseEvent evt) {                                         
		// TODO add your handling code here:
	}                                           

	private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
		x = evt.getX();
		y = evt.getY();
		Point3D pointPixel =new Point3D(x,y,0);
		Point3D gpsPixel = m.toGPS(pointPixel);
		if(myplayer_addable)
		{
			m.setMap(width, height);
			myPlayerLabel= new MyPlayerLabel(jPanel1.getWidth(), jPanel1.getHeight(),gpsPixel.x(),gpsPixel.y());
			myplayer_addable= false;
			play1.setIDs(315858340,208252684);

			play1.setInitLocation(gpsPixel.x(),gpsPixel.y());
			repaint();
		}
		if(runTheGame)
		{
			double azimuth = c.azimuth_elevation_dist((Point3D)game.getMyPlayer().getGeom(), gpsPixel)[0];
			goThere = (int)azimuth;
		}
    }     
	
	private void loadGame() {

		int Width = jPanel1.getWidth();
		System.out.println(Width);
		int Height= jPanel1.getHeight();
		System.out.println(Height);
		m = new Map(jPanel1.getImag(),jPanel1.getWidth(),jPanel1.getHeight(),start,end);

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
		}

		for (int i = 0; i < pacmans.size(); i++) {
			//add pacman in right pixel
			PacmanLabel pac = new PacmanLabel(Width,Height,pacmans.get(i));
			pacmansLabels.add(pac);
		}
		for (int i = 0; i < ghosts.size(); i++) {
			//add ghosts in right pixel
			GhostLabel ghost = new GhostLabel(Width,Height,ghosts.get(i));
			ghostLabels.add(ghost);
		}
		for(int i = 0; i < _boxs.size(); i++){
			//add boxs in right pixel
			BoxLabel box = new BoxLabel(Width,Height,_boxs.get(i));
			boxs.add(box);
		}
//		this.paintComponent(jPanel1.getGraphics());
		repaint();
	}
    
    public PanelComp getjPanel1() {
		return jPanel1;
	}
    
    public void paintComponent(Graphics g)
	{
		width = jPanel1.getWidth(); 
		height = jPanel1.getHeight();
		DrawMyPlayer(g,width,height);
		DrawBoxs(g,width,height);
		DrawFruits(g,width,height);
		DrawPackmens(g,width,height);
		DrawGhosts(g,width,height);
	}
    
    public void DrawMyPlayer(Graphics g, int width, int height) {
    	if(myPlayerLabel!=null) {
			myPlayerLabel.update(width, height);
			int x_player= (int)(((double)width)*((double)myPlayerLabel.x_factor));
			int y_player= (int)(((double)height)*((double)myPlayerLabel.y_factor));
			g.drawImage(playerIcon,x_player, y_player, 45, 45,jPanel1);
		}
    }
    
    public void DrawBoxs(Graphics g, int width, int height) {
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
				g.fillRect(x_box, y_box, box.x_dis, box.y_dis);
			}
		}
	}

	public void DrawFruits(Graphics g, int width, int height) {
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
	}
	
	public void DrawPackmens(Graphics g, int width, int height) {
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
					g.drawImage(packmenIcon,x_pacman, y_pacman, 28, 28,jPanel1);
				}
			}
		}
	}
	
	public void DrawGhosts(Graphics g, int width, int height) {
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
				g.drawImage(ghostIcon,x_ghost, y_ghost, 28, 28,jPanel1);
			}
		}
	}
	
	public void runGame() {
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
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(MyPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(MyPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(MyPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MyPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new MyPanel().setVisible(true);
			}
		});
	}

	private javax.swing.JMenuItem addFileItem;
	private javax.swing.JMenuItem addPlayerItem;
	private javax.swing.JMenuItem autoRunItem;
	private javax.swing.JMenu elementMenu;
	private javax.swing.JMenu fileMenu;
	private javax.swing.JMenuBar jMenuBar1;
	private PanelComp jPanel1;
	private javax.swing.JMenuItem runItem;
	private javax.swing.JMenu startMenu;
	// End of variables declaration 

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

		public BoxLabel(int width, int height, Rec rec)
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
