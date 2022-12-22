package member;

import java.sql.Date;
/*
  CREATE TABLE member (
	id VARCHAR(16) PRIMARY KEY,
	pass VARCHAR(16) NOT NULL,
	name VARCHAR(20) NOT NULL,
	date DATETIME NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	address VARCHAR(50) NOT NULL,
	phone VARCHAR(20) NOT NULL,
	mobile VARCHAR(20) NOT NULL
  );
 */
public class MemberDTO {
	private String id;
	private String pass;
	private String name;
	private Date date;
	private String email;
	private String mobile;
	private String address;
	private String phone;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
