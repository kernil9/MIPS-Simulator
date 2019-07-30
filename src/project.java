

import java.io.*;
import java.util.*;


public class project //project
{
	
	private String instruction;
	private String opCode;
	private String rs;
	private String rt;
	private String rd;
	private String shamt;
	private String funct;
	private String immediate;
	
	
	private static final int FOUR = 4;
	private static final int EIGHT = 8;
	
	//for separating instruction bits
	
	private static final int ZERO = 0;
	private static final int SIX = 6;
	private static final int SEVEN = 7;
	private static final int ELEVEN = 11;
	private static final int SIXTEEN = 16;
	private static final int TWENTY_ONE = 21;
	private static final int TWENTY_SIX = 26;
	private static final int THIRTY_TWO = 32;
	
	//instructions
	
	private static final String addFunct = "100000";
	private static final String addOp = "000000";
	
	private static final String addIOp = "001000";
	
	private static final String sllOp = "000000";
	private static final String sllFunct = "000000";
	
	private static final String srlOp = "000000";
	private static final String srlFunct = "000010";
	
	private static final String andOp = "000000";
	private static final String andFunct = "100100";
	
	private static final String sltOp = "000000";
	private static final String sltFunct = "101010";
	
	private static final String sltUOp = "000000";
	private static final String sltUFunct = "101011";
	
	private static final String beqOp = "000100";
	
	private static final String bneOp = "000101";
	
	private static final String norOp = "000000";
	private static final String norFunct = "100111";
	
	//Use object for storing instructions so that information can be easily extracted
	public project(String instruction)
	{
		String toBinary = project.hexToBinary(instruction);
		
		this.setImmediate(instruction.substring(FOUR));
		
		this.setOpCode(toBinary.substring(ZERO, SIX));
		this.setRs(toBinary.substring(SIX, ELEVEN));
		this.setRt(toBinary.substring(ELEVEN, SIXTEEN));
		this.setRd(toBinary.substring(SIXTEEN, TWENTY_ONE));
		this.setShamt(toBinary.substring(TWENTY_ONE, TWENTY_SIX));
		this.setFunct(toBinary.substring(TWENTY_SIX, THIRTY_TWO));
	}
	

	public static String hexToBinary(String hex)
	{
		
		if (hex.contains("0x"))
		{
			hex = hex.replace("0x", "");
		}
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < hex.length(); i = i + 1)
		{
			int nummer = Character.getNumericValue(hex.charAt(i));
			String toBin = Integer.toBinaryString(nummer);
			while (toBin.length() < 4)
			{
				toBin = "0" + toBin;
			}
			sb.append(toBin);
		}
		
		String insLtZero = sb.toString();
		
		while (insLtZero.length() < THIRTY_TWO)
		{
			insLtZero = "0" + insLtZero;
		}
		
