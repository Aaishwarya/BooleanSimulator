
import java.lang.String;
import java.io.LineNumberReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.*;
import java.lang.Integer;

public class Simulator
{
    static String gates[]=new String[101];
    static int isgatenumber[]=new int[101];

    public static void main(String args[])throws Exception
    {
        BufferedReader netList=new BufferedReader(new FileReader("./netlist.txt"));
        LineNumberReader lineIndex=new LineNumberReader(netList);
        Simulator obj=new Simulator();

        int count=0,fcount=0;
        String gate="a";
        while((gate=lineIndex.readLine())!=null)
        {
            count=lineIndex.getLineNumber()-1;
            gates[count]=gate;
        }
        int n=count+1;
        System.out.println("Total number of gates are: "+(count+1));
        for(int i=0;i<n;i++)
        {
            System.out.println(gates[i]);
            if(gates[i]!=null)
            {
                fcount=obj.printStatistics(i,n);
            }
            System.out.println("Fanout in gate number: "+i+" is "+fcount);
        }

    }

    public static int printStatistics(int gnum,int maxgnum)
    {
        int count=0;
        for(int i=0;i<maxgnum;i++)
        {
            if(i==gnum)continue;
            String result[]=gates[i].split("\\s");
            for(int j=2;j<result.length;j++)
            {
                if(result[j].startsWith("I")==false)
                {
                    int input=Integer.parseInt(result[j]);
                    if(input==gnum)count++;
                }
            }
        }
        return count;
    }
}