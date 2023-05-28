import java.awt.*;  import java.awt.event.*;

import javax.swing.*; import javax.swing.event.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.*;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
public class CardFighters extends JFrame{
	static MainGame mp;

	public CardFighters()
	{
		super("CardFighters.java");
		setSize(1000, 700);    
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);   
		setLocation(150,0);
		mp = new MainGame();   
		setContentPane( mp );
		setVisible(true);  
	}
	/**
	*main
	*/
	public static void main(String[]args)
	{
		CardFighters cf=new CardFighters();
	}
	/**
	 * getter for MainGame
	 */
	public static MainGame getMainGame() 
	{
		return mp;
	}
	
}
class MainGame extends JPanel
{
	CardLayout cl=new CardLayout();
	JPanel panelContainer;
	int winner;
	GamePanel gamePanel;
	DicePanel dicePanel;

	/**
	 * constructor for MainGame
	 */
	public MainGame()
	{
		setLayout(new GridLayout(1,1));
		runIt();
		
	}
	/**
	 * sets up panelContainer
	 */
	public void runIt()
	{
		panelContainer=new JPanel(cl);
		panelContainer.setLayout(cl);
		panelContainer.setOpaque(false);
		
		MainPanel main=new MainPanel();
		panelContainer.add(main,"Main");
		

		JPanel optionsPanel=main.getOptions();
		gamePanel=new GamePanel();
		dicePanel=new DicePanel();
		
		panelContainer.add("Dice Panel",dicePanel);

		//panelContainer.add("Options",optionsPanel);
		panelContainer.add("Play",gamePanel);
		
		add(panelContainer);		
	}
	/**
	 * shows the game over screen
	 */
	public void gameOver(int winner)
	{
		this.winner=winner;
		GameOver gameoverPanel=new GameOver(winner);
		panelContainer.add("Game Over",gameoverPanel);
		cl.show(panelContainer, "Game Over");
	}
	
	/**
	 * shows the dice panel
	 */
	public void dicePanel()
	{
		
		cl.show(panelContainer, "Dice Panel");
	}
	/**
	 * getter for winner
	 */
	public int getWinner() {
		return winner;
	}
	/**
	 * getter for winner
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}
	/**
	 * getter for gamePanel
	 */
	public GamePanel getGamePanel()
	{
		return gamePanel;
	}

	public DicePanel getDicePanel()
	{
		return dicePanel;
	}

