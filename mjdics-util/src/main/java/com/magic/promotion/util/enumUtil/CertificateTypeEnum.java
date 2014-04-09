package com.magic.promotion.util.enumUtil;


public enum CertificateTypeEnum implements EnumName{
		ID("身份证");
		
		private String name;
		
		CertificateTypeEnum(String name){
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
