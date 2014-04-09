package com.magic.promotion.util.enumUtil;



public enum ApplyCashStatusEnum implements EnumName{
		APPLY("申请中"), APPLY_SUCCESS("提现成功"), APPLY_FAIL("提现失败");
		
		private String name;
		
		private int index;
		ApplyCashStatusEnum(String name){
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