	/**
	 * swap method to change what is shown by the card layout
	 */
	public void swap(String panel)
	{
		cl.show(panelContainer, panel);
		
	}
}
class DicePanel extends JPanel
{
	Image bg;
	ImageIcon dice,nextImg;
	int roll;
	JLabel rolls;
	JButton rollDice,next;
	int rollsLeft;
	/**
	 * constructor for dice panel
	 */
	public DicePanel()
	{
		dice=new ImageIcon("dicebutton.png");
		nextImg=new ImageIcon("nextbutton.png");
		rolls=new JLabel("");
		rolls.setForeground(Color.red);
		rollDice=new JButton("",dice);
		rollDice.setActionCommand("Roll Dice");
		rollDice.addActionListener(new DiceListener());
		next=new JButton("",nextImg);
		next.addActionListener(new DiceListener());
		next.setActionCommand("Next");
		rollsLeft=1;
		setLayout(new GridLayout(3,1));
		setBorder(new EmptyBorder(100, 100, 100, 100));
		Font medieval;
		try {
		     
		     medieval = Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf"));  
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf")));
		     rolls.setFont(medieval.deriveFont(Font.PLAIN, 40));
		     
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		rolls.setPreferredSize(new Dimension(300,100));
		rollDice.setPreferredSize(new Dimension(300,100));
		next.setPreferredSize(new Dimension(215,86));
		ClearPanel p1,p2,p3;
		p1=new ClearPanel();
		p2=new ClearPanel();
		p3=new ClearPanel();
		p1.add(rollDice);
		p2.add(rolls);
		p3.add(next);
		add(p1);
		add(p2);
		add(p3);
	}
	/**
	 * getter for num rolls left
	 */
	public int getRollsLeft() {
		return rollsLeft;
	}
	/**
	 * setter for num rolls left
	 */
	public void setRollsLeft(int rollsLeft) {
		this.rollsLeft = rollsLeft;
	}
	/**
	 * paint component
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		bg=new ImageIcon("dicepanel.png").getImage();
		
		g.drawImage(bg, 0,0,1000,700,null);
	}
	/**
	 * randomly generates a dice roll
	 */
	public void rollDice() {
		if(rollsLeft>0) {
			roll=(int)(Math.random()*6)+1;
			rolls.setText("  You rolled a "+roll+"!");
			rollsLeft--;
		}
	}
	/**
	 * getter for roll
	 */
	public int getRoll() {
		return roll;
	}
	/**
	 * setter for roll
	 */
	public void setRoll(int roll) {
		this.roll = roll;
		if(!(roll==0))
			rolls.setText("");
		else
			rolls.setText("  You rolled a "+roll+"!");
	}
	/**
	 * getter for rolls jlabel
	 */
	public JLabel getRolls() {
		return rolls;
	}
	/**
	 * setter for rolls jlabel
	 */
	public void setRolls(String str) {
		rolls.setText(str);
	}
	
}
/**
 * action listener for dice button
 */
class DiceListener implements ActionListener
{
	/**
	 * rolls the dice amd changes panels when the buttons are pressed
	 */
	public void actionPerformed(ActionEvent e)
	{
		String c=e.getActionCommand();
		if(c.equals("Roll Dice")) {
			CardFighters.getMainGame().getDicePanel().rollDice();
		}
		else if(c.equals("Next"))
		{
			if(CardFighters.getMainGame().getDicePanel().getRollsLeft()==0) {
				CardFighters.getMainGame().getGamePanel().round();
				CardFighters.getMainGame().swap("Play");
			}
		}
	}
}

class GameOver extends JPanel
{
	
	JButton back;
	JPanel layout;
	ImageIcon backImg=new ImageIcon("BackToMenu.png");
	Image bg,blood;
	int winner,time;
	Timer timer;
	/**
	 * constructor for GameOver
	 */
	public GameOver(int winner)
	{
		setBackground(Color.black);
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(1000,700));
		layout=new JPanel();
		this.winner=winner;
		
		repaint();
		setPreferredSize(new Dimension(1000,700));
		layout.setLayout(new GridLayout(3,1,150,150));
		layout.setBorder(new EmptyBorder(20, 20, 20, 20));
		back=new JButton("",backImg);
		back.setActionCommand("Back");
		back.setPreferredSize(new Dimension(300,100));
		back.addActionListener(new PanelChanger());
		JPanel p1=new JPanel();
		JPanel p2=new JPanel();
		p1.setOpaque(false);
		p2.setOpaque(false);
		layout.add(p1);
		layout.add(p2);
		layout.add(back);
		
		layout.setOpaque(false);
		add(layout);
		GameoverAnimation gameoveranimation=new GameoverAnimation();
		blood=new ImageIcon("000.png").getImage();
		timer=new Timer(5,gameoveranimation);
		timer.start();
	}
	/**
	 * paint component
	 */
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.white);

		if(winner==1)
			bg=new ImageIcon("GameOver1.png").getImage();
		else
			bg=new ImageIcon("GameOver2.png").getImage();
		
		

		g.drawImage(bg, 0,0,1000,700,null);
		g.drawImage(blood, 0,0,1000,700,null);
	}

	class GameoverAnimation implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			time++;
			if(10<time&&time<15)
				blood=new ImageIcon("death1.png").getImage();
			else if(time<20)
				blood=new ImageIcon("death2.png").getImage();
			else if(time<25)
				blood=new ImageIcon("death3.png").getImage();
			else if(time<30)
				blood=new ImageIcon("death4.png").getImage();
			else if(time<35)
				blood=new ImageIcon("death5.png").getImage();
			else if(time>35) {
				
				timer.stop();
				time=0;
			}
			
			repaint();
			grabFocus();
		}
	}
}
/**
*class for setting up the main menu
*including the directions panel and options panel
*/
class MainPanel extends JPanel
{
	JPanel optionsPanel, directionsPanel, opLayout;

