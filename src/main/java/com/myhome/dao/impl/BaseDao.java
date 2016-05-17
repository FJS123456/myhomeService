package com.myhome.dao.impl;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.myhome.dao.IBaseDao;

public class BaseDao implements IBaseDao{
	
	protected Connection conn;
	private Statement stmt = null;
	
	protected void openconnection() throws ClassNotFoundException,SQLException{		
		try {
			if( conn == null || conn.isClosed() ){
				Class.forName("com.mysql.jdbc.Driver");  //
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/home?useUnicode=true&characterEncoding=utf-8","home","Fujisheng425047_");
			}			
		} catch (ClassNotFoundException e) {
		    throw e;
		} catch (SQLException e) {	
			throw e;
		}
	}	
	
	/* (non-Javadoc)
	 * @see com.hotel.dao.IBaseDao#closeResource()
	 */
	@Override
	public void closeResource(){
		
		if(conn != null){
			try {
				conn.close();
				conn = null;
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.hotel.dao.IBaseDao#beginTransaction()
	 */
	@Override
	public void beginTransaction() throws Exception{
		if(conn != null){
			conn.setAutoCommit(false);
		}
			
	}
	
	/* (non-Javadoc)
	 * @see com.hotel.dao.IBaseDao#commit()
	 */
	@Override
	public void commit() throws Exception{
		if(conn != null){
			conn.commit();
		}	
	}
	
	/* (non-Javadoc)
	 * @see com.hotel.dao.IBaseDao#rollback()
	 */
	@Override
	public void rollback() throws Exception{
		if(conn != null){
			conn.rollback();
		}
		
	}

	@Override
	public Integer getMaxId(String tableName,String primaryKey) throws Exception 
	{
		Integer maxId = null;
		String sql = "select max(" + primaryKey + ") as currentIndex from " + tableName;
		this.openconnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			rs.next();
			maxId = rs.getInt("currentIndex") + 1;
		}
		rs.close();
		ps.close();
		return maxId;
	}

	@Override
	public int[] batch(List<String> sqlList) throws Exception
	{
		try {
			this.openconnection();
			conn.setAutoCommit(false);
			stmt =  conn.createStatement();
			for (int i = 0; i < sqlList.size(); i++) {
				stmt.addBatch(sqlList.get(i));
			}
			this.commit();
			return stmt.executeBatch();
		} catch (Exception e) {
			try {
				this.rollback();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			throw e;
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
