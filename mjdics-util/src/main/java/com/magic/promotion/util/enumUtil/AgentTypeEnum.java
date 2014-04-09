package com.magic.promotion.util.enumUtil;


public enum AgentTypeEnum implements EnumName{
		FIRST_BUSS("一级经销商"), SEC_BUSS("二级经销商"),SALER("业务员"),ADMIN("管理员");
		
		private String name;
		
		AgentTypeEnum(String name){
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
		
		public static AgentTypeEnum valueOf(int ordinal) {
		        if (ordinal < 0 || ordinal > values().length) {
		            throw new IndexOutOfBoundsException("Invalid ordinal");
		        }else if(ordinal == values().length){
		        	ordinal = 0;
		        }
		        return values()[ordinal];
		 }
}