	Boolean gory=true;
	ImageIcon backImg= new ImageIcon("BackButton.png");
	JCheckBox gore,normal,bg1,bg2,bg3,bg4;
	int swordx,speed;
	int bloody;
	int y,hp1,hp2;
	JMenuBar mb1,mb2;
	
	int time;
	JMenu p1hp,p2hp;
	JMenuItem m1,m2,m3,m4,m5,m6;
	Boolean moveSword;
	Timer swordtimer,bloodtimer;
	ArrayList<Circle>blood=new ArrayList<>();
	public MainPanel()
	{
		hp1=10;
		hp2=10;
		setBackground(Color.white);
		setPreferredSize(new Dimension(1000,700));
		setOpaque(false);
		createPanels();
		speed=60;
		swordx=2800;
		time=0;
		moveSword=true;
		
		SwordMover swordmover=new SwordMover();
		swordtimer = new Timer(5,swordmover);
		swordtimer.start();
		
		BloodMover bloodmover=new BloodMover();
		bloodtimer=new Timer(5,bloodmover);
		bloodtimer.start();
		
		for(int i=0;i<25;i++)
		{
			blood.add(new Circle());
		}
		blood.get(0).setX(70);
		blood.get(10).setX(930);
	}
	
	/**
	 * Changes the y value of the blood
	 */
	class BloodMover implements ActionListener{
		/**
		 * reacts to ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			
			y++;
			if(y>700)
				y=0;
			for(int i=0;i<blood.size();i++)
			{
				blood.get(i).setY(blood.get(i).getY()-blood.get(i).getSpeed());
				if(blood.get(i).getY()<0)
					blood.get(i).setY(700);
			}
			
			repaint();
			grabFocus();
		}
	}
	class SwordMover implements ActionListener{
		/**
		 * reacts to ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			bloody+=4;
			swordx-=speed;
			if(swordx<=900)
			{
				speed=50;
			}
			if(swordx<=750)
			{
				speed=40;
			}
			if(swordx<=600)
			{
				speed=30;
			}
			if(swordx<=500)
			{
				speed=20;
			}
			if(swordx<=400)
				speed=10;
			if(swordx<=200) {
				speed=0;
				moveSword=false;
				swordx=200;
				if(bloody>940)
				{
					swordtimer.stop();
				}
			}
			repaint();
			grabFocus();
		}
	}
	/**
	*draws the background image
	*/
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		Image img;
		Image blooddrop=new ImageIcon("DropOfBlood.png").getImage();
		if(gory)
			img = new ImageIcon("CFStartGory.png").getImage();
		else
			img = new ImageIcon("CFStartPanel.png").getImage();
		g.drawImage(img, 0,0,1000,700,null);
		
		Image swordImg=new ImageIcon("startpanelsword.png").getImage();
		g.drawImage(swordImg, swordx, 0, 700, 120, null);
		
		g.setColor(Color.red);
		for(int i=0;i<blood.size();i++)
		{
			g.drawImage(blood.get(i).getImg(),blood.get(i).getX(), blood.get(i).getY(),10,10, null);
		}
		if(bloody>=240)
		{
			g.drawImage(blooddrop,250,bloody-210,50,50, null);
			g.drawImage(blooddrop,680,bloody-210,50,50, null);
		}
		g.setColor(Color.black);
	}
	/**
	*sets up main panel and game panels
	*creates labels & buttons for panels
	*adds them all to a panel with cardlayout
	*/
	public void createPanels()
	{
		
		JPanel main=new JPanel(new BorderLayout(0,40));
		main.setBorder(new EmptyBorder(15, 15, 15, 15));
		JPanel main1=new JPanel();
		JPanel main2=new JPanel(new GridLayout(4,1,200,50));
		main.setOpaque(false);
		main1.setOpaque(false);
		main2.setOpaque(false);
		
		JLabel gameTitle=new JLabel("CARD FIGHTERS");
		Font casino;
		try {
		     
		     casino = Font.createFont(Font.TRUETYPE_FONT, new File("CasinoShadow.ttf"));  
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("CasinoShadow.ttf")));
		     gameTitle.setFont(casino.deriveFont(Font.PLAIN, 80));
		     
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		Color gold=new Color(250,220,55);
		gameTitle.setForeground(Color.black);
		gameTitle.setOpaque(false);
		Font serif=new Font("Sans Serif", Font.PLAIN, 50);
		
