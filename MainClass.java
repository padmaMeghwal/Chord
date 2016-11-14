import java.io.*;
import java.net.*;
import java.util.*;
public class MainClass
{
 
  
  public static void main(String[] args) throws IOException 
  {
    int port = 1987;
    int i=0;
    //ArrayList<Node> al = new ArrayList<>();
    StuffThread s = new StuffThread();
    Random rand = new Random();
    for (int k=0;k<50 ;k++ ) 
    {
        s.keys[k] = k+5;
    }

    ServerSocket server = new ServerSocket(port);
    while (true) 
    {
      Socket socket = server.accept();
    
      //Node tmp = new Node(i,socket.getInetAddress().toString(),socket.getPort());
      //al.add(tmp); 
     
      
      Thread stuffer = new StuffThread(socket,i);
        i++;
      stuffer.start();
    }

  }

}

class StuffThread extends Thread {

  static HashMap<Integer,String> hm = new HashMap<>();
  static int[]suc = new int[3];
  static int[]keys = new int[50];
  //static ArrayList[] al = new ArrayList[8];
  static String[] ar = new String[8];
  static int count;
  PrintWriter out;
  private Socket socket;
  int id;
  public StuffThread(){};
  public StuffThread(Socket socket,int id) 
  {
    count++;
    try
    {
        BufferedReader in1 = new BufferedReader( new InputStreamReader(socket.getInputStream()));
        String ipAddr = in1.readLine(); //ip + idt;
        String[]array = ipAddr.split("#");
        int n = Integer.parseInt(array[1]); //idt
        hm.put(Integer.parseInt(array[1]),array[0]);
        if (count==1) 
        {
            for (int i=0;i<8 ;i++ ) 
            {
                ar[i]= "";  
            }
            for (int i=0;i<50 ;i++ ) 
            {
                ar[n] += keys[i];
                ar[n] += " ";
                //al[n].add(keys[i]);
            }
        }
        else
        {
            
            for (int i=0;i<50 ;i++ ) 
            {
                if (keys[i]%8==n) 
                {
                    ar[n] += keys[i];
                    ar[n] += " ";
                }  
            }
        }
    }
    catch(Exception e)
    {

    }

    this.id = id; 
    this.socket = socket;
  }

  public void run() 
  {
    try 
    {
        out = new PrintWriter(socket.getOutputStream(),true);
        BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
    
      while (!socket.isClosed()) 
      {
            String inputLine;
            
            //String ipAddr = in.readLine();
            //System.out.println(ipAddr);
            while((inputLine = in.readLine())!= null)
            {
             
                String[]a = new String[2];
                a = inputLine.split("#");
                
                int check = Integer.parseInt(a[0]);
                int idt = Integer.parseInt(a[1]);
                if (check==2) 
                {
                  
                    immediateNeighbour(idt);  
                }
                if (check==3) 
                {
                    keys_Files(idt);
                }
                if (check==4) 
                {
                    /*for (int i=0;i<8 ;i++ ) 
                    {
                        System.out.print(hm.get(i)+" ");  
                    }*/
                    //System.out.println();
                    for (int i=0;i<3 ;i++ ) 
                    {
                        //System.out.print(a[i+2]+" ");
                        suc[i]=find_suc(Integer.parseInt(a[i+2]));
                        //System.out.print(suc[i]+" ");
                    }
                    out.println(suc[0]+"#"+suc[1]+"#"+suc[2]);
                }
                if (check==5) 
                {
                    hm.put(idt,null);
                    ar[idt] = "";  
                }
            }
      }
      socket.close();
    } 
    catch (Exception e) {
    }
  }
  public int find_suc(int n)
  {

      int tmp=0;
      boolean flag = false;
      for (int i=n;i<8 ;i++ ) 
      {
          if (hm.get(i)!=null) 
          {
              tmp =i; 
              flag = true;    
              break;   
          }  
      }
      if (flag == false) 
      {
          for (int i=0;i<n ;i++ ) 
          {
              if (hm.get(i)!=null) 
              {
                  tmp = i;  
                  break;
              }
          }  
      }
      return tmp;
  }

  public void keys_Files(int idt)
  {
      //System.out.println("Hello");
      //ArrayList<Integer> arrl = new ArrayList<>();
      //arrl = al[idt];
      String l ="";
      l = ar[idt];
      out.println(l);
  }
  public void immediateNeighbour(int idt)
  {
        boolean flag = false;
        for (int i=idt+1;i<8 ;i++ ) 
        {
            if (hm.get(i)!=null) 
            { 
                out.println(i+"#"+hm.get(i));
                flag = true;   
                break;   
            }  
        }
        if (flag==false) 
        {
            for (int i=0;i<idt ;i++ ) 
            {
                if (hm.get(i)!=null) 
                {  
                    out.println(i+"#"+hm.get(i));
                    flag = true;   
                    break;   
                }          
            }  
        }
        if (flag==false) 
        {
            out.println("no"+"#"+"immediateNeighbour");  
        }
  }
  public void searchID(String str)
  {
      int portnumber = Integer.parseInt(str);
      
  }
}