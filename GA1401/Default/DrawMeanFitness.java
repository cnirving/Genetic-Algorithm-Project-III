package Default;

import java.awt.*;
import javax.swing.JPanel;

public class DrawMeanFitness extends JPanel{

	double x;
	double y;
	double width;
	double height;
	double scale;
	
	DrawMeanFitness(int numGens, int[] frameSize) {
		
		width = frameSize[0] - 100;
		height = frameSize[1] - 50;
		scale = (double)numGens;
		
	}

	public void updateDrawing(int generation, double fitness) {

		x = (double)generation;
		y = fitness;

		x *= (width/scale);
		x += 50;
		
		switch(Main.CODING_TYPE) {
		
			case "maxima":
				y -= 650;
				y *= -1.0;
				y += height;
				break;
			
			case "tsp":
				y *= Math.sqrt(Main.CHROM_LENGTH);
				y /= 2;
				y *= -1;
				y += height;
				break;
				
			case "action":
				y *= 5;
				y *= -1;
				y += height;
				break;
		}
		
	}
	
	public void paint(Graphics grc) {
			
			grc.setColor(Color.PINK);		
			grc.fillRect((int)x - 1, (int)y + 1, 2, 2);
	}
}
