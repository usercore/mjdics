package com.magic.promotion.util.enumUtil;


public enum SexEnum implements EnumName{
		WOMAN("女"), MAN("男");
		
		private String name;
		
		SexEnum(String name){
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
