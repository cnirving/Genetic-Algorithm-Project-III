import java.util.Arrays;

public class Processes {

	public static double initVelocity = Math.sqrt(2) * Math.sqrt(9.81 * Main.rho);
	public static double totalTime = 1.5;
	public static double timeStep = totalTime/Main.CHROM_LENGTH;
	
	public Processes() {

	}
	
	public static String[] geneCoding(String geneCoding) {
		
		String[] chromosome = null;		

		double lowerLimit = Integer.parseInt(Main.LIMITS[0]);
		double upperLimit = Integer.parseInt(Main.LIMITS[1]);
		double range = upperLimit - lowerLimit;
		
		switch (geneCoding) {
		
		case "binary":
			
			chromosome = new String[Main.CHROM_LENGTH];
			
			for (int i = 0; i < chromosome.length; i++) {
				
				chromosome[i] = String.valueOf(Algorithms.randomInt(0, 1));
			}
						
			break;
		
		case "maxima":
			
			// generate random number in specified domain and store in an array
			double chromDub = lowerLimit + (Algorithms.randomDouble() * range);
			String chromStr = String.valueOf(chromDub);
			String chromInt = chromStr.substring(0, chromStr.indexOf('.'));
			// include leading zeroes so decimal is always in same place
			while (chromInt.length() < Main.LIMITS[0].length()) {

				chromStr = "0" + chromStr;
				chromInt = chromStr.substring(0, chromStr.indexOf('.'));
			}
			chromosome = Algorithms.strToArr(chromStr);
			
			break;
			
		case "tsp":

			chromosome = new String[Main.CHROM_LENGTH];
			
			String[] cities = Main.tspCities.clone();
		    
		    for (int i = cities.length - 1; i > 0; i--)
		    {
		      int index = Algorithms.randomInt(0, i);
		      String holder = cities[index];
		      cities[index] = cities[i];
		      cities[i] = holder;
		    };
			
		    chromosome = cities;
		    
		    break;
		    
		case "trajectory":
		
			chromosome = new String[Main.CHROM_LENGTH];
			
			chromosome[0] = String.valueOf(0.0);
			chromosome[chromosome.length - 1] = String.valueOf(0.0);
		
			for (int i = 1; i < chromosome.length - 1; i++) {
				
				chromosome[i] = String.valueOf(lowerLimit + (Algorithms.randomDouble()*range));
			}
			
			break;

		case "pendulum":
		
			chromosome = new String[Main.CHROM_LENGTH];
			
			chromosome[0] = String.valueOf(0.0);
			chromosome[1] = String.valueOf(initVelocity * timeStep);
		
			for (int i = 2; i < chromosome.length-1; i++) {
				
				chromosome[i] = String.valueOf(lowerLimit + (Algorithms.randomDouble()*range));
			}
			
			break;
			
		default:
			
			break;
		}
		
		return chromosome;
	}
	
	
	public static double computeFitness(String[] chromosome) {

		double fitness = 0.0;

		double gravity = 9.81;
		double actionSum = 0.0;
		double currentGene;
		double lastGene;
		
		switch (Main.CODING_TYPE) {
		
			case "binary":

				for (int i = 0; i < chromosome.length; i++) {
					
					fitness += Double.parseDouble(chromosome[chromosome.length - 1 - i]) * Math.pow(2, i);
				}
				
				break;
			
			case "maxima":

				// WolframAlpha input: maximize ((1.3x-800)^2*1.3*x^(0.05) - (1.3x^2*cos(1.3x/50)))/3500 , (x from 0 to 1000)
				
				double chromDub = Double.parseDouble(Algorithms.arrToStr(chromosome));
				
				/*fitness += Math.pow((1.3*chromDub)-800, 2);
				fitness *= 1.3 * Math.pow(chromDub, 0.05);
				fitness -= (1.3*Math.pow(chromDub, 2) * Math.cos((1.3*chromDub)/50));*/
				
				
				fitness += Math.pow((1.3*chromDub)-800, 2);
				fitness *= Math.pow(1.3*chromDub, 0.05);
				fitness -= (Math.pow(1.3*chromDub, 2) * Math.cos((1.3*chromDub)/50));//note changed drawing line 119: /100 there aswell
				
				fitness /= 3500;
				fitness += 240; //to make all points map to a posiive number	
				
				/*if (fitness < 0) {
					
					fitness = 0;
				}*/
				
				fitness*=2;
				
				break;
		
			case "tsp":
				
				double[] city1 = new double[2];
				double[] city2 = new double[2];
				int commaIndex1;
				int commaIndex2;
				double[] differenceVector = new double[2];
				double distance;
				double totalDistance = 0;
				
				for (int i = 0; i < chromosome.length; i++) {
					
					if (i < chromosome.length - 1) {
						
						commaIndex1 = chromosome[i].indexOf(',');
						commaIndex2 = chromosome[i + 1].indexOf(',');
						city1[0] = Double.parseDouble(chromosome[i].substring(0, commaIndex1));
						city1[1] = Double.parseDouble(chromosome[i].substring(commaIndex1 + 1));
						city2[0] = Double.parseDouble(chromosome[i + 1].substring(0, commaIndex2));
						city2[1] = Double.parseDouble(chromosome[i + 1].substring(commaIndex2 + 1));
						
						differenceVector[0] = Math.abs(city2[0] - city1[0]);
						differenceVector[1] = Math.abs(city2[1] - city1[1]); 
	
						distance = Math.sqrt(Math.pow(differenceVector[0], 2) + Math.pow(differenceVector[1], 2));
						
						totalDistance += distance;
					}
					else {
						
						commaIndex1 = chromosome[i].indexOf(',');
						commaIndex2 = chromosome[0].indexOf(',');
						city1[0] = Double.parseDouble(chromosome[i].substring(0, commaIndex1));
						city1[1] = Double.parseDouble(chromosome[i].substring(commaIndex1 + 1));
						city2[0] = Double.parseDouble(chromosome[0].substring(0, commaIndex2));
						city2[1] = Double.parseDouble(chromosome[0].substring(commaIndex2 + 1));
						
						differenceVector[0] = Math.abs(city2[0] - city1[0]);
						differenceVector[1] = Math.abs(city2[1] - city1[1]); 

						distance = Math.sqrt(Math.pow(differenceVector[0], 2) + Math.pow(differenceVector[1], 2));
						
						totalDistance += distance;
					}
				}
				
				fitness = 100000000 / totalDistance;
				break;
				
			case "trajectory":				

				initVelocity = Main.v0;
				totalTime = (2 * initVelocity) / gravity;
				timeStep = totalTime / chromosome.length;
				
				for (int i = 0; i < chromosome.length; i++) {
					
					currentGene = Double.parseDouble(chromosome[i]);
					
					if (i == 0) {
						
						actionSum += (0.5 * Math.pow(currentGene/timeStep, 2)) - (gravity * currentGene);
					}
					else {
						
						lastGene = Double.parseDouble(chromosome[i - 1]);
						actionSum += (0.5 * Math.pow((currentGene - lastGene)/timeStep, 2)) - (gravity * currentGene);
					}
				}

				fitness = 100000000 - actionSum;
				fitness /= 100000;
				break;
				
			case "pendulum":				

				double rho = Main.rho; //pendulum rod length
				
				for (int i = 0; i < chromosome.length; i++) {
					
					currentGene = Double.parseDouble(chromosome[i]);
					
					if (i == 0) {
						
						actionSum += (0.5 * Math.pow(rho, 2) * Math.pow(currentGene/timeStep, 2)) - (gravity * rho * (1 - Math.cos(currentGene)));
					}
					else {
						
						lastGene = Double.parseDouble(chromosome[i - 1]);
						actionSum += (0.5 * Math.pow(rho, 2) * Math.pow((currentGene - lastGene)/timeStep, 2)) - (gravity * rho * (1 - Math.cos(currentGene)));
					}
				}
				
				fitness = 1000000000 - actionSum;
				fitness /= 10000000;
				break;
		
			default:
				
				break;
		}
		
		if (Main.FITNESS_SCALING) {

			//fitness /= 10;
			fitness *= Math.pow(fitness, 11);
			fitness /= 1000000000;
		}
		
		return fitness;
	}

	
	public static Individual pickFittest(Individual[] members) {
		
		Individual fittest;
		double memberFitness = 0;
		int fittestIndex = -1;
		double fittestScore = 0;
		
		for (int i = 0; i < members.length; i++) {

			memberFitness = members[i].getFitness();
			
			if (memberFitness > fittestScore) {
				
				fittestScore = memberFitness;
				fittestIndex = i;
			}
		}
		
		fittest = members[fittestIndex];

		return fittest;
	}
	
	
	public static double meanFitness(Individual[] population) {
		
		double meanFitness;
		double fitnessSum = 0.0;
		
		for (int i = 0; i < population.length; i++) {
			
			fitnessSum += population[i].getFitness();
		}
		
		meanFitness = fitnessSum / population.length;

		return meanFitness;
	}
	

	
	public static Population evolveStep(Population population) {
		
		Individual[] oldGenerationMembers = population.getMembers();
		Individual[] newGenerationMembers = new Individual[oldGenerationMembers.length];
		Population newGeneration;
		int numToFind = oldGenerationMembers.length;
		int newGenCount = 0;
		
		if (Main.ELITISM) {

			newGenerationMembers[0] = population.getFittest();
			Population tempPop = Algorithms.removeIndividual(population, newGenerationMembers[0]);
			newGenerationMembers[1] = tempPop.getFittest();
			newGenCount += 2;
			numToFind -= newGenCount;
		}

		Individual[] matingPool = createMatingPool(numToFind, oldGenerationMembers);
		
		Individual[][] matingPairs = null;
		Individual[] offspring = new Individual[2];

		matingPairs = chooseMatingPairs(matingPool);

		for (int i = 0; i < numToFind / 2; i++) {
			
			offspring = crossover(matingPairs[i]);
			offspring = mutate(offspring);

			newGenerationMembers[newGenCount] = offspring[0];
			newGenerationMembers[newGenCount + 1] = offspring[1];
			newGenCount += 2;
		}
		
		newGeneration = new Population(newGenerationMembers);
		
		return newGeneration;
	}
	
	
	public static Individual[] createMatingPool(int numToFind, Individual[] oldGenerationMembers) {

		Individual[] matingPool = new Individual[numToFind];

		switch (Main.SELECT_TYPE) {
		
			case "roulette":

				for (int i = 0; i < matingPool.length; i++) {
					
					matingPool[i] = Algorithms.rouletteGetSingle(oldGenerationMembers);
				}
																
				break;
			
			case "SRWR":
				
				// Stochastic remainder sampling with replacement
				
				// Create array to hold the expected values for number of appearances of each individual in the mating pool
				double[] expectedValues = new double[numToFind];
				
				double[] relFitArray = Algorithms.getRelFitArray(oldGenerationMembers);

				// add individuals to mating pool according to integer part of expected value
				int expInt;
				double[] expDec = new double[numToFind];
				int poolCount = 0; //keeps track of how many individuals are in the mating pool
				for (int i = 0; i < numToFind; i++) {
					
					
					expectedValues[i] = relFitArray[i] * numToFind;
					
					expInt = (int)expectedValues[i];
					expDec[i] = expectedValues[i] - expInt;
					
					for (int j = 0; j < expInt; j++) {
												
						matingPool[poolCount] = oldGenerationMembers[i];
						poolCount++;
					}
				}
				
				// add individuals to mating pool according to roulette wheel selection based on decimal part of expected value
				int spaceCount = matingPool.length - poolCount; //number of spaces left to fill with roulette wheel selection
				Individual selected;
				for (int i = 0; i < spaceCount; i++) {

					selected = Algorithms.rouletteGetSingle(oldGenerationMembers, expDec);
					matingPool[poolCount] = selected; 
					poolCount++;
				}
				
				break;
				
			case "none":
				
				matingPool = oldGenerationMembers.clone();
				
				break;
				
			default:
				break;
		}
		
		return matingPool;		
	}
	
	
	public static Individual[][] chooseMatingPairs(Individual[] matingPool) {
		
		int[] index = new int[2];
		int numPairs = matingPool.length / 2;
		Individual[][] pairs = new Individual[numPairs][2];
		
		for (int i = 0; i < numPairs; i++) {
			
			index[0] = Algorithms.randomInt(0, matingPool.length - 1);
			index[1] = Algorithms.randomInt(0, matingPool.length - 1);
			while (index[1] == index[0]) {

				index[1] = Algorithms.randomInt(0, matingPool.length - 1);
			}
			
			pairs[i][0] = matingPool[index[0]];
			pairs[i][1] = matingPool[index[1]];
			matingPool = Algorithms.removeIndividual(matingPool, matingPool[index[0]]);
			
			// location of second individual to be removed may have changed after removing first, so alter index[1] accordingly
			if (index[0] < index[1]) {
				
				index[1] -= 1;
			}
			
			matingPool = Algorithms.removeIndividual(matingPool, matingPool[index[1]]);
		}

		return pairs;
	}
	
	
	public static Individual[] crossover(Individual[] parents){
		
		int numGenes = Math.min(parents[0].getChromosome().length, parents[1].getChromosome().length); // parent chromosomes may be of different lengths
		Individual[] offspring = new Individual[2];
		String[] chromosome1 = parents[0].getChromosome();
		String[] chromosome2 = parents[1].getChromosome();
		String[] offspringChrom1 = new String[numGenes];
		String[] offspringChrom2 = new String[numGenes];
		Individual offspring1;
		Individual offspring2;
		
		int crossPoint1;
		int crossPoint2;
		int crossSize;
		
		if (Algorithms.coinFlip(Main.CROSS_RATE)) {
			switch (Main.CROSS_TYPE) {
			
				case "2point":
					// randomly choose 2 crosspoints with CP2 >= CP1
					crossPoint1 = Algorithms.randomInt(0, numGenes);
					crossPoint2 = Algorithms.randomInt(0, numGenes);
					if (crossPoint1 > crossPoint2) {
						
						int holder = crossPoint1;
						crossPoint1 = crossPoint2;
						crossPoint2 = holder;
					}
					for (int i = 0; i < numGenes; i++) {
						
						if (i < crossPoint1 | i >= crossPoint2) {
		
							offspringChrom1[i] = chromosome2[i];
							offspringChrom2[i] = chromosome1[i];
						}
						else {
		
							offspringChrom1[i] = chromosome1[i];
							offspringChrom2[i] = chromosome2[i];
						}
					}
					
					offspring1 = new Individual(offspringChrom1);
					offspring2 = new Individual(offspringChrom2);
					
					offspring[0] = offspring1;
					offspring[1] = offspring2;
					break;
					
				case "average":
					
					double chromDub1 = Double.parseDouble(Algorithms.arrToStr(chromosome1));
					double chromDub2 = Double.parseDouble(Algorithms.arrToStr(chromosome2));
					String offChromString1;
					String offChromString2;
					double fitness1 = computeFitness(chromosome1);
					double fitness2 = computeFitness(chromosome2);
					
					double offChromDub1 = (0.5 * chromDub1) + (0.5 * chromDub2);
					double offChromDub2;
					
					if (fitness1 > fitness2) {
						
						offChromDub2 = (0.7 * chromDub1) + (0.3 * chromDub2);
					}
					else {
						
						offChromDub2 = (0.4 * chromDub1) + (0.6 * chromDub2);
					}
					
					offChromString1 = String.valueOf(offChromDub1);
					while (offChromString1.substring(0, offChromString1.indexOf('.')).length() < Main.LIMITS[1].length()) {
						
						offChromString1 = "0" + offChromString1;
					}
					offChromString2 = String.valueOf(offChromDub2);
					while (offChromString2.substring(0, offChromString2.indexOf('.')).length() < Main.LIMITS[1].length()) {
						
						offChromString2 = "0" + offChromString2;
					}
					
					offspringChrom1 = Algorithms.strToArr(offChromString1);
					offspringChrom2 = Algorithms.strToArr(offChromString2);
					
					offspring1 = new Individual(offspringChrom1);
					offspring2 = new Individual(offspringChrom2);
					
					offspring[0] = offspring1;
					offspring[1] = offspring2;
					
					break;
					
				case "tsp":
					
					crossPoint1 = Algorithms.randomInt(0, numGenes);
					crossPoint2 = Algorithms.randomInt(crossPoint1, numGenes); //cP2 > cP1
					crossSize = crossPoint2 - crossPoint1;
					String[] crossSection1 = new String[crossSize];
					String[] crossSection2 = new String[crossSize];
					String[] otherSection1 = new String[numGenes - crossSize];
					String[] otherSection2 = new String[numGenes - crossSize];
					String[] otherSection1New = new String[numGenes - crossSize];
					String[] otherSection2New = new String[numGenes - crossSize];
					int counterCross = 0;
					int counterOther = 0;
					
					// fill the arrays otherSection and crossSection for each parent
					for (int i = 0; i < numGenes; i++) {
						
						if (i >= crossPoint1 && i < crossPoint2) {
							
							crossSection1[counterCross] = parents[0].getChromosome()[i];
							crossSection2[counterCross] = parents[1].getChromosome()[i];
							counterCross += 1;
						}
						else {
		
							otherSection1[counterOther] = parents[0].getChromosome()[i];
							otherSection2[counterOther] = parents[1].getChromosome()[i];
							otherSection1New[counterOther] = otherSection1[counterOther];
							otherSection2New[counterOther] = otherSection2[counterOther];
							counterOther += 1;
						}
					}
		
					int matchIndex;
					for (int i = 0; i < crossSize; i++) {
						
						if (Arrays.asList(otherSection1).contains(crossSection2[i])) {
							
							matchIndex = Arrays.asList(otherSection1).indexOf(crossSection2[i]);
							
							for (int j = 0; j < crossSize; j++) {
								
								if (Arrays.asList(otherSection1New).contains(crossSection1[j]) == false && Arrays.asList(crossSection2).contains(crossSection1[j]) == false) {
		
									otherSection1New[matchIndex] = crossSection1[j];
								}
							}
						}
					}
					for (int i = 0; i < crossSize; i++) {
						
						if (Arrays.asList(otherSection2).contains(crossSection1[i])) {
							
							matchIndex = Arrays.asList(otherSection2).indexOf(crossSection1[i]);
							
							for (int j = 0; j < crossSize; j++) {
								
								if (Arrays.asList(otherSection2New).contains(crossSection2[j]) == false && Arrays.asList(crossSection1).contains(crossSection2[j]) == false) {
		
									otherSection2New[matchIndex] = crossSection2[j];
								}
							}
						}
					}
					
					int countOther = 0;
					int countCross = 0;
					for (int i = 0; i < numGenes; i++) {
						
						if (i >= crossPoint1 && i < crossPoint2) {
		
							offspringChrom1[i] = crossSection2[countCross];
							offspringChrom2[i] = crossSection1[countCross];
							countCross += 1;
						}
						else {
		
							offspringChrom1[i] = otherSection1New[countOther];
							offspringChrom2[i] = otherSection2New[countOther];
							countOther += 1;
						}
					}
					
					offspring1 = new Individual(offspringChrom1);
					offspring2 = new Individual(offspringChrom2);
					
					offspring[0] = offspring1;
					offspring[1] = offspring2;
					
					break;
					
				default:
					offspring = null;
					break;
			}
		}
		else {
			
			offspring[0] = parents[0];
			offspring[1] = parents[1];
		}
		
		return offspring;
	}	
	
