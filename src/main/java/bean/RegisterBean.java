package bean;

import java.io.Serializable;

public class RegisterBean implements Serializable{
  private String name;
  private String age;
  private String[] langs;
  private String money;
  private int number1, number2;

  public RegisterBean(){
	  
  }
  
  public void setName(String name){
    this.name = name;
  }
  public void setAge(String age){
    this.age = age;
  }
  public void setLangs(String[] langs){
    this.langs = langs;
  }
  public void setMoney(String money) {
	  this.money = money;
  }
  public void setNumber1(int number1) {
	  this.number1 = number1;
  }
  public void setNumber2(int number2) {
	  this.number2 = number2;
  }
  public String getName(){
    return name;
  }
  public String getAge(){
    return age;
  }
  public String[] getLangs(){
    return langs;
  }
  public String getMoney() {
	  return money;
  }
  public int getNumberTotal() {
	  return number1 + number2;
  }
  public String getJpnAge(){
    String jpnAge;
    if(age.equals("child")){
      jpnAge = "18歳以下";
    } else {
      jpnAge = "18歳以上";
    }
    return jpnAge;
  }
  public String getStrLangs(){
    String strLangs = "";
    for(int i = 0; i < langs.length; i++){
      strLangs = strLangs + langs[i] + " ";
    }
    return strLangs;
  }
}

