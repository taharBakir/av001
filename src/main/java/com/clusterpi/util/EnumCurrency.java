package com.clusterpi.util;

public enum EnumCurrency{
  EUR("EUR","euro","€","&euro;");
  private String code;
  private String name;
  private String sign;
  private String htmlCode;

  private EnumCurrency(String code, String name, String sign, String htmlCode){
    this.code = code ;
    this.name = name ;
    this.sign = sign ;
    this.htmlCode = htmlCode;
  }

  public getCode (){
    return this.code;
  }

  public getName (){
    return this.name;
  }

  public getSign (){
    return this.sign;
  }

  public getHtmlCode (){
    return this.htmlCode;
  }
}
