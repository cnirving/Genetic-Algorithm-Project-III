import javax.swing.*;

public class Main {

	public static final int NUM_GENS = 10000;
	public static final int POP_SIZE = 20; // Must be divisible by 2
	public static final int CHROM_LENGTH = 24;
	public static final String CODING_TYPE = "pendulum"; // tsp or maxima or trajectory or pendulum or binary
	public static final String[] LIMITS = {"-4", "4"}; //both must have same number of digits
	public static final String SELECT_TYPE = "SRWR"; // roulette or SRWR or none
	public static double MUTATION_RATE = Math.pow(POP_SIZE, 0.15) * (CHROM_LENGTH/20) * 1/300;
	public static final double CROSS_RATE = 1.0;
	public static final String CROSS_TYPE = "2point"; // tsp or 2point or average
	public static boolean ELITISM = false;
	public static boolean FITNESS_SCALING = false;
	public static boolean TOGGLE_PARAMETERS = true;
	public static final String GRAPHICS = "diagram"; // maxFit or meanFit or diagram/diagramall
	
	
	//public static String[] tspCities = Algorithms.generateTspCities();
	//public static String[] tspCities = {"46,0", "23,10", "15,28", "2,50", "9,65", "18,99", "51,88", "83,93", "89,70", "100,50", "90,32", "86,5"};
	//public static String[] tspCities = {"46,0", "23,10", "15,28", "2,50", "9,65", "18,99", "51,88", "83,93", "89,70", "100,50", "90,32", "86,5", "52,95", "27,86", "54,79", "27,83", "60,63", "59,66", "53,53", "1,12", "12,15", "21,54", "71,50", "61,38", "67,17", "14,39", "66,24", "65,28", "95,23", "72,30"};
	public static double v0 = 85; //initial velocity
	public static double rho = 1; //pendulum rod length
	public static String[] tspCities = Algorithms.generateTspCities();
	public static String actionMutate = "a lot"; //set mutation type for action problem
	public static int i; //generation counter
		
