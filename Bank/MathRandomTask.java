package Bank;

public class MathRandomTask
{
    /**
     * 1出现的概率为%20
     */
    public static double rate1 = 0.20;
    /**
     * 2出现的概率为%20
     */
    public static double rate2 = 0.20;
    /**
     * 3出现的概率为%10
     */
    public static double rate3 = 0.10;
    /**
     * 4出现的概率为%10
     */
    public static double rate4 = 0.10;
    /**
     * 5出现的概率为%5
     */
    public static double rate5 = 0.05;
    /**
     * 6出现的概率为%15
     */
    public static double rate6 = 0.15;
    /**
     * 7出现的概率为%15
     */
    public static double rate7 = 0.10;
    /**
     * 8出现的概率为%15
     */
    public static double rate8 = 0.10;

    /**
     * Math.random()产生一个double型的随机数，判断一下
     * 例如1出现的概率为%20，则介于0到0.20中间的返回1
     * @return int
     *
     */
    public int PercentageRandom()
    {
        double randomNumber;
        randomNumber = Math.random();
        if (randomNumber >= 0 && randomNumber <= rate1)
        {
            return 1;
        }
        else if (randomNumber >= rate1  && randomNumber <= rate1 + rate2)
        {
            return 2;
        }
        else if (randomNumber >= rate1 + rate2
                && randomNumber <= rate1 + rate2 + rate3)
        {
            return 3;
        }
        else if (randomNumber >= rate1 + rate2 + rate3
                && randomNumber <= rate1 + rate2 + rate3 + rate4)
        {
            return 4;
        }
        else if (randomNumber >= rate1 + rate2 + rate3 + rate4
                && randomNumber <= rate1 + rate2 + rate3 + rate4 + rate5)
        {
            return 5;
        }
        else if (randomNumber >= rate1 + rate2 + rate3 + rate4 + rate5
                && randomNumber <= rate1 + rate2 + rate3 + rate4 + rate5 + rate6)
        {
            return 6;
        }
        else if (randomNumber >= rate1 + rate2 + rate3 + rate4 + rate5 + rate6
                && randomNumber <= rate1 + rate2 + rate3 + rate4 + rate5 + rate6 + rate7)
        {
            return 7;
        }
        else if (randomNumber >= rate1 + rate2 + rate3 + rate4 + rate5 + rate6 + rate7
                && randomNumber <= rate1 + rate2 + rate3 + rate4 + rate5 + rate6 + rate7 + rate8)
        {
            return 8;
        }
        return -1;
    }

    /**
     * 测试主程序
     * @param agrs
     *
     */
//    public static void main(String[] agrs)
//    {
//        int i = 0;
//        //java.lang.Math.random() 返回一个正符号的double值，大于或等于0.0且小于1.0
//        MathRandomTask a = new MathRandomTask();
//        for (i = 0; i <= 100; i++)//打印100个测试概率的准确性
//        {
//            System.out.println(a.PercentageRandom());
//        }
//    }
}
