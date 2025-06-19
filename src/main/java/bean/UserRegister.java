package bean;

import java.io.Serializable;

public class UserRegister implements Serializable{
  private String name;
  private String pass;
  private String repass;
  private int id;
  
  public UserRegister(){
	  
  }
  public void setName(String name) {
	  this.name = name;
  }
  public void setPass(String pass) {
	  this.pass = pass;
  }
  public void setRePass(String repass) {
	  this.repass = repass;
  }
  public void setId(int id) {
	  this.id = id;
  }
  public String getName() {
	  return name;
  }
  public String getPass() {
	  return pass;
  }
  public String getRePass() {
	  return repass;
  }
  public int getId() {
	  return id;
  }
}