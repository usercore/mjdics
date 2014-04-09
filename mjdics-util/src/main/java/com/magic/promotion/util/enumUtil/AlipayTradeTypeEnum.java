package com.magic.promotion.util.enumUtil;


public enum AlipayTradeTypeEnum implements EnumName{
		AGENT_RECHARGE("商户充值"), USER_RECHARGE("用户充值");
		
		private String name;
		
		AlipayTradeTypeEnum(String name){
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
