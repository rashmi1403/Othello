import java.io.*;

public class projectmain 
{
	public static void main (String[] args) throws java.io.IOException
	{
		
		System.out.println("Starting to read Input");
		
		String input=null, maxPlayer=null;
		int taskNo = 0, maxDepth=0;
		String [][] boardCurrState = new String[8][8];
		
		//BufferedReader br = new BufferedReader(new FileReader("C:\\USC\\AI\\HW2\\input.txt"));
		BufferedReader br = new BufferedReader(new FileReader(new File ("input.txt").getAbsolutePath()));
		
		if((input = br.readLine()) != null)
			taskNo = Integer.valueOf(input);
		
		if((input = br.readLine()) != null)
			maxPlayer = input;
		
		if((input = br.readLine()) != null)
			maxDepth = Integer.valueOf(input);
		
		int i=0;
		while ((input = br.readLine()) != null) 
        {
			for(int j=0;j<input.length();j++)
			{
					boardCurrState[i][j] = Character.toString(input.charAt(j));
					//System.out.println(boardCurrState[i][j]);
			}
			i++;
			
        }
		
		br.close();
		
		//Determining minPlayer
		String minPlayer=null;
		if(maxPlayer.equals("X"))
			minPlayer = "O";
		if(maxPlayer.equals("x"))
			minPlayer = "o";
		if(maxPlayer.equals("O"))
			minPlayer = "X";
		if(maxPlayer.equals("o"))
			minPlayer = "x";
		
		
		System.out.println("Input read!");
		System.out.println("Values are as below:");
		System.out.println("taskNo = " +taskNo);
		System.out.println("maxPlayer = " +maxPlayer);
		System.out.println("minPlayer = " +minPlayer);
		System.out.println("maxDepth = " +maxDepth);
		System.out.println("The current state of the board:");

		for(int j=0;j<8;j++)
		{
			for(int k=0;k<8;k++)
				System.out.print(boardCurrState[j][k]);
			System.out.println();
		}
		
		
		
		
		if(taskNo == 1)
		{
			//GreedyMethod Not passing maxDepth as it will ALWAYS be 1
			GreedyTask greedyMethodObj = new GreedyTask(maxPlayer,minPlayer,boardCurrState);
			greedyMethodObj.GreedyMethodImplementation();
		}
		if(taskNo == 2)
		{
			MinMax MinMaxObj = new MinMax(maxPlayer,minPlayer,boardCurrState,maxDepth);
			MinMaxObj.MinMaxImplementation();
			
		}
		if(taskNo == 3)
		{
			AlphaBeta AlphaBetaObj = new AlphaBeta(maxPlayer,minPlayer,boardCurrState,maxDepth);
			AlphaBetaObj.AlphaBetaImplementation();
			
		}
	}
}
