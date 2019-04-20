package CompressTechnique;
public class LZ78Compress {
	public static void main(String[] args)  {
		try {
			LZ78Implement object=new LZ78Implement();
			object.CompressFile();
			object.Decompress();
		} catch(Exception e){
			System.out.println(e);
		}	
	}
}
