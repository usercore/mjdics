package com.magic.promotion.util.enumUtil;


public enum RoleTypeEnum implements EnumName{
		ADMIN("管理员角色"), NORMAL("普通角色");
		
		private String name;
		
		RoleTypeEnum(String name){
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
