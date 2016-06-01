package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @Description:  数据库测试，打印表结构
 * @Author: nutony
 * @Date: 2013-05-15
 */
public class TestJDBC {
	@Test 
	public void call() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException{
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/jkdb?characterEncoding=UTF-8";
		String user = "root";
		String password = "root";
		
//		String srcDriver = "oracle.jdbc.driver.OracleDriver";
//		String srcUrl = "jdbc:oracle:thin:@127.0.0.1:1521:ORCL";
//		String srcUser = "jk9db";
//		String srcPassword = "jk";	
		
		this.execute("contract_c", driver, url, user, password);
		
//		List<String> sqls = new ArrayList<String>();
//		sqls.add("1.create global temporary table temp_tbl(col_a varchar2(30)) on commit preserve rows");
//		this.updateBatch(sqls , driver, url, user, password);
	}
	
	//复制表数据
	@Test
	public void copy(){
		for(int i=0;i<100;i++){
			//1)insert into 临时表；2)修改其主键UUID；3)再插回主表
		}
	}
	
	private void execute(String tableName, String driver, String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException{


		Class.forName(driver).newInstance();  
		Connection conn = DriverManager.getConnection(url, user,password);   
		Statement stmt = conn.createStatement();   

		ResultSet rs = stmt.executeQuery("select * from "+tableName);  
		ResultSetMetaData rsmd = rs.getMetaData();					//获得表结构对象
		
		for(int j=1;j<rsmd.getColumnCount()+1;j++){					//打印字段标题和类型
			System.out.print(rsmd.getColumnName(j)+"["+rsmd.getColumnTypeName(j)+"]\t");
		}
		System.out.println("");

		rs.close();   
		stmt.close();   
		conn.close(); 
		

		System.out.println("Successfull!");
	}
	
	//批量执行sql语句
	public void updateBatch(List<String> sqls, String driver, String url, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, IOException, InterruptedException {
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
		System.out.println("Successfull!");
	}

}
