/**
 * ログインに責任を持つクラス（新規の記録・目標追加を呼び出す）
 * @author 朴智美
 */

import java.sql.*;
import java.util.*;


public class Login2 {
	public void login() {
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
			PreparedStatement st=conn.prepareStatement("SELECT user.ID, name,  gender.gender, birthday FROM user, gender" 
				+" WHERE user.ID = ?" 
				+" AND user.name = ?" 
				+" AND user.gender = gender.number;");


			Scanner scanner = new Scanner(System.in);	
			
			System.out.println("ログインをします。");
			System.out.println("IDを入力してください");
			String user_ID = scanner.nextLine();
			int userID = Integer.parseInt(user_ID);
			
			System.out.println("氏名を入力してください");
			String user_name = scanner.nextLine();
			
			st.setInt(1, userID); 
			st.setString(2, user_name); 
			
			// SQLを実行して、実行結果をResultSetに入れる
			ResultSet r = st.executeQuery();
			
			System.out.println("\nこんにちは、"+user_name+"さん。あなたの登録情報は以下になります。");
			while(r.next()) {
				System.out.println(
						"ID："+r.getInt("ID")+"\n"+
						"氏名："+r.getString("name")+"\n"+
						"性別："+r.getString("gender")+"\n"+
						"生年月日："+r.getString("birthday"));
						}
			
			Loop:
			while(true){
				System.out.println("\n＝＝＝ログインメニュー＝＝＝");
				System.out.println("1: 新しい記録を追加する");
				System.out.println("2: 新しい目標を立てる");
				System.out.println("3: 今までの推移を見る");
				System.out.println("0: 戻る");
				System.out.println("メニュー番号を入力してください: ");
				
				Add_record2 e3 = new Add_record2(userID);
				Add_target e4 = new Add_target(userID);
				Accumulation e5 = new Accumulation(userID);
		
				String line = scanner.nextLine();
				switch(line) {
				case "0":
					System.out.println("最初の画面に戻ります。");
					break Loop;
					
				case "1":
					e3.addRecord();
					break;
				
				case "2":
					e4.addTarget();
					break;
					
				case "3":
					e5.Accumulator();
					break;
				
				default:
					System.out.println("入力が正しくありません。");	
				}
			}
			
			

			// 終了処理
			st.close();
			r.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Error 1: " + se.toString() + " "
				+ se.getErrorCode() + " " + se.getSQLState());
		} catch (Exception e) {
			System.out.println("Error: " + e.toString() + e.getMessage());
		}
	}
}
