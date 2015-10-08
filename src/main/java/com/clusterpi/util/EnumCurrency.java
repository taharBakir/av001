package com.clusterpi.util;

public enum EnumCurrency{
  EUR("EUR","euro","€","&euro;"),
  USD("USD","dollar","$","$"),
  POUND("GBP","pound","£","&pound;");

  private final String code;
  private final String name;
  private final String sign;
  private final String htmlCode;

  private EnumCurrency(String code, String name, String sign, String htmlCode){
    this.code = code ;
    this.name = name ;
    this.sign = sign ;
    this.htmlCode = htmlCode;
  }

  public String getCode (){
    return this.code;
  }

  public String getName (){
    return this.name;
  }

  public String getSign (){
    return this.sign;
  }

  public String getHtmlCode (){
    return this.htmlCode;
  }
}