		main1.add(gameTitle);
		JButton play;
		ImageIcon playbg = new ImageIcon("playbutton.png");
		play = new JButton("",playbg);
		ClearPanel button1=new ClearPanel();
		
		button1.add(play);
		play.setActionCommand("Play");
		play.setPreferredSize(new Dimension(300,100));
		
		play.addActionListener(new PanelChanger());
		main2.add(button1);
		
		ImageIcon dirbg = new ImageIcon("directionsbutton.png");
		JButton directions=new JButton("",dirbg);
		ClearPanel button2=new ClearPanel();
		button2.add(directions);
		directions.setActionCommand("Directions");
		directions.setPreferredSize(new Dimension(300,100));
		main2.add(button2);
		

		
		main.add(main1,BorderLayout.NORTH);
		main.add(main2,BorderLayout.CENTER);
		add(main);
		setLayout(new FlowLayout());

		
		


		mb1=new JMenuBar();
		mb2=new JMenuBar();
		p1hp = new JMenu("10");
		p2hp = new JMenu("10");
		m1=new JMenuItem("5");
		m2=new JMenuItem("10");
		m3=new JMenuItem("15");
		m4=new JMenuItem("5");
		m5=new JMenuItem("10");
		m6=new JMenuItem("15");
		m1.addActionListener(new Hp1Listener());
		m2.addActionListener(new Hp1Listener());
		m3.addActionListener(new Hp1Listener());
		m4.addActionListener(new Hp2Listener());
		m5.addActionListener(new Hp2Listener());
		m6.addActionListener(new Hp2Listener());
		p1hp.add(m1);
		p1hp.add(m2);
		p1hp.add(m3);
		p2hp.add(m4);
		p2hp.add(m5);
		p2hp.add(m6);
		mb1.add(p1hp);
		mb2.add(p2hp);
		
		ClearPanel hppanel=new ClearPanel(new BorderLayout());
		ClearPanel menupanel=new ClearPanel(new GridLayout(2,1,20,20));
		ClearPanel menu1=new ClearPanel((new BorderLayout()));
		ClearPanel menu2=new ClearPanel((new BorderLayout()));
		
		ClearPanel menuformat=new ClearPanel(new BorderLayout());
		menuformat.add(menupanel,BorderLayout.NORTH);
		hppanel.add(menuformat,BorderLayout.WEST);
		JLabel p1=new JLabel("  Player 1 hp:  ");
		JLabel p2=new JLabel("  Player 2 hp:  ");
		menu1.add(p1,BorderLayout.WEST);
		menu1.add(mb1,BorderLayout.EAST);
		menu2.add(p2,BorderLayout.WEST);
		menu2.add(mb2,BorderLayout.EAST);
		menupanel.add(menu1);
		menupanel.add(menu2);

		ClearPanel format=new ClearPanel(new BorderLayout());
		//format.add(checkboxes,BorderLayout.WEST);
		


		hppanel.setBorder(new EmptyBorder(30,0,0,0));
		Font medieval;
		try {
		     
		     medieval = Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf"));  
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf")));

		     p1.setFont(medieval.deriveFont(Font.PLAIN, 19));
		     p2.setFont(medieval.deriveFont(Font.PLAIN, 19));
		     
		     
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}

