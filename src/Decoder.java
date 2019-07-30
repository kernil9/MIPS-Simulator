import java.util.*;


public class Decoder 
{	
	private Map<String , String> addressInstruction = new TreeMap<String, String>();
	private String pc;
	private String op;
	private String rs;
	private String rt;
	private String rd;
	private String shamt;
	private String funct;
	private String immediate;
	final int SIX = 6;
	final int ZERO = 0;
	final int ELEVEN = 11;
	final int SIXTEEN = 16;
	final int TWENTYONE = 21;
	final int TWENTYSIX = 26;
	
	public Decoder()
	{
		
	}
	public String getInstruction(String key) 
	{
		return addressInstruction.get(key);
	}
	
	public static String hexToBin(String hex)
	{
	    String bin = "";
	    String binFragment = "";
	    int iHex;
	    hex = hex.trim();
	    hex = hex.replaceFirst("0x", "");

	    for(int i = 0; i < hex.length(); i++){
	        iHex = Integer.parseInt(""+hex.charAt(i),16);
	        binFragment = Integer.toBinaryString(iHex);

	        while(binFragment.length() < 4){
	            binFragment = "0" + binFragment;
	        }
	        bin += binFragment;
	    }
	    return bin;
	}
	
	public void breakDown(String binary)
	{
		if (binary != null)
		{
			String opx = binary.substring(ZERO, SIX);
			this.setOp(opx);
			String rs = binary.substring(SIX, ELEVEN);
			this.setRs(rs);
			String rt = binary.substring (ELEVEN, SIXTEEN);
			this.setRt(rt);
			String rd = binary.substring(SIXTEEN,TWENTYONE);
			this.setRd(rd);
			String shamt = binary.substring(TWENTYONE, TWENTYSIX);
			this.setShamt(shamt);
			String funct = binary.substring(TWENTYSIX);
			this.setFunct(funct);
			String immediate = binary.substring(SIXTEEN);
			this.setImmediate(immediate);
		}
	}
	
	public static String binaryToHex(String bin) {
	    String hex = Long.toHexString(Long.parseLong(bin, 2));
	    return String.format("%" + (int)(Math.ceil(bin.length() / 4.0)) + "s", hex).replace(' ', '0');
	}
	
	public void setImmediate(String x)
	{
		this.immediate = x;
	}
	
	public String getImmediate()
	{
		return this.immediate;
	}

	/**
	 * @return the op
	 */
	public String getOp() {
		return op;
	}

	/**
	 * @return the rs
	 */
	public String getRs() {
		return rs;
	}

	/**
	 * @return the rt
	 */
	public String getRt() {
		return rt;
	}

	/**
	 * @return the rd
	 */
	public String getRd() {
		return rd;
	}

	/**
	 * @return the shamt
	 */
	public String getShamt() {
		return shamt;
	}

	/**
	 * @return the funct
	 */
	public String getFunct() {
		return funct;
	}

	/**
	 * @param op the op to set
	 */
	public void setOp(String op) {
		this.op = op;
	}

	/**
	 * @param rs the rs to set
	 */
	public void setRs(String rs) {
		this.rs = rs;
	}

	/**
	 * @param rt the rt to set
	 */
	public void setRt(String rt) {
		this.rt = rt;
	}

	/**
	 * @param rd the rd to set
	 */
	public void setRd(String rd) {
		this.rd = rd;
	}

	/**
	 * @param shamt the shamt to set
	 */
	public void setShamt(String shamt) {
		this.shamt = shamt;
	}

	/**
	 * @param funct the funct to set
	 */
	public void setFunct(String funct) {
		this.funct = funct;
	}
	
	public void changePC(String address, String immediate)
	{
		int address2 = Integer.parseInt(immediate,2) + 4 + (Integer.parseInt(address,2) * 4);
		this.setPC(Integer.toBinaryString(address2));
	}

	public void setPC(String address) {
		this.pc = address;
	}

	public String getPC() {
		return this.pc;
	}
}
