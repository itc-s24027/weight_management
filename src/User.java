import java.util.ArrayList;

//Weight_contorolを実装することで体重管理ができるクラスになる
//UserクラスはWeight_controlに定義されたメソッドを必ず実装
class User implements Weight_control{
    private String name; //名前
    private int password; //パスワード
    private double weight; //体重
    private double height; //身長
    private double targetWeight; //目標体重
    private ArrayList<Double> weightList; //体重履歴リスト

    //コンストラクタ
    public User(String name, int password, double weight, double height, double targetWeight) {
        this.name = name;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.targetWeight = targetWeight;
        this.weightList = new ArrayList<>();
        weightList.add(weight); //最初の体重をリストに記録
    }

    //ゲッター
    public String getName() {return name;}

    public int getPassword() {return password;}

    public double getWeight() {return weight;}

    public double getHeight() {return height;}

    public double getTargetWeight() {return targetWeight;}

    public ArrayList<Double> getWeightList() {return weightList;}

    //BMI計算
    @Override
    public double calculateBMI() {
        double heightInMeters = height / 100.0; // cm を m に変換
        double bmi = weight / (heightInMeters * heightInMeters);
        return Math.round(bmi * 10.0) / 10.0; // 小数第2位で四捨五入
    }

    //BMIの数値に応じて肥満度を判定
    //別のクラスでも良かったかな？
    public void bmiTable(double bmi) {
        if (bmi < 18.50) {
            System.out.println("+ 痩せ +");
        } else if (bmi >= 18.50 && bmi < 25.00) {
            System.out.println("+ 普通体重 +");
        } else if (bmi >= 25.00 && bmi < 30.00) {
            System.out.println("+ 肥満（1度）+");
        } else if (bmi >= 30.00 && bmi < 35.00) {
            System.out.println("+ 肥満（2度）+");
        } else if (bmi >= 35.00 && bmi < 40.00) {
            System.out.println("+ 肥満（3度）+");
        } else if (bmi >= 40.00) {
            System.out.println("+ 肥満（4度）+");
        }
    }

    //体重記録
    @Override
    public void updateWeight(double newWeight) {
        this.weight = newWeight;
        weightList.add(newWeight);
        System.out.println("--"+name + "さんの体重" + newWeight + "kg 記録しました---");
        System.out.println();
    }

    //目標体重変更
    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
        System.out.println("--目標体重を"+targetWeight+"kgに変更しました--");
    }

    //身長変更
    public void setHeight(double height) {
        this.height = height;
        System.out.println("--身長を"+height+"cmに変更しました--");
    }

    //名前変更
    public void setName(String name) {
        this.name = name;
        System.out.println("--名前を"+name+"に変更しました--");
    }

    //パスワード変更
    public void setPassword(int password) {
        this.password = password;
        System.out.println("--パスワードを変更しました--");
    }

    //過去の体重一覧とその他情報の表示
    @Override
    public void printWeightList() {
        System.out.println();
        System.out.println("+++++++++++++++");
        System.out.println("身長:" + height + "cm");
        System.out.println("目標体重:" +targetWeight + "kg");
        System.out.println("過去の体重一覧:");
        for (int i = 0; i < weightList.size(); i++) {
            System.out.println(i+1 + "回目:" + weightList.get(i) + "kg");
        }
        System.out.println("+++++++++++++++");
    }

}

