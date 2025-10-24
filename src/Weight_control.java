//体重の記録、管理を行う
//定義するだけで、処理の詳細は持たない
interface Weight_control {

    //BMIの計算
    double calculateBMI();
    //体重登録
    void updateWeight(double new_weight);
    //体重の履歴表示
    void printWeightList();

}
