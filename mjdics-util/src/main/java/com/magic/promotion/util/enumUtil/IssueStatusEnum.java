package com.magic.promotion.util.enumUtil;



public enum IssueStatusEnum implements EnumName{
	SELL("销售"), STOP("暂停"), CLOSURE("封期"),Lottery("开奖"),StirAward("搅奖"),SentAwardL("派奖");
		
		private String name;
		
		private int index;
		IssueStatusEnum(String name){
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
