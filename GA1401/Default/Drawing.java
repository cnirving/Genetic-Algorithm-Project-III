package Default;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class Drawing extends JPanel{
	
	private int gen;
	private Individual[] members;
	
	public void updateDrawing(int gen, Population population) {
		
		this.gen = gen;
		members = population.getMembers().clone();
	}
	
	public void paintComponent(Graphics g) {
		
			//super.paintComponent(grc);
			Graphics2D g2 = (Graphics2D)g;
			
			g2.setColor(Color.BLACK);
			
			g2.draw3DRect(gen, 600-(int)Processes.pickFittest(members).getFitness()/10, 30, 40, true);
	}
	
}