package com.magic.promotion.util;



public class PagePO {
	
	private int currentPage = 1;
	private int totalPage;
	private int afterPage;// 当前页标记前显示多少
	private int beforePage;// 当前页标记后显示多少
	private int totalCount;//总记录数
	
	public static final int PAGE_SIZE = 20;
	private static int PAGE_BEFORE_NUM=2;// 页数显示前显示多少
	private static int PAGE_AFTER_NUM=3;// 页数显示后显示多少
	
	protected int startRecord = 0;//开始的记录数后台数据分页
	protected int pageSize =PAGE_SIZE;//每页的记录数（后台数据分页）
	
	 public int getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	//初始化总页数
    private void initTotalPage(){
        this.totalPage = (totalCount+pageSize-1)/pageSize ;
    }
    //设置前面的页数；后面的页数，
    private void setAfterBefore(){
        /**
         * 判断该页数前应该显示多少数字
         */
    	beforePage = currentPage - PAGE_BEFORE_NUM;
        if (currentPage - PAGE_BEFORE_NUM < 0) {
        	beforePage = 1;
        } 
        //页数后显示多少页
        afterPage = currentPage + PAGE_AFTER_NUM ;
        if (currentPage + PAGE_AFTER_NUM >= totalPage) {
            afterPage = totalPage;
        }
    }
    private void setPageDiv(){
    	this.startRecord = (this.currentPage-1)*pageSize;
    }
    public void initPage(int totalCount){
    	setTotalCount(totalCount);
    	initTotalPage();
    	setAfterBefore();
    	setPageDiv();
    }
    
   
}
