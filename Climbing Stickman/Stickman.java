
public class Stickman {

	
	public static void main(String [] args) {
		
		int stickmanHeight = Integer.parseInt(args[0]);
		
		int stairHeight = Integer.parseInt(args[1]);
		
		for(int frame=0; frame<=stairHeight; frame++ ) {			//This is the main for loop to create each movement of stickman
																	//each frame is the stickman's location on the different parts of the stairs
			drawUpperSpaces(stairHeight,frame);
			
			drawHeadTorso(frame);
			
			drawBodynonStair(stickmanHeight,stairHeight,frame);			
			
			drawBodywithStair(stairHeight,frame);						
			
			drawLegs(stairHeight,frame);								
			
			drawBelowLegs(stairHeight,frame);							
			
			for (int space=1; space<=3; space++) {						//To put spaces between each frame
				System.out.println();
			}
		}
		}

	
	public static void drawUpperSpaces(int stairHeight,int frame) {						//This method puts blank spaces on top of each frame
		
		for (int upperSpaces= 0; upperSpaces <stairHeight-frame; upperSpaces++) {		
			System.out.println();																				
		}
	}

	public static void spaceBeginning(int frame) {										//This method puts blank spaces on the left of stickman so that our stickman can move
		
		for (int space=1; space<=3*frame; space++) {									// space is the space count before the each line
			System.out.print(" ");								
		}
	}
	
	public static void drawHeadTorso(int frame) {										//This method draws the head and torso of the stickman
		
		spaceBeginning(frame);
		System.out.println(" O ");		
		
		spaceBeginning(frame);
		System.out.println("/|\\");	
	}

	public static void drawBodynonStair(int stickmanHeight, int stairHeight, int frame) {		//This method draws the body parts that are not in an interaction with the stairs
		
		for ( int count=0; count <stickmanHeight - stairHeight-3 + frame ; count++) {			// count is the amount of bodies above the stairs
			spaceBeginning(frame);
			System.out.println(" | ");
		}	
	}
																	
	public static void drawBodywithStair(int stairHeight,int frame) {				// This part of the body code is for the drawing of body parts which interact with stairs
		
		for (int bodyCount = stairHeight; bodyCount >frame ;bodyCount--) {			//bodyCount is the amount of bodies interacting with stairs
				
			spaceBeginning(frame);
			System.out.print(" | ");
			
			for (int spaceAfter= 1; spaceAfter <=3*(bodyCount-frame); spaceAfter++  ) {		//spaceAfter is the count of spaces between body and stairs
				System.out.print(" ");
			}
			System.out.print("___");														
			System.out.print("|");															
			for (int starCount=0; starCount< 3*(stairHeight-bodyCount); starCount++) {		//number of stars in the stairs
				System.out.print("*");
			}
			System.out.print("|");	
			System.out.println();															
			}
		
	}
	
	public static void drawLegs(int stairHeight,int frame) {						// This method draws the legs of stickman
		
		spaceBeginning(frame);
		System.out.print("/ \\");
		System.out.print("___");
		System.out.print("|");
		
		for(int starCount=1; starCount<= (stairHeight-frame)*3; starCount++) {		//number of stars in the stairs
			System.out.print("*");
		}
		System.out.print("|");
		System.out.println();
	}

	public static void drawBelowLegs(int stairHeight, int frame) {					//This method draws the part below legs as our stickman climbs
		
		for( int counter=1; counter <= frame; counter++) {							// counter is the amount of recurrence to draw a line below legs
			
			for (int space=1; space<= 3*frame-3*(counter-1); space++) {				//space is the count of spaces before the stairs
				System.out.print(" ");
			}
			System.out.print("___");
			System.out.print("|");
			
			for(int starCount=1; starCount<=3*counter+3*(stairHeight-frame) ; starCount++) {	//starCount is the star count in the stairs
				System.out.print("*");
			}
			System.out.print("|");
			System.out.println();
	}
	}
}
