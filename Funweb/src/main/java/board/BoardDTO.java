package board;
/*
 CREATE TABLE board (
	num INT PRIMARY KEY,
	name VARCHAR(16) NOT NULL,
	pass VARCHAR(16) NOT NULL,
	subject VARCHAR(100) NOT NULL,
	content VARCHAR(1000) NOT NULL,
	date DATETIME NOT NULL,	
	readcount INT NOT NULL
  );
 */

import java.sql.Date;

public class BoardDTO {
	private int num;
	private String name;
	private String pass;
	private String subject;
	private String content;
	private Date date;
	private int readcount;
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	
}









