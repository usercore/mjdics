package com.magic.promotion.util.enumUtil;



public enum MakeCardStatusEnum implements EnumName{
		MAKE("制作中"), MAKE_SUCCESS("卡片制作完成");
		
		private String name;
		
		private int index;
		MakeCardStatusEnum(String name){
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
