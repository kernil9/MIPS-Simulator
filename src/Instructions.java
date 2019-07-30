
import java.util.TreeMap;


public class Instructions 
{
	Decoder decoder = new Decoder();
	private boolean ans;
	private TreeMap<Integer,Integer> map = new TreeMap<Integer,Integer>();
	public Instructions()
	{
		for(int i = 0; i < 32; i++)
		{
			map.put(i,0);
		}
		this.setAns(false);
	}
	
	public String doInstruction(String memory, String instruction)
	{
		Decoder decoder = new Decoder();
		decoder.breakDown(instruction);
		if (decoder.getOp().equals( "000000") && decoder.getFunct().equals("100000"))
		{
			add(decoder.getRd(), decoder.getRs(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals( "001000"))
		{
			addi(decoder.getRs(), decoder.getRt(), decoder.getImmediate());
			return null;
		}
		else if (decoder.getOp().equals ("000000") && decoder.getFunct().equals("100100"))
		{
			and(decoder.getRd(), decoder.getRs(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("100111"))
		{
			nor(decoder.getRd(), decoder.getRs(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("101010"))
		{
			slt(decoder.getRd(), decoder.getRs(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("101011"))
		{
			slt(decoder.getRd(), decoder.getRs(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("000000"))
		{
			sll(decoder.getRd(), decoder.getShamt(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("000010"))
		{
			srl(decoder.getRd(), decoder.getShamt(), decoder.getRt());
			return null;
		}
		else if (decoder.getOp().equals("000100"))
		{
			return beq(decoder.getRs(), decoder.getRt(), memory, decoder.getImmediate());
		}
		else if (decoder.getOp().equals("000101"))
		{
			return bne(decoder.getRs(), decoder.getRt(), memory, decoder.getImmediate());
		}
		else 
		{
			return "Illegal";
		}
	}
	
	public void add(String rd, String rs , String rt)
	{
		
		int rdInt = Integer.parseInt(rd, 2);
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rsInt) + getRegister(rtInt);
		setRegister(rdInt, ans);
	}
	
	public void addi(String rs, String rt, String immediate)
	{
		int immediateInt = Integer.parseInt(immediate,2);
		int rsInt = Integer.parseInt(rs,2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rsInt) + immediateInt;
		setRegister(rtInt, ans);
	}
	
	public void srl (String rd, String shamt, String rt)
	{
		int rdInt = Integer.parseInt(rd, 2);
		int shamtInt = Integer.parseInt(shamt, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rtInt) >>> shamtInt;
		setRegister(rdInt,ans);
	}
	
	public void sll (String rd, String shamt, String rt)
	{
		int rdInt = Integer.parseInt(rd, 2);
		int shamtInt = Integer.parseInt(shamt, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rtInt) << shamtInt;
		setRegister(rdInt,ans);
	}
	
	public void slt (String rd, String rt, String rs)
	{
		int rdInt = Integer.parseInt(rd, 2);
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rsInt) < getRegister(rtInt) ? 1 :0;
		setRegister(rdInt,ans);
	}
	
	public void sltu (String rd, String rt, String rs)
	{
		int rdInt = Integer.parseInt(rd, 2);
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rsInt) < getRegister(rtInt) ? 1 :0;
		setRegister(rdInt,ans);
	}
	
	public void and (String rd, String rt, String rs)
	{
		int rdInt = Integer.parseInt(rd, 2);
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = getRegister(rsInt) & getRegister(rtInt);
		setRegister(rdInt,ans);
	}
	
	public void nor (String rd, String rt, String rs)
	{
		int rdInt = Integer.parseInt(rd, 2);
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int ans = ~(getRegister(rsInt) | getRegister(rtInt));
		setRegister(rdInt,ans);
	}
	
	public String beq (String rs, String rt, String pc, String immediate)
	{
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int immediate32 = Integer.parseInt(immediate, 2);
		immediate32 = (short)immediate32;
		short immAddressFour = (short) (immediate32 * 4);
		if (getRegister(rsInt) == getRegister(rtInt))
		{
			int x = Integer.parseInt(pc, 2) + immAddressFour;
			String x2 = Integer.toHexString(x);
			while (x2.length() < 8)
			{
				x2 = "0" + x2;
			}
			setAns(true);
			return Decoder.hexToBin(x2);
		}
		else
		{
			setAns(false);
			return null;
		}
	}
	
	public String bne (String rs, String rt, String pc, String immediate)
	{
		int rsInt = Integer.parseInt(rs, 2);
		int rtInt = Integer.parseInt(rt, 2);
		int immediate32 = Integer.parseInt(immediate, 2);
		immediate32 = (short)immediate32;
		short immAddressFour = (short) (immediate32 * 4);
		if (getRegister(rsInt) != getRegister(rtInt))
		{
			int x = Integer.parseInt(pc, 2) + immAddressFour;
			String x2 = Integer.toHexString(x);
			while (x2.length() < 8)
			{
				x2 = "0" + x2;
			}
			setAns(true);
			return Decoder.hexToBin(x2);
		}
		else
		{
			setAns(false);
			return null;
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
		
		for (int i = 0; i < 32; i++)
		{
			String doppel = "[" + i + "]" + "\t " + obtainedMap.get(i) + "\t";
			if ((i % 4 == 0) && (i > 3))
			{
				sb.append("\n");
			}
			sb.append(doppel);
		}
		
		return sb.toString();
				
		
	}
	
	public void setAns(boolean x)
	{
		this.ans = x;
	}
	
	public boolean getAns()
	{
		return this.ans;
	}
}
