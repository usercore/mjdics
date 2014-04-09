package com.magic.promotion.util.enumUtil;


public enum CardTypeStatusEnum implements EnumName{
	    Active("有效"),Invalid("失效");
		
		private String name;
		
		CardTypeStatusEnum(String name){
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
