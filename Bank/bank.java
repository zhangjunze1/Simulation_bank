package Bank;

import com.sun.deploy.security.CertificateHostnameVerifier;
import com.sun.org.apache.xml.internal.security.Init;
import com.sun.xml.internal.ws.runtime.config.TubelineFeatureReader;
import javafx.util.converter.PercentageStringConverter;
import sun.font.TrueTypeFont;

import java.awt.print.Printable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static Bank.Service.needSeconds;
import static java.lang.Thread.sleep;

public class bank {
    public static int count = 1;//银行单号
    public static int Minute =10; //多少分钟来一个顾客
    public static int Time = (17-8)*60;//每天营业时间
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    public static long millis;//
    public static long closeDoor;
    public static Calendar calendar;
    public static int aNUM=0;
    public static int bNUM=0;
    public static int vNUM=0;



    static BlockingQueue<Customer> Queue = new ArrayBlockingQueue<Customer>(200);
    static BlockingQueue<Customer> Queue1 = new ArrayBlockingQueue<Customer>(200);

    public static void main(String[] args) {

        Date date = null;
        Date date2 = null;
        try {
            date = format.parse("2020-11-20 08:00:00");
            date2 = format.parse("2020-11-20 16:40:00");
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        closeDoor = date2.getTime();////"2020-11-20 16:40:00"
        millis = date.getTime();//"2020-11-20 08:00:00"
        calendar = Calendar.getInstance();//银行开门的时间/ 开始执行项目的具体时间
        new bank().excute();//银行开门//最后一个人的结束时间到达16：40之后则不让顾客继续进入
        myPrint(Queue);
        aNUM = Queue.size()-bNUM-vNUM;
        resultPrint(Queue1);
        return ;
    }

    private static void resultPrint(BlockingQueue<Customer> Queue1) {
        Customer customer = new Customer();
        int Sum = Queue1.size();
        int num1 = 0 ,num2 = 0,num3 = 0,num4 = 0,num5 = 0,num6 = 0,num7 = 0,num8 = 0;
        long time=0;
        while (!Queue1.isEmpty()){
            customer= Queue1.remove();
            time = time + customer.getTask().getCostTime()*72/5;
            switch (customer.getTask().getTaskID()){
                case 1 :
                    num1++;break;
                case 2 :
                    num2++;break;
                case 3 :
                    num3++;break;
                case 4 :
                    num4++;break;
                case 5 :
                    num5++;break;
                case 6 :
                    num6++;break;
                case 7 :
                    num7++;break;
                case 8 :
                    num8++;break;
            }
        }
        long x = (int)(time/Sum);
        DecimalFormat decimalFormat=new DecimalFormat("0.0000");
        System.out.println("                                              ");
        System.out.println("====================统计表格====================");
        System.out.println("=======业务序号======业务内容======业务比例========");
        System.out.println("         1         Deposit      "+decimalFormat.format((float)num1/(float)Sum));
        System.out.println("         2         DrawMoney    "+decimalFormat.format((float)num2/(float)Sum));
        System.out.println("         3         Forfeit      "+decimalFormat.format((float)num3/(float)Sum));
        System.out.println("         4         EBank        "+decimalFormat.format((float)num4/(float)Sum));
        System.out.println("         5         WaterEle     "+decimalFormat.format((float)num5/(float)Sum));
        System.out.println("         6         Fund         "+decimalFormat.format((float)num6/(float)Sum));
        System.out.println("         7         TransAccount "+decimalFormat.format((float)num7/(float)Sum));
        System.out.println("         8         RepayLoan    "+decimalFormat.format((float)num8/(float)Sum));
        System.out.println("==============================================");
        System.out.println("顾客平均办理时间为："+x/60+"分"+x%60+"秒");
    }

    private static void myPrint(BlockingQueue<Customer> Queue) {
        Customer customer = new Customer();
        long starttime = 0;
        long endtime = 0;
        while (!Queue.isEmpty()){
             customer= Queue.remove();
             if (customer.getWindowsName()==null){
                 System.out.println(customer.getName()+"于"+longToDate(starttime)+"到达，在["+"A类窗口"
                         +"]办理了业务序号["+customer.getTask().getTaskID()+"]的"+customer.getService()+"业务。" +"持续"+
                         (customer.getTask().getCostTime()*72/5)/60+"分"+(customer.getTask().getCostTime()*72/5)%60+
                         "秒，顾客离开时间为"+longToDate(endtime));
             }
             else{
                 System.out.println(customer.getName()+"于"+longToDate(customer.getBirthTime())+"到达，在["+customer.getWindowsName()
                         +"]办理了业务序号["+customer.getTask().getTaskID()+"]的"+customer.getService()+"业务。" +"持续"+
                         (customer.getTask().getCostTime()*72/5)/60+"分"+(customer.getTask().getCostTime()*72/5)%60+
                         "秒，顾客离开时间为"+longToDate(customer.getEndTime()));
                 starttime = customer.getEndTime();
                 endtime = customer.getEndTime()+customer.getTask().getCostTime();
             }

        }
    }


    private void excute() {
        /**
         * 创建窗口
         */
        AWindows aWindows = new AWindows("A类窗口");
        BWindows bWindows1 = new BWindows("B类1号窗口");
        BWindows bWindows2 = new BWindows("B类2号窗口");
        VWindows vipWindows = new VWindows("VIP窗口");
        /**
        * vip窗口队列
        */
        BlockingQueue<Customer> VIPQueue = new ArrayBlockingQueue<Customer>(100);
        /**
         *  A窗口队列
         */
        BlockingQueue<Customer> AQueue = new ArrayBlockingQueue<Customer>(100);
        /**
         *  B窗口队列
         */
        BlockingQueue<Customer> BQueue = new ArrayBlockingQueue<Customer>(100);

        /**
         * 添加处于本窗口管理的客户队列
         */
        aWindows.addCustomerQueue(AQueue);
        bWindows1.addCustomerQueue(BQueue);
        bWindows2.addCustomerQueue(BQueue);
        vipWindows.addCustomerQueue(VIPQueue);

        /**
         * 给vip窗口和a窗口添加b类窗口的客户队列
         */
        aWindows.addCustomerQueue(VIPQueue);
        aWindows.addCustomerQueue(BQueue);

        aWindows.start();
        bWindows1.start();
        bWindows2.start();
        vipWindows.start();

        while (true){
            try {
                Customer customer = new Customer();
                customer = InitCustomer();//初始化顾客(随机生成vip及业务序号)

//            System.out.println(customer.getName()+" 业务序号："+customer.getTask().getTaskID()+" 业务内容："+customer.getService()+" 业务持续时间："
//                   +customer.getTask().getCostTime()/60+"分"+customer.getTask().getCostTime()%60+"秒 "+" 窗口名字:"+customer.getWindowsName());

                if (customer.isIfVIP())
                        VIPQueue.add(customer);
                else if (customer.getTask().getTaskID()==3||customer.getTask().getTaskID()==6||customer.getTask().getTaskID()==8)
                        AQueue.add(customer);
                else
                        BQueue.add(customer);


                sleep(50);
                Queue.add(customer);
                Queue1.add(customer);
                if (closeDoor<customer.getEndTime()){
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }//银行开门

    /**
     * @Description: long类型转换成日期
     * @param lo 毫秒数
     * @return String yyyy-MM-dd HH:mm:ss
     */
    public static String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }

    /**
     * 初始化顾客
     * 随机生成vip及业务序号
     */
    public Customer InitCustomer() {//vip概率为0.3

        Customer customer = new Customer();
        Task task = new Task();

        if (new Random().nextInt(100)<30)//vip概率为0.3
            customer.setIfVIP(true);
        else
            customer.setIfVIP(false);

        MathRandomTask a = new MathRandomTask();//随机生成业务序号
        int x =a.PercentageRandom();//得到具体数字
        Service service = Service.getServiceByIndex(x);
        task.setBirthTime(Calendar.getInstance());//生成业务的时间
        task.setTaskID(x);//业务序号x
        customer.setTask(task);//衔接Task和Customer
        customer.setService(service);//衔接Service和Customer
        customer.setName("客户"+count);
        customer.getTask().setCostTime(needSeconds(customer.getService()));//把service中预测的时间存入task的costtime中
        count++;
        return customer;
    }

}
