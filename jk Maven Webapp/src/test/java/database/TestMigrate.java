package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @Description:  数据迁移，由mysql迁移到oracle
 * @Author: nutony
 * @Date: 2013-05-15
 */
public class TestMigrate {
	@Test 
	public void clearTable() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException{
//		Map<String,String> map = new HashMap<String,String>();		//新表中修改字段名的情况，待开发
//		map.put("CONTRACT_C.CNOTE", "NOTE");
//		map.put("CONTRACT_C.CONTRACTOR", "CONTACTS");
		String s = "CONTRACT_C,CONTRACT_PRODUCT_C,EXT_CPRODUCT_C";
		//String s = "export_c,export_product_c,ext_eproduct_c";
		this.execute("clear", s.split(","));
	}
	
	@Test
	public void copyData() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException{
		String s = "CONTRACT_C,CONTRACT_PRODUCT_C,EXT_CPRODUCT_C";
		this.execute("copy", s.split(","));
	}
	
	private void execute(String what, String[] tables) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException{
		//源数据库信息
		String srcDriver = "com.mysql.jdbc.Driver";
		String srcUrl = "jdbc:mysql://localhost:3306/jkdb?characterEncoding=UTF-8";
		String srcUser = "root";
		String srcPassword = "root";
		
		//目标数据库信息
		String desDriver = "oracle.jdbc.driver.OracleDriver";
		String desUrl = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
		String desUser = "jkdb";
		String desPassword = "jk";	

		if("copy".equals(what)){
			for(String tableName : tables){
				String desColumns = this.getDesColumns(tableName, desDriver, desUrl, desUser, desPassword);		//目标表字段
				System.out.println(desColumns);
				List<String> sqls = this.insertSql(tableName, desColumns, srcDriver, srcUrl, srcUser ,srcPassword);
				this.executeBatch(sqls, desDriver, desUrl, desUser, desPassword);
			}
		}else{
			List<String> sqls = new ArrayList();
			for(String tableName : tables){
				sqls.add("delete from " + tableName);			//clear table data
				this.executeBatch(sqls, desDriver, desUrl, desUser, desPassword);
			}
		}
		System.out.println("Successfull!");
	}

	//根据sql生成insert sql脚本
	public List<String> insertSql(String tableName, String desColumns, String driver, String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException {
		Class.forName(driver).newInstance();  
		Connection conn = DriverManager.getConnection(url, user,password);   
		Statement stmt = conn.createStatement();   

		ResultSet rs = stmt.executeQuery("select * from "+tableName);  
		ResultSetMetaData rsmd = rs.getMetaData();					//获得表结构对象
		
		for(int j=1;j<rsmd.getColumnCount()+1;j++){					//打印字段标题和类型
			System.out.print(rsmd.getColumnName(j)+"["+rsmd.getColumnTypeName(j)+"]\t");
		}
		System.out.println("");
		
		List<String> sqls = new ArrayList();
		StringBuffer sBuf = new StringBuffer();
		
		while(rs.next()) {
			sBuf.append("insert into "+tableName+" ");
			sBuf.append("(");
			for(int j=1;j<rsmd.getColumnCount()+1;j++){
				if(desColumns.indexOf(rsmd.getColumnName(j)+",")>-1){			//目标表有该字段才处理
					sBuf.append(rsmd.getColumnName(j)).append(",");
				}
			}
			sBuf.delete(sBuf.length()-1, sBuf.length());		//del last char
			sBuf.append(")");
			
			sBuf.append(" values(");
			for(int j=1;j<rsmd.getColumnCount()+1;j++){
				if(desColumns.indexOf(rsmd.getColumnName(j)+",")>-1){			//目标表有该字段才处理
					if(rs.getString(j)!=null && "VARCHAR".indexOf(rsmd.getColumnTypeName(j))>-1){
						sBuf.append("'").append(rs.getString(j)).append("'").append(",");
					}else if(rs.getString(j)!=null && "DATETIME".indexOf(rsmd.getColumnTypeName(j))>-1){
						sBuf.append("to_date('").append(rs.getString(j).replace(".0", "")).append("','yyyy-mm-dd hh24:mi:ss'),");		//oracle日期类型
					}else{
						sBuf.append(rs.getString(j)).append(",");
					}
				}
			}
			sBuf.delete(sBuf.length()-1, sBuf.length());		//del last char
			sBuf.append(")");
			sqls.add(sBuf.toString());				//add sql
			sBuf.delete(0, sBuf.length());			//clear
		}
		
		rs.close();   
		stmt.close();   
		conn.close(); 
		
		return sqls;
	}
	
	//获得目标表列，有时目标表和源表字段会有差异，这里处理源表有而目标表没有的情况
	public String getDesColumns(String tableName, String driver, String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException {
		StringBuilder sb = new StringBuilder();
		Class.forName(driver).newInstance();  
		Connection conn = DriverManager.getConnection(url, user,password);   
		Statement stmt = conn.createStatement();   
		
		ResultSet rs = stmt.executeQuery("select * from "+tableName);  
		ResultSetMetaData rsmd = rs.getMetaData();					//获得表结构对象
		
		for(int j=1;j<rsmd.getColumnCount()+1;j++){					//打印标题
			sb.append(rsmd.getColumnName(j)+",");
		}

		rs.close();   
		stmt.close();   
		conn.close(); 
		
		return sb.toString();
	}
	
	//批量执行sql语句
	public void executeBatch(List<String> sqls, String driver, String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException {
		if(sqls.size()>0){
			Class.forName(driver).newInstance();  
			Connection conn = DriverManager.getConnection(url, user,password);   
			conn.setAutoCommit(false);							//不自动提交，否则无法批量提交
			Statement stmt = conn.createStatement();
			for(String sql : sqls){
				stmt.addBatch(sql);								//添加要执行的sql语句
			}
			stmt.executeBatch();								//批量提交执行
			
			stmt.close();   
			conn.close(); 
		}
	}

}
