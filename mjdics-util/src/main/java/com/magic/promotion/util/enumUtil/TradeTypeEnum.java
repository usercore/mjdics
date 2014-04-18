package com.magic.promotion.util.enumUtil;



public enum TradeTypeEnum implements EnumName{
	    INCOME("收入"), OUTCOME("支出");
		
		private String name;
		
	 	TradeTypeEnum(String name){
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
