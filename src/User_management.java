import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User_management {

    Scanner sc = new Scanner(System.in);
    private ArrayList<User> users = new ArrayList<>();//ユーザーのリスト

    //ユーザー登録
    public void addUser(User user) {
        users.add(user);
        System.out.println();
        System.out.println("--"+user.getName()+"さんの登録が完了しました--");
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("身長:"+user.getHeight()+"cm");
        System.out.println("現在の体重:"+user.getWeight()+"kg");
        System.out.println("目標体重:"+user.getTargetWeight()+"kg");
        System.out.println("+++++++++++++++++++++++++++++++");
    }

    //ユーザーの名前チェック（例外）
    public String nameCheck(String name) throws Exception {
        //ユーザー名がnullか空の場合
        if (name == null || name.isEmpty()){
            throw new Exception("--正しく入力してください--");
        }
        //ユーザー名に数字が含まれていたらエラー (英字と日本語のみ許可）
        if (name.matches(".*\\d.*")) {
            throw new Exception("--ユーザー名に数字は入力できません--");
        }
        return name;
    }

    //パスワードチェック（例外）
    public int passCheck() {
        while (true) {
            try {
                System.out.print("パスワードを入力してください(整数のみ):");
                int pass = sc.nextInt();
                sc.nextLine();
                return pass;
            } catch (InputMismatchException e) {
                System.out.println("--文字は使えません--");
                sc.nextLine();

            }
        }
    }

    //体重・身長チェック（例外）
    public double weight_Height_Check() {
        while (true) {
            try {
                double height = sc.nextDouble();
                sc.nextLine();
                return height;
            } catch (InputMismatchException e) {
                System.out.println("--文字は使えません--");
                System.out.print("数字で入力してください:");
                sc.nextLine();
            }
        }
    }


    //ログイン試行
    public User login(String username, int password) {

        //ユーザーと暗証番号を確認
        for (User user : users) {
            if (user.getName().equals(username) )
               if (user.getPassword() == password) {
                return user;
            }else {
                   System.out.println("-- パスワードが間違っています --");
               }
        }
        //ユーザーが見つからない場合
        return null;
    }
}
