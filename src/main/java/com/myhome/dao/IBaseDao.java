package com.myhome.dao;

import java.util.List;

public interface IBaseDao {

	public abstract void closeResource();
	public abstract void beginTransaction() throws Exception;
	public abstract void commit() throws Exception;
	public abstract void rollback() throws Exception;
	public abstract Integer getMaxId(String tableName,String primaryKey) throws Exception;
	public int[] batch(List<String> sqlList) throws Exception;
}