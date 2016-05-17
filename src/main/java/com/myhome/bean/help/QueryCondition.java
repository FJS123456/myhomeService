package com.myhome.bean.help;

public class QueryCondition 
{
	private Integer pageIndex;         //第几页，从0页算起
	private Integer count;             //每页有多少条数据
	
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
