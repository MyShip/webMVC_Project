package bean;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MemoRegister implements Serializable{
  private int Id;
  private String title;
  private String message;
  private LocalDateTime createAt;
  private int userId;

  public MemoRegister(){
	  
  }
  public void setUserId(int userId) {
	  this.userId = userId;
  }
  public void setId(int Id) {
	  this.Id = Id;
  }
  public void setTitle(String title) {
	  this.title = title;
  }
  public void setMessage(String message) {
	  this.message = message;
  }
  public void setCreateAt(LocalDateTime createAt) {
	  this.createAt = createAt;
  }
  public int getId() {
	  return Id;
  }
  public String getTitle() {
	  return title;
  }
  public String getMessage() {
	  return message;
  }
  public int getUserId() {
	  return userId;
  }
  public LocalDateTime getCreateAt() {
	  return createAt;
  }
}

