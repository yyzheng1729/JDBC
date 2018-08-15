package JDBCTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import pojo.Product;
import utils.JDBCUtils;
import utils.JDBCUtils2;

public class Jdbc {

	/**
	 * 封装数据 查询
	 * 
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void fun5() throws Exception {

		// 1. 获取数据库连接
		Connection conn = JDBCUtils2.getConnection();

		// 2. 准备 SQL 语句
		String sql = "select * from product";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		// 3. 执行 SQL 语句
		ResultSet rs = ptmt.executeQuery();

		// 4. 创建一个集合，用来保存查询出来的对象实例
		// 菱形语法，在泛型中，用来指定集合中存储的内容类型
		List<Product> proList = new ArrayList<Product>();

		// 5. 处理结果
		while (rs.next()) {
			// 5.1 获取目标对象的实例，可以通过有参构造器
			int id = rs.getInt("id");
			String name = rs.getString("name");
			double price = rs.getDouble("price");
			String mark = rs.getString("mark");
			Product pro = new Product(id, name, price, mark);
			proList.add(pro);
		}

		// 5.2 查看保存数据的集合
		for (Product pro : proList) {
			System.out.println("商品的名字是：" + pro.getNAME());
		}

		// 6. 关闭资源
		JDBCUtils2.close(rs, ptmt, conn);
	}

	@Test
	@Ignore
	public void fun4() throws Exception {

		// 1.获取数据库连接
		Connection conn = JDBCUtils.getConnection();

		// 2. 准备 SQL 语句
		String sql = "update product set name = ?, price = ? where id = ?";

		PreparedStatement ptmt = conn.prepareStatement(sql);

		ptmt.setString(1, "奥特曼");
		ptmt.setString(2, "49.8");
		ptmt.setInt(3, 1);

		// 3. 执行 SQL 语句
		// 此处的返回值是 int 类型，如果有一条数据发生改变的话，则返回 1
		int i = ptmt.executeUpdate();

		// 4. 处理结果
		if (i == 1) {
			System.out.println("恭喜你，修改成功了...");
		}

		// 6. 关闭资源
		JDBCUtils.close(ptmt, conn);
	}

	@Test
	@Ignore
	public void fun3() throws Exception {
		// 1.获取数据库连接
		Connection conn = JDBCUtils.getConnection();

		// 2. 获取执行者对象
		// 如果使用 Statement 的话，则有可能会被 SQL 注入破坏
		// Statement stmt = conn.createStatement();
		// 推荐使用 PreparedStatement，它是 Statement 的子类，
		// 可以预防 SQL 注入，而且还可以把我们的 SQL 语句进行预初始化

		// 3. 准备 SQL 语句
		String sql = "select * from product where NAME = ? and price = ?";
		PreparedStatement ptmt = conn.prepareStatement(sql);
		// 这里的 1和2 表示的 where 后面的第n个属性
		ptmt.setString(1, "伊利牛奶");
		ptmt.setDouble(2, 3.5);

		// 4. 执行 SQL 语句
		ResultSet rs = ptmt.executeQuery();

		// 5. 处理结果
		while (rs.next()) {
			int idStr = rs.getInt("id");
			String nameStr = rs.getString("NAME");
			String passStr = rs.getString("price");
			System.out.println(idStr + "--" + nameStr + "--" + passStr);
		}

		// 6. 关闭资源
		JDBCUtils.close(rs, ptmt, conn);
	}

	@Test
	@Ignore
	public void fun2() throws Exception {
		// 1.获取数据库连接
		Connection conn = JDBCUtils.getConnection();

		// 2.准备 SQL 语句
		String sql = "select * from product";

		// 3.获取执行者对象
		Statement stmt = conn.createStatement();

		// 4.执行 SQL 语句
		ResultSet rs = stmt.executeQuery(sql);

		// 5.处理结果
		while (rs.next()) {
			int idStr = rs.getInt("id");
			String nameStr = rs.getString("name");
			double priceStr = rs.getDouble("price");
			String markStr = rs.getString("mark");
			System.out.println(idStr + "--" + nameStr + "--" + markStr);
		}

		// 6.关闭资源
		JDBCUtils.close(rs, stmt, conn);
	}

	@Test
	@Ignore
	public void fun1() throws Exception {
		// 1.注册驱动
		// 这里，其实是用到了反射的原理，通过给定类的名字，让程序自动去找对应的类
		// 然后执行得到对应实例
		Class.forName("com.mysql.jdbc.Driver");

		// 使用注册方法的时候，底层会注册两次，不推荐使用
		// DriverManager.registerDriver(new com.mysql.jdbc.Driver());

		// 2.获取连接：数据库地址、用户名、密码 Connection
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "root";
		Connection conn = DriverManager.getConnection(url, user, password);

		// 3.获取执行者对象
		Statement stmt = conn.createStatement();

		// 4.准备 SQL 语句
		String sql = "select * from product";

		// 5.执行 SQL 语句
		ResultSet rs = stmt.executeQuery(sql);

		// 6.处理结果
		while (rs.next()) {
			int idStr = rs.getInt("id");
			String nameStr = rs.getString("name");
			double priceStr = rs.getDouble("price");
			String markStr = rs.getString("mark");
			System.out.println(idStr + "--" + nameStr + "--" + markStr);
		}

		// 7.关闭资源
		rs.close();
		stmt.close();
		conn.close();
	}

}
