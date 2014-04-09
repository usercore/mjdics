package com.magic.promotion.util.enumUtil;



public enum CardTypeEnum implements EnumName{
	    Charge("收费"), Free("免费");
		
		private String name;
		
	 	CardTypeEnum(String name){
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
