import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MinMax 
{
	public String maxPlayer,minPlayer,boardCurrState[][],node,depth,value,logValue;
	public int maxDepth,evalMatrix [][] = new int[8][8],currDepth=-1,bestMax=-100,maxFnd=0,flips=0,nxtStateflips=0,finalIJ=0;
	List<String> log = new ArrayList<String>();
	public int finalI=0,finalJ=0;
	
	public MinMax(String maxPlayer,String minPlayer,String[][] boardCurrState,int maxDepth)
	{
		this.maxPlayer 		= maxPlayer;
		this.minPlayer 		= minPlayer;
		this.boardCurrState = boardCurrState;
		this.maxDepth 		= maxDepth;
	}
	
	public void MinMaxImplementation() throws IOException
	{
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File ("output.txt").getAbsolutePath()));
		
		//Construction of Evaluation Matrix
		evalMatrix[0][0] = evalMatrix[0][7] = evalMatrix[7][0] = evalMatrix[7][7] = 99;
		evalMatrix[1][0] = evalMatrix[1][7] = evalMatrix[6][0] = evalMatrix[6][7] = -8;
		evalMatrix[0][1] = evalMatrix[0][6] = evalMatrix[7][1] = evalMatrix[7][6] = -8;
		evalMatrix[2][7] = evalMatrix[2][0] = evalMatrix[5][0] = evalMatrix[5][7] =  8;
		evalMatrix[0][2] = evalMatrix[0][5] = evalMatrix[7][2] = evalMatrix[7][5] =  8;
		evalMatrix[3][0] = evalMatrix[3][7] = evalMatrix[4][0] = evalMatrix[4][7] =  6;
		evalMatrix[0][3] = evalMatrix[0][4] = evalMatrix[7][3] = evalMatrix[7][4] =  6;
		evalMatrix[1][1] = evalMatrix[1][6] = evalMatrix[6][1] = evalMatrix[6][6] = -24;
		evalMatrix[2][1] = evalMatrix[2][6] = evalMatrix[5][1] = evalMatrix[5][6] = -4;
		evalMatrix[1][2] = evalMatrix[1][5] = evalMatrix[6][2] = evalMatrix[6][5] = -4; 
		evalMatrix[3][1] = evalMatrix[3][6] = evalMatrix[4][1] = evalMatrix[4][6] = -3;
		evalMatrix[1][3] = evalMatrix[1][4] = evalMatrix[6][3] = evalMatrix[6][4] = -3;
		evalMatrix[2][2] = evalMatrix[2][5] = evalMatrix[5][2] = evalMatrix[5][5] =  7;
		evalMatrix[2][3] = evalMatrix[2][4] = evalMatrix[5][3] = evalMatrix[5][4] =  4;
		evalMatrix[3][2] = evalMatrix[3][5] = evalMatrix[4][2] = evalMatrix[4][5] =  4;
		evalMatrix[3][3] = evalMatrix[3][4] = evalMatrix[4][3] = evalMatrix[4][4] =  0;
		
		System.out.println("Evaluation Matrix:");
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
				System.out.print(evalMatrix[j][k] +"\t");
			System.out.println();
		}
		
		String[][] rootState = new String[8][8];
		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				rootState[j][k]=boardCurrState[j][k];
		//rootState = Arrays.copyOf(boardCurrState,boardCurrState.length);
		
		//Call the Max Funtion initially
		int Fvalue = Max_Value_Fn(rootState,currDepth,-1,-1,0);
		
		System.out.println("Value at root node = " +Fvalue); 
	
		
		int noOfMoves=0,legalMoves_0[][] = new int [64][3];
		String[][] boardState_0 = new String[8][8];
		String[][] boardStateFinal = new String[8][8];

		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				boardState_0[j][k]=boardCurrState[j][k];
		//boardState_0 = Arrays.copyOf(boardCurrState,boardCurrState.length);

		//Find all Legal Moves
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardState_0[j][k].equals("*"))
				{
					boolean foundLegalMove;
					foundLegalMove = findlegalmoves(j,k,maxPlayer,boardState_0);
					if(foundLegalMove)
					{
						noOfMoves++;
						legalMoves_0[noOfMoves][0]=j;
						legalMoves_0[noOfMoves][1]=k;
					}
				}
			}
		}
		
	/*	for(int r=1;r<=noOfMoves;r++ )
		{
			for(int j=0;j<8;j++)
				for(int k=0;k<8;k++)
					boardState_0[j][k]=boardCurrState[j][k];
			//boardState_0 = Arrays.copyOf(boardCurrState,boardCurrState.length);
			boardStateFinal = applyLegalmove(legalMoves_0[r][0],legalMoves_0[r][1],boardState_0,maxPlayer);
			legalMoves_0[r][2] = getEvaluationFnValue(boardStateFinal);
			System.out.println("X=" +legalMoves_0[r][0] +" Y=" +legalMoves_0[r][1] +" Eval=" +legalMoves_0[r][2]);
		}*/
		
		boardStateFinal = applyLegalmove(finalI,finalJ,boardState_0,maxPlayer);
		System.out.println("finalI=" +finalI+ " finalJ=" +finalJ);
		
	/*	int maxv=-100;
		for(int r=1;r<=noOfMoves;r++ )
		{
			if(legalMoves_0[r][2] > maxv)
				maxv = legalMoves_0[r][2];
		}
		
		System.out.println("Maxv =" +maxv);
		
		int fnd=0;
		for(int r=1;r<=noOfMoves;r++ )
		{
			if(legalMoves_0[r][2] == maxv)
			{
				if(fnd==0)
				{
					finalI = legalMoves_0[r][0];
					finalJ = legalMoves_0[r][1];
					for(int j=0;j<8;j++)
						for(int k=0;k<8;k++)
							boardState_0[j][k]=boardCurrState[j][k];
					//boardState_0 = Arrays.copyOf(boardCurrState,boardCurrState.length);
					boardStateFinal = applyLegalmove(legalMoves_0[r][0],legalMoves_0[r][1],boardState_0,maxPlayer);
					fnd=1;
				}
				
			}
		}
		*/
		if(noOfMoves == 0)
		{
			System.out.println("Its a pass:");
			for(int j=0;j<8;j++)
			{
				for(int k=0;k<8;k++)
					System.out.print(boardCurrState[j][k]);
				System.out.println();
			}
			
			System.out.println("Node,Depth,Value");
			for(String s:log)
				System.out.println(s); 
			
			//Write to output file
			for(int j=0;j<8;j++)
			{
				for(int k=0;k<8;k++)
					bw.write(boardCurrState[j][k]);
				bw.newLine();
			}
			
			//Check if you have to add LOG ****************************************************************************
			bw.write("Node,Depth,Value");
			bw.newLine();
			for(String s:log)
			{
				bw.write(s);
				bw.newLine();
			}
		}
		else
		{
			System.out.println("The next state of the board:");
			for(int j=0;j<8;j++)
			{
				for(int k=0;k<8;k++)
					System.out.print(boardStateFinal[j][k]);
				System.out.println();
			}
		
			System.out.println("Node,Depth,Value");
			for(String s:log)
				System.out.println(s); 
		
			//Write in output file
			for(int j=0;j<8;j++)
			{
				for(int k=0;k<8;k++)
					bw.write(boardStateFinal[j][k]);
				bw.newLine();
			}
		
			bw.write("Node,Depth,Value");
			bw.newLine();
			for(String s:log)
			{
				bw.write(s);
				bw.newLine();
			}
		}
		bw.close();
	}
	
	public int Max_Value_Fn(String[][] tempArray_1,int currDepth,int moveX1,int moveY1,int pass1)
	{
		
		String[][] boardState_1 = new String[8][8];
		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				boardState_1[j][k]=tempArray_1[j][k];
		//boardState_1 = Arrays.copyOf(tempArray_1,tempArray_1.length);
		currDepth++;
	
		if(currDepth == maxDepth)
		{
			node = getNode(moveX1,moveY1);
			depth = Integer.toString(currDepth);
			int evalval1 = getEvaluationFnValue(boardState_1);
			value = Integer.toString(evalval1);
			logValue = node + "," +depth +"," +value;
			log.add(logValue);
			return evalval1;
		}
		
		int v = -999999;
		int noOfMoves=0,legalMoves_1[][] = new int [64][2];
		int noOfMaxPlayer_1=0,noOfMinPlayer_1=0;
		
		//Check if board has only 1 player
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardState_1[j][k].equals(maxPlayer))
					noOfMaxPlayer_1++;
				if(boardState_1[j][k].equals(minPlayer))
							noOfMinPlayer_1++;
			}
		}
		
		if(noOfMaxPlayer_1 == 0 || noOfMinPlayer_1 == 0)
		{
			node = getNode(moveX1,moveY1);
			depth = Integer.toString(currDepth);
			int evalval1 = getEvaluationFnValue(boardState_1);
			value = Integer.toString(evalval1);
			logValue = node + "," +depth +"," +value;
			log.add(logValue);
			return evalval1;	
		}
		
		//Find all Legal Moves
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardState_1[j][k].equals("*"))
				{
					boolean foundLegalMove;
					foundLegalMove = findlegalmoves(j,k,maxPlayer,boardState_1);
					if(foundLegalMove)
					{
						noOfMoves++;
						legalMoves_1[noOfMoves][0]=j;
						legalMoves_1[noOfMoves][1]=k;
						System.out.println("Legal moved added for maxPlayer: X=" +j+ " Y="+k);
					}
				}
			}
		}
	/*	
		node = getNode(moveX1,moveY1);
		depth = Integer.toString(currDepth);
		if(v == -999999)
			value = "-Infinity";
		else
			value = Integer.toString(v);
		logValue = node + "," +depth +"," +value;
		log.add(logValue);
		*/

		int maxPass = 0;
		//Terminate Condition
		if(noOfMoves == 0)
		{
			maxPass = 1;
			
			node = getNode(moveX1,moveY1);
			if(node.equals("root"))
				maxPass = 0;
			
			if(pass1 == 1)
			{
				node = getNode(moveX1,moveY1);
				depth = Integer.toString(currDepth);
				int evalval1 = getEvaluationFnValue(boardState_1);
				value = Integer.toString(evalval1);
			
				logValue = node + "," +depth +"," +value;
				log.add(logValue);
				return evalval1;
			}
			else
			{
				node = getNode(moveX1,moveY1);
				depth = Integer.toString(currDepth);
				if(v == -999999)
					value = "-Infinity";
				else
					value = Integer.toString(v);
				logValue = node + "," +depth +"," +value;
				log.add(logValue);
				
				//Call min
				for(int j=0;j<8;j++)
					for(int k=0;k<8;k++)
						boardState_1[j][k]=tempArray_1[j][k];
				v = MAX(v,Min_Value_Fn(boardState_1,currDepth,-10,-10,maxPass));
				
				node = getNode(moveX1,moveY1);
				depth = Integer.toString(currDepth);
				if(v == -999999)
					value = "-Infinity";
				else
					value = Integer.toString(v);
				logValue = node + "," +depth +"," +value;
				log.add(logValue);
			
			}
		}
		else
		{
			node = getNode(moveX1,moveY1);
			depth = Integer.toString(currDepth);
			if(v == -999999)
				value = "-Infinity";
			else
				value = Integer.toString(v);
			logValue = node + "," +depth +"," +value;
			log.add(logValue);
		}
	
		
		for(int r=1;r<=noOfMoves;r++)
		{	
			for(int j=0;j<8;j++)
				for(int k=0;k<8;k++)
					boardState_1[j][k]=tempArray_1[j][k];
			//boardState_1 = Arrays.copyOf(tempArray_1,tempArray_1.length);
			
			boardState_1	= applyLegalmove(legalMoves_1[r][0],legalMoves_1[r][1],boardState_1,maxPlayer);
			
			//int vm;
			int vBefore = v;
			v = MAX(v,Min_Value_Fn(boardState_1,currDepth,legalMoves_1[r][0],legalMoves_1[r][1],maxPass));
			
		
			if(Arrays.deepEquals(boardState_1,tempArray_1))
			{
				node  = "root";
				depth = Integer.toString(currDepth);
				if(v == -999999)
					value = "-Infinity";
				else
					value = Integer.toString(v);
				logValue = node + "," +depth +"," +value;
				log.add(logValue);
			}
			else
			{
				node = getNode(moveX1,moveY1);
				if(node.equals("root"))
				{
					if(vBefore != v)
					{
						finalI = legalMoves_1[r][0];
						finalJ = legalMoves_1[r][1];
					}
					
				}
				depth = Integer.toString(currDepth);
				value = Integer.toString(v);
				logValue = node + "," +depth +"," +value;
				log.add(logValue);
			}	
		}
		
		return v;
		
	}
	
	int MAX(int a,int b)
	{
		if(a>b)
			return a;
		else 
			return b;
	}
	
	public int Min_Value_Fn(String[][] tempArray_2,int currDepth,int moveX2,int moveY2,int pass2)
	{
		String[][] boardState_2 = new String[8][8];
		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				boardState_2[j][k]=tempArray_2[j][k];
		//boardState_2 = Arrays.copyOf(tempArray_2,tempArray_2.length);
		currDepth++;
		System.out.println("Min called");
		if(currDepth == maxDepth)
		{
			node = getNode(moveX2,moveY2);
			depth = Integer.toString(currDepth);
			int evalval2 = getEvaluationFnValue(boardState_2);
			value = Integer.toString(evalval2);
			logValue = node + "," +depth +"," +value;
			log.add(logValue);
			return evalval2;
		}
	
		int v = 999999;
		int noOfMoves=0,legalMoves_2[][] = new int [64][2];
		int noOfMaxPlayer_2=0,noOfMinPlayer_2=0;
		
		//Check if board has only 1 player
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardState_2[j][k].equals(maxPlayer))
					noOfMaxPlayer_2++;
				if(boardState_2[j][k].equals(minPlayer))
							noOfMinPlayer_2++;
			}
		}
		
		if(noOfMaxPlayer_2 == 0 || noOfMinPlayer_2 == 0)
		{
			node = getNode(moveX2,moveY2);
			depth = Integer.toString(currDepth);
			int evalval2 = getEvaluationFnValue(boardState_2);
			value = Integer.toString(evalval2);
			logValue = node + "," +depth +"," +value;
			log.add(logValue);
			return evalval2;	
		}
		
		//Find all Legal Moves
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardState_2[j][k].equals("*"))
				{
					boolean foundLegalMove;
					foundLegalMove = findlegalmoves(j,k,minPlayer,boardState_2);
					if(foundLegalMove)
					{
						noOfMoves++;
						legalMoves_2[noOfMoves][0]=j;
						legalMoves_2[noOfMoves][1]=k;
						System.out.println("Legal moved added for minPlayer: X=" +j+ " Y="+k);
					}
				}
			}
		}
		
		//Add log
	/*	node = getNode(moveX2,moveY2);
		depth = Integer.toString(currDepth);
		if(v == 999999)
			value = "Infinity";
		else
			value = Integer.toString(v);
		logValue = node + "," +depth +"," +value;
		log.add(logValue); 
	*/

		int minPass=0;
		if(noOfMoves == 0)
		{
			minPass=1;
			if(pass2 ==1)
			{
				node = getNode(moveX2,moveY2);
				depth = Integer.toString(currDepth);
				int evalval2 = getEvaluationFnValue(boardState_2);
				value = Integer.toString(evalval2);			
				logValue = node + "," +depth +"," +value;
				log.add(logValue);
				return evalval2;
			}
			else
			{
				node = getNode(moveX2,moveY2);
				depth = Integer.toString(currDepth);
				if(v == 999999)
					value = "Infinity";
				else
					value = Integer.toString(v);
				logValue = node + "," +depth +"," +value;
				log.add(logValue); 
				
				//Call max
				for(int j=0;j<8;j++)
					for(int k=0;k<8;k++)
						boardState_2[j][k]=tempArray_2[j][k];
				v = MIN(v,Max_Value_Fn(boardState_2,currDepth,-10,-10,minPass));
				
				node = getNode(moveX2,moveY2);
				depth = Integer.toString(currDepth);
				if(v == 999999)
					value = "Infinity";
				else
					value = Integer.toString(v);
				logValue = node + "," +depth +"," +value;
				log.add(logValue); 
			}
		}
		else
		{
			node = getNode(moveX2,moveY2);
			depth = Integer.toString(currDepth);
			if(v == 999999)
				value = "Infinity";
			else
				value = Integer.toString(v);
			logValue = node + "," +depth +"," +value;
			log.add(logValue);
		}
		
		for(int r=1;r<=noOfMoves;r++)
		{	
			
			for(int j=0;j<8;j++)
				for(int k=0;k<8;k++)
					boardState_2[j][k]=tempArray_2[j][k];
			//boardState_2 = Arrays.copyOf(tempArray_2,tempArray_2.length);
			
			boardState_2	= applyLegalmove(legalMoves_2[r][0],legalMoves_2[r][1],boardState_2,minPlayer);
	
			v = MIN(v,Max_Value_Fn(boardState_2,currDepth,legalMoves_2[r][0],legalMoves_2[r][1],minPass));
			
			node = getNode(moveX2,moveY2);
			//node = getNode(legalMoves_2[r][0],legalMoves_2[r][1]);
			depth = Integer.toString(currDepth);
			if(v == 999999)
				value = "Infinity";
			else
				value = Integer.toString(v);
			logValue = node + "," +depth +"," +value;
			log.add(logValue); 
			
		}
		
		return v;
	}
	
	int MIN(int a,int b)
	{
		if(a<b)
			return a;
		else 
			return b;
	}
	
	public boolean findlegalmoves(int x,int y,String p,String[][]tempArray_3)
	{
		String[][] boardCurrState_3 = new String[8][8];
		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				boardCurrState_3[j][k]=tempArray_3[j][k];
		//boardCurrState_3 = Arrays.copyOf(tempArray_3,tempArray_3.length);
		
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		
		String boardIntermedState[][] = new String[8][8];	
		//Intermediary array
		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				boardIntermedState[j][k]=boardCurrState_3[j][k];
		
			flips = 0;
			//Check Neighbor in N-W
			if( ((x-1) <= 7) && ((y-1) <= 7) && ((x-1) >= 0) && ((y-1) >= 0) ) 
			{
				if(boardCurrState_3[x-1][y-1].equals(oppPlayer))
				{
					boardIntermedState[x-1][y-1] = currPlayer;
					flips++;
					recursiveNorthWest((x-1),(y-1),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
		
			flips = 0;
			//Check Neighbor in N
			if( ((x-1) <= 7) && ((x-1) >= 0) ) 
			{		
				if(boardCurrState_3[x-1][y].equals(oppPlayer))
				{
					boardIntermedState[x-1][y] = currPlayer;
					flips++;
					recursiveNorth((x-1),(y),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}	
				}
			}
		
			flips = 0;
			//Check Neighbor in N-E
			if( ((x-1) <= 7) && ((y+1) <= 7) && ((x-1) >= 0) && ((y+1) >= 0) ) 
			{
				if(boardCurrState_3[x-1][y+1].equals(oppPlayer))
				{		
					boardIntermedState[x-1][y+1] = currPlayer;
					flips++;
					recursiveNorthEast((x-1),(y+1),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
				
			flips = 0;
			//Check Neighbor in W
			if( ((y-1) <= 7) && ((y-1) >= 0) ) 
			{
				if(boardCurrState_3[x][y-1].equals(oppPlayer))
				{		
					boardIntermedState[x][y-1] = currPlayer;
					flips++;
					recursiveWest((x),(y-1),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
				
			flips = 0;
			//Check Neighbor in E
			if( ((y+1) <= 7) && ((y+1) >= 0) ) 
			{
				
				if(boardCurrState_3[x][y+1].equals(oppPlayer))
				{		
					boardIntermedState[x][y+1] = currPlayer;
					flips++;
					recursiveEast((x),(y+1),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
				
			flips = 0;
			//Check Neighbor in S-W
			if( ((x+1) <= 7) && ((y-1) <= 7) && ((x+1) >= 0) && ((y-1) >= 0) ) 
			{
				
				if(boardCurrState_3[x+1][y-1].equals(oppPlayer))
				{						
					boardIntermedState[x+1][y-1] = currPlayer;
					flips++;
					recursiveSouthWest((x+1),(y-1),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
				
			flips = 0;
			//Check Neighbor in S
			if( ((x+1) <= 7) && ((x+1) >= 0) ) 
			{
					
				if(boardCurrState_3[x+1][y].equals(oppPlayer))
				{			
					boardIntermedState[x+1][y] = currPlayer;
					flips++;
					recursiveSouth((x+1),(y),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
				
			flips = 0;
			//Check Neighbor in S-E
			if( ((x+1) <= 7) && ((y+1) <= 7) && ((x+1) >= 0) && ((y+1) >= 0) ) 
			{
				if(boardCurrState_3[x+1][y+1].equals(oppPlayer))
				{			
					boardIntermedState[x+1][y+1] = currPlayer;
					flips++;
					recursiveSouthEast((x+1),(y+1),p,boardCurrState_3,boardIntermedState);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardIntermedState[x][y] = currPlayer;
						maxFnd = 0;
					}
				}
			}
			
			//Add the legal move only if the array has changed
			boolean areEqual = Arrays.deepEquals(boardCurrState_3,boardIntermedState);
		//	if(!areEqual)
		//	{
		//		noOfLegalMoves += 1;
				/*String r = Integer.toString(x);
				String s = Integer.toString(y);
				String intermed = r.concat(s);
				legalMoves[noOfLegalMoves][0] = Integer.valueOf(intermed); */
				
		//		legalMoves[noOfLegalMoves][0] = x;
		//		legalMoves[noOfLegalMoves][1] = y;
		//		System.out.println("Legal move added =" +legalMoves[noOfLegalMoves][0]+ "," +legalMoves[noOfLegalMoves][1]);
				
			/*	System.out.println("Legal Moves Matrix:");
				for(int f=1;f<=noOfLegalMoves;f++)
					System.out.println(legalMoves[f][0] +"=>" +legalMoves[f][1]); */
		
				//System.out.println("Found legal move for =" +intermed);
		//	} 
			
			return (!areEqual);
		
		
	}
	
	public void recursiveNorthWest(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((a-1) <= 7) && ((b-1) <= 7) && ((a-1) >= 0) && ((b-1) >= 0) )
		{
			if(boardCurrState_4[a-1][b-1].equals(oppPlayer))
			{
				boardInterState[a-1][b-1] = currPlayer;
				flips++;
				recursiveNorthWest((a-1),(b-1),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a-1][b-1].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}	
			if (boardCurrState_4[a-1][b-1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a+r][b+r] = oppPlayer;
					//flips--;
				}
			}
		}
		if( ((a-1) > 7) || ((b-1) > 7) || ((a-1) < 0) || ((b-1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a+r][b+r] = oppPlayer;
					//flips--;
				}
				
			}
		}
	}

	public void recursiveNorth(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((a-1) <= 7) && ((a-1) >= 0) )
		{
			if(boardCurrState_4[a-1][b].equals(oppPlayer))
			{
				boardInterState[a-1][b] = currPlayer;
				flips++;
				recursiveNorth((a-1),(b),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a-1][b].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}	
			if (boardCurrState_4[a-1][b].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a+r][b] = oppPlayer;
					//flips--;
				}
			}
		}	
				
		if( ((a-1) > 7) || ((a-1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a+r][b] = oppPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveNorthEast(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((a-1) <= 7) && ((b+1) <= 7) && ((a-1) >= 0) && ((b+1) >= 0) )
		{
			if(boardCurrState_4[a-1][b+1].equals(oppPlayer))
			{
				boardInterState[a-1][b+1] = currPlayer;
				flips++;
				recursiveNorthEast((a-1),(b+1),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a-1][b+1].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState_4[a-1][b+1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a+r][b-r] = oppPlayer;
					//flips--;
				}
			}
		}	
				
		if( ((a-1) > 7) || ((b+1) > 7) || ((a-1) < 0) || ((b+1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a+r][b-r] = oppPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveWest(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((b-1) <= 7) && ((b-1) >= 0) )
		{
			if(boardCurrState_4[a][b-1].equals(oppPlayer))
			{
				boardInterState[a][b-1] = currPlayer;
				flips++;
				recursiveWest((a),(b-1),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a][b-1].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState_4[a][b-1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a][b+r] = oppPlayer;
					//flips--;
				}
			}
		}	
		
		if( ((b-1) > 7) || ((b-1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a][b+r] = oppPlayer;
					//flips--;
				}	
			}
		}
	}
	
	public void recursiveEast(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((b+1) <= 7) && ((b+1) >= 0) )
		{
			if(boardCurrState_4[a][b+1].equals(oppPlayer))
			{
				boardInterState[a][b+1] = currPlayer;
				flips++;
				recursiveEast((a),(b+1),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a][b+1].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState_4[a][b+1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a][b-r] = oppPlayer;
					//flips--;
				}
			}
		}
		
		if( ((b+1) > 7) || ((b+1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a][b-r] = oppPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveSouthWest(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((a+1) <= 7) && ((b-1) <= 7) && ((a+1) >= 0) && ((b-1) >= 0) )
		{
			if(boardCurrState_4[a+1][b-1].equals(oppPlayer))
			{
				boardInterState[a+1][b-1] = currPlayer;
				flips++;
				recursiveSouthWest((a+1),(b-1),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a+1][b-1].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState_4[a+1][b-1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a-r][b+r] = oppPlayer;
					//flips--;
				}
			}
		}	
	
		if( ((a+1) > 7) || ((b-1) > 7) || ((a+1) < 0) || ((b-1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a-r][b+r] = oppPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveSouth(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((a+1) <= 7) && ((a+1) >= 0) )
		{
			if(boardCurrState_4[a+1][b].equals(oppPlayer))
			{
				boardInterState[a+1][b] = currPlayer;
				flips++;
				recursiveSouth((a+1),(b),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a+1][b].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState_4[a+1][b].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardInterState[a-r][b] = oppPlayer;
					//flips--;
				}
			}
		}	
		
		if( ((a+1) > 7) || ((a+1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a-r][b] = oppPlayer;
					//flips--;
				}
			}
		}
	}
	
	public void recursiveSouthEast(int a,int b,String p,String[][] boardCurrState_4,String[][] boardInterState)
	{
		String currPlayer,oppPlayer;
		if(p.equals(maxPlayer))
		{
			currPlayer = maxPlayer;
			oppPlayer  = minPlayer;
		}
		else
		{
			currPlayer = minPlayer;
			oppPlayer  = maxPlayer;
		}
		
		if( ((a+1) <= 7) && ((b+1) <= 7) && ((a+1) >= 0) && ((b+1) >= 0) )
		{
			if(boardCurrState_4[a+1][b+1].equals(oppPlayer))
			{
				boardInterState[a+1][b+1] = currPlayer;
				flips++;
				recursiveSouthEast((a+1),(b+1),p,boardCurrState_4,boardInterState);
			}
			if (boardCurrState_4[a+1][b+1].equals(currPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState_4[a+1][b+1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				//while(flips != 0)
				for(int r=0;r<flips;r++)
				{
					boardInterState[a-r][b-r] = oppPlayer;
					//flips--;
				}
			}
		}	
		
		if( ((a+1) > 7) || ((b+1) > 7) || ((a+1) < 0) || ((b+1) < 0) )
		{
			if(flips>0)
			{
				for(int r=0;r<flips;r++)
				{
					boardInterState[a-r][b-r] = oppPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public int getEvaluationFnValue(String boardNState[][])
	{
		int maxPlayerValue = 0, minPlayerValue = 0;
		
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardNState[j][k].equals(maxPlayer))
				{
					//System.out.println("Before maxPlayerValue =" +maxPlayerValue);
					maxPlayerValue = maxPlayerValue + evalMatrix[j][k];
					//System.out.println("evalMatrix =" + evalMatrix[j][k] );
					//System.out.println("After maxPlayerValue =" +maxPlayerValue);
				}
				if(boardNState[j][k].equals(minPlayer))
				{
					//System.out.println("Before minPlayerValue =" +minPlayerValue);
					minPlayerValue = minPlayerValue + evalMatrix[j][k];
					//System.out.println("evalMatrix =" + evalMatrix[j][k] );
					//System.out.println("After minPlayerValue =" +minPlayerValue);
				}
			}
		}
		
		//System.out.println("maxPlayerValue =" +maxPlayerValue);
		//System.out.println("minPlayerValue =" +minPlayerValue);
		
		return (maxPlayerValue - minPlayerValue);
	}
	
	public  String getNode(int m,int n)
	{
		String s="root";
		
		//column value
		if(n == 0)
			s = "a";
		if(n == 1)
			s = "b";
		if(n == 2)
			s = "c";
		if(n == 3)
			s = "d";
		if(n == 4)
			s = "e";
		if(n == 5)
			s = "f";
		if(n == 6)
			s = "g";
		if(n == 7)
			s = "h";
		
		//Row value
		if(m == 0)
			s = s + "1";
		if(m == 1)
			s = s + "2";
		if(m == 2)
			s = s + "3";
		if(m == 3)
			s = s + "4";
		if(m == 4)
			s = s + "5";
		if(m == 5)
			s = s + "6";
		if(m == 6)
			s = s + "7";
		if(m == 7)
			s = s + "8";
		if(m == -10)
			s = "pass";
		
		return s;
	}
	
	public String[][] applyLegalmove(int x,int y,String tempArray_4[][],String player)
	{
		//Get the array values
	/*	String str = Integer.toString(legalMove);
		System.out.println("legalMove =" +legalMove);
		int x = Character.getNumericValue(str.charAt(0));
		int y = Character.getNumericValue(str.charAt(1));
		
		*/
	//	System.out.println("Applying legal move to x=" +x+ " y=" +y);
		String[][] boardNState_4 = new String[8][8];
		//boardNState_4= Arrays.copyOf(tempArray_4,tempArray_4.length);
		for(int p=0;p<8;p++)
			for(int q=0;q<8;q++)
				boardNState_4[p][q] = tempArray_4[p][q];
		
		if(player.equals(maxPlayer))
		{
			//Place maxPlayer
			//boardNState[x][y] = maxPlayer;
			
			nxtStateflips = 0;
			maxFnd = 0;
			//Check in all directions
			//Check Neighbor in N-W
			if( ((x-1) <= 7) && ((y-1) <= 7) && ((x-1) >= 0) && ((y-1) >= 0) ) 
			{
				if(boardNState_4[x-1][y-1].equals(minPlayer))
				{
					boardNState_4[x-1][y-1] = maxPlayer;
					nxtStateflips++;
					recurNW((x-1),(y-1),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
			
			nxtStateflips = 0;
			//Check Neighbor in N
			if( ((x-1) <= 7) && ((x-1) >= 0) ) 
			{		
				if(boardNState_4[x-1][y].equals(minPlayer))
				{
					boardNState_4[x-1][y] = maxPlayer;
					nxtStateflips++;
					recurN((x-1),(y),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
							
				}
			}
			
			nxtStateflips = 0;
			//Check Neighbor in N-E
			if( ((x-1) <= 7) && ((y+1) <= 7) && ((x-1) >= 0) && ((y+1) >= 0) ) 
			{
				if(boardNState_4[x-1][y+1].equals(minPlayer))
				{		
					boardNState_4[x-1][y+1] = maxPlayer;
					nxtStateflips++;
					recurNE((x-1),(y+1),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in W
			if( ((y-1) <= 7) && ((y-1) >= 0) ) 
			{
				if(boardNState_4[x][y-1].equals(minPlayer))
				{		
					boardNState_4[x][y-1] = maxPlayer;
					nxtStateflips++;
					recurW((x),(y-1),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in E
			if( ((y+1) <= 7) && ((y+1) >= 0) ) 
			{
					
				if(boardNState_4[x][y+1].equals(minPlayer))
				{		
					boardNState_4[x][y+1] = maxPlayer;
					nxtStateflips++;
					recurE((x),(y+1),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in S-W
			if( ((x+1) <= 7) && ((y-1) <= 7) && ((x+1) >= 0) && ((y-1) >= 0) ) 
			{
					
				if(boardNState_4[x+1][y-1].equals(minPlayer))
				{						
					boardNState_4[x+1][y-1] = maxPlayer;
					nxtStateflips++;
					recurSW((x+1),(y-1),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in S
			if( ((x+1) <= 7) && ((x+1) >= 0) ) 
			{
						
				if(boardNState_4[x+1][y].equals(minPlayer))
				{			
					boardNState_4[x+1][y] = maxPlayer;
					nxtStateflips++;
					recurS((x+1),(y),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in S-E
			if( ((x+1) <= 7) && ((y+1) <= 7) && ((x+1) >= 0) && ((y+1) >= 0) ) 
			{
				if(boardNState_4[x+1][y+1].equals(minPlayer))
				{			
					boardNState_4[x+1][y+1] = maxPlayer;
					nxtStateflips++;
					recurSE((x+1),(y+1),minPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = maxPlayer;
						maxFnd = 0;
					}
				}
			}
		}
		
		if(player.equals(minPlayer))
		{
			//Place maxPlayer
			//boardNState[x][y] = maxPlayer;
			
			nxtStateflips = 0;
			maxFnd = 0;
			//Check in all directions
			//Check Neighbor in N-W
			if( ((x-1) <= 7) && ((y-1) <= 7) && ((x-1) >= 0) && ((y-1) >= 0) ) 
			{
				if(boardNState_4[x-1][y-1].equals(maxPlayer))
				{
					boardNState_4[x-1][y-1] = minPlayer;
					nxtStateflips++;
					recurNW((x-1),(y-1),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
			
			nxtStateflips = 0;
			//Check Neighbor in N
			if( ((x-1) <= 7) && ((x-1) >= 0) ) 
			{		
				if(boardNState_4[x-1][y].equals(maxPlayer))
				{
					boardNState_4[x-1][y] = minPlayer;
					nxtStateflips++;
					recurN((x-1),(y),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
							
				}
			}
			
			nxtStateflips = 0;
			//Check Neighbor in N-E
			if( ((x-1) <= 7) && ((y+1) <= 7) && ((x-1) >= 0) && ((y+1) >= 0) ) 
			{
				if(boardNState_4[x-1][y+1].equals(maxPlayer))
				{		
					boardNState_4[x-1][y+1] = minPlayer;
					nxtStateflips++;
					recurNE((x-1),(y+1),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in W
			if( ((y-1) <= 7) && ((y-1) >= 0) ) 
			{
				if(boardNState_4[x][y-1].equals(maxPlayer))
				{		
					boardNState_4[x][y-1] = minPlayer;
					nxtStateflips++;
					recurW((x),(y-1),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in E
			if( ((y+1) <= 7) && ((y+1) >= 0) ) 
			{
					
				if(boardNState_4[x][y+1].equals(maxPlayer))
				{		
					boardNState_4[x][y+1] = minPlayer;
					nxtStateflips++;
					recurE((x),(y+1),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in S-W
			if( ((x+1) <= 7) && ((y-1) <= 7) && ((x+1) >= 0) && ((y-1) >= 0) ) 
			{
					
				if(boardNState_4[x+1][y-1].equals(maxPlayer))
				{						
					boardNState_4[x+1][y-1] = minPlayer;
					nxtStateflips++;
					recurSW((x+1),(y-1),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in S
			if( ((x+1) <= 7) && ((x+1) >= 0) ) 
			{
						
				if(boardNState_4[x+1][y].equals(maxPlayer))
				{			
					boardNState_4[x+1][y] = minPlayer;
					nxtStateflips++;
					recurS((x+1),(y),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
					
			nxtStateflips = 0;
			//Check Neighbor in S-E
			if( ((x+1) <= 7) && ((y+1) <= 7) && ((x+1) >= 0) && ((y+1) >= 0) ) 
			{
				if(boardNState_4[x+1][y+1].equals(maxPlayer))
				{			
					boardNState_4[x+1][y+1] = minPlayer;
					nxtStateflips++;
					recurSE((x+1),(y+1),maxPlayer,boardNState_4);
					if(maxFnd == 1)
					{
						//Place your maxPlayer at the empty cell
						boardNState_4[x][y] = minPlayer;
						maxFnd = 0;
					}
				}
			}
		}
		
	/*	System.out.println("In applyLegalMove, tempArray_4:");
		for(int p=0;p<8;p++)
		{
			for(int q=0;q<8;q++)
				System.out.print(tempArray_4[p][q]);
			System.out.println();
		}
		System.out.println("In applyLegalMove, boardNState_4, for values X=" +x+ " Y="+y);
		for(int p=0;p<8;p++)
		{
			for(int q=0;q<8;q++)
				System.out.print(boardNState_4[p][q]);
			System.out.println();
		}
	*/
		return boardNState_4;
	}
	
	public void recurNW(int a,int b,String p,String boardNState_R[][])
	{
		
		if(p.equals(minPlayer))
		{
			if( ((a-1) <= 7) && ((b-1) <= 7) && ((a-1) >= 0) && ((b-1) >= 0) )
			{
				if(boardNState_R[a-1][b-1].equals(minPlayer))
				{
					boardNState_R[a-1][b-1] = maxPlayer;
					nxtStateflips++;
					recurNW((a-1),(b-1),minPlayer,boardNState_R);
				}
				if (boardNState_R[a-1][b-1].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}	
				if (boardNState_R[a-1][b-1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b+r] = minPlayer;
						//flips--;
					}
				}
			}	
			if( ((a-1) > 7) || ((b-1) > 7) || ((a-1) < 0) || ((b-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b+r] =minPlayer;
						//flips--;
					}
					
				}
			}
		}	
		
		if(p.equals(maxPlayer))
		{
			if( ((a-1) <= 7) && ((b-1) <= 7) && ((a-1) >= 0) && ((b-1) >= 0) )
			{
				if(boardNState_R[a-1][b-1].equals(maxPlayer))
				{
					boardNState_R[a-1][b-1] = minPlayer;
					nxtStateflips++;
					recurNW((a-1),(b-1),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a-1][b-1].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}	
				if (boardNState_R[a-1][b-1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b+r] = maxPlayer;
						//flips--;
					}
				}
			}	
			if( ((a-1) > 7) || ((b-1) > 7) || ((a-1) < 0) || ((b-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b+r] =maxPlayer;
						//flips--;
					}
					
				}
			}
			
		}	
	}
		
	public void recurN(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((a-1) <= 7) && ((a-1) >= 0) )
			{
				if(boardNState_R[a-1][b].equals(minPlayer))
				{
					boardNState_R[a-1][b] = maxPlayer;
					nxtStateflips++;
					recurN((a-1),(b),minPlayer,boardNState_R);
				}
				if (boardNState_R[a-1][b].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}	
				if (boardNState_R[a-1][b].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((a-1) > 7) || ((a-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b] = minPlayer;
						//flips--;
					}
					
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((a-1) <= 7) && ((a-1) >= 0) )
			{
				if(boardNState_R[a-1][b].equals(maxPlayer))
				{
					boardNState_R[a-1][b] = minPlayer;
					nxtStateflips++;
					recurN((a-1),(b),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a-1][b].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}	
				if (boardNState_R[a-1][b].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((a-1) > 7) || ((a-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b] = maxPlayer;
						//flips--;
					}
					
				}
			}
		}
	}
	
	public void recurNE(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((a-1) <= 7) && ((b+1) <= 7) && ((a-1) >= 0) && ((b+1) >= 0) )
			{
				if(boardNState_R[a-1][b+1].equals(minPlayer))
				{
					boardNState_R[a-1][b+1] = maxPlayer;
					nxtStateflips++;
					recurNE((a-1),(b+1),minPlayer,boardNState_R);
				}
				if (boardNState_R[a-1][b+1].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a-1][b+1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b-r] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((a-1) > 7) || ((b+1) > 7) || ((a-1) < 0) || ((b+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b-r] = minPlayer;
						//flips--;
					}
					
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((a-1) <= 7) && ((b+1) <= 7) && ((a-1) >= 0) && ((b+1) >= 0) )
			{
				if(boardNState_R[a-1][b+1].equals(maxPlayer))
				{
					boardNState_R[a-1][b+1] = minPlayer;
					nxtStateflips++;
					recurNE((a-1),(b+1),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a-1][b+1].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a-1][b+1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b-r] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((a-1) > 7) || ((b+1) > 7) || ((a-1) < 0) || ((b+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a+r][b-r] = maxPlayer;
						//flips--;
					}
					
				}
			}
		}
	}

	public void recurW(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((b-1) <= 7) && ((b-1) >= 0) )
			{
				if(boardNState_R[a][b-1].equals(minPlayer))
				{
					boardNState_R[a][b-1] = maxPlayer;
					nxtStateflips++;
					recurW((a),(b-1),minPlayer,boardNState_R);
				}
				if (boardNState_R[a][b-1].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a][b-1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b+r] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((b-1) > 7) || ((b-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b+r] = minPlayer;
						//flips--;
					}	
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((b-1) <= 7) && ((b-1) >= 0) )
			{
				if(boardNState_R[a][b-1].equals(maxPlayer))
				{
					boardNState_R[a][b-1] = minPlayer;
					nxtStateflips++;
					recurW((a),(b-1),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a][b-1].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a][b-1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b+r] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((b-1) > 7) || ((b-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b+r] = maxPlayer;
						//flips--;
					}	
				}
			}
		}
	}
	
	public void recurE(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((b+1) <= 7) && ((b+1) >= 0) )
			{
				if(boardNState_R[a][b+1].equals(minPlayer))
				{
					boardNState_R[a][b+1] = maxPlayer;
					nxtStateflips++;
					recurE((a),(b+1),minPlayer,boardNState_R);
				}
				if (boardNState_R[a][b+1].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a][b+1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b-r] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((b+1) > 7) || ((b+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b-r] = minPlayer;
						//flips--;
					}
					
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((b+1) <= 7) && ((b+1) >= 0) )
			{
				if(boardNState_R[a][b+1].equals(maxPlayer))
				{
					boardNState_R[a][b+1] = minPlayer;
					nxtStateflips++;
					recurE((a),(b+1),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a][b+1].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a][b+1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b-r] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((b+1) > 7) || ((b+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a][b-r] = maxPlayer;
						//flips--;
					}
					
				}
			}
		}
	}
	
	public void recurSW(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((a+1) <= 7) && ((b-1) <= 7) && ((a+1) >= 0) && ((b-1) >= 0) )
			{
				if(boardNState_R[a+1][b-1].equals(minPlayer))
				{
					boardNState_R[a+1][b-1] = maxPlayer;
					nxtStateflips++;
					recurSW((a+1),(b-1),minPlayer,boardNState_R);
				}
				if (boardNState_R[a+1][b-1].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a+1][b-1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b+r] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((a+1) > 7) || ((b-1) > 7) || ((a+1) < 0) || ((b-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b+r] = minPlayer;
						//flips--;
					}
					
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((a+1) <= 7) && ((b-1) <= 7) && ((a+1) >= 0) && ((b-1) >= 0) )
			{
				if(boardNState_R[a+1][b-1].equals(maxPlayer))
				{
					boardNState_R[a+1][b-1] = minPlayer;
					nxtStateflips++;
					recurSW((a+1),(b-1),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a+1][b-1].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a+1][b-1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b+r] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((a+1) > 7) || ((b-1) > 7) || ((a+1) < 0) || ((b-1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b+r] = maxPlayer;
						//flips--;
					}
					
				}
			}
		}
	}
	
	public void recurS(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((a+1) <= 7) && ((a+1) >= 0) )
			{
				if(boardNState_R[a+1][b].equals(minPlayer))
				{
					boardNState_R[a+1][b] = maxPlayer;
					nxtStateflips++;
					recurS((a+1),(b),minPlayer,boardNState_R);
				}
				if (boardNState_R[a+1][b].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a+1][b].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((a+1) > 7) || ((a+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b] = minPlayer;
						//flips--;
					}
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((a+1) <= 7) && ((a+1) >= 0) )
			{
				if(boardNState_R[a+1][b].equals(maxPlayer))
				{
					boardNState_R[a+1][b] = minPlayer;
					nxtStateflips++;
					recurS((a+1),(b),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a+1][b].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a+1][b].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((a+1) > 7) || ((a+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b] = maxPlayer;
						//flips--;
					}
				}
			}
		}
	}

	public void recurSE(int a,int b,String p,String boardNState_R[][])
	{
		if(p.equals(minPlayer))
		{
			if( ((a+1) <= 7) && ((b+1) <= 7) && ((a+1) >= 0) && ((b+1) >= 0) )
			{
				if(boardNState_R[a+1][b+1].equals(minPlayer))
				{
					boardNState_R[a+1][b+1] = maxPlayer;
					nxtStateflips++;
					recurSE((a+1),(b+1),minPlayer,boardNState_R);
				}
				if (boardNState_R[a+1][b+1].equals(maxPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a+1][b+1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					//while(flips != 0)
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b-r] = minPlayer;
						//flips--;
					}
				}
			}
			if( ((a+1) > 7) || ((b+1) > 7) || ((a+1) < 0) || ((b+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b-r] = minPlayer;
						//flips--;
					}
					
				}
			}
		}
		
		if(p.equals(maxPlayer))
		{
			if( ((a+1) <= 7) && ((b+1) <= 7) && ((a+1) >= 0) && ((b+1) >= 0) )
			{
				if(boardNState_R[a+1][b+1].equals(maxPlayer))
				{
					boardNState_R[a+1][b+1] = minPlayer;
					nxtStateflips++;
					recurSE((a+1),(b+1),maxPlayer,boardNState_R);
				}
				if (boardNState_R[a+1][b+1].equals(minPlayer))
				{
					//It is a Legal move. Place the max player there
					maxFnd = 1;
				}
				if (boardNState_R[a+1][b+1].equals("*"))
				{
					//Reverse back board to beginning position before this was called
					//while(flips != 0)
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b-r] = maxPlayer;
						//flips--;
					}
				}
			}
			if( ((a+1) > 7) || ((b+1) > 7) || ((a+1) < 0) || ((b+1) < 0) )
			{
				if(nxtStateflips>0)
				{
					for(int r=0;r<nxtStateflips;r++)
					{
						boardNState_R[a-r][b-r] = maxPlayer;
						//flips--;
					}
					
				}
			}
		}
	}
}
