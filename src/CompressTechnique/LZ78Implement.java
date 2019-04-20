package CompressTechnique;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class LZ78Implement {
	
    String data;
    String temp;
    String prev;
    ArrayList<String> Dictionary=new ArrayList<String>();
    ArrayList<String> DecompressDictionary=new ArrayList<String>();
    ArrayList<Tag> Tags=new ArrayList<Tag>();
    File WriteToFile;
    PrintWriter Output;
    File ReadFromFile;
    Scanner Input;
    File Decompress;
    Scanner ReadFromCompressed;
    PrintWriter WriteToDecompressed;
    
    public LZ78Implement() {
    	try {
    		//For write to the file 
    		WriteToFile=new File("compressedData.txt");
            Output =new PrintWriter(WriteToFile);
            //For Read From the file 
            ReadFromFile=new File("originalData.txt");
            Input =new Scanner(ReadFromFile);
            //read from compressed file and write to decompressed file and decode data
            ReadFromCompressed=new Scanner(WriteToFile);
            Decompress=new File("decompressedData.txt");
            WriteToDecompressed=new PrintWriter(Decompress);
        }
        catch (FileNotFoundException e) {
        	System.out.println(e);
        }
    }
 // Compression the File Function
	public  void CompressFile() {
		Dictionary.add("---");
		Tag obj;
		while(Input.hasNextLine()) {
			data=Input.nextLine();
			temp=data.substring(0,1);
			
			for(int i=1;i<=data.length();i++) {
				if(temp.equals('.')||temp.equals(',')||temp.equals('_')||temp.equals('-')||temp.equals('/'))
					continue;
				obj=new Tag();
				
				if(!Dictionary.contains(temp)) {
					if(temp.length()==1) {
						Dictionary.add(temp);
						obj.next=0;
						obj.symbol=temp;
						Tags.add(obj);
						Output.println(obj.next+" "+obj.symbol);
						if(i==data.length())
							temp=data.substring(data.length()-1, data.length());
						else
							temp=data.substring(i,i+1);
						continue;
					}
					else if(temp.length()>1) {
						Dictionary.add(temp);
						String exist=temp.substring(0,temp.length()-1);//maybe here is wrong
						obj.symbol=temp.substring(temp.length()-1,temp.length());
						obj.next=Dictionary.indexOf(exist);
						Tags.add(obj);
						Output.println(obj.next+" "+obj.symbol);
						if(i==data.length())
							temp=data.substring(data.length()-1, data.length());
						else
							temp=data.substring(i,i+1);
						continue;
					}
				}
				else {
					if(i==data.length()) {
						prev=temp;
						if(Dictionary.contains(prev)) {
							obj.symbol="null";
							obj.next=Dictionary.indexOf(prev);
							Tags.add(obj);
							Output.println(obj.next+" "+obj.symbol);
						}
						temp+=data.substring(data.length()-1, data.length());
					}
					else
						temp+=data.substring(i,i+1);	
				}
			}
			temp="";
		}
		Output.close();
		Input.close();
	}
	// Decompression the File Function 
	public void Decompress() {
		String OriginalData="";
		DecompressDictionary.add(" ");
		String Decom;
		int Index;
		while(ReadFromCompressed.hasNextLine()) {
			Decom=ReadFromCompressed.nextLine();
			String Array[]=Decom.split(" ", 2);
			Index=Integer.parseInt(Array[0]);
			if(Index==0) {
				DecompressDictionary.add(Array[1]);
				OriginalData+=Array[1];
			}
			else {
				if(Array[1].equals("null")) 
					OriginalData+=DecompressDictionary.get(Index);
				else
				{
					OriginalData+=DecompressDictionary.get(Index)+Array[1];
					DecompressDictionary.add(DecompressDictionary.get(Index)+Array[1]);
				}
			}		
		}
		WriteToDecompressed.println(OriginalData);
		WriteToDecompressed.close();
		ReadFromCompressed.close();
		System.out.println("Data After Decompressed : " + OriginalData);
	}
}