import java.awt.Image;

/**
*creates the graphic for the onscreen deck
*that can be interacted with by the user
*and can be clicked to draw a card
*/

// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
class Deck{
	
	CardDeck deck;
	Image cardImg;
	Image deckImg;
	/**
	* constructor for the deck
	*/
	public Deck(CardDeck deck)
	{
		this.deck=deck;
		deck.shuffle();
		deckImg=deck.getImage();
	}
	/**
	* getter for the deck's image
	*/// AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
	public Image getDeckImage()
	{
		return deckImg;
	}
}
