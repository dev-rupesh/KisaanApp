package rsoni.Utils;

public class DataResult {
	
	public boolean Status = false;
	public Object Data = "";
	public String msg = "";
	public int mode = 0;
	public Object extras = null;
	public Object extras2 = null;

	public DataResult(){}

	public DataResult( boolean Status,String msg,Object Data) {
		this.Status = Status;
		this.msg = msg;
		this.Data = Data;
	}
}
