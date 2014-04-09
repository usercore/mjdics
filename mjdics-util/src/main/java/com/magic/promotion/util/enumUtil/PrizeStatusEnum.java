package com.magic.promotion.util.enumUtil;



public enum PrizeStatusEnum implements EnumName{
		UNKNOW_PRIZE("未派奖"), PRIZE("中奖"),NOT_PRIZE("未中奖");
		
		private String name;
		
	 	PrizeStatusEnum(String name){
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