	public static Individual[] mutate(Individual[] offspring) {
		
		boolean mutate;
		String[][] chromosomes = {offspring[0].getChromosome().clone(), offspring[1].getChromosome().clone()};
		
		switch (Main.CODING_TYPE) {
		
			case "binary":
				
				for (int i = 0; i < 2; i++) {
					
					for (int j = 0; j < chromosomes[i].length; j++) {
						
						if (Algorithms.coinFlip(Main.MUTATION_RATE)) {
							
							chromosomes[i][j] = String.valueOf((Integer.valueOf(chromosomes[i][j]) + 1) % 2);
						}
					}
				}
				
				break;
		
			case "maxima":
				
				int[] numGenes = {offspring[0].getChromosome().length, offspring[1].getChromosome().length};
				
				for (int i = 0; i < 2; i++) {
					
					for (int j = 0; j < numGenes[i]; j++) {
						
						mutate = Algorithms.coinFlip(Main.MUTATION_RATE);
						if (mutate) {
							
							if (chromosomes[i][j].equals(".")) {
	
							}
							else {
								
								boolean mutationInLimits = false;
								while (mutationInLimits == false) {

									chromosomes[i][j] = String.valueOf(Algorithms.randomInt(0, 9));
									
									double chromDub = Double.parseDouble(Algorithms.arrToStr(chromosomes[i]));
									
									if (chromDub > Double.parseDouble(Main.LIMITS[0]) && chromDub < Double.parseDouble(Main.LIMITS[1])) {
										
										mutationInLimits = true;
									}
									else {
										
										mutationInLimits = false;
									}
								}
							}
						}
					}
				}
				break;
				
			case "tsp":
				
				int mutationIndex;
				String geneHolder;
				
				for (int i = 0; i < 2; i++) {
					
					for (int j = 0; j < offspring[i].getChromosome().length; j++) {
						
						mutate = Algorithms.coinFlip(Main.MUTATION_RATE);
						
						if (mutate) {
							mutationIndex = Algorithms.randomInt(0, Main.CHROM_LENGTH - 1);
							geneHolder = chromosomes[i][mutationIndex];
							chromosomes[i][mutationIndex] = chromosomes[i][j];
							chromosomes[i][j] = geneHolder;
						}
					}
				}
				break;

			case "trajectory":
				
				if (Main.actionMutate.equals("a little")) {				
					double geneValue;
					double distToLimit;
					double distToLimit2;
					double mutationMax;
					double mutationValue;
					
					for (int i = 0; i < 2; i++) {
						
						// First and final genes remain fixed to determine start and end points
						for (int j = 1; j < chromosomes[i].length - 1; j++) {
							
							if (Algorithms.coinFlip(Main.MUTATION_RATE)) {
								geneValue = Double.parseDouble(chromosomes[i][j]);
								
								distToLimit = geneValue - Double.parseDouble(Main.LIMITS[0]);
								distToLimit2 = Double.parseDouble(Main.LIMITS[1]) - geneValue;
								if (distToLimit2 < distToLimit) {
									
									distToLimit = distToLimit2;
								}
								
								mutationMax = distToLimit / 20;
								mutationValue = ((Algorithms.randomDouble() - 0.5) * 2) * mutationMax;
								geneValue += mutationValue;
								
								chromosomes[i][j] = String.valueOf(geneValue);
							}
						}
					}
				}
				else if (Main.actionMutate.equals("a lot")) {
					
					for (int i = 0; i < 2; i++) {
						
						// First and final genes remain fixed to determine start and end points
						for (int j = 1; j < chromosomes[i].length - 1; j++) {
							
							if (Algorithms.coinFlip(Main.MUTATION_RATE)) {
								
								chromosomes[i][j] = String.valueOf(Algorithms.randomInt(Integer.valueOf(Main.LIMITS[0]), Integer.valueOf(Main.LIMITS[1])));
							}
							
						}
					}
				}
				
				break;
				
			case "pendulum":

				if (Main.actionMutate.equals("a little")) {				
					double geneValue;
					double distToLimit;
					double distToLimit2;
					double mutationMax;
					double mutationValue;
					
					for (int i = 0; i < 2; i++) {
						
						// First and final genes remain fixed to determine start and end points
						for (int j = 2; j < chromosomes[i].length; j++) {
							
							if (Algorithms.coinFlip(Main.MUTATION_RATE)) {
								geneValue = Double.parseDouble(chromosomes[i][j]);
								
								distToLimit = geneValue - Double.parseDouble(Main.LIMITS[0]);
								distToLimit2 = Double.parseDouble(Main.LIMITS[1]) - geneValue;
								if (distToLimit2 < distToLimit) {
									
									distToLimit = distToLimit2;
								}
								
								mutationMax = distToLimit / 20;
								mutationValue = ((Algorithms.randomDouble() - 0.5) * 2) * mutationMax;
								geneValue += mutationValue;
								
								chromosomes[i][j] = String.valueOf(geneValue);
							}
						}
					}
				}
				else if (Main.actionMutate.equals("a lot")) {
					
					for (int i = 0; i < 2; i++) {
						
						// First and final genes remain fixed to determine start and end points
						for (int j = 2; j < chromosomes[i].length; j++) {
							
							if (Algorithms.coinFlip(Main.MUTATION_RATE)) {
								
								chromosomes[i][j] = String.valueOf(Algorithms.randomInt(Integer.valueOf(Main.LIMITS[0]), Integer.valueOf(Main.LIMITS[1])));
							}
							
						}
					}
				}
				
			default:
				
				break;
		}

				
		Individual[] mutOffspring = {new Individual(chromosomes[0]), new Individual(chromosomes[1])};
		
		return mutOffspring;
	}

}
