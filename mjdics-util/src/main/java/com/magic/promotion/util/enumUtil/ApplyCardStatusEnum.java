package com.magic.promotion.util.enumUtil;



public enum ApplyCardStatusEnum implements EnumName{
		APPLY("申请中"), MAKE_SUCCESS("卡片制作完成"), APPLY_SUCCESS("申请成功"),APPLY_FAIL("失败");
		
		private String name;
		
		private int index;
		ApplyCardStatusEnum(String name){
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
