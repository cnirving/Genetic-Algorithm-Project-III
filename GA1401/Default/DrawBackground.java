package Default;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class DrawBackground extends JLayeredPane{

	double x1, y1;
	double[][] tspDoubles;
	int commaIndex1, commaIndex2;
	double width;
	double height;
	String[] tspCities;
	
	DrawBackground(String[] tspCities, int[] frameSize) {
		
		width = frameSize[0] - 100;
		height = frameSize[1] - 50;
		
		this.tspCities = tspCities.clone();
		tspDoubles = new double[tspCities.length][2];
		

	}

	public void paintComponent(Graphics g) {
		

		switch(Main.CODING_TYPE) {
		
			case "tsp":

				Graphics2D grc = (Graphics2D)g;
				//super.paintComponent(grc);
				grc.setStroke(new BasicStroke(3));
				
				for (int i = 0; i < tspCities.length; i++) {
	
					commaIndex1 = tspCities[i].indexOf(',');
					
					x1 = Double.parseDouble(tspCities[i].substring(0, commaIndex1));
					x1 *= 10;
					y1 = height;
					y1 -= (Double.parseDouble(tspCities[i].substring(commaIndex1 + 1)) * 7);
					
					grc.setColor(Color.BLACK);
					grc.fillOval((int)x1 + 45, (int)y1 - 5, 10, 10);		
				}
		
				break;
				
			default:
				break;
		}
		
		/*Graphics2D grc = (Graphics2D)g;
		//super.paintComponent(grc);
		grc.setStroke(new BasicStroke(3));

		grc.setColor(Color.BLACK);
		grc.fillOval((int)x1 + 45, (int)y1 - 5, 10, 10);*/
	}

}
