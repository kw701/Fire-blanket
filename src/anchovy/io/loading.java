package anchovy.io;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;


public class loading

{
	String path;
	public loading(String filepath)
	{
	path= filepath;
	}
	public String[] readfile()throws IOException
	{
		FileReader fr=new FileReader(path);
		BufferedReader br=new BufferedReader(fr);
		int numberOfLines = 3;
		String[ ] textData = new String[numberOfLines];
		int i;

		for (i=0; i < numberOfLines; i++) 
		{
		textData[ i ] = br.readLine();
		System.out.println(textData[i]);
		}
		return textData;
	}
}