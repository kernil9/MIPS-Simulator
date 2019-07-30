import java.util.*;


public class Alu 
{
	private Instructions instructions;
	public Alu()
	{
		Instructions instructions = new Instructions();
		this.instructions = instructions;
	}
	public void run(String memory, String instruction)
	{
		Decoder decoder = new Decoder();
		decoder.breakDown(instruction);
		if (decoder.getOp().equals( "000000") && decoder.getFunct().equals("100000"))
		{
			instructions.add(decoder.getRd(), decoder.getRs(), decoder.getRt());
		}
		else if (decoder.getOp().equals( "001000"))
		{
			instructions.addi(decoder.getRs(), decoder.getRt(), decoder.getImmediate());
		}
		else if (decoder.getOp().equals ("000000") && decoder.getFunct().equals("100100"))
		{
			instructions.and(decoder.getRd(), decoder.getRs(), decoder.getRt());
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("100111"))
		{
			instructions.nor(decoder.getRd(), decoder.getRs(), decoder.getRt());
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("101010"))
		{
			instructions.slt(decoder.getRd(), decoder.getRs(), decoder.getRt());
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("101011"))
		{
			instructions.slt(decoder.getRd(), decoder.getRs(), decoder.getRt());
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("000000"))
		{
			instructions.sll(decoder.getRd(), decoder.getShamt(), decoder.getRt());
		}
		else if (decoder.getOp().equals("000000") && decoder.getFunct().equals("000010"))
		{
			instructions.srl(decoder.getRd(), decoder.getShamt(), decoder.getRt());
		}
	}
	public String toString()
	{

		StringBuffer sb = new StringBuffer();
		TreeMap<Integer, Integer> obtainedMap = instructions.getMap();
		
		for (int i = 0; i < 32; i++)
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
	
	public Map getMap()
	{
		return instructions.getMap();
	}
	
	public void setRegister(int key, int value)
	{
		instructions.setRegister(key,value);
	}
}
