
import java.util.*;


public class Variables 
{	
	private TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
	public Variables()
	{
		for(int i = 0; i < 32; i++)
		{
			map.put(i,0);
		}
		
	}
	
	public TreeMap<Integer, Integer> getMap()
	{
		return this.map;
	}
	
	public void setMap(TreeMap<Integer, Integer> map)
	{
		this.map = map;
	}
	
	public int getRegister(int key)
	{
		return map.get(key);
	}
	
	public void setRegister(int key, int value)
	{
		map.put(key,value);
	}
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		TreeMap<Integer, Integer> obtainedMap = this.getMap();
		
		for (int i = 0; i < 32; i = i + 1)
		{
			String doppel = "[" + i + "]" + " " + obtainedMap.get(i) + "\t";
			if ((i % 4 == 0) && (i > 3))
			{
				sb.append("\n");
			}
			sb.append(doppel);
		}
		
		return sb.toString();
				
		
	}
}
