package com.magic.promotion.util.enumUtil;



public enum BetStatusEnum implements EnumName{
		NO_PRIZE("暂未出票"), BET_SUCC("出票成功"),BET_FAIL("出票失败"),NO_RESULT("出票结果未知");
		
		private String name;
		
	 	BetStatusEnum(String name){
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
