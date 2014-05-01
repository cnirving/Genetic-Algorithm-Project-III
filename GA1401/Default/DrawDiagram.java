package Default;

import java.awt.*;
import javax.swing.*;
import java.util.Arrays;

public class DrawDiagram extends JPanel{

	double x1, x2, y1, y2;
	int commaIndex1, commaIndex2;
	String[] fitChrom;
	Population population;
	Individual[] members;
	Individual fittest;
	int[] frameSize;
	
	DrawDiagram(Population population, int[] frameSize) {
		
		fitChrom = population.getFittest().getChromosome();
		this.population = population;
		members = population.getMembers();
		this.fittest = fittest;
		this.frameSize = frameSize.clone();
	}

	public void paintComponent(Graphics g) {
		
			Graphics2D grc = (Graphics2D)g;
			super.paintComponent(grc);
			grc.setStroke(new BasicStroke(3));

			switch(Main.CODING_TYPE) {
			
				case "tsp":
					
					double scalingX = ((double)frameSize[0] - 100) / Double.parseDouble(Main.LIMITS[1]);
					double scalingY = ((double)frameSize[1] - 100) / Double.parseDouble(Main.LIMITS[1]);
					
					for (int i = 0; i < fitChrom.length; i++) {
	
						if (i == 0) {
	
							commaIndex1 = fitChrom[0].indexOf(',');
							commaIndex2 = fitChrom[i + 1].indexOf(',');

							x1 = Double.parseDouble(fitChrom[0].substring(0, commaIndex1));
							x1 *= scalingX;
							y1 = frameSize[1] - 50;
							x1 += 50;
							y1 -= (Double.parseDouble(fitChrom[0].substring(commaIndex1 + 1)) * scalingY);
							
							x2 = Double.parseDouble(fitChrom[i + 1].substring(0, commaIndex2));
							x2 *= scalingX;
							y2 = frameSize[1] - 50;
							x2 += 50;
							y2 -= (Double.parseDouble(fitChrom[i + 1].substring(commaIndex2 + 1)) * scalingY);
						}
						else if (i == fitChrom.length - 1) {
							
							commaIndex1 = fitChrom[i].indexOf(',');
							commaIndex2 = fitChrom[0].indexOf(',');

							x1 = Double.parseDouble(fitChrom[i].substring(0, commaIndex1));
							x1 *= scalingX;
							y1 = frameSize[1] - 50;
							x1 += 50;
							y1 -= (Double.parseDouble(fitChrom[i].substring(commaIndex1 + 1)) * scalingY);
			
							x2 = Double.parseDouble(fitChrom[0].substring(0, commaIndex2));
							x2 *= scalingX;
							y2 = frameSize[1] - 50;
							x2 += 50;
							y2 -= (Double.parseDouble(fitChrom[0].substring(commaIndex2 + 1)) * scalingY);
						}
						else {
							
							commaIndex1 = fitChrom[i].indexOf(',');
							commaIndex2 = fitChrom[i + 1].indexOf(',');

							x1 = Double.parseDouble(fitChrom[i].substring(0, commaIndex1));
							x1 *= scalingX;
							y1 = frameSize[1] - 50;
							x1 += 50;
							y1 -= (Double.parseDouble(fitChrom[i].substring(commaIndex1 + 1)) * scalingY);
			
							x2 = Double.parseDouble(fitChrom[i + 1].substring(0, commaIndex2));
							x2 *= scalingX;
							y2 = frameSize[1] - 50;
							x2 += 50;
							y2 -= (Double.parseDouble(fitChrom[i + 1].substring(commaIndex2 + 1)) * scalingY);
						}
						
						grc.setColor(Color.RED);
						grc.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
						grc.setColor(Color.BLACK);
						grc.fillOval((int)x1 - 5, (int)y1 - 5, 10, 10);
					}
					break;
						
				case "maxima":
					
					grc.setStroke(new BasicStroke(1));
					grc.setColor(Color.GRAY);
					grc.drawLine(50, frameSize[1] - 50, 50, 50);
					grc.drawString("0", 40, frameSize[1] - 35);
					grc.drawLine(250, frameSize[1] - 50, 250, 50);
					grc.drawString("200", 240, frameSize[1] - 35);
					grc.drawLine(450, frameSize[1] - 50, 450, 50);
					grc.drawString("400", 440, frameSize[1] - 35);
					grc.drawLine(650, frameSize[1] - 50, 650, 50);
					grc.drawString("600", 640, frameSize[1] - 35);
					grc.drawLine(850, frameSize[1] - 50, 850, 50);
					grc.drawString("800", 840, frameSize[1] - 35);
					grc.drawLine(1050, frameSize[1] - 50, 1050, 50);
					grc.drawString("1000", 1040, frameSize[1] - 35);
					grc.drawLine(50, frameSize[1] - 300, frameSize[0] - 50, frameSize[1] - 300);
					grc.drawString("0", 40, frameSize[1] - 295);
					grc.setColor(Color.BLACK);
					
					double y;
					for (int i = 0; i < 1000; i++) {
						
						y = Math.pow((1.3*i)-800, 2);
						y *= Math.pow(1.3*i, 0.05);
						y -= (Math.pow(1.3*i, 2) * Math.cos((1.3*i)/50));
						y /= 3500;
						y += 300;
						
						grc.fillOval(i + 49, frameSize[1] - ((int)y - 1), 5, 5);
					}
					
					if (Main.GRAPHICS.contains("all")) {

						for (int i = 0; i < members.length; i++) {
							
							double x = Double.parseDouble(Algorithms.arrToStr(members[i].getChromosome()));
							double yCoord;
							yCoord = Math.pow((1.3*x)-800, 2);
							yCoord *= Math.pow(1.3*x, 0.05);
							yCoord -= (Math.pow(1.3*x, 2) * Math.cos((1.3*x)/50));
							yCoord /= 3500;
							yCoord += 299;
							
							if (members[i].Equals(population.getFittest())) {
								
								grc.setStroke(new BasicStroke(3));
								grc.setColor(Color.GREEN);
							}
							else {
								
								grc.setStroke(new BasicStroke(2));
								grc.setColor(Color.RED);
							}
							grc.drawLine((int)x + 52, frameSize[1] - 50, (int)x + 52, frameSize[1] - (int)yCoord + 1);
						}
						
					}
					else {
						
						double x = Double.parseDouble(Algorithms.arrToStr(fitChrom));
						double yCoord;
						yCoord = Math.pow((1.3*x)-800, 2);
						yCoord *= Math.pow(1.3*x, 0.05);
						yCoord -= (Math.pow(1.3*x, 2) * Math.cos((1.3*x)/50));
						yCoord /= 3500;
						yCoord += 299;
						
						grc.setColor(Color.RED);
						grc.setStroke(new BasicStroke(2));
						grc.drawLine((int)x + 52, frameSize[1] - 50, (int)x + 52, frameSize[1] - (int)yCoord + 1);
					}
					
					break;
				
				case "trajectory":
										
					int width = frameSize[0] - 100;
					int height = frameSize[1] - 100;
					double grav = 9.81;
					double r;
					double v0 = Main.v0;
					double t;
					double totalTime = (2*v0) / grav;
					double numPoints = fitChrom.length;
					
					grc.setColor(Color.LIGHT_GRAY);
					grc.setStroke(new BasicStroke(2));
					grc.drawLine(50, frameSize[1] - 96, frameSize[0] - 50, frameSize[1] - 96);
					grc.fillOval(50, height, 4, 4);
					for (int i = 0; i < numPoints; i++) {

						// Scale time to run until arc returns to zero level
						t = ((double)i/(numPoints-1)) * totalTime;
						// Scale x-coordinate to fit frame
						x1 = ((double)i/numPoints) * (width);
						
						// Plot the actual trajectory
						r = v0 * t;
						r -= ((9.8 / 2) * Math.pow(t, 2));
						grc.setColor(Color.BLACK);
						grc.setStroke(new BasicStroke(1));
						grc.fillOval((int)x1 + 50, height - (int)r, 4, 4);
					}

					grc.setColor(Color.RED);
					for (int i = 0; i < numPoints; i++) {
						
						x1 = ((double)i/numPoints) * (width);
						y1 = Double.parseDouble(fitChrom[i]);
						grc.fillOval((int)x1 + 50, height - (int)y1, 8, 8);
					}
					
					break;
					
				case "pendulum":
					
					int pWidth = frameSize[0] - 100;
					int pHeight = frameSize[1] - 100;
					double pGrav = 9.81;
					double pV0 = Main.v0;
					double pNumPoints = fitChrom.length;
					double vertScale = pHeight / (2*Main.rho);
					double horiScale;
					
					/*grc.setColor(Color.DARK_GRAY);
					grc.setStroke(new BasicStroke(2));
					grc.fillOval(frameSize[0]/2 - 6, frameSize[1]/2 - 6, 12, 12);

					for (int i = 0; i < pNumPoints; i++) {
						
						x1 = Main.rho * Math.sin(Double.parseDouble(fitChrom[i]));
						y1 = Main.rho * (1 - Math.cos(Double.parseDouble(fitChrom[i])));
						
						y1 *= vertScale;
						x1 *= vertScale;
						
						x1 += frameSize[0]/2 - 6;

						Color colour = new Color(255 , 250 - (int)((250/pNumPoints)*i), 250 - (int)((250/pNumPoints)*i));
						grc.setStroke(new BasicStroke(2));
						grc.setColor(colour);
						grc.fillOval((int)x1, pHeight + 50 - 6 - (int)y1, 12, 12);
						grc.setStroke(new BasicStroke(1));
						grc.setColor(Color.BLACK);
						grc.drawOval((int)x1, pHeight + 50 - 6 - (int)y1, 12, 12);
					}*/

					// Plot the actual trajectory
					//double[] actualTrajectory = {0.0, 1.42615, 2.3165, 2.7581, 2.96135, 3.04925, 3.0777, 3.06496, 3.00285, 2.85165, 2.51733, 1.81802, 0.564593, -0.95452, -2.05545, -2.63372, -2.90506, -3.02593, -3.07243, -3.07435, -3.03294, -2.92164, -2.67015, -2.13117, -1.08674, 0.417588, 1.72213, 2.46929, 2.82943, 2.99308, 3.06146, 3.0782, 3.05409, 2.97363, 2.78557, 2.3751, 1.538, 0.151581, -1.3086, -2.25367, -2.72847};
					/*double[] actualTrajectory = {0.0, 1.42615, 2.3165, 2.7581, 2.96135, 3.04925, 3.0777, 3.06496, 3.00285, 2.85165, 2.51733, 1.81802, 0.564593, -0.95452, -2.05545, -2.63372, -2.90506, -3.02593, -3.07243, -3.07435};
					horiScale = pWidth/20;
					double theta;
					for (int i = 0; i < actualTrajectory.length; i++) {
						
						theta = actualTrajectory[i];
						
						//x1 = Main.rho * Math.sin(theta);
						x1 = (double)i;
						y1 = Main.rho * (1 - Math.cos(theta));
						
						y1 *= vertScale;
						x1 *= horiScale;
						
						//x1 += frameSize[0]/2 - 3;
						x1 += 50;

						Color colour = new Color(250 - (int)((250/pNumPoints)*i) , 250 - (int)((250/pNumPoints)*i), 255);
						grc.setStroke(new BasicStroke(3));
						grc.setColor(Color.BLACK);
						grc.drawOval((int)x1, pHeight + 50 - 3 - (int)y1, 6, 6);
					}*/
					
					double[] trajectory = {0.0, 0.8221741375767059, 1.4823674590097335, 1.9866306768597, 2.3530755419418647, 2.607878606751464, 2.777432938295478, 2.8928691163603144, 2.9732678419171727, 3.023769010787537, 3.0596590050859005, 3.0871115179150483, 3.1150776795980124, 3.141822133838182, 3.1822460915765376, 3.2325850017922884, 3.290688606136165, 3.3775580583095586, 3.4989129778801478, 3.6720036459500993, 3.925778048679006, 4.285008792490699, 4.785830066655838, 5.44};
					double theta;
					horiScale = pWidth/trajectory.length;
					for (int i = 0; i < trajectory.length; i++) {
						
						theta = trajectory[i];
						
						x1 = (double)i;
						y1 = Main.rho * (1 - Math.cos(theta));
						
						y1 *= vertScale;
						x1 *= horiScale;
						
						x1 += 50;
						
						grc.setStroke(new BasicStroke(3));
						grc.setColor(Color.RED);
						grc.drawOval((int)x1, pHeight + 50 - 3 - (int)y1, 6, 6);
					}
					
					break;
						
				default:
					break;
			}
	}

}
