package com.magic.promotion.util.enumUtil;


public enum CardEnum implements EnumName{
		Init("初始化"),AppCard("申请制卡"),EntityCard("制作实体卡"),fulCard("完成制作实体卡"),getCard("领卡"),Sell("销售"),Used("已使用"),Not_Pay("未付款");
        
		private String name;
		
		CardEnum(String name){
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
