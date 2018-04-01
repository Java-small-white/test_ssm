package com.hjn.tmall.pojo;

public class User {
    private Integer id;

    private String name;

    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
    
    //匿名显示name
    public String getAnonymousName() {
    	if(name==null)
    		return null;
    	else if(name.length()<=1)
    		return "*";
    	else if(name.length()==2)
    		return name.substring(0, 1)+"*";
    	else {//保留name的第一个、最后一个字符。其余显示*
    		char cs[]=name.toCharArray();
    		for(int i=1;i<cs.length-1;i++) {
    			cs[i]='*';
    		}
    		return new String(cs);
    	}
    }
}