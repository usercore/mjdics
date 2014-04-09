package com.magic.promotion.util.enumUtil;


public enum ActiveEnum implements EnumName{
		NOT_ACTIVE("未激活"), ACTIVE("已激活");
		
		private String name;
		
		ActiveEnum(String name){
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
