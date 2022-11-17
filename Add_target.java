/**
 * 新規目標を追加するプログラム
 * @author 朴智美, 前田
 */

import java.util.*;
import java.sql.*;


public class Add_target {
	private int userID;
	
	public Add_target(int userID){
		this.userID = userID;
	}


	public void addTarget() {
		try {
			// 接続
			// characterEncoding=utf8 <- 文字エンコーディングとしてutf-8を使用
			// &useServerPrepStmts=true <- 静的プレースホルダを使用
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost/healthcheck?useSSL=false&characterEncoding=utf8&useServerPrepStmts=true", 
				"root", ""
				);		 

			// Statementの代わりにPreparedStatementを使う
			// 最初にSQLを組み立ててしまう。後から値を入れる部分(ホルダー)を ? で示す
			PreparedStatement st=conn.prepareStatement("INSERT INTO target VALUE "+
			"(?,?,?,?,?,?,?,?,?,?,?,?);");
			
			//  ID   | created_date | waist | height | weight | target_date | HiBloodpre | LowBloodpre | Bloodsuger | BMI  | muscle | body_fat 
			Scanner scanner = new Scanner(System.in);	
			System.out.println("新たな目標を追加します。");
		
			System.out.println("ウエスト：");
			String waist = scanner.nextLine();
			
			System.out.println("身長：");
			String height = scanner.nextLine();
			
			System.out.println("体重：");
			String weight = scanner.nextLine();
			
			System.out.println("BMI：");
			String bmi = scanner.nextLine();

			System.out.println("筋肉量：");
			String muscle = scanner.nextLine();

			System.out.println("体脂肪率：");
			String body_fat = scanner.nextLine();
			
			System.out.println("最高血圧：");
			String hiBloodpre = scanner.nextLine();
			
			System.out.println("最低血圧：");
			String lowBloodpre = scanner.nextLine();
			
			System.out.println("血糖値：");
			String bloodsuger = scanner.nextLine();
			
			//System.out.println("IDを入力してください。");
			//String user_ID = scanner.nextLine();
			
			System.out.println("本日の日付を入力してください。（例：2022-11-16）：");
			String today = scanner.nextLine();

			System.out.println("達成目標日を入力してください。（例：2023-01-31）：");
			String target_date = scanner.nextLine();


			//  ID   | created_date | waist | height | weight | target_date | HiBloodpre | LowBloodpre | Bloodsuger | BMI  | muscle | body_fat 
			st.setInt(1, userID); 
			st.setString(2,today);
			st.setFloat(3, Float.parseFloat(waist)); 
			st.setFloat(4, Float.parseFloat(height)); 
			st.setFloat(5, Float.parseFloat(weight)); 
			st.setString(6,target_date);
			st.setInt(7, Integer.parseInt(hiBloodpre));
			st.setInt(8, Integer.parseInt(lowBloodpre));
			st.setInt(9, Integer.parseInt(bloodsuger));
			st.setFloat(10, Float.parseFloat(bmi)); 
			st.setFloat(11, Float.parseFloat(muscle)); 
			st.setFloat(12, Float.parseFloat(body_fat)); 
			
         
			// SQLを実行して、実行結果をResultSetに入れる
			int res = st.executeUpdate();
			System.out.println("データベースへの登録が完了しました。");

			// 終了処理
			st.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}