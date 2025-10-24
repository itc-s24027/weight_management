import java.util.InputMismatchException;
import java.util.Scanner;

//アプリ全体の流れを担当
public class Weight_control_app {
    //スキャナー
    Scanner sc = new Scanner(System.in);

    //ユーザー管理クラスのインスタンス
    User_management userManagement = new User_management();

    //新規ユーザー登録
    public void newUser() {
        //名前
        String name = "";
        try {
            System.out.print("名前を入力してください(英字、日本語のみ):");
            name = sc.nextLine();
            //ユーザー名がきちんと入力されているか確認
            //checkがついているメソッドはエラーが出ないように例外処理をかいたメソッド
            name = userManagement.nameCheck(name);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println();
            return;
        }

        //パスワード
        int password = userManagement.passCheck();

        //体重
        System.out.print("現在の体重(kg)を入力してください:");
        double weight = userManagement.weight_Height_Check();

        //身長
        System.out.print("現在の身長(cm)を入力してください:");
        double height = userManagement.weight_Height_Check();

        //目標体重
        System.out.print("目標体重(kg)を入力してください:");
        double targetWeight = userManagement.weight_Height_Check();

        //ユーザーのインスタンスを生成、ユーザーリストに追加
        User newUser = new User(name, password, weight, height, targetWeight);
        userManagement.addUser(newUser);

    }

    //ログインメソッド
    public void loginMenu() {
        int count = 0;

        while (count < 5) { //5回までログイン試行可能
            //名前入力
            String name = " ";
            try {
                System.out.print("名前を入力してください(英字、日本語のみ)：");
                String username = sc.nextLine();
                name = userManagement.nameCheck(username);
            } catch (Exception e) {
                System.out.println(e.getMessage());
//                continue;
            }

            //パスワード入力
            int id = userManagement.passCheck();

                //入力された情報でログイン試行
                User user = userManagement.login(name, id);

                if (user != null) { //戻り値がnullじゃなければログイン成功
                    System.out.println("--ログインが完了しました--");
                    managementMenu(user);//管理メニューを表示
                    return;
                }else {

                    System.out.println("--ログインに失敗しました--");
                    System.out.println();
                }
            count++;
        }
    System.out.println("-- 一定の回数を超えたため、操作を終了します。--");

}


    //ログアウト
    public void logout() {
        System.out.println("--ログイン画面に戻ります--");
        System.out.println();
        menu();
    }

    //体重更新メニュー
    public void updateWeightMenu(User user) {
        System.out.print("今日の体重を記録してください:");
        double newWeight = userManagement.weight_Height_Check();
        user.updateWeight(newWeight);

        //目標体重に達したか確認
        if (newWeight <= user.getTargetWeight()) {
            System.out.println("--🎉目標体重になりました！！おめでとう！！--");
            System.out.print("目標体重を更新しますか？: 'yes','no' :");
            String input = sc.nextLine().trim(); //空白除去

            //equalsIgnoreCaseは大文字小文字のパターンでも認識したいときに便利
            if (input.equalsIgnoreCase("yes")) {
                //yesを入力すると目標体重を変更できる
                System.out.print("新しい目標体重(kg)を入力してください:");
                double newTargetWeight = userManagement.weight_Height_Check();
                user.setTargetWeight(newTargetWeight);
                System.out.println("--頑張りましょう💪--");

            }else if (input.equalsIgnoreCase("no")) {
                //noを入力するとログアウト
                System.out.println("--お疲れ様でした！体重が増えたらいつでも戻ってきてね😉--");
                logout();
            }

        }else {

            //前回の体重をlatestWeightに代入
            //新しい体重を追加しているので、前回の体重は最後から２番めになる
            double latestWeight = user.getWeightList().get(user.getWeightList().size() -2);

            if (newWeight == latestWeight) {
                    //体重をキープできている
                    System.out.println("--(*^^*)変化なし！継続は力なり💪--");
                } else if (newWeight > latestWeight) {
                    //体重が増えた
                    System.out.println("--(；´∀｀)ちょっと増えちゃったね。次も頑張ろう！--");
                } else if (newWeight < latestWeight) {
                    //体重が減った
                    System.out.println("--(≧∇≦)bいい調子！！このまま目標まで頑張ろう！--");
                }
        }
    }

