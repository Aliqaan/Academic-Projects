
import java.io.*;
import java.util.*;

public class Image{

	public static void main(String [] args) throws FileNotFoundException{
		
		int mode = Integer.parseInt(args[0]);							//First we need to learn which process we will go through
		String inputFile = args[1];										//Then we get the name of the input file		
		File f = new File(inputFile);
		
		Scanner input = new Scanner(f);									//Create a scanner to read the contents
		
		input.nextLine();												//Reading the parts until pixel values
		int numColumn = input.nextInt();								
		int numRow = input.nextInt();									//Keeping the important values
		int maxVal=input.nextInt();
		
		int [][][] pixelArray = new int [numRow][numColumn][3];			//Create an array to keep pixel values
		
		input.nextLine();
		
		for(int i=0;i<numRow;i++) {
			String line = input.nextLine();
			Scanner lineScan= new Scanner(line);						//New scanner to process the lines
				for(int j = 0; j<numColumn;j++) {
							
					int numRed = lineScan.nextInt();
					int numGreen = lineScan.nextInt();
					int numBlue = lineScan.nextInt(); 					//Store and assign the pixel values
					
					pixelArray [i][j][0] = numRed;
					pixelArray [i][j][1] = numGreen;
					pixelArray [i][j][2] = numBlue;
					
					}

	}
		if(mode==0) {													//Write the contents to a PPM file	
			String fileName="output.ppm";
			writePpm(pixelArray, maxVal,fileName);
		} else if(mode==1) {											//converting to black-and-white image
			String fileName="black-and-white.ppm";
			blackAndWhite(pixelArray);
			writePpm(pixelArray, maxVal,fileName);
		} else if(mode==2) {											// Applying convolution
			String fileName="convolution.ppm";
			String filterFile = args[2];
			
			int [][] filter = filterArray(filterFile);							//Creating a filter array
			int [][][] convoArr = convolution(pixelArray,filter,maxVal);		//Final version of our original pixel Array
			blackAndWhite(convoArr);											//converting to black-and-white
			writePpm(convoArr,maxVal,fileName);
		
		} else  if (mode==3) {													//quantization part
			String fileName="quantized.ppm";			
			int range = Integer.parseInt(args[2]);			
			boolean [][][] test = new boolean [numRow][numColumn][3];			//boolean array to control whether or not the element is changed
			
			for(int i=0; i<3;i++) {												//for loop to start quantization for each element
				for(int j=0; j<numColumn;j++) {
					for(int k=0;k<numRow;k++) {
						test[j][k][i]=true;										//Before calling quantization method we need to make this value true
						quantization(pixelArray, j , k , i ,range, test);		//so we know that we checked for this element and will not change this more
																				// than once
						}	
				}
			}
			
			writePpm(pixelArray,maxVal,fileName);
		}
		
				}
				
	public static void writePpm(int [][][] pixelArray,int maxVal,String name) throws FileNotFoundException {		
																		//Writing the content of 3D array to PPM file
		int numRow=pixelArray.length;									//Getting important values
		int numColumn=pixelArray.length;
		PrintStream output = new PrintStream(new File(name));			// PrintStream object to write into a file
		
		output.println("P3");
		output.println(numRow + " " + numColumn);						//First 3 lines of PPM file
		output.println(maxVal);
		for(int i=0;i<numRow;i++) {
			for(int j=0; j<numColumn; j++) {							//Writing the pixel values
			
				output.print(pixelArray[i][j][0] + " " + pixelArray[i][j][1] +" " + pixelArray[i][j][2] + " \t");
			}
			output.println();
		}
		
	}
	
	public static void blackAndWhite(int [][][]pixelArray) {					//Converting image to black and white version
	
		int numRow=pixelArray.length;
		int numColumn=pixelArray.length;
		for(int i=0;i<numRow;i++) {												//for loop to make calculations on all pixel triplets
			
				for(int j = 0; j<numColumn;j++) {
						
						int avgVal= (pixelArray[i][j][0] + pixelArray[i][j][1] + pixelArray[i][j][2]) /3 ;	//Taking the average
						pixelArray [i][j][0] = avgVal;
						pixelArray [i][j][1] = avgVal;												//Assigning new values
						pixelArray [i][j][2] = avgVal;
								
					}
					}

	}
	
	public static int[][] filterArray(String filterFile) throws FileNotFoundException {		//reading the content of filter to a 2D array
		
		File f= new File(filterFile);													//Creating a file object
		
		Scanner filter = new Scanner(f);												//Scanner object to read the contents
		String line=filter.nextLine();
		line= line.replace('x',' ');												//Getting rid of 'x' so we can get the row and column values
		Scanner lineScan= new Scanner(line);
		int filterRow = lineScan.nextInt();
		int filterColumn = lineScan.nextInt();
		
		
		int [][] filterArray = new int [filterRow][filterColumn];				//Declaring a 2D array
		
		for(int i=0; i<filterRow;i++) {											//Reading the contents
			for(int j=0; j<filterColumn;j++) {
				filterArray[i][j]=filter.nextInt();
			}
		}
		
		return filterArray;														//return final 2D filter Array
		
	}
	