	public static void main(String[] args) {

		Population population = new Population();
		
		// Define initial population
		//String[] initialChroms = {"0319.6094824968805", "0111.2852243372282", "0405.18053812242374", "0976.621379009869", "0118.19455938014234", "0375.5607925119202", "0478.63243517581964", "0475.0229275925658", "0467.72417504779975", "0756.0171545858564", "0710.8017808348433", "0739.3481832534678", "0490.66351322764655", "0452.83191818303794", "0320.5324972227457", "0410.7738764209176"};
		//String[] initialChroms = {"01101", "00011", "11010", "00100", "00011", "10010"};
		//population.setMembers(initialChroms);

		// Generate random initial population
		population.setMembers();
		
		System.out.println("Initial Population: " + population.toString());
		double initFit = population.getFittest().getFitness();
		
		/*
		// define schema to be monitored
		String schema1 = "***1******1********0*******************0";
		String schema2 = "1***11**********************************";
		int schema1Count = 0;
		int schema2Count = 0;*/
		
		
		// Set up JFrame(s) for graphics
		JFrame frameMax = new JFrame();
		JFrame frameMean = new JFrame();
		JFrame frameDiagram = new JFrame();
		int[] frameFitSize = {1100, 800};
		int[] frameDiagramSize = {1100, 800};
		if (GRAPHICS.contains("maxFit")) {

			frameMax.setSize(frameFitSize[0], frameFitSize[1]);
			frameMax.setTitle("Population Fittest");
			frameMax.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frameMax.setVisible(true);
		}
			
		if (GRAPHICS.contains("meanFit")) {
			
			frameMean.setSize(frameFitSize[0], frameFitSize[1]);
			frameMean.setTitle("Population Mean Fitness");
			frameMean.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frameMean.setVisible(true);
		}
			
		if (GRAPHICS.contains("diagram")) {

			frameDiagram.setSize(frameDiagramSize[0], frameDiagramSize[1]);
			frameDiagram.setTitle("Diagram");
			frameDiagram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frameDiagram.setVisible(true);
		}

		DrawFitness drawFitness = new DrawFitness(NUM_GENS, frameFitSize);
		Drawing drawing;
		drawing = new Drawing(population, frameDiagramSize);
		frameDiagram.add(drawing);
		frameDiagram.setVisible(true);
		JOptionPane.showMessageDialog(null, "TAKE A SCREENSHOT", "State: " + (int)(((double)i/NUM_GENS)*100) + "%", JOptionPane.INFORMATION_MESSAGE);		
		
		for (i = 1; i <= NUM_GENS; i++) {

			if (TOGGLE_PARAMETERS) {
				// Dynamically alter mutation rates
				if (i == (int)(NUM_GENS * 0.15) | i == (int)(NUM_GENS * 0.25) | i == (int)(NUM_GENS * 0.4) | i == (int)(NUM_GENS * 0.66) | i == (int)(NUM_GENS * 0.75) | i == (int)(NUM_GENS * 0.83)) {
	
					if (i == (int)(NUM_GENS * 0.15)) {
						
						ELITISM = true;
						MUTATION_RATE *= 2;
					}
					if (i == (int)(NUM_GENS * 0.25)) {
						
						ELITISM = false;
						MUTATION_RATE *= 0.6;
						actionMutate = "a little";
					}
					if (i == (int)(NUM_GENS * 0.4)) {
						
						MUTATION_RATE *= 1.1;
					}
					if (i == (int)(NUM_GENS * 0.66)) {
						
						FITNESS_SCALING = true;
						ELITISM = true;
						MUTATION_RATE *= 1;
					}
					if (i == (int)(NUM_GENS * 0.75)) {
	
						ELITISM = false;
						MUTATION_RATE *= 0.4;
						actionMutate = "a lot";
					}
					if (i == (int)(NUM_GENS * 0.83)) {
	
						ELITISM = true;
						MUTATION_RATE *= 4;
						actionMutate = "a little";
					}
					JOptionPane.showMessageDialog(null, "Elitism: " + ELITISM + ", Mutation rate: " + (MUTATION_RATE*100.0) + "%", "State: " + (((double)i/NUM_GENS)*100) + "%", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			population = Processes.evolveStep(population);

			//System.out.println("population: {" + i + "} "  + population.toString());
			System.out.println("popFittest: {" + i + "} "  + population.getFittest().toString());
			
			if (GRAPHICS.contains("diagram")) {
				
				drawing = new Drawing(population, frameDiagramSize);
				frameDiagram.add(drawing);
				frameDiagram.setVisible(true);
			}
			else if (GRAPHICS.contains("maxFit")) {
				
				drawFitness.updateDrawing(i, population.getFittest().getFitness());
				drawFitness.repaint();
				frameMax.add(drawFitness);
				frameMax.setVisible(true);
			}
			else if (GRAPHICS.contains("meanFit")) {
				
				drawFitness.updateDrawing(i, population.getMeanFitness());
				drawFitness.repaint();
				frameMean.add(drawFitness);
				frameMean.setVisible(true);
			}
			
			
			/*
			// monitor schema prevalence
			schema1Count = 0;
			schema2Count = 0;
			for (int j = 0; j < population.getMembers().length; j++) {
				
				Individual[] members = population.getMembers();
				
				if (Algorithms.checkSchema(schema1, members[j].getChromosome())) {
					
					schema1Count++;
				}
				if (Algorithms.checkSchema(schema2, members[j].getChromosome())) {
					
					schema2Count++;
				}
			}
			System.out.println("SCHEMA1: " + schema1Count + ", SCHEMA2: " + schema2Count);*/
			
		}

		System.out.println();
		System.out.println("Final population: " + population.toString());
		System.out.println("Fittest: " + population.getFittest().toString());
		System.out.println();
		if(CODING_TYPE.contains("tsp")) {
			System.out.println("Generation {0} fittest distance: " + 100000000/initFit);
			System.out.println("Generation {" + NUM_GENS + "} fittest distance: " + 100000000/population.getFittest().getFitness());
		}
	}

}
