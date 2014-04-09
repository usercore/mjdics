package com.magic.promotion.util.enumUtil;


public enum MenuTypeEnum implements EnumName{
	    Y("目录"),N("菜单");
		
		private String name;
		
		MenuTypeEnum(String name){
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public String getValue() {
			return ""+ordinal();
		}
}
