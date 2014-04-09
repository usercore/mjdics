package com.magic.promotion.util.enumUtil;



public enum UserStatusEnum implements EnumName{
		NORMAL("正常"), LOCK("锁定");
		
		private String name;
		
		private int index;
		UserStatusEnum(String name){
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

		public int getIndex() {
			return ordinal();
		}

		public void setIndex(int index) {
			this.index = ordinal();
		}
		
}
