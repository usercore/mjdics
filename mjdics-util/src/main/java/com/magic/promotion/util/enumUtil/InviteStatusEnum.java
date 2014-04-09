package com.magic.promotion.util.enumUtil;


public enum InviteStatusEnum implements EnumName{
		NOT_INVITE("未邀请"), INVITE("已邀请");
		
		private String name;
		
		InviteStatusEnum(String name){
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
