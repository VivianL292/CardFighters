import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


class GamePanel extends JPanel implements KeyListener,MouseListener,MouseMotionListener
{
	CardLayout phaseLayout=new CardLayout();
	CardDeck list1,list2;
	Deck deck1,deck2;
	Image cardImg,swordImg,directImg,bloodImg;
	JButton endButton,endButton2;
	int hp1,hp2,dealCards,summonCards,round,offense,drawsLeft,maxcards,summonsLeft,maxfield,player,turn,x,attacks,atknum,time,winner,dmgDealt,time2,effectOf,bgnum;
	Image deckIcon,deckIcon2,effectImg;
	JLabel draws,summons,roundText,roundText2,p1hp,p2hp,cardname;
	JTextArea carddesc1;
	ArrayList<Card>hand1=new ArrayList<>();
	ArrayList<Card>field1=new ArrayList<>();
	ArrayList<Card>hand2=new ArrayList<>();
	ArrayList<Card>field2=new ArrayList<>();
	ArrayList<Card>effects=new ArrayList<>();
	Player player1,player2,currentPlayer;
	String phase;
	Timer timer,timer2; 
	boolean playAnimation,directatk,showhelp;
	Card shown,currCard,nextCard,effectCard;
	public GamePanel()
	{
		
		addMouseListener(this);
		addMouseMotionListener(this);
		carddesc1=new JTextArea("");
		carddesc1.setEditable(false);
		cardname=new JLabel("");
		bgnum=1;
		setLayout(new BorderLayout(10,10));
		setOpaque(false);
		deckIcon=new ImageIcon("CardBack.png").getImage();
		dmgDealt=1;
		deckIcon2=new ImageIcon("CardBack2.png").getImage();
		swordImg=new ImageIcon("000.png").getImage();
		effectImg=new ImageIcon("000.png").getImage();
		bloodImg=new ImageIcon("000.png").getImage();
		directImg=new ImageIcon("DirectAttackSword.png").getImage();
		showhelp=false;
		effectOf=0;
		effectCard=null;
		shown=null;
		currCard=null;
		nextCard=null;
		player1=new Player(1);
		player2=new Player(2);
		winner=0;
		hp1=10;
		hp2=10;
		attacks=0;
		atknum=0;
		dealCards=0;
		maxcards=6;
		drawsLeft=0;
		round=1;
		offense=1;
		player=1;
		currentPlayer=player1;
		phase="offense";
		summonsLeft=0;
		maxfield=6;
		summonCards=0;
		x=260;
		turn=0;
		time=1;
		time2=0;
		directatk=false;
		
		AttackAnimation atkanimation=new AttackAnimation();
		timer = new Timer(5,atkanimation);
		EffectAnimation effanimation=new EffectAnimation();
		timer2=new Timer(5,effanimation);
		
		runIt();
		addKeyListener(this);
		
	}
	public void paintComponent(Graphics g) 
    {
		requestFocus();
		super.paintComponent(g);
		Image bg;
		
		if(bgnum==1)
			bg = new ImageIcon("gamepanelbg.png").getImage();
		else if(bgnum==2) {
			bg = new ImageIcon("magicbg.png").getImage();
			
		}
		else if(bgnum==3)
			bg = new ImageIcon("cubicbg.png").getImage();
		else
			bg = new ImageIcon("battlefieldbg.png").getImage();
		setLabels();
		g.drawImage(bg,0,0,1000,700,null);
		g.drawImage(deckIcon, 0,500,100,145,null);
		g.drawImage(deckIcon2, 890,0,100,145,null);
		player1.setHp(hp1);
		player2.setHp(hp2);
		p1hp.setText((" Player 1's health: "+hp1));
		p2hp.setText((" Player 2's health: "+hp2));
		while(dealCards>0&&hand1.size()<=maxcards&&drawsLeft>0)
		{
			for(int i=0;i<hand1.size();i++)
				g.drawImage((hand1.get(i)).getImage(),(hand1.get(i).getPosition())*105+50,500,100,145,null);
			dealCards--;
			drawsLeft--;
			draws.setText("Draws Left: "+drawsLeft);
			
		}
		if(dealCards==0||hand1.size()>maxcards||drawsLeft==0)
		{
			for(int i=0;i<hand1.size();i++)
				g.drawImage((hand1.get(i)).getImage(),(hand1.get(i).getPosition())*105+50,500,100,145,null);
		}
		
		while(summonCards>0&&field1.size()<=maxfield&&summonsLeft>0)
		{
			for(int i=0;i<field1.size();i++)
				g.drawImage((field1.get(i)).getImage(),(field1.get(i).getPosition())*110+150,350,100,145,null);
			summonCards--;
			summonsLeft--;
			summons.setText("Summons Left: "+summonsLeft);
		}
		if(summonCards==0||field1.size()>maxfield||summonsLeft==0)
		{
			for(int i=0;i<field1.size();i++)
				g.drawImage((field1.get(i)).getImage(),(field1.get(i).getPosition())*110+150,350,100,145,null);
		}
		while(dealCards>0&&hand2.size()<=maxcards&&drawsLeft>0)
		{
			for(int i=0;i<hand2.size();i++)
				g.drawImage((hand2.get(i)).getImage(),(hand2.get(i).getPosition())*105+100,3,100,145,null);
			dealCards--;
			drawsLeft--;
			draws.setText("Draws Left: "+drawsLeft);
		}
		if(dealCards==0||hand2.size()>maxcards||drawsLeft==0)
		{
			for(int i=0;i<hand2.size();i++)
				g.drawImage((hand2.get(i)).getImage(),(hand2.get(i).getPosition())*105+100,3,100,145,null);
		}
		while(summonCards>0&&field2.size()<=maxfield&&summonsLeft>0)
		{
			for(int i=0;i<field2.size();i++)
				g.drawImage((field2.get(i)).getImage(),(field2.get(i).getPosition())*110+150,150,100,145,null);
			summonCards--;
			summonsLeft--;
			summons.setText("Summons Left: "+summonsLeft);
		}
		if(summonCards==0||field2.size()>maxfield||summonsLeft==0)
		{
			for(int i=0;i<field2.size();i++)
				g.drawImage((field2.get(i)).getImage(),(field2.get(i).getPosition())*110+150,150,100,145,null);
		}
		if(directatk&&timer.isRunning())
		{
			int changey=time*10;
			if(player1.onOffense) {
				directImg=new ImageIcon("DirectAttackSword.png").getImage();
				if(!(currCard==null))
				{
					g.drawImage(bloodImg, currCard.getPosition()*110+108, 0, 175, 175, null);
					g.drawImage(directImg, currCard.getPosition()*110+108, 180-changey, 175, 175, null);
				}
			}
			else
			{
				directImg=new ImageIcon("DirectAttackSword2.png").getImage();
				if(!(currCard==null))
				{
					g.drawImage(bloodImg, currCard.getPosition()*110+108, 525, 175, 175, null);
					g.drawImage(directImg, currCard.getPosition()*110+108, 300+changey, 175, 175, null);
				}
			}
		}
		else
		{
			if(player1.onOffense) {
				g.setColor(Color.red);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke((float)3.0));
				int linelength=time*5;
				if(time>10)
					linelength=50;
				if(!(currCard==null))
				{
					if(timer.isRunning())
						g.drawLine(currCard.getPosition()*110+201,348,currCard.getPosition()*110+201,348-linelength);
					g.setColor(Color.black);
					g.drawImage(swordImg, currCard.getPosition()*110+120, 200, 150, 175, null);
				}
			}
			else {
				
				g.setColor(Color.red);
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke((float)3.0));
				int linelength=time*5;
				if(time>10)
					linelength=50;
				if(!(currCard==null))
				{
					if(timer.isRunning())
						g.drawLine(currCard.getPosition()*110+201,348,currCard.getPosition()*110+201,298+linelength);
					g.setColor(Color.black);
					g.drawImage(swordImg, currCard.getPosition()*110+130, 275, 150, 175, null);
				}
				
			}
		}
		if(timer2.isRunning())
		{
			for(Card card: effects)
			{
				if(card.getOwner()==1) {
					g.drawImage(effectImg, card.getPosition()*110+150,373,100,100,null);
				
				}
				if(card.getOwner()==2)
				{
					g.drawImage(effectImg, card.getPosition()*110+150,172,100,100,null);
				}
			}
		}
		if(showhelp)
		{
			Image helpscreen=new ImageIcon("helpscreen.png").getImage();
			g.drawImage(helpscreen,240,185,600,330,null);
			
			Font medieval;
			try {
			     
			     medieval = Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf"));  
			     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf")));
			     g.setFont(medieval.deriveFont(Font.PLAIN, 22));
			    
			     
			} catch (IOException | FontFormatException e) {
				e.printStackTrace();
			}
			g.setColor(Color.black);
			String currentText="";
			
			
			if(phase=="battlesim")
			{
				currentText="Now that you're done battling, press 'NEXT' to go \nto the next turn.";
				String currText=currentText;
				int linenum=0;
				while(currText.length()>0)
				{
					int i = currText.indexOf("\n");
					if(i>=0)
					{
						String draw=currText.substring(0,i);
						g.drawString(draw,310,290+linenum*40);
						currText=currText.substring(i+1,currText.length());
						linenum++;
					}
					else
					{
						g.drawString(currText, 310, 290+40*linenum);
						currText="";
					}
				}
			}
			if(phase=="battle")
			{
				if(attacks==0)
					currentText="Now that you're done battling, press 'NEXT' to go \nto the next turn.";
				else
					currentText="Now it's time for battle. Press 'NEXT' to make \nthe offense player's cards' attack.";
				String currText=currentText;
				int linenum=0;
				while(currText.length()>0)
				{
					int i = currText.indexOf("\n");
					if(i>=0)
					{
						String draw=currText.substring(0,i);
						g.drawString(draw,310,290+linenum*40);
						currText=currText.substring(i+1,currText.length());
						linenum++;
					}
					else
					{
						g.drawString(currText, 310, 290+40*linenum);
						currText="";
					}
				}
			}
			if(phase=="offense")
			{
				
				currentText="It's currently the offense player's chance to play. \nDraw cards by clicking 'draw' and summon cards \nfrom your hand to your field by selecting which card \nto summon using keys 1-6 on your keyboard. \nPress 'Next' when you're done.";
				String currText=currentText;
				int linenum=0;
				while(currText.length()>0)
				{
					int i = currText.indexOf("\n");
					if(i>=0)
					{
						String draw=currText.substring(0,i);
						g.drawString(draw,310,290+linenum*40);
						currText=currText.substring(i+1,currText.length());
						linenum++;
					}
					else
					{
						g.drawString(currText, 310, 290+40*linenum);
						currText="";
					}
				}
			}
			if(phase=="defense")
			{
				
				currentText="It's currently the defense player's chance to play. \nDraw cards by clicking 'draw' and summon cards \nfrom your hand to your field by selecting which card \nto summon using keys 1-6 on your keyboard. \nPress 'End defense phase' when you're done.";
				String currText=currentText;
				int linenum=0;
				while(currText.length()>0)
				{
					int i = currText.indexOf("\n");
					if(i>=0)
					{
						String draw=currText.substring(0,i);
						g.drawString(draw,310,290+linenum*40);
						currText=currText.substring(i+1,currText.length());
						linenum++;
					}
					else
					{
						g.drawString(currText, 310, 290+40*linenum);
						currText="";
					}
				}
			}
			
		}
    }
	public Player getP1() {
		return player1;
	}
	public Player getP2() {
		return player2;
	}
	public int getBgnum() {
		return bgnum;
	}
	public void setBgnum(int bgnum) {
		this.bgnum = bgnum;
	}
	public int getHp1() {
		return hp1;
	}
	public void setHp1(int hp1) {
		this.hp1 = hp1;
	}
	public int getHp2() {
		return hp2;
	}
	public void setHp2(int hp2) {
		this.hp2 = hp2;
	}
	public void mouseMoved(MouseEvent e) {
		int x=e.getX();
		int y=e.getY();
				
		if(3<y&&y<148)
		{
			if(205<x&&x<305)
			{
				for(Card card: hand2) {
					if(card.getPosition()==1)
						shown=card;
				}
			}
			else if(310<x&&x<410)
			{
				for(Card card: hand2) {
					if(card.getPosition()==2)
						shown=card;
				}
			}
			else if(415<x&&x<515)
			{
				for(Card card: hand2) {
					if(card.getPosition()==3)
						shown=card;
				}
			}
			else if(520<x&&x<620)
			{
				for(Card card: hand2) {
					if(card.getPosition()==4)
						shown=card;
				}
			}
			else if(625<x&&x<725)
			{
				for(Card card: hand2) {
					if(card.getPosition()==5)
						shown=card;
				}
			}
			else if(730<x&&x<830)
			{
				for(Card card: hand2) {
					if(card.getPosition()==6)
						shown=card;
				}
			}
		}
		else if(150<y&&y<295)
		{
			if(260<x&&x<360)
			{
				for(Card card: field2) {
					if(card.getPosition()==1)
						shown=card;
				}
			}
			else if(370<x&&x<470)
			{
				for(Card card: field2) {
					if(card.getPosition()==2)
						shown=card;
				}
			}
			else if(480<x&&x<580)
			{
				for(Card card: field2) {
					if(card.getPosition()==3)
						shown=card;
				}
			}
			else if(590<x&&x<690)
			{
				for(Card card: field2) {
					if(card.getPosition()==4)
						shown=card;
				}
			}
			else if(700<x&&x<800)
			{
				for(Card card: field2) {
					if(card.getPosition()==5)
						shown=card;
				}
			}
			else if(810<x&&x<910)
			{
				for(Card card: field2) {
					if(card.getPosition()==6)
						shown=card;
				}
			}
		}
		else if(350<y&&y<495)
		{
			if(260<x&&x<360)
			{
				for(Card card: field1) {
					if(card.getPosition()==1)
						shown=card;
				}
			}
			else if(370<x&&x<470)
			{
				for(Card card: field1) {
					if(card.getPosition()==2)
						shown=card;
				}
			}
			else if(480<x&&x<580)
			{
				for(Card card: field1) {
					if(card.getPosition()==3)
						shown=card;
				}
			}
			else if(590<x&&x<690)
			{
				for(Card card: field1) {
					if(card.getPosition()==4)
						shown=card;
				}
			}
			else if(700<x&&x<800)
			{
				for(Card card: field1) {
					if(card.getPosition()==5)
						shown=card;
				}
			}
			else if(810<x&&x<910)
			{
				for(Card card: field1) {
					if(card.getPosition()==6)
						shown=card;
				}
			}
		}
		else if(500<y&&y<645)
		{
			if(155<x&&x<255)
			{
				for(Card card: hand1) {
					if(card.getPosition()==1)
						shown=card;
				}
			}
			else if(260<x&&x<360)
			{
				for(Card card: hand1) {
					if(card.getPosition()==2)
						shown=card;
				}
			}
			else if(365<x&&x<465)
			{
				for(Card card: hand1) {
					if(card.getPosition()==3)
						shown=card;
				}
			}
			else if(470<x&&x<570)
			{
				for(Card card: hand1) {
					if(card.getPosition()==4)
						shown=card;
				}
			}
			else if(575<x&&x<675)
			{
				for(Card card: hand1) {
					if(card.getPosition()==5)
						shown=card;
				}
			}
			else if(680<x&&x<780)
			{
				for(Card card: hand1) {
					if(card.getPosition()==6)
						shown=card;
				}
			}
		}
		if(!(shown==null))
		{
			grabFocus();
			cardname.setText(shown.getName());
			carddesc1.setText(shown.getEffect());
		}
		
	}
	public void runIt()
	{
		ClearPanel game4=new ClearPanel(new GridLayout(1,1));
		ClearPanel game3=new ClearPanel(new GridLayout(1,3));
		ClearPanel game1=new ClearPanel(new GridLayout(1,1));
		
		ClearPanel cdtop=new ClearPanel(new GridLayout(3,1));
		cdtop.add(cardname);
		cdtop.add(carddesc1);
		cdtop.add(new ClearPanel());
		cdtop.setBorder(new EmptyBorder(10, 10, 10, 10));
		cardname.setForeground(Color.white);
		carddesc1.setForeground(Color.white);
		carddesc1.setOpaque(false);
		Font serif=new Font("Serif", Font.PLAIN, 15);
		Font serif2=new Font("Serif", Font.PLAIN, 20);
		carddesc1.setFont(serif);
		Font medieval;
		try {
		     
		     medieval = Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf"));  
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Seagram tfb.ttf")));
		     cardname.setFont(medieval.deriveFont(Font.PLAIN, 20));
		    
		     
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		cardname.setFont(serif2);
		game3.add(cdtop);
		game3.add(new ClearPanel());
		ClearPanel right=new ClearPanel(new BorderLayout());
		ClearPanel rightlayout=new ClearPanel(new BorderLayout());
		rightlayout.add(right,BorderLayout.NORTH);
		ImageIcon helpImg=new ImageIcon("helpbutton.png");
		JButton helpbutton=new JButton("",helpImg);
		helpbutton.setPreferredSize(new Dimension(45,45));
		helpbutton.setActionCommand("Help");
		helpbutton.addActionListener(new BoardListener());
		right.add(helpbutton,BorderLayout.EAST);
		game3.add(rightlayout);
		
		
		list1=player1.getList();
		deck1=player1.getDeck();
		for(Card card:list1.getDeck())
		{
			card.setOwner(1);
		}
		ClearPanel deck=new ClearPanel();
		ClearPanel p1panel=new ClearPanel(new BorderLayout(200,0));
		ClearPanel p1structure=new ClearPanel(new GridLayout(1,4));
		p1structure.add(new ClearPanel());
		p1structure.add(new ClearPanel());
		p1structure.add(new ClearPanel());
		ClearPanel offensePanel=new ClearPanel(new GridLayout(2,1));
		
		JButton draw=new JButton("Draw");
		draw.addActionListener(new BoardListener());
		draw.setActionCommand("Draw");
		deck.add(draw);
		ImageIcon end=new ImageIcon("endturnbutton.png");
		endButton=new JButton("End offense phase",end);
		
    endButton.setPreferredSize(new Dimension(195,100));
		endButton.setBackground(Color.gray);
		endButton.addActionListener(new BoardListener());
		roundText=new JLabel("offensive player plays (player "+player+")");
		offensePanel.add(roundText);
		offensePanel.add(endButton);
		
		ClearPanel defensePanel=new ClearPanel(new GridLayout(2,1));
		endButton2=new JButton("End defense phase");
		endButton2.addActionListener(new BoardListener());
		roundText2=new JLabel("Defensive player plays (player "+player+")");
		defensePanel.add(roundText2);
		defensePanel.add(endButton2);
		
		p1structure.setLayout(phaseLayout);
		p1structure.add("offense",offensePanel);
		p1structure.add("defense",defensePanel);
		p1panel.add(deck,BorderLayout.WEST);
		p1panel.add(p1structure,BorderLayout.EAST);
		game1.add(p1panel);
		
		Player player2=new Player(2);
		list2=player2.getList();
		deck2=player2.getDeck();
		for(Card card:list2.getDeck())
		{
			card.setOwner(2);
		}
		ClearPanel p2panel=new ClearPanel(new BorderLayout(00,0));
		ClearPanel labels=new ClearPanel(new GridLayout(4,1));
		ClearPanel deck2=new ClearPanel();
		p1hp=new JLabel(" Player 1's health: "+hp1);
		p2hp=new JLabel(" Player 2's health: "+hp2);
		draws=new JLabel(" Draws Left: "+drawsLeft);
		summons=new JLabel(" Summons Left: "+summonsLeft);
		
		labels.add(p1hp);
		labels.add(p2hp);
		labels.add(draws);
		labels.add(summons);
		
		JButton draw2=new JButton("Draw");
		draw2.addActionListener(new BoardListener());
		draw2.setActionCommand("Draw");
		ClearPanel p2draw=new ClearPanel();
		p2draw.add(draw2);
		
		p2panel.add(labels,BorderLayout.WEST);
		p2panel.add(deck2,BorderLayout.CENTER);
		p2panel.add(p2draw,BorderLayout.EAST);
		game4.add(p2panel);
		
		game1.setPreferredSize(new Dimension(165,165));
		game4.setPreferredSize(new Dimension(150,150));
		add(game4,BorderLayout.NORTH);
		add(game3,BorderLayout.CENTER);
		add(game1,BorderLayout.SOUTH);
		
		roundText.setForeground(Color.black);
		p1hp.setForeground(Color.black);
		p2hp.setForeground(Color.black);
		summons.setForeground(Color.black);
		draws.setForeground(Color.black);
		
	}
	public void round()
	{

		
		if(phase=="offense") {
			
			offensePhase();
		}
		else if(phase=="defense")
			defensePhase();
		else {
			battleSim();
			
		}
	}
	public void battleSim()
	{
		directatk=false;
		if(attacks==0)
		{
			phase="battlesim";
		}
		else
		{
			endButton.setText("Next battle");
		}
		if(phase=="battle") {
			
				if(player1.getOnOffense())
				{
					offense=1;
					for(Card card:field1)
					{
						if(atknum+1==card.getPosition())
						{
							currCard=card;
							for(Card cd: field1)
							{
								if(atknum+2==cd.getPosition())
									nextCard=cd;
							}
							if(currCard.getName().equals("Heal 2"))
							{
								heal(2,offense);
								effectActivation(currCard,offense);
							}
							
							if(currCard.getName().equals("Steal 1"))
							{
								steal(1,offense);
								effectActivation(currCard,offense);
							}
							if(currCard.getName().equals("Buff 2"))
							{
								if(!(nextCard==null)) {
									buff("dmg+2",nextCard,offense);
									effectActivation(currCard,offense);
								}
								
							}
							if(currCard.getName().equals("Dmg 2"))
							{
								damage(2,2);
								effectActivation(currCard,offense);
							}
							if(currCard.getName().equals("Direct Attack"))
							{
									directatk=true;
									effectActivation(currCard,offense);
							}
							dmgDealt=card.getDmg();
						}
					}
				}
				else if(player2.getOnOffense())
				{
					offense=2;
					for(Card card:field2)
					{
						if(atknum+1==card.getPosition())
						{
							currCard=card;
							for(Card cd: field2)
							{
								if(atknum+2==cd.getPosition())
									nextCard=cd;
							}
							if(currCard.getName().equals("Heal 2"))
							{
								heal(2,offense);
								effectActivation(currCard,offense);
							}
							if(currCard.getName().equals("Steal 1"))
							{
								steal(1,offense);
								effectActivation(currCard,offense);
							}
							if(currCard.getName().equals("Buff 2"))
							{
								if(!(nextCard==null)) {
									buff("dmg+2",nextCard,offense);
									effectActivation(currCard,offense);
								}
							}
							if(currCard.getName().equals("Dmg 2"))
							{
								damage(2,1);
								effectActivation(currCard,offense);
							}
							if(currCard.getName().equals("Direct Attack"))
							{
									directatk=true;
									effectActivation(currCard,offense);
							}
							dmgDealt=card.getDmg();
						}
				}
				
			}
			if(player1.getOnOffense())
			{
				offense=1;
				for(Card card:field1)
				{
					if(atknum+1==card.getPosition())
					{
						currCard=card;
						dmgDealt=card.getDmg();
					}
				}
			}
			if(!directatk)
			{
				if(player1.getOnOffense())
				{
					
					if(field2.isEmpty()) {
						directatk=true;
						
					}
					else
						directatk=false;
				}
				else if(player2.getOnOffense())
				{
					if(field1.isEmpty()) {
						directatk=true;
						
					}
					else
						directatk=false;
				}
			}
			if(player1.getOnOffense())
			{
				Boolean check=true;
				for(Card card:field2)
				{
					if(currCard.getPosition()==card.getPosition())
						check=false;
				}
				if(check)
					directatk=true;
			}
			else if(player2.getOnOffense())
			{
				Boolean check=true;
				for(Card card:field1)
				{
					if(currCard.getPosition()==card.getPosition())
						check=false;
				}
				if(check)
					directatk=true;
			}
			time=0;
			timer.start();
			atknum++;
			attacks--;
		}
		if(attacks==0)
		{
			endButton.setText("End turn");
		}
		
	}
	class EffectAnimation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			time2++;
			if(6<time2&&time2<10)
				effectImg=new ImageIcon("eff1.png").getImage();
			else if(time2<14)
				effectImg=new ImageIcon("eff2.png").getImage();
			else if(time2<18)
				effectImg=new ImageIcon("eff3.png").getImage();
			else if(time2<22)
				effectImg=new ImageIcon("eff4.png").getImage();
			else if(time2<26)
				effectImg=new ImageIcon("eff5.png").getImage();
			else if(time2<30)
				effectImg=new ImageIcon("eff6.png").getImage();
			else if(time2<34)
				effectImg=new ImageIcon("eff7.png").getImage();
			else if(time2<38)
				effectImg=new ImageIcon("eff8.png").getImage();
			else if(time2<42)
				effectImg=new ImageIcon("eff9.png").getImage();
			else if(time2<46)
				effectImg=new ImageIcon("eff10.png").getImage();
			else if(time2<50)
				effectImg=new ImageIcon("eff11.png").getImage();
			else if(time2<54)
				effectImg=new ImageIcon("eff12.png").getImage();
			else if(time2<58)
				effectImg=new ImageIcon("eff13.png").getImage();
			else if(time2<62)
				effectImg=new ImageIcon("eff14.png").getImage();
			else if(time2<66)
				effectImg=new ImageIcon("eff15.png").getImage();
			else if(time2<70)
				effectImg=new ImageIcon("eff16.png").getImage();
			else if(time2>80) {
				
				effectImg=new ImageIcon("000.png").getImage();
				timer2.stop();
				
					if(effectCard.getName().contains("+1 summon")) {
						summonsLeft++;
						summons.setText("Summons Left: "+summonsLeft);
					}
					else if(effectCard.getName().contains("+1 draw")) {
						drawsLeft++;
						draws.setText("Draws Left: "+drawsLeft);
					}
				
				time2=0;
				effects.clear();
			}
			
			repaint();
			grabFocus();
		}
	}
	class AttackAnimation implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			time++;
			if(directatk)
			{
				if(time==5)
				{
					if(player1.getOnOffense())
					{
						for(Card cd:field1) {
							if(cd.getName().contains("On Direct")) {
								damage(1,2);
								effectActivation(cd,1);
							}
						}
					}
					else if(player2.getOnOffense())
					{
						for(Card cd:field2) {
							if(cd.getName().contains("On Direct")) {
								damage(1,1);
								effectActivation(cd,2);
							}
						}
					}
				}
				repaint();
				grabFocus();
				
				if(time>30)
				{
					if(player1.getOnOffense())
						bloodImg=new ImageIcon("Blood0.png").getImage();
					else
						bloodImg=new ImageIcon("Blood3.png").getImage();
				}
				if(time>34)
				{
					if(player1.getOnOffense())
						bloodImg=new ImageIcon("Blood1.png").getImage();
					else
						bloodImg=new ImageIcon("Blood4.png").getImage();
				}
				if(time>38)
				{
					if(player1.getOnOffense())
						bloodImg=new ImageIcon("Blood2.png").getImage();
					else
						bloodImg=new ImageIcon("Blood5.png").getImage();				}
				if(time>45)
				{
					
					time=0;
					bloodImg=new ImageIcon("000.png").getImage();
					if(player1.getOnOffense())
					{
						damage(dmgDealt,2);
					}
					else
					{
						damage(dmgDealt,1);
					}
					p1hp.setText(" Player 1's health: "+hp1);
					p2hp.setText(" Player 2's health: "+hp2);
					timer.stop();
				}
			}
			else
			{
				if(13<time&&time<17)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("001.png").getImage();
					else
						swordImg=new ImageIcon("02.png").getImage();
					
				}
				else if(17<=time&&time<21)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("002.png").getImage();
					else
						swordImg=new ImageIcon("03.png").getImage();				}
				else if(21<=time&&time<25)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("003.png").getImage();
					else
						swordImg=new ImageIcon("04.png").getImage();				}
				else if(25<=time&&time<29)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("004.png").getImage();
					else
						swordImg=new ImageIcon("05.png").getImage();				}
				else if(29<=time&&time<33)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("005.png").getImage();
					else
						swordImg=new ImageIcon("06.png").getImage();				}
				else if(33<=time&&time<37)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("006.png").getImage();
					else
						swordImg=new ImageIcon("07.png").getImage();				}
				else if(37<=time&&time<41)
				{
					if(player1.getOnOffense())
						swordImg=new ImageIcon("007.png").getImage();
					else
						swordImg=new ImageIcon("08.png").getImage();				}
				else if(time>50) {
					
					swordImg=new ImageIcon("000.png").getImage();
					timer.stop();
					time=0;
					if(player1.getOnOffense())
					{
						for(int i=0;i<field2.size();i++)
						{
							if(i<field2.size())
							{
								if(field2.get(i).getPosition()==currCard.getPosition()) {
									if(field2.get(i).getName().contains("On Destruction"))
									{
										if(field2.get(i).getName().contains("Heal"))
										{
											heal(2,field2.get(i).getOwner());
											effectActivation(field2.get(i),2);
										
										}
										if(field2.get(i).getName().contains("Dmg"))
										{
											damage(2,1);
											effectActivation(field2.get(i),2);
										}
										if(field2.get(i).getName().contains("Steal"))
										{
											steal(1,field2.get(i).getOwner());
											effectActivation(field2.get(i),2);
										}
									}
									list2.getDeck().add(field2.get(i));
									for(Card cd:field2)
									{
										if(cd.getName().contains("Card Destroyed")) {
											damage(1,1);
											effectActivation(cd,2);
										}
									}
									field2.remove(i);
								}
							}
						}
						
					}
					if(player2.getOnOffense())
					{
						for(int i=0;i<field1.size();i++)
						{
							if(i<field1.size())
							{
								if(field1.get(i).getPosition()==currCard.getPosition()) {
									if(field1.get(i).getName().contains("On Destruction"))
									{
										if(field1.get(i).getName().contains("Heal"))
										{
											heal(2,field1.get(i).getOwner());
											effectActivation(field1.get(i),1);
										
										}
										if(field1.get(i).getName().contains("Dmg"))
										{
											damage(2,2);
											effectActivation(field1.get(i),1);
										}
										if(field1.get(i).getName().contains("Steal"))
										{
											steal(1,field1.get(i).getOwner());
											effectActivation(field1.get(i),1);
										}
									}
									list1.getDeck().add(field1.get(i));
									for(Card cd:field1)
									{
										if(cd.getName().contains("Card Destroyed")) {
											damage(1,2);
											effectActivation(cd,1);
										}
									}
									field1.remove(i);
								}
							}
						}
					}
				}
			}
			repaint();
			grabFocus();
		}
	}
	public void damage(int dmg, int dmgplayer)
	{
		if(dmgplayer==1)
		{
			if(hp1<=dmg) {
				hp1=0;
				winner=2;
				reset();
				(CardFighters.getMainGame()).gameOver(winner);
				
			}
			else
				hp1-=dmg;
		}
		else
		{
			if(hp2<=dmg) {
				hp2=0;
				winner=1;
				reset();
				(CardFighters.getMainGame()).gameOver(winner);
				
			}
			else
				hp2-=dmg;
		}
		
	}
	public void reset()
	{
		time=0;
		time2=0;
		timer.stop();
		timer2.stop();
		hp1=10;
		hp2=10;
		showhelp=false;
		effectOf=0;
		shown=null;
		nextCard=null;
		player1=new Player(1);
		player2=new Player(2);
		winner=0;
		hp1=10;
		hp2=10;
		attacks=0;
		atknum=0;
		dealCards=0;
		maxcards=6;
		drawsLeft=0;
		round=1;
		offense=1;
		player=1;
		currentPlayer=player1;
		phase="offense";
		summonsLeft=0;
		maxfield=6;
		summonCards=0;
		x=260;
		turn=0;
		time=1;
		time2=0;
		directatk=false;
		clear();
		CardFighters.getMainGame().getDicePanel().setRolls("");
		CardFighters.getMainGame().getDicePanel().setRollsLeft(1);
		cardname.setText("");
		carddesc1.setText("");
		effects.clear();
		currCard=null;
		effectCard=null;
	}
	public void effectActivation(Card cd,int ownedBy)
	{
		effectCard=cd;
		effectOf=ownedBy;
		
			time2=0;
			timer2.start();
		effects.add(cd);
		
	}
	public void extraAttack(Card attacking)
	{
		if(player1.getOnOffense())
		{
			if(field2.isEmpty()) {
				directatk=true;
				if(player1.getOnOffense())
				{
					for(Card card:field1) {
						if(card.getName().contains("On Direct")) {
							damage(1,2);
							effectActivation(card,1);
						}
					}
				}
				else if(player2.getOnOffense())
				{
					for(Card card:field2) {
						if(card.getName().contains("On Direct")) {
							damage(1,1);
							effectActivation(card,2);
						}
					}
				}
			}
			else
				directatk=false;
			for(Card card:field1)
			{
				if(card.getPosition()==attacking.getPosition()) {
					dmgDealt=card.getDmg();
				}
			}
		}
		else if(player2.getOnOffense())
		{
			if(field1.isEmpty()) {
				directatk=true;
				if(player1.getOnOffense())
				{
					for(Card card:field1) {
						if(card.getName().contains("On Direct")) {
							damage(1,2);
							effectActivation(card,1);
						}
					}
				}
				else if(player2.getOnOffense())
				{
					for(Card card:field2) {
						if(card.getName().contains("On Direct")) {
							damage(1,1);
							effectActivation(card,2);
						}
					}
				}
			}
			else
				directatk=false;
			for(Card card:field2)
			{
				if(card.getPosition()==attacking.getPosition()) {
					dmgDealt=card.getDmg();
				}
			}
		}
		time=0;
		timer.start();
	}
	public void checkSummon(Card card)
	{
		
		
			if(card.getName().contains("+1 summon"))
			{
				effectActivation(card,player);
			}
			if(card.getName().contains("+1 draw"))	
			{
				draws.setText("Draws Left: "+drawsLeft);
				effectActivation(card,player);
			}
			if(player==1)
			{
				for(Card cd: field1)
				{
					if(cd.getName().contains("On Summon: Buff"))
					{
						buff("Buff 1", cd, 1);
					}
				}
			}
			else
			{
				for(Card cd: field2)
				{
					if(cd.getName().contains("On Summon: Buff"))
					{
						buff("Buff 1", cd, 2);
					}
				}
			}
		
		
	}
	public void heal(int heal, int healplayer)
	{
		Boolean canheal=true;
		
		if(healplayer==1)
		{
			for(Card card:field2) {
				if(card.getName().contains("Block Heal")) {
					canheal=false;
					effectActivation(card,2);
				}
			}
			if(canheal) {
				hp1+=heal;
				for(Card card:field1) {
					if(card.getName().contains("On Heal:")) {
						damage(1,2);
						effectActivation(card,1);
					}
				}
			}
		}
		else
		{
			for(Card card:field1) {
				if(card.getName().contains("Block Heal")) {
					canheal=false;
					effectActivation(card,1);
				}
			}
			if(canheal)
			{
				hp2+=heal;
				for(Card card:field2) {
					if(card.getName().contains("On Heal")) {
						damage(1,1);
						effectActivation(card,2);
					}
				}
			}
		}
	}
	public void buff(String buff,Card buffed,int ownedby)
	{
		boolean canbuff=true;
		if(buff.equals("dmg+2")) {
			if(ownedby==1) {
				for(Card card:field2) {
					if(card.getName().contains("Block Buff")) {
						canbuff=false;
						effectActivation(card,2);
					}
				}
			}
			else
			{
				for(Card card:field1) {
					if(card.getName().contains("Block Buff")) {
						canbuff=false;
						effectActivation(card,1);
					}
				}
			}
			if(canbuff) {
				if(ownedby==1)
				{
					for(Card card:field1) {
						if(card.getPosition()==buffed.getPosition()) {
							card.setDmg(buffed.getDmg()+2);
						}
					}
				}
				else
				{
					for(Card card:field2) {
						if(card.getPosition()==buffed.getPosition()) {
							card.setDmg(buffed.getDmg()+2);
						}
					}
				}
			}
		}
		else if(buff.equals("indestructable"))
			buffed.setDestructible(false);
		else if(buff.equals("Buff 1"))
		{
			if(ownedby==1) {
				for(Card card:field2) {
					if(card.getName().contains("Block Buff")) {
						canbuff=false;
						effectActivation(card,2);
					}
				}
			}
			else
			{
				for(Card card:field1) {
					if(card.getName().contains("Block Buff")) {
						canbuff=false;
						effectActivation(card,1);
					}
				}
			}
			if(canbuff) {
				if(ownedby==1)
				{
					for(Card card:field1) {
						if(card.getPosition()==buffed.getPosition()) {
							card.setDmg(buffed.getDmg()+1);
							effectActivation(card,1);
						}
					}
				}
				else
				{
					for(Card card:field2) {
						if(card.getPosition()==buffed.getPosition()) {
							card.setDmg(buffed.getDmg()+1);
							effectActivation(card,2);
						}
					}
				}
			}
		}
		if(canbuff) {
			if(ownedby==1)
			{
				for(Card card:field1) {
					if(card.getName().contains("On Buff")) {
						damage(1,2);
						effectActivation(card,1);
					}
				}
			}
			else
			{
				for(Card card:field2) {
					if(card.getName().contains("On Buff")) {
						damage(1,1);
						effectActivation(card,2);
					}
				}
			}
		}
	}
	public void steal(int steal, int giveto)
	{
		
		Boolean cansteal=true;
		if(giveto==1)
		{
			for(Card card:field2) {
				if(card.getName().contains("Block Steal")) {
					cansteal=false;
					effectActivation(card,2);
				}
			}
			if(cansteal) {
				hp1+=steal;
				hp2-=steal;
				for(Card card:field1) {
					if(card.getName().equals("On Steal")) {
						damage(1,2);
						effectActivation(card,1);
					}
				}
			}
			
		}
		else
		{
			for(Card card:field1) {
				if(card.getName().contains("Block Steal")) {
					cansteal=false;
					effectActivation(card,1);
				}
			}
			if(cansteal) {
				hp2+=steal;
				hp1-=steal;
				for(Card card:field2) {
					if(card.getName().contains("On Steal")) {
						damage(1,1);
						effectActivation(card,2);
					}
				}
			}
		}
	}
	public int getWinner() {
		return winner;
	}
	public void setWinner(int winner) {
		this.winner = winner;
	}
	public void offensePhase()
	{
		CardFighters.getMainGame().dicePanel();
		atknum=0;
		if(round==1) {
		}
		else
		{
		}
		summonsLeft=CardFighters.getMainGame().getDicePanel().getRoll();
		drawsLeft=CardFighters.getMainGame().getDicePanel().getRoll();
		
		if(round%2==0)
		{
			player2.setOnOffense(true);
			player1.setOnOffense(false);
			setPlayer();
			
			roundText.setText("offensive player plays (player "+player+")");
		}
		else//player 1 is on offense
		{
			player1.setOnOffense(true);
			player2.setOnOffense(false);
			setPlayer();
			roundText.setText("offensive player plays (player "+player+")");
		}
		endButton.setText("End offense phase");
		setLabels();
		
	}
	public void defensePhase()
	{
		summonsLeft=CardFighters.getMainGame().getDicePanel().getRoll();
		drawsLeft=CardFighters.getMainGame().getDicePanel().getRoll();
		setLabels();
		if(round==1) {
			setLabels();
		}
		setPlayer();
	}
	public void clear()
	{
		for(int i=0;i<field2.size();i++)
		{
			list2.getDeck().add(field2.remove(0));
		}
		for(int i=0;i<field1.size();i++)
		{
			list1.getDeck().add(field1.remove(0));
		}
		for(int i=0;i<hand1.size();i++)
		{
			list1.getDeck().add(hand1.remove(0));
		}
		
		for(int i=0;i<hand2.size();i++)
		{
			list2.getDeck().add(hand2.remove(0));
		}
		field1.clear();
		hand1.clear();
		field2.clear();
		hand2.clear();
	}
	public void setPlayer()
	{
		if(player1.getOnOffense()) {
			if(phase=="offense") {
				player=1;
				currentPlayer=player1;
			}
			else if(phase=="defense") {
				player=2;
				currentPlayer=player2;
			}
			else {
				player=0;
			}
		}
		if(player2.getOnOffense()) {
			if(phase=="offense") {
				player=2;
				currentPlayer=player2;
			}
			else if(phase=="defense") {
				player=1;
				currentPlayer=player1;
			}
			else {
				player=0;
			}
		}
	}
	public void setLabels()
	{
		summons.setText("Summons Left: "+summonsLeft);
		draws.setText("Draws Left: "+drawsLeft);
			summons.setForeground(Color.white);
			draws.setForeground(Color.white);
			p1hp.setForeground(Color.white);
			p2hp.setForeground(Color.white);
			roundText.setForeground(Color.white);
			roundText2.setForeground(Color.white);
		if(phase=="offense")
			roundText.setText("offensive player plays (player "+player+")");
		else if(phase=="defense")
			roundText.setText("defensive player plays (player "+player+")");
		else
			roundText.setText("<html>Battle simulator: <br>Battles go left to right</html>");
	}
	class BoardListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String c=e.getActionCommand();
			if(c.equals("Draw"))
			{
				if(player1.onOffense) {
					if(phase=="offense") {
						if(drawsLeft!=0&&hand1.size()<maxcards)
						{
							Card temp=list1.dealCard();
							int earliestpos=hand1.size()+1;
							for(int i=hand1.size()+1;i>0;i--)
							{
								boolean empty=true;
								for(Card card:hand1) {
									if(i==card.getPosition())
										empty=false;
								}
								if(empty)
									earliestpos=i;
							}
							temp.setPosition(earliestpos);
							hand1.add(temp);
							cardImg=temp.getImage();
							dealCards++;
							for(Card card:field1) {
								if(card.getName().contains("On Draw: Buff")) {
									buff("Buff 1",card,1);
									effectActivation(card,1);
								}
							}
						}
					}
					else if(phase=="defense")
					{
						if(drawsLeft!=0&&hand2.size()<maxcards)
						{
							Card temp=list2.dealCard();
							int earliestpos=hand2.size()+1;
							for(int i=hand2.size()+1;i>0;i--)
							{
								boolean empty=true;
								for(Card card:hand2) {
									if(i==card.getPosition())
										empty=false;
								}
								if(empty)
									earliestpos=i;
							}
							temp.setPosition(earliestpos);
							hand2.add(temp);
							cardImg=temp.getImage();
							dealCards++;
							for(Card card:field2) {
								if(card.getName().contains("On Draw: Buff")) {
									buff("Buff 1",card,2);
									effectActivation(card,2);
								}
							}
						}
					}
				}
				else if(player2.onOffense) {
					if(phase=="offense") {
						if(drawsLeft!=0&&hand2.size()<maxcards)
						{
							Card temp=list2.dealCard();
							int earliestpos=hand2.size()+1;
							for(int i=hand2.size()+1;i>0;i--)
							{
								boolean empty=true;
								for(Card card:hand2) {
									if(i==card.getPosition())
										empty=false;
								}
								if(empty)
									earliestpos=i;
							}
							temp.setPosition(earliestpos);
							hand2.add(temp);
							cardImg=temp.getImage();
							dealCards++;
							for(Card card:field2) {
								if(card.getName().contains("On Draw: Buff")) {
									buff("Buff 1",card,2);
									effectActivation(card,2);
								}
							}
						}
					}
					else if(phase=="defense")
					{
						if(drawsLeft!=0&&hand1.size()<maxcards)
						{
							Card temp=list1.dealCard();
							int earliestpos=hand1.size()+1;
							for(int i=hand1.size()+1;i>0;i--)
							{
								boolean empty=true;
								for(Card card:hand1) {
									if(i==card.getPosition())
										empty=false;
								}
								if(empty)
									earliestpos=i;
							}
							temp.setPosition(earliestpos);
							hand1.add(temp);
							cardImg=temp.getImage();
							dealCards++;
							for(Card card:field1) {
								if(card.getName().contains("On Draw: Buff")) {
									buff("Buff 1",card,1);
									effectActivation(card,1);
								}
							}
						}
						
					}
				}
			}
			if(c.equals("End offense phase"))
			{
				
				endButton.setText("End defense phase");
				roundText.setText("offensive player plays (player "+player+")");
				phase="defense";
				setPlayer();
				round();
				
			}
			if(c.equals("End defense phase"))
			{
				roundText.setText("<html>Battle simulator: <br>Battles go left to right</html>");
				phase="battlesim";
				setLabels();
				setPlayer();
				if(player1.onOffense)
				{
					attacks=field1.size();
				}
				else
				{
					attacks=field2.size();
				}
				round();
			}
			if(c.equals("Next battle"))
			{
				if(!(timer.isRunning())) {
					phase="battle";
					roundText.setText("<html>Battle simulator: <br>Battles go left to right</html>");
					setLabels();
					round();
				}
			}
			if(c.equals("End turn"))
			{
				if(!(timer.isRunning())) {
					
					
					endButton.setText("End offense phase");
					phase="offense";
					setPlayer();
					setLabels();
					round++;
					clear();
					
					CardFighters.getMainGame().getDicePanel().setRollsLeft(CardFighters.getMainGame().getDicePanel().getRollsLeft()+1);
					CardFighters.getMainGame().getDicePanel().setRolls("");
					round();
				}
				
			}
			if(c.equals("Help")) {
				if(showhelp)
					showhelp=false;
				else
					showhelp=true;
			}
			repaint();
		}
	}
	public void keyPressed(KeyEvent e)
	{
		int c=e.getKeyChar();
		int index=(int)c-'1';
		int wantedPos = index+1;
		Card wantedCard = null;
		if(player==1) {
			if(c==KeyEvent.VK_1)
			{
				for(Card card : hand1)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field1.size()<6))
				{
					field1.add(wantedCard);
					wantedCard.setPosition(field1.size());
					hand1.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_2)
			{
				for(Card card : hand1)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field1.size()<6))
				{
					field1.add(wantedCard);
					wantedCard.setPosition(field1.size());
					hand1.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_3)
			{
				for(Card card : hand1)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field1.size()<6))
				{
					field1.add(wantedCard);
					wantedCard.setPosition(field1.size());
					hand1.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_4)
			{
				for(Card card : hand1)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field1.size()<6))
				{
					field1.add(wantedCard);
					wantedCard.setPosition(field1.size());
					hand1.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_5)
			{
				for(Card card : hand1)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field1.size()<6))
				{
					field1.add(wantedCard);
					wantedCard.setPosition(field1.size());
					hand1.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_6)
			{
				for(Card card : hand1)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field1.size()<6))
				{
					field1.add(wantedCard);
					wantedCard.setPosition(field1.size());
					hand1.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			
		}
		else if(player==2)
		{
			if(c==KeyEvent.VK_1)
			{
				for(Card card : hand2)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field2.size()<6))
				{
					field2.add(wantedCard);
					wantedCard.setPosition(field2.size());
					hand2.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_2)
			{
				for(Card card : hand2)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field2.size()<6))
				{
					field2.add(wantedCard);
					wantedCard.setPosition(field2.size());
					hand2.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_3)
			{
				for(Card card : hand2)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field2.size()<6))
				{
					field2.add(wantedCard);
					wantedCard.setPosition(field2.size());
					hand2.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_4)
			{
				for(Card card : hand2)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field2.size()<6))
				{
					field2.add(wantedCard);
					wantedCard.setPosition(field2.size());
					hand2.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_5)
			{
				for(Card card : hand2)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field2.size()<6))
				{
					field2.add(wantedCard);
					wantedCard.setPosition(field2.size());
					hand2.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			else if(c==KeyEvent.VK_6)
			{
				for(Card card : hand2)
				{
					if(card.position == wantedPos)
						wantedCard = card;
				}
				if(wantedCard != null&&(summonsLeft>0&&field2.size()<6))
				{
					field2.add(wantedCard);
					wantedCard.setPosition(field2.size());
					hand2.remove(wantedCard);
					checkSummon(wantedCard);
					summonCards++;
				}
			}
			
		}
		
		repaint();
		
	}
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
