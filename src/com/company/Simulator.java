
import java.lang.String; //imported to use String class
import java.io.LineNumberReader; //imported to read a file line by line
import java.io.FileReader; //imported to read a file
import java.util.regex.*; //imported to use split() method
import java.lang.Integer; //imported to do Integer conversions from String

interface BooleanSimulator
    {
        /**
         * This method finds and prints out the fanout of every gate in the simulator
         */
        public void printStatistics();
        
        /**
         * This method assigns the values to corresponding inputs of gates and computes outputs for all the gates, based on these inputs
         */
        public void printOutput();
    }

public class Simulator implements BooleanSimulator
{
    //static class variables
    static String gates[]=new String[101];
    static int isGateOutput[]=new int[101];
    static String primeInputs[];
    static int count;
    
    //constructor
    public Simulator(String str)
    {
        primeInputs=str.split("\\s");//splits line of input into individual prime inputs (Assuming, syntax is followed and inputs are separated by whitespace
    }
    
    //main method
    public static void main(String args[])throws Exception
    {
        //objects creation
        LineNumberReader netLineIndex=new LineNumberReader(new FileReader("./netlist.txt")); //to read netlist file
        LineNumberReader inputLineIndex=new LineNumberReader(new FileReader("./inputlist.txt")); //to read input file
        Simulator obj=new Simulator("");//this class object
        
        String gate="a",ginput="a";
        while((gate=netLineIndex.readLine())!=null)
        {
            count=netLineIndex.getLineNumber()-1; //subtracting one as the indexing starts from 1 while we want it to start from zero
            gates[count]=gate;
        }
        
        //printing required gate statistics
        System.out.println("Total number of gates are: "+(count+1)+"\n");
        obj.printStatistics();
        
        //finding output based on given inputs
        while((ginput=inputLineIndex.readLine())!=null)
        {
            Simulator obj2=new Simulator(ginput);
            obj2.printOutput();
        }
    }
    
    //To print gate statistics
    public void printStatistics()
    {
        for(int gnum=0;gnum<=count;gnum++)
        {
            int fcount=0;
            for(int i=0;i<=count;i++)
            {
                if(i==gnum)continue;
                String result[]=gates[i].split("\\s"); //splits string into non-whitespace tokens
                for(int j=2;j<result.length;j++)
                {
                    if(result[j].startsWith("I")==false)
                    {
                        int input=Integer.parseInt(result[j].trim());
                        if(input==gnum)fcount++;
                    }
                }
            }
            System.out.println("Fanout of gate "+gnum+" is "+fcount); //Prints fanout of gate
        }
    }
    
    //to compute outputs for all gates, given particular input
    
    public void printOutput()
    {
        for(int i=0;i<=count;i++)
        {
            String result[]=gates[i].split("\\s");
            String gtype=result[1].toUpperCase();
            boolean inputs[]=new boolean[2];
            int inum=0;
            boolean boutput=false;
            
            //loop to assign input values
            for(int j=2,k=0;j<=3;j++,k++)
            {
                int temp=0;
                if(result[j].startsWith("I"))
                {
                    temp=Integer.parseInt(primeInputs[Integer.parseInt(((result[j].substring(1,result[j].length())).trim()))]);
                }
                else temp=isGateOutput[Integer.parseInt((result[j]).trim())];
                if(temp==0)inputs[k]=false;
                else inputs[k]=true;
                if(gtype.equals("NOT"))
                {break;}
            }
            
            //Checking with different gatetypes and doing corresponding operations
            if(gtype.equals("NOT"))
            {
                boutput=!(inputs[inum++]);
            }
            else if(gtype.equals("AND"))
            {
                boutput=(inputs[inum++]&&inputs[inum]);
            }
            else if(gtype.equals("OR"))
            {
                boutput=(inputs[inum++]||inputs[inum]);
            }
            else if(gtype.equals("NAND"))
            {
                boutput=!(inputs[inum++]&&inputs[inum]);
            }
            else if(gtype.equals("NOR"))
            {
                boutput=!(inputs[inum++]||inputs[inum]);
            }
            else if(gtype.equals("EXOR"))
            {
                if(inputs[inum]==inputs[++inum]) boutput=false;
                else boutput=true;
            }
            isGateOutput[i]=((boutput==true)?1:0);
            System.out.print(isGateOutput[i]+" ");
        }
        System.out.println("");
    }
}