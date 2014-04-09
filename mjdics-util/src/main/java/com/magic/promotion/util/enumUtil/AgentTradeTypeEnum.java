package com.magic.promotion.util.enumUtil;


public enum AgentTradeTypeEnum implements EnumName{
		RECHARGE("充值"), APPLY_CASH("提现"),ADD_COMM("增加佣金"),APPLY_CARD("买卡"),SALE_CARD("售卡"),SEND_MESSAGE("推广短信"),SALE_CARD_COMMISSION("售卡提成");
		
		private String name;
		
		AgentTradeTypeEnum(String name){
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
