package com.magic.promotion.util.enumUtil;


import java.util.Map;

public class EnumFactory {
	
	private Map<String,String> maps;

	public void setMaps(Map<String, String> maps) {
		this.maps = maps;
	}
	
	public String getEnumClassName(String shortName){
		return maps.get(shortName);
	}

}
