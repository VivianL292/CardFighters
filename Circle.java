import java.awt.Image;
import java.util.*;

import javax.swing.ImageIcon;
public class Circle {
	int x,y,speed;
	Image img;
	public Circle()
	{
		x=(int)(Math.random()*1000);
		y=(int)(Math.random()*500)+100;
		speed=(int)(Math.random()*3)+1;
		img=new ImageIcon("blooddrop.png").getImage();
	}
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
}