	public JPanel getOptions()
	{
		return opLayout;
	}
	class BgPanel extends JPanel
	{
		public BgPanel()
	    {
	    	setOpaque(false);
	    }
		/**
		 * paint component for BgPanel
		 * paints the main menu's bg
		 */
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			Image img;
			if(gory)
				img = new ImageIcon("CFStartGory.png").getImage();
			else
				img = new ImageIcon("CFStartPanel.png").getImage();
			g.drawImage(img, 0,0,1000,700,null);
		}
	}
	public int getHp2() {
		return hp2;
	}
	public void setHp2(int hp2) {
		this.hp2 = hp2;
	}
	public int getHp1() {
		return hp1;
	}
	public void setHp1(int hp1) {
		this.hp1 = hp1;
	}
	/**
	 * Action Listener class that listens to the hp JMenu for player 1
	 */
	class Hp1Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			p1hp.setText(c);
			hp1=Integer.parseInt(c);
			CardFighters.getMainGame().getGamePanel().setHp1(hp1);
		}
	}
	/**
	 * Action Listener class that listens to the hp JMenu for player 2
	 */
	class Hp2Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			p2hp.setText(c);
			hp2=Integer.parseInt(c);
			CardFighters.getMainGame().getGamePanel().setHp2(hp2);
		}
	}
	/**
	*Action Listener class that listens to checkboxes
	*/
	class CheckListener implements ActionListener {
		/**
		 * reacts to ActionEvent
		 */
		public void actionPerformed(ActionEvent e) {
			String c = e.getActionCommand();
			if (c.equals("Extra Gory Graphics")) {
				
				gore.setSelected(true);
				normal.setSelected(false);
				gory=true;
			}
			else if(c.equals("Gory Graphics"))
			{
				gore.setSelected(false);
				normal.setSelected(true);
				gory=false;
			}
			if (c.equals("Classic")) {
				
				bg1.setSelected(true);
				bg2.setSelected(false);
				bg3.setSelected(false);
				bg4.setSelected(false);
				CardFighters.getMainGame().getGamePanel().setBgnum(1);
				CardFighters.getMainGame().getGamePanel().repaint();
			}
			else if (c.equals("Magic")) {
				
				bg2.setSelected(true);
				bg1.setSelected(false);
				bg3.setSelected(false);
				bg4.setSelected(false);
				CardFighters.getMainGame().getGamePanel().setBgnum(2);
				CardFighters.getMainGame().getGamePanel().repaint();
			}
			else if (c.equals("Cubic")) {
				
				bg3.setSelected(true);
				bg1.setSelected(false);
				bg2.setSelected(false);
				bg4.setSelected(false);
				CardFighters.getMainGame().getGamePanel().setBgnum(3);
				CardFighters.getMainGame().getGamePanel().repaint();
			}
			else if (c.equals("Battlefield")) {
				
				bg4.setSelected(true);
				bg2.setSelected(false);
				bg3.setSelected(false);
				bg1.setSelected(false);
				CardFighters.getMainGame().getGamePanel().setBgnum(4);
				CardFighters.getMainGame().getGamePanel().repaint();
			}
			repaint();
			opLayout.repaint();
		}
	}
	
}
/**
*ActionListener class that listens to buttons and switches panels
*/
class PanelChanger implements ActionListener
{
	/**
	 * switches panel shown by card layout depending on button pressed
	 */
	public void actionPerformed(ActionEvent e)
	{
		MainGame mainGame =CardFighters.getMainGame();
		String c=e.getActionCommand();
		if(c.equals("Back"))
			mainGame.swap("Main");
		else if(c.equals("Play"))
		{
			mainGame.dicePanel();
			
		}
		else {
			mainGame.swap(c);
		}
	}
}

class ClearPanel extends JPanel
{
	/**
	 * constructor for ClearPanel
	 */
	public ClearPanel()
    {
    	setOpaque(false);
    }
	/**
	 * constructor for ClearPanel that accepts a BorderLayout as a parameter
	 */
    public ClearPanel(BorderLayout layout)
    {
    	BorderLayout panellayout=layout;
    	setLayout(panellayout);
    	setOpaque(false);
    }
    /**
	 * constructor for ClearPanel that accepts a GridLayout as a parameter
	 */
    public ClearPanel(GridLayout layout)
    {
    	GridLayout panellayout=layout;
    	setLayout(panellayout);
    	setOpaque(false);
    }
}