    //BMIを表示するメニュー
    public void bmiMenu(User user) {
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("--最新の体重をもとに算出しています--");
        System.out.println("現在の体重"+user.getWeight()+"kg");
        System.out.println("身長"+user.getHeight()+"cm");
        double bmi = user.calculateBMI();
        System.out.println("BMI:"+ bmi);
        user.bmiTable(bmi);
        System.out.println("+++++++++++++++++++++++++++++++");
    }

    //セットメニュー
    public void accountSetMenu(User user) {
        System.out.println();
        while (true) {
            System.out.println("===========================");
            System.out.println("1: 名前を変更");
            System.out.println("2: パスワードを変更");
            System.out.println("3: 身長を変更");
            System.out.println("4: 目標体重を変更");
            System.out.println("5: 戻る");
            System.out.println("===========================");
            System.out.print("メニューを選択してください:");

            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){ //文字が入力されたら
                System.out.println("--数字でメニューを選択してください--");
                System.out.println();
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    setNameMenu(user);
                    break;
                case 2:
                    setPasswordMenu(user);
                    break;
                case 3:
                    setHeightMenu(user);
                    break;
                case 4:
                    setTargetWeightMenu(user);
                    break;
                case 5:
                    managementMenu(user);
                    return;
                default:
                    System.out.println("--無効な選択です--");
            }
        }
    }

    //名前変更メニュー
    public void setNameMenu(User user) {
        System.out.print("新しい名前を入力してください(英字、日本語のみ):");
        String name = sc.nextLine();
        try {
            name = userManagement.nameCheck(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        user.setName(name);
    }

    //パスワード変更メニュー
    public void setPasswordMenu(User user) {
        System.out.print("新しい");
        int password = userManagement.passCheck();
        user.setPassword(password);
    }

    //身長変更メニュー
    public void setHeightMenu(User user) {
        System.out.print("新しい身長(cm)を入力してください:");
        double height = userManagement.weight_Height_Check();
        user.setHeight(height);
    }

    //目標体重変更メニュー
    public void setTargetWeightMenu(User user) {
        System.out.print("新しい目標体重(kg)を入力してください:");
        double targetWeight = userManagement.weight_Height_Check();
        user.setTargetWeight(targetWeight);
    }

    //最初に表示されるメニュー
    public void menu() {
        while (true) {
            System.out.println();
            System.out.println("======体重管理あぷり======");
            System.out.println("1: 新規登録");
            System.out.println("2: ログイン");
            System.out.println("3: アプリ終了");
            System.out.println("=======================");
            System.out.print("メニューを選択してください:");

            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){ //文字が入力されたら
                System.out.println("--数字でメニューを選択してください--");
                System.out.println();
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    newUser();
                    break;
                case 2:
                    loginMenu();
                    return;
                case 3:
                    System.out.println("--アプリを終了します--");
                    return;
                default:
                    System.out.println("--無効な選択です--");
            }
        }
    }


    //機能管理メニュー（ログイン後に表示される）
    public void managementMenu(User user) {
        System.out.println();
        System.out.println("--" + user.getName() + "さんおかえりなさい！--");
        while (true) {
            System.out.println();
            System.out.println("===========================");
            System.out.println("1: 体重の記録");
            System.out.println("2: ユーザー情報を表示する");
            System.out.println("3: BMIを見る");
            System.out.println("4: 情報を変更");
            System.out.println("5: ログアウト");
            System.out.println("===========================");
            System.out.print("メニューを選択してください:");

            int choice = 0;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            }catch (InputMismatchException e){ //文字が入力されたら
                System.out.println("--数字でメニューを選択してください--");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    updateWeightMenu(user);
                    break;
                case 2:
                    user.printWeightList();
                    break;
                case 3:
                    bmiMenu(user);
                    break;
                case 4:
                    accountSetMenu(user);
                    break;
                case 5:
                    logout();
                    return;
                default:
                    System.out.println("--無効な選択です--");
            }
        }
    }
}
