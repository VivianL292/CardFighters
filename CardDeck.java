import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;
class CardDeck{
	ArrayList<Card> deck;
	Image cardImage;
	public CardDeck()
	{
		cardImage=new ImageIcon("CardBack.png").getImage();
		//List<String>suits=Card.getSuits();
		List<String>names=Card.getNames();
		deck=new ArrayList<>();
		
		for(String name:names)
			deck.add(new Card(name));
		
	}
	public CardDeck(ArrayList<Card> edited)
	{
		cardImage=new ImageIcon("CardBack.png").getImage();
		//List<String>suits=Card.getSuits();
		deck=new ArrayList<>();
		
		for(Card card:edited)
			deck.add(new Card(card.getName()));
		
	}
	public void setDeck(ArrayList<Card>deck)
	{
		this.deck=deck;
	}
	public ArrayList<Card> getDeck()
	{
		return deck;
	}
	public Image getImage()
	{
		return cardImage;
	}
	public void setImage(Image cardImage) {
        this.cardImage = cardImage;
    }

	public Card dealCard()
	{
		return deck.remove(0);
	}

	public void shuffle()
    {
        Collections.shuffle(deck);
    }
}
