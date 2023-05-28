import java.awt.Image;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

/**
*creates cards
*/
public class Card{
	String suit, name, effect;
	Boolean destructible;
	Image img;
	int position,dmg,owner;
	int attacked;


	public Card(String name)
	{
		suit="";
		owner=0;
		attacked=0;
		dmg=1;
		destructible=true;
		setName(name);
		setSuit(suit);
		setEffect();
		img=new ImageIcon(""+(name.replace(' ', '_')).replace(':', '_')+".png").getImage();
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public int getAttacked() {
		return attacked;
	}

	public void setAttacked(int attacked) {
		this.attacked = attacked;
	}

	public int getDmg() {
		return dmg;
	}

	public void setDmg(int dmg) {
		this.dmg = dmg;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect() {
		effect="";

		if(toString().contains("On Destruction: Heal")) {
			effect+="When destroyed by battle: heal \n2 hp";
			name="On Destruction: Heal";
		}
		else if(toString().contains("On Heal: Dmg")) {
			effect+="When you heal: the opponent \nloses hp";
			name="On Heal: Dmg";
		}
		else if(toString().contains("Heal 2")) {
			effect+="When attacking: heal 2 hp";
			name="Heal 2";
		}
		if(toString().contains("On Destruction: Dmg")) {
			effect+="When destroyed by battle: deal \n2 dmg";
			name="On Destruction: Dmg";
		}
		else if(toString().contains("On Direct Atk: Dmg")) {
			effect+="When you attack directly: the \nopponent loses 1 hp";
			name="On Direct Atk: Dmg";
		}
		else if(toString().contains("Direct Attack")) {
			effect+="When attacking: this card \nattacks directly even if blocked \nby an opponent's card";
			name="Direct Attack";
		}
		if(toString().contains("+1 summon")) {
			effect+="When summoned: Gain 1 extra \nsummon (can only summon if you \nhave cards in hand)";
			name="On Summon: +1 summon";
		}
		else if(toString().contains("On Buff: Dmg")) {
			effect+="When a card you own gets \nbuffed: the opponent loses 1 hp";
			name="On Buff: Dmg";
		}
		else if(toString().contains("Buff 2")) {
			effect+="When attacking: Your next card \nto the right does 2 more dmg \nwhen attacking directly";
			name="Buff 2";
		}
		if(toString().contains("On Destruction: Steal")) {
			effect+="When destroyed by battle: \nsteal 1 hp from the opponent";
			name="On Destruction: Steal";
		}
		else if(toString().contains("On Steal: Dmg")) {
			effect+="When you steal hp from the \nopponent: the opponent loses \n1 hp";
			name="On Steal: Dmg";
		}
		else if(toString().contains("Steal 1")) {
			effect+="When attacking: steal 1 hp \nfrom the opponent";
			name="Steal 1";
		}
		else if(toString().contains("+1 draw"))
			effect+="When summoned: Gain 1 extra draw";
		else if(toString().contains("Card Destroyed: Dmg"))
			effect+="When any card you own gets \ndestroyed: the opponent loses \n1 hp";
		else if(toString().contains("Block Heal"))
			effect+="The opponent can't heal while \nthis card is on the field";
		else if(toString().contains("Block Buff"))
			effect+="The opponent can't buff cards \nwhile this card is on the field";		
		else if(toString().contains("Block Steal"))
			effect+="The opponent can't steal hp while \nthis card is on the field";
		else if(toString().contains("On Draw: Buff"))
			effect+="When you draw a card: this card \nbuffs itself, doing 1 more \ndmg when attacking directly\n(must be on field to buff itself)";
		else if(toString().contains("On Summon: Buff"))
			effect+="When you summon a card: this card \nbuffs itself, doing 1 more \ndmg when attacking directly\n(must be on field to buff itself)";
		else if(toString().contains("Dmg 2"))
			effect+="When attacking: Deal 2 dmg";
	}
	//getter for destructible boolean
	public Boolean getDestructible() {
		return destructible;
	}
	//setter for destructible boolean
	public void setDestructible(Boolean destructible) {
		this.destructible = destructible;
	}
	/**
	*getter for the card's position
	*/
	public int getPosition() {
		return position;
	}
	
	/**
	*setter for the card's position
	*/
	public void setPosition(int position) {
		this.position = position;
	}
	
	/**
	*setter for the card's name
	*/
	public void setName(String name)
	{
		List<String>names=getNames();
		if(names.contains(name))
			this.name=name;
		name=name.toLowerCase();
	}
	
	/**
	*getter for the card's name
	*/
	public String getName()
	{
		return name;
	}
	

	public static List<String> getNames()
	{
		List<String>royals=Arrays.asList("On Destruction: Heal","Heal 2","On Heal: Dmg","On Destruction: Dmg","On Direct Atk: Dmg","Direct Attack","+1 summon","On Buff: Dmg","Buff 2","On Destruction: Steal","On Steal: Dmg","Steal 1","+1 draw","Card Destroyed: Dmg","Block Heal","Block Buff","Block Steal","On Draw: Buff","On Summon: Buff","Dmg 2");
		return royals;
	}
	
	/**
	*returns a list w/ all of the suits for cards
	*suits: jack, queen, king
	*/
	public static List<String> getSuits()
	{
		List<String>suits=Arrays.asList("hearts","diamonds","spades","clubs");
		return suits;
	}
	/**
	*setter for the card's suit
	*/
	public void setSuit(String suit)
	{
		List<String>suits=getSuits();
		if(suits.contains(suit))
			this.suit=suit;
		suit=suit.toLowerCase();
	}
	/**
	*getter for the card's suit
	*/
	public String getSuit()
	{
		return suit;
	}
	/**
	*overrides toString method for object class
	*/
	public String toString()
	{
		return name;
	}
	/**
	* setter for image
	*/
    public void setImage(Image img) {
        this.img = img;
    }

	public Image getImage() {
        return img;
	}
}
