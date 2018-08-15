package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtils {
	// 重构：一旦发现代码用起来不是很爽的时候，一直有重复动作，必须想把饭抽取出来一个新的
	public JDBCUtils() {
	}

	public static Connection conn;

	// 静态块：在类初始化之后，会加载一次
	// 一般情况，我们会将一些常用的数据，放在静态块中进行加载
	// 加载完成之后，后面如果想要用的话，直接就可以用
	// 优点是，只会创建一次，不会重复创建良妃性能
	static {
		try {
			// 1.注册驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 2.获取连接：数据库地址、用户名、密码 Connection
			String url = "jdbc:mysql://localhost:3306/test";
			String user = "root";
			String password = "root";
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取 Connection 对象
	 */
	public static Connection getConnection() {
		return conn;
	}

	/**
	 * 释放资源
	 */
	public static void close(Statement stmt, Connection conn) {
		// 思路：在关闭资源之前，先要判断，到底有没有用到这个资源
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void close(ResultSet rs, Statement stmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
