
import java.lang.String;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.util.regex.*;
import java.lang.Integer;

public class Simulator
{
    static String gates[]=new String[101];
    static int isGateOutput[]=new int[101];
    static String primeInputs[];
    static int count;
    
    public Simulator(String str)
    {
        primeInputs=str.split("\\s");
    }
    
    public static void main(String args[])throws Exception
    {
        LineNumberReader netLineIndex=new LineNumberReader(new FileReader("./netlist.txt"));
        LineNumberReader inputLineIndex=new LineNumberReader(new FileReader("./inputlist.txt"));
        Simulator obj=new Simulator("");
        
        String gate="a",ginput="a";
        while((gate=netLineIndex.readLine())!=null)
        {
            count=netLineIndex.getLineNumber()-1;
            gates[count]=gate;
        }
        System.out.println("Total number of gates are: "+(count+1)+"\n");
        obj.printStatistics();
        
        while((ginput=inputLineIndex.readLine())!=null)
        {
            Simulator obj2=new Simulator(ginput);
            obj2.printOutput();
        }
        
    }
    
    String gatetype[]=new String[count+1];
    public static void printStatistics()
    {
        for(int gnum=0;gnum<=count;gnum++)
        {
            int fcount=0;
            for(int i=0;i<=count;i++)
            {
                if(i==gnum)continue;
                String result[]=gates[i].split("\\s");
                for(int j=2;j<result.length;j++)
                {
                    if(result[j].startsWith("I")==false)
                    {
                        int input=Integer.parseInt(result[j]);
                        if(input==gnum)fcount++;
                    }
                }
            }
            System.out.println("Fanout of gate "+gnum+" is "+fcount);
        }
    }
    
    public static void printOutput()
    {
        for(int i=0;i<=count;i++)
        {
            String result[]=gates[i].split("\\s");
            String gtype=result[1].toUpperCase();
            boolean inputs[]=new boolean[2];
            int inum=0;
            boolean boutput=false;
            for(int j=2,k=0;j<=3;j++,k++)
            {
                int temp=0;
                if(result[j].startsWith("I"))
                {
                    temp=Integer.parseInt(primeInputs[Integer.parseInt((result[j].substring(1,result[j].length())))]);
                }
                else temp=isGateOutput[Integer.parseInt(result[j])];
                if(temp==0)inputs[k]=false;
                else inputs[k]=true;
                if(gtype.equals("NOT"))
                {break;}
            }
            
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