		return insLtZero;
			
	}

	
	public String getOpCode()
	{
		return this.opCode;
	}
	
	public void setOpCode(String opCode)
	{
		this.opCode = opCode;
	}
	
	public String getRs()
	{
		return this.rs;
	}
	
	public void setRs(String rs)
	{
		this.rs = rs;
	}
	
	public String getRt()
	{
		return this.rt;
	}
	
	public void setRt(String rt)
	{
		this.rt = rt;
	}
	
	
	public String getRd()
	{
		return this.rd;
	}
	
	public void setRd(String rd)
	{
		this.rd = rd;
	}
	
	public String getShamt()
	{
		return this.shamt;
	}
	
	public void setShamt(String shamt)
	{
		this.shamt = shamt;
	}
	
	public String getFunct()
	{
		return this.funct;
	}
	
	public void setFunct(String funct)
	{
		this.funct = funct;
	}
	
	public String getImmediate()
	{
		return this.immediate;
	}
	
	public void setImmediate(String immediate)
	{
		this.immediate = immediate;
	}
	
	@Override
	public String toString()
	{
		return this.getOpCode() + this.getRs() + this.getRt() + this.getRd() + this.getShamt()
				+ this.getFunct();
	}
	
	
	
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		PrintStream output = new PrintStream(System.out);
		
		//Link memory addresses to instructions
		HashMap<String, project> holdMap = new HashMap<String, project>();
		
		//A list for instructions
		LinkedList<project> lt = new LinkedList<project>();
		
		//A list for memory addresses
		LinkedList<String> holdMemory = new LinkedList<String>();
		
		//Store registers
		TreeMap<Integer, Integer> registerMap = new TreeMap<Integer, Integer>();
		for (int i = 0; i < THIRTY_TWO; i = i + 1)
		{
			registerMap.put(i, 0);
		}
		
		//Request file
		try 
		{
			Scanner fileInput = new Scanner(new File (input.nextLine()));
			
			while (fileInput.hasNext())
			{
				String memAddress = fileInput.next();
				String instruction = fileInput.next();
				
				project anInstruction = new project(instruction);
				//output.println(anInstruction.getOpCode());
				
				holdMemory.add(memAddress);
				holdMap.put(memAddress, anInstruction);
				lt.add(anInstruction);	
			}
			
			boolean pastEqual = false;
			
			int desReg = 0;
			int rsReg = 0;
			int rtReg = 0;
			
			short imm = 0;
			String immediate = "";
			String destination = "";
			
			int outcome = 0;
			
			//Execute instructions
			for (int j = 0; j < lt.size(); j = j + 1)
			{
				
				if (pastEqual == true)
				{
					j = holdMemory.indexOf(destination);
					//output.println("Equal was activated and value is " + j);
				}
				
				pastEqual = false;
				String s = holdMemory.get(j);
				project executedInstruction = holdMap.get(s);
				
				//output.println(executedInstruction.getImmediate());
				
				//Get components
				String theOpCode = executedInstruction.getOpCode();
				String theRs = executedInstruction.getRs();
				String theRt = executedInstruction.getRt();
				String theRd = executedInstruction.getRd();
				String theShamt = executedInstruction.getShamt();
				String theFunct = executedInstruction.getFunct();
				
				
				
				//How to obtain content of register?
				int conDesReg = 0;
				int conRsReg = 0;
				int conRtReg = 0;
				
				//add
				if (theOpCode.equals(addOp) && theFunct.equals(addFunct))
				{
					//Required registers
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);					
					outcome = conRsReg + conRtReg;
					
					registerMap.put(desReg, outcome);
				}
				
				//addi
				else if (theOpCode.equals(addIOp))
				{
					immediate = executedInstruction.getImmediate();
					
					desReg = Integer.parseInt(theRt, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					imm = Short.parseShort(immediate, 16);
					
					
					
					outcome = conRsReg + imm;
					
					registerMap.put(desReg, outcome);
				}
				
				// have to differentiate between slt and sltu
				//slt 
				else if (theOpCode.equals(sltOp) && (theFunct.equals(sltFunct)))
				{
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					short conRsRegShort = (short) conRsReg;
					
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);	
					short conRtRegShort = (short) conRtReg;
					
					if (conRsRegShort < conRtRegShort)
					{
						outcome = 1;
					}
					else
					{
						outcome = 0;
					}
					
					registerMap.put(desReg, outcome);
				}
				
				//sltu
				else if (theOpCode.equals(sltUOp) && theFunct.equals(sltUFunct))
				{
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					long conRsRegLong = (long) conRsReg;
					
					
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);	
					long conRtRegLong = (long) conRtReg;
					
					if (conRsRegLong < conRtRegLong)
					{
						outcome = 1;
					}
					else
					{
						outcome = 0;
					}
					
					registerMap.put(desReg, outcome);
				}
				
				//srl
				else if (theOpCode.equals(srlOp) && theFunct.equals(srlFunct))
				{
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);	
					
					outcome = conRsReg >>> conRtReg;
			
					registerMap.put(desReg, outcome);
				}
				
				//sll
				else if (theOpCode.equals(sllOp) && theFunct.equals(sllFunct))
				{
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);	
					
					outcome = conRsReg << conRtReg;
					
					registerMap.put(desReg, outcome);
				}
				
				//and
				else if (theOpCode.equals(andOp) && theFunct.equals(andFunct))
				{
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					short conRsRegShort = (short) conRsReg;
					
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);	
					short conRtRegShort = (short) conRtReg;
					
					outcome = conRsRegShort & conRtRegShort;
					
					registerMap.put(desReg, outcome);
				}
				
				
				
				//nor 
				else if (theOpCode.equals(norOp) && theFunct.equals(norFunct))
				{
					desReg = Integer.parseInt(theRd, 2);
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);	
					
					outcome = ~(conRsReg | conRtReg);
					
					registerMap.put(desReg, outcome);
				}
				
				
				//beq
				//Must somehow get program to recall position
				//If it does indeed equal, jump
				
				else if (theOpCode.equals(beqOp))
				{
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);
					
					immediate = executedInstruction.getImmediate();
					
					int toInt = Integer.parseInt(immediate, 16);
					imm = (short) toInt;
					short immAddressFour = (short) (imm * 4);
					
					
					if (conRsReg == conRtReg)
					{
						int whereToGo = Integer.parseInt(s, 16) + immAddressFour;
						output.println(whereToGo);
						destination = Integer.toHexString(whereToGo);
						
						while (destination.length() < 8)
						{
							destination = "0" + destination;
						}
						
						pastEqual = true;
					}
					else
					{
						pastEqual = false;
					}
				}
				
				//bne
				
				else if (theOpCode.equals(bneOp))
				{
					rsReg = Integer.parseInt(theRs, 2);
					conRsReg = registerMap.get(rsReg);
					rtReg = Integer.parseInt(theRt, 2);
					conRtReg = registerMap.get(rtReg);
					
					immediate = executedInstruction.getImmediate();
					
					//Convert to hex and then to int and then to short
					
					int toInt = Integer.parseInt(immediate, 16);
					imm = (short) toInt;
					short immAddressFour = (short) (imm * 4);
					
					if (conRsReg != conRtReg)
					{
						int whereToGo = Integer.parseInt(s, 16) + immAddressFour;
						destination = Integer.toHexString(whereToGo);
						
						while (destination.length() < 8)
						{
							destination = "0" + destination;
						}
						
						pastEqual = true;
					}
					else
					{
						pastEqual = false;
					}
					
				}
				
				else
				{
					output.println("Illegal instruction");
					System.exit(0);
				}
			}
			
			
			StringBuffer outRegs = new StringBuffer();
			
			for (int k = 0; k < registerMap.size(); k = k + 1)
			{
				if ((k % 4 == 0) && (k > 3))
				{
					outRegs.append("\n");
				}
				String line = "[" + k + "]" + registerMap.get(k) + " ";
				outRegs.append(line);
			}
			
			output.println(outRegs.toString());
			
		}
		
		catch (FileNotFoundException e)
		{
			output.println("The file cannot be found.");
		}
		
		catch (NoSuchElementException e)
		{
			output.println("Illegal Instruction");
		}
		
	}

}
