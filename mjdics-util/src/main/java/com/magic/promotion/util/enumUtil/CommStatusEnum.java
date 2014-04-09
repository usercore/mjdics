package com.magic.promotion.util.enumUtil;


public enum CommStatusEnum implements EnumName{
	    NOT_ADD_COMM("未加提成"),ADD_COMM("已加提成");
		
		private String name;
		
		CommStatusEnum(String name){
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
