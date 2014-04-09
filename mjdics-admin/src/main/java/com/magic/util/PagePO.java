package com.magic.util;



public class PagePO {
	
	private int page = 1;
	private int totalPage;
	private int afterPage;// ��ǰҳ���ǰ��ʾ����
	private int beforePage;// ��ǰҳ��Ǻ���ʾ����
	private int totalCount;//�ܼ�¼��
	
	private static int PAGE_BEFORE_NUM=2;// ҳ����ʾǰ��ʾ����
	private static int PAGE_AFTER_NUM=3;// ҳ����ʾ����ʾ����
	
	protected int startRecord = 0;//��ʼ�ļ�¼���̨��ݷ�ҳ
	protected int rows = 10;//ÿҳ�ļ�¼���̨��ݷ�ҳ��
	
	protected int pageSize = 10;
	protected int currentPage = 10;
	
	 public int getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getAfterPage() {
		return afterPage;
	}
	public void setAfterPage(int afterPage) {
		this.afterPage = afterPage;
	}
	public int getBeforePage() {
		return beforePage;
	}
	public void setBeforePage(int beforePage) {
		this.beforePage = beforePage;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	//��ʼ����ҳ��
    private void initTotalPage(){
        this.totalPage = (totalCount+rows-1)/rows ;
    }
    //����ǰ���ҳ������ҳ��
    private void setAfterBefore(){
        /**
         * �жϸ�ҳ��ǰӦ����ʾ��������
         */
    	beforePage = page - PAGE_BEFORE_NUM;
        if (page - PAGE_BEFORE_NUM < 0) {
        	beforePage = 1;
        } 
        //ҳ�����ʾ����ҳ
        afterPage = page + PAGE_AFTER_NUM ;
        if (page + PAGE_AFTER_NUM >= totalPage) {
            afterPage = totalPage;
        }
    }
    private void setPageDiv(){
    	this.startRecord = (this.page-1)*rows;
    }
    public void initPage(int totalCount){
    	setTotalCount(totalCount);
    	initTotalPage();
    	setAfterBefore();
    	setPageDiv();
    }
	public int getPageSize() {
		return rows;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCurrentPage() {
		return page;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
    
   
}
