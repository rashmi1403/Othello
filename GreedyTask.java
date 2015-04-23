import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
public class GreedyTask 
{
	public String maxPlayer,minPlayer,boardCurrState[][];
	public int legalMoves[][] = new int [64][2],legalMoveNo=-1,result[][] = new int [64][2];
	public String boardIntermedState[][] = new String[8][8];
	public int evalMatrix [][] = new int[8][8];
	public String boardfinalState[][] = new String[8][8];
	public int finalIJ=0,finalPlayI,finalPlayJ,bestMax=-100, maxFnd=0,flips=0;
	
	
	public GreedyTask(String maxPlayer,String minPlayer,String[][] boardCurrState)
	{
		this.maxPlayer 		= maxPlayer;
		this.minPlayer 		= minPlayer;
		this.boardCurrState = boardCurrState;
	}
	
	public void GreedyMethodImplementation() throws IOException
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
				
				//Find all Legal Moves
				for(int j=0;j<8;j++)
					for(int k=0;k<8;k++)
						if(boardCurrState[j][k].equals("*"))
							findlegalmoves(j,k);
				
				if(legalMoveNo == -1)
				{
					System.out.println("Its a pass:");
					for(int j=0;j<8;j++)
					{
						for(int k=0;k<8;k++)
							System.out.print(boardCurrState[j][k]);
						System.out.println();
					}
					
					//Write in output file
					for(int j=0;j<8;j++)
					{
						for(int k=0;k<8;k++)
							bw.write(boardCurrState[j][k]);
						bw.newLine();
					}	
				}
				else 
				{
				System.out.println("Legal Moves Matrix:");
				for(int f=0;f<=legalMoveNo;f++)
					System.out.println(legalMoves[f][0] +"=>" +legalMoves[f][1]);
				
				//Print the next Best State
				String a = Integer.toString(finalIJ);
				if(a.length() == 2)
				{
					finalPlayI = Character.getNumericValue(a.charAt(0));
					finalPlayJ = Character.getNumericValue(a.charAt(1));
					System.out.println("finalPlayI = " +finalPlayI);
					System.out.println("finalPlayJ = " +finalPlayJ);
				}
				
				System.out.println("The next state of the board:");
				for(int j=0;j<8;j++)
				{
					for(int k=0;k<8;k++)
						System.out.print(boardfinalState[j][k]);
					System.out.println();
				}
				
				//Write in output file
				for(int j=0;j<8;j++)
				{
					for(int k=0;k<8;k++)
						bw.write(boardfinalState[j][k]);
					bw.newLine();
				}
				
				}
				bw.close();
				
	}
	
	public void findlegalmoves(int x,int y)
	{
		System.out.println("Finding legal moves for:");
		System.out.println("x= "+x+ " y= "+y);
		
		//Intermediary array
		for(int j=0;j<8;j++)
			for(int k=0;k<8;k++)
				boardIntermedState[j][k]=boardCurrState[j][k];
		
		flips = 0;
		//Check Neighbor in N-W
		if( ((x-1) <= 7) && ((y-1) <= 7) && ((x-1) >= 0) && ((y-1) >= 0) ) 
		{
			if(boardCurrState[x-1][y-1].equals(minPlayer))
			{
				boardIntermedState[x-1][y-1] = maxPlayer;
				flips++;
				recursiveNorthWest((x-1),(y-1));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
		
		flips = 0;
		//Check Neighbor in N
		if( ((x-1) <= 7) && ((x-1) >= 0) ) 
		{		
			if(boardCurrState[x-1][y].equals(minPlayer))
			{
				boardIntermedState[x-1][y] = maxPlayer;
				flips++;
				recursiveNorth((x-1),(y));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
						
			}
		}
		
		flips = 0;
		//Check Neighbor in N-E
		if( ((x-1) <= 7) && ((y+1) <= 7) && ((x-1) >= 0) && ((y+1) >= 0) ) 
		{
			if(boardCurrState[x-1][y+1].equals(minPlayer))
			{		
				boardIntermedState[x-1][y+1] = maxPlayer;
				flips++;
				recursiveNorthEast((x-1),(y+1));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
				
		flips = 0;
		//Check Neighbor in W
		if( ((y-1) <= 7) && ((y-1) >= 0) ) 
		{
			if(boardCurrState[x][y-1].equals(minPlayer))
			{		
				boardIntermedState[x][y-1] = maxPlayer;
				flips++;
				recursiveWest((x),(y-1));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
				
		flips = 0;
		//Check Neighbor in E
		if( ((y+1) <= 7) && ((y+1) >= 0) ) 
		{
				
			if(boardCurrState[x][y+1].equals(minPlayer))
			{		
				boardIntermedState[x][y+1] = maxPlayer;
				flips++;
				recursiveEast((x),(y+1));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
				
		flips = 0;
		//Check Neighbor in S-W
		if( ((x+1) <= 7) && ((y-1) <= 7) && ((x+1) >= 0) && ((y-1) >= 0) ) 
		{
				
			if(boardCurrState[x+1][y-1].equals(minPlayer))
			{						
				boardIntermedState[x+1][y-1] = maxPlayer;
				flips++;
				recursiveSouthWest((x+1),(y-1));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
				
		flips = 0;
		//Check Neighbor in S
		if( ((x+1) <= 7) && ((x+1) >= 0) ) 
		{
					
			if(boardCurrState[x+1][y].equals(minPlayer))
			{			
				boardIntermedState[x+1][y] = maxPlayer;
				flips++;
				recursiveSouth((x+1),(y));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
				
		flips = 0;
		//Check Neighbor in S-E
		if( ((x+1) <= 7) && ((y+1) <= 7) && ((x+1) >= 0) && ((y+1) >= 0) ) 
		{
			if(boardCurrState[x+1][y+1].equals(minPlayer))
			{			
				boardIntermedState[x+1][y+1] = maxPlayer;
				flips++;
				recursiveSouthEast((x+1),(y+1));
				if(maxFnd == 1)
				{
					//Place your maxPlayer at the empty cell
					boardIntermedState[x][y] = maxPlayer;
					maxFnd = 0;
				}
			}
		}
			
		//Add the legal move only if the array has changed
		
		//boolean areEqual = Arrays.equals(Arrays.sort(boardCurrState), Arrays.sort(boardIntermedState));
		boolean areEqual = Arrays.deepEquals(boardCurrState,boardIntermedState);
		if(!areEqual)
		{
			legalMoveNo += 1;
			String r = Integer.toString(x);
			String s = Integer.toString(y);
			String intermed = r.concat(s);
			legalMoves[legalMoveNo][0] = Integer.valueOf(intermed);
		
			System.out.println("Found legal move for =" +intermed);
		
			//Finding Evaluation Fn Value for - boardIntermedState
			legalMoves[legalMoveNo][1] = getEvaluationFnValue();
			System.out.println("Eval value = " +legalMoves[legalMoveNo][1]);
		
			//Assign it to finalBoard
			if(legalMoves[legalMoveNo][1] > bestMax )
			{
				bestMax = legalMoves[legalMoveNo][1];
				finalIJ = Integer.valueOf(intermed);
				for(int l=0;l<8;l++)
					for(int m=0;m<8;m++)
						boardfinalState[l][m]=boardIntermedState[l][m];
			}
		
			//Keep track of Best Move
			if(legalMoves[legalMoveNo][1] == bestMax  )
			{
				if(Integer.valueOf(intermed) < finalIJ)
				{
					finalIJ = Integer.valueOf(intermed);
					for(int l=0;l<8;l++)
						for(int m=0;m<8;m++)
							boardfinalState[l][m]=boardIntermedState[l][m];
				}
			}	 
		}
		
	}
	
	public void recursiveNorthWest(int a,int b)
	{
		if( ((a-1) <= 7) && ((b-1) <= 7) && ((a-1) >= 0) && ((b-1) >= 0) )
		{
			if(boardCurrState[a-1][b-1].equals(minPlayer))
			{
				boardIntermedState[a-1][b-1] = maxPlayer;
				flips++;
				recursiveNorthWest((a-1),(b-1));
			}
			if (boardCurrState[a-1][b-1].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}	
			if (boardCurrState[a-1][b-1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a+r][b+r] = minPlayer;
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
					boardIntermedState[a+r][b+r] = minPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveNorth(int a,int b)
	{
		if( ((a-1) <= 7) && ((a-1) >= 0) )
		{
			if(boardCurrState[a-1][b].equals(minPlayer))
			{
				boardIntermedState[a-1][b] = maxPlayer;
				flips++;
				recursiveNorth((a-1),(b));
			}
			if (boardCurrState[a-1][b].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}	
			if (boardCurrState[a-1][b].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a+r][b] = minPlayer;
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
					boardIntermedState[a+r][b] = minPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveNorthEast(int a,int b)
	{
		if( ((a-1) <= 7) && ((b+1) <= 7) && ((a-1) >= 0) && ((b+1) >= 0) )
		{
			if(boardCurrState[a-1][b+1].equals(minPlayer))
			{
				boardIntermedState[a-1][b+1] = maxPlayer;
				flips++;
				recursiveNorthEast((a-1),(b+1));
			}
			if (boardCurrState[a-1][b+1].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState[a-1][b+1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a+r][b-r] = minPlayer;
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
					boardIntermedState[a+r][b-r] = minPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveWest(int a,int b)
	{
		if( ((b-1) <= 7) && ((b-1) >= 0) )
		{
			if(boardCurrState[a][b-1].equals(minPlayer))
			{
				boardIntermedState[a][b-1] = maxPlayer;
				flips++;
				recursiveWest((a),(b-1));
			}
			if (boardCurrState[a][b-1].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState[a][b-1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a][b+r] = minPlayer;
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
					boardIntermedState[a][b+r] = minPlayer;
					//flips--;
				}	
			}
		}
	}
	
	public void recursiveEast(int a,int b)
	{
		if( ((b+1) <= 7) && ((b+1) >= 0) )
		{
			if(boardCurrState[a][b+1].equals(minPlayer))
			{
				boardIntermedState[a][b+1] = maxPlayer;
				flips++;
				recursiveEast((a),(b+1));
			}
			if (boardCurrState[a][b+1].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState[a][b+1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a][b-r] = minPlayer;
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
					boardIntermedState[a][b-r] = minPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveSouthWest(int a,int b)
	{
		if( ((a+1) <= 7) && ((b-1) <= 7) && ((a+1) >= 0) && ((b-1) >= 0) )
		{
			if(boardCurrState[a+1][b-1].equals(minPlayer))
			{
				boardIntermedState[a+1][b-1] = maxPlayer;
				flips++;
				recursiveSouthWest((a+1),(b-1));
			}
			if (boardCurrState[a+1][b-1].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState[a+1][b-1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a-r][b+r] = minPlayer;
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
					boardIntermedState[a-r][b+r] = minPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public void recursiveSouth(int a,int b)
	{
		if( ((a+1) <= 7) && ((a+1) >= 0) )
		{
			if(boardCurrState[a+1][b].equals(minPlayer))
			{
				boardIntermedState[a+1][b] = maxPlayer;
				flips++;
				recursiveSouth((a+1),(b));
			}
			if (boardCurrState[a+1][b].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState[a+1][b].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a-r][b] = minPlayer;
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
					boardIntermedState[a-r][b] = minPlayer;
					//flips--;
				}
				
			}
		}
		
	}
	
	public void recursiveSouthEast(int a,int b)
	{
		if( ((a+1) <= 7) && ((b+1) <= 7) && ((a+1) >= 0) && ((b+1) >= 0) )
		{
			if(boardCurrState[a+1][b+1].equals(minPlayer))
			{
				boardIntermedState[a+1][b+1] = maxPlayer;
				flips++;
				recursiveSouthEast((a+1),(b+1));
			}
			if (boardCurrState[a+1][b+1].equals(maxPlayer))
			{
				//It is a Legal move. Place the max player there
				maxFnd = 1;
			}
			if (boardCurrState[a+1][b+1].equals("*"))
			{
				//Reverse back board to beginning position before this was called
				//while(flips != 0)
				for(int r=0;r<flips;r++)
				{
					boardIntermedState[a-r][b-r] = minPlayer;
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
					boardIntermedState[a-r][b-r] = minPlayer;
					//flips--;
				}
				
			}
		}
	}
	
	public int getEvaluationFnValue()
	{
		int maxPlayerValue = 0, minPlayerValue = 0;
		
		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
			{
				if(boardIntermedState[j][k].equals(maxPlayer))
				{
					//System.out.println("Before maxPlayerValue =" +maxPlayerValue);
					maxPlayerValue = maxPlayerValue + evalMatrix[j][k];
					//System.out.println("evalMatrix =" + evalMatrix[j][k] );
					//System.out.println("After maxPlayerValue =" +maxPlayerValue);
				}
				if(boardIntermedState[j][k].equals(minPlayer))
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
}
