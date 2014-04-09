package com.magic.promotion.util.enumUtil;



public enum ActiveAndInvalidEnum implements EnumName{
		Invalid("失效"), Active("激活");
		
		private String name;
		
		private int index;
		ActiveAndInvalidEnum(String name){
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
