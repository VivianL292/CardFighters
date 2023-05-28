import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

/**
 * class that stores all the data for both players
 */
public class Player {
	CardDeck list,fulllist;
	Deck deck;
	int hp,summonsLeft,drawsLeft,playernum;
	Image deckIcon;
	ArrayList<Card>hand=new ArrayList<>();
	ArrayList<Card>field=new ArrayList<>();
	Boolean onOffense;
	/**
	 * constructor for each player
	 */
	public Player(int playernum)
	{
		this.playernum=playernum;
		list=new CardDeck();
		deck=new Deck(list);
		hp=1000;
		if(playernum==1)
			deckIcon=new ImageIcon("CardBack.png").getImage();
		else
			deckIcon=new ImageIcon("CardBack2.png").getImage();
		onOffense=true;
		
	}
	/**
	 * getter for offense boolean
	 */
	public Boolean getOnOffense() {
		return onOffense;
	}
	/**
	 * setter for offense boolean
	 */
	public void setOnOffense(Boolean onOffense) {
		this.onOffense = onOffense;
	}
	/**
	 * getter for deck
	 */
	public Deck getDeck() {
		return deck;
	}
	/**
	 * setter for deck
	 */
	public void setDeck(Deck deck) {
		this.deck = deck;
	}
	/**
	 * getter for list
	 */
	public CardDeck getList() {
		return list;
	}
	/**
	 * setter for list
	 */
	public void setList(CardDeck list) {
		this.list = list;
		fulllist=list;
	}
	/**
	 * getter for full list
	 */
	public CardDeck getFullList() {
		return fulllist;
	}
	/**
	 * setter for full list
	 */
	public void setFullList(CardDeck fulllist) {
		this.fulllist = fulllist;
	}
	/**
	 * getter for hp
	 */
	public int getHp() {
		return hp;
	}
	/**
	 * setter for hp
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}
	
}
