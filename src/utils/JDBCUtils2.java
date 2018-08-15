package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils2 {
	public JDBCUtils2() {
	}

	public static Connection conn;

	private static String driver;
	private static String url;
	private static String user;
	private static String password;

	static {
		try {
			// 0.加载配置文件，获取对应的信息
			readConfig();
			// 1.注册驱动
			Class.forName(driver);
			// 2.获取连接：数据库地址、用户名、密码 Connection
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用来加载配置文件
	 * 
	 * source folder 在部署之后，他的路径就是 bin 路径，也就是 classes 类资源路径
	 * 
	 * @throws IOException
	 */
	public static void readConfig() throws IOException {
		// 1.找到文件，并且加载文件
		InputStream is = JDBCUtils2.class.getClassLoader().getResourceAsStream("jdbc.properties");
		System.out.println("is 的值：" + is);

		// 2.从文件中获取数据
		Properties pro = new Properties();

		// 3.通过输入流加载数据
		pro.load(is);

		// 4.当获取到数据之后，应该把数据赋值给上面的变量
		driver = pro.getProperty("driver");
		url = pro.getProperty("url");
		user = pro.getProperty("user");
		password = pro.getProperty("password");
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
