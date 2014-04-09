package com.magic.promotion.util.enumUtil;


public enum AlipayTradeStatusEnum implements EnumName{
	   INIT("初始化"), SUCCESS("处理成功"),FAIL("处理失败");
		
		private String name;
		
		AlipayTradeStatusEnum(String name){
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