	public static int [][][] convolution(int [][][] pixelArray, int [][] filter, int maxVal){	//Applying convolution
		
		int [][][] convoArray = new int [pixelArray.length - filter.length/2*2][pixelArray.length - filter.length/2*2][3];
		//Declaring a new 3D array with appropriate sizes.
		
		for (int d=0;d<3;d++) {												//	0-R 1-G 2-B (dimensions)
			for(int i=0 ; i<pixelArray.length-filter.length+1 ; i++) {			//traversing 3Darray
				
				for(int j=0 ; j<pixelArray.length-filter.length+1 ; j++) {		//traversing 3Darray
					int sum =0;
					for(int y =0; y<filter.length; y++) {						//traversing filter
						for(int x=0; x<filter.length; x++) {					//traversing filter
							sum+= pixelArray[i+y][j+x][d]*filter[y][x];			//Making the calculations
						}
					}
						
					if(sum<0) {													//Rearranging values if necessary
						sum=0;
					} else if(sum>maxVal) {
						sum=maxVal;
						
					}
					convoArray[filter.length/2 + i-1][filter.length/2 + j-1][d] = sum;		//Assigning the new value
				}
				
			}
		}
		return convoArray;													//Return final 3D array
	}
	
	public static void quantization(int [][][] pixelArray,int row, int column, int dimension,int range,boolean [][][] test){
		//The part where we handle quantization
		int now=pixelArray[row][column][dimension];						//Value of the current element
		
		if( column < pixelArray.length-1 ) {							//Checking if boundaries are OK to move right
			if(	(now-range <= pixelArray[row][column+1][dimension] && now+range >= pixelArray[row][column+1][dimension])) {  //Checking the range
				if( !test[row][column+1][dimension]) {									//Checking whether or not it is changed before
					
				pixelArray[row][column+1][dimension]=now;								//Equalizing the neighbour value to current value
				test [row][column+1][dimension]=true;											//If we change we set the boolean value true
				quantization(pixelArray,row,column+1,dimension,range,test);						//Recursive call for next element
				
			}
			}	
		}
		
		if( column > 0 ) {											//Checking if boundaries are OK to move left
			if(	(now-range <= pixelArray[row][column-1][dimension] && now+range >= pixelArray[row][column-1][dimension])  ) {	//Checking the range
				if(!test[row][column-1][dimension] ) {									//Checking whether or not it is changed before
					
				pixelArray[row][column-1][dimension]=now;								//Equalizing the neighbour value to current value
				test [row][column-1][dimension]=true;											//If we change we set the boolean value true
				quantization(pixelArray,row,column-1,dimension,range,test);						//Recursive call for next element
				
			}
			}	
		}
		
		if( row < pixelArray.length-1 ) {
			if(	(now-range <= pixelArray[row+1][column][dimension] && now+range >= pixelArray[row+1][column][dimension])  ) {
			if ( !test[row+1][column][dimension] )	{
				
				pixelArray[row+1][column][dimension]=now;								//Equalizing the neighbour value to current value
				test [row+1][column][dimension]=true;											//If we change we set the boolean value true
				quantization(pixelArray,row+1,column,dimension,range,test);						//Recursive call for next element
				
			}
			}
		}
			
		if( row > 0 ) {
			if(	(now-range <= pixelArray[row-1][column][dimension] && now+range >= pixelArray[row-1][column][dimension])  ) {
				if(!test[row-1][column][dimension] ) {
					
				pixelArray[row-1][column][dimension]=now;								//Equalizing the neighbour value to current value
				test [row-1][column][dimension]=true;											//If we change we set the boolean value true
				quantization(pixelArray,row-1,column,dimension,range,test);						//Recursive call for next element
				
			}
			}		
		}
		
		if( dimension < 2 ) {
			if(	(now-range <= pixelArray[row][column][dimension+1] && now+range >= pixelArray[row][column][dimension+1])  ) {
				if(!test[row][column][dimension+1] ) {
					
				pixelArray[row][column][dimension+1]=now;								//Equalizing the neighbour value to current value
				test [row][column][dimension+1]=true;											//If we change we set the boolean value true
				quantization(pixelArray,row,column,dimension+1,range,test);						//Recursive call for next element
				
			}
			}
		}
		
		if( dimension > 0 ) {
			if(	(now-range <= pixelArray[row][column][dimension-1] && now+range >= pixelArray[row][column][dimension-1])  ) {
				if(!test[row][column][dimension-1] ) {
					
				pixelArray[row][column][dimension-1]=now;							//Equalizing the neighbour value to current value
				test [row][column][dimension-1]=true;											//If we change we set the boolean value true
				quantization(pixelArray,row,column,dimension-1,range,test);						//Recursive call for next element
				
			}
		}
		}
		
			return;
		
	}
	
	}
	
	
	
	
