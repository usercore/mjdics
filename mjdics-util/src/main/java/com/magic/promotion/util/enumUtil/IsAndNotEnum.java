package com.magic.promotion.util.enumUtil;



public enum IsAndNotEnum implements EnumName{
		NOT("否"), IS("是");
		
		private String name;
		
	 	IsAndNotEnum(String name){
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
