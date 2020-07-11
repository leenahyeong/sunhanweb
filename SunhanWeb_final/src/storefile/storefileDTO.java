package storefile;

public class storefileDTO {
	
	String fileName;
	String fileRealName;
	String Userid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	String userid;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileRealName() {
		return fileRealName;
	}
	public void setFileRealName(String fileRealName) {
		this.fileRealName = fileRealName;
	}
	public storefileDTO(String fileName, String fileRealName, String userid, String userid2) {
		super();
		this.fileName = fileName;
		this.fileRealName = fileRealName;
		Userid = userid;
		userid = userid2;
	}
	public storefileDTO(){
		
	}
	
}
