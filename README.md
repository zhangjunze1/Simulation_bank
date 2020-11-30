#模拟银行业务办理
---
# 一、题目要求
### 1.模拟客户可以随机办理银行提供的8种业务中的一种；期中办理时间规划为：（基准时间每个同学自行指定）   **完成**
|业务|业务序号|办理时间 * benchMarkTime|
|--|--|--|
|存款|1|0.5-1.5|
|取款|2|0.5-1.5|
|缴纳罚款|3|1.2-2.0|
|开通网银|4|5.0-8.0|
|交水电费|5|1.5-2.0|
|购买基金|6|2.0-3.0|
|转账汇款|7|3.0-4.0|
|个贷还款|8|2.0-4.0|
### 2.程序模拟多个窗口服务，窗口服务具有一定的差异化，现在窗口的开放情况如下   **完成**

|窗口来信|能办理业务类型|说明|数量|
|--|--|--|--|
|A类窗口|1,2,3,4,5,6,7,8|所有客户可以办理|1|
|B类窗口|1,2,4,5,7|所有客户可以办理|2|
|V类窗口|1,2,3,4,5,6,7,8|vip客户优先|1|

### 3.顾客模拟要求：  **完成**
+ 3.1 顾客分为普通客户和VIP客户，普通用户和VIP客户比例自行确定
+ 3.2 顾客到达随机模拟

### 4.模拟程序模拟一天的营业情况，在完成当天的营业（模拟为上午8:00-下午17:00）后打印出客户列表，包括：  **完成**
+ 客户名称
+ 客户到达时间
+ 客户办理业务类型
+ 客户所用时间

### 5.统计出以下数据：  **完成**
+ 5.1 当天的所有顾客平均办理时间（从到达到办理完成时间定义为顾客办理时间）
+ 5.2 不同业务在所有办理业务中所占的比例

### 6.仿真（*需要评定优秀的同学需要提供此部分实现）：
+ 6.1 程序能够参数化设定当天到达客户办理不同业务的比例（比如业务1占20%等）  **完成**
+ 6.2 程序能够模拟回答以下问题：如果基准日开放的窗口情况为： **未完成**

A类：1个
B类：2个
V类：1个

那么针对营业日B的情况，在相同顾客数量的情况下，要如何开发窗口才能得到和基准日基本一致的顾客平均服务时间；

|业务|基准日A|基准日B|
|--|--|--|
|存款|20%|10%|
|取款|20%|10%|
|缴纳罚款|10%|5%|
|开通网银|10%|5%|
|交水电费|5%|5%|
|购买基金|15%|40%|
|转账汇款|10%|5%|
|个贷还款|10%|20%|

# 二、类的划分
## Customer
    private String name;//顾客名字
    private boolean ifVIP;//是否为vip(具体的vip分配看bank类(main))
    private AWindows aWindows;//办理业务的A类窗口
    private BWindows bWindows;//办理业务的B类窗口
    private VWindows vipWindows;//办理业务的V类窗口
    private String WindowsName;//本顾客对应的窗口名字
    private Task task;//本顾客对应的业务类型
    private Service service;//对应的enum
    private long birthTime;//顾客到达时间
    private long endTime;//顾客离开时间
## Service
	//enum Service
	Deposit(1),DrawMoney(2),Forfeit(3),EBank(4),WaterEle(5),Fund(6),TransAccount(7),RepayLoan(8);

    public int index;//通过下标寻找具体办理业务
    public double minTime;//最短办理时间
    public double maxTime;//最长办理时间
    public static final double benchMarkTime = 10 ;//基准时间为10分钟 “单位：分”
## Task
    private SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//时间格式定义
    private int costTime;// 业务需要消耗的时间
    private Calendar birthTime;// 业务生成时间
    private Calendar startTime;// 业务开始时间
    private Calendar endTime;// 业务结束时间
    private Integer TaskID;//业务序号
## AWindows(BWindows / VWindows)三个类放在一起介绍
	//extends Thread
	public String name;//窗口名字
    protected ArrayList<BlockingQueue<Customer>> CustomerQueue = null;// 客户队列容器，每个窗口至少有一个客户队列

	private BlockingQueue<Customer> BlockingQueue;//A有三个队列 B与V只有一个
    private BlockingQueue<Customer> BlockingQueue1;
    private BlockingQueue<Customer> BlockingQueue2;
## MathRandomTask(实现分配各业务的概率)
    public static double rate1 = 0.20;//1出现的概率为%20
    public static double rate2 = 0.20;//2出现的概率为%20
    public static double rate3 = 0.10;//3出现的概率为%10
    public static double rate4 = 0.10;//4出现的概率为%10
    public static double rate5 = 0.05;//5出现的概率为%5
    public static double rate6 = 0.15;//6出现的概率为%15
    public static double rate7 = 0.10;//7出现的概率为%10
    public static double rate8 = 0.10;//8出现的概率为%10
## bank(main函数)
	public static int count = 1;//银行单子序号
    public static int Minute =10; //多少分钟来一个顾客
    public static int Time = (17-8)*60;//每天营业时间
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    public static long millis;//开门时间
    public static long closeDoor;//关门时间
    public static Calendar calendar;//银行开门的时间/ 开始执行项目的具体时间
    public static int aNUM=0;//A类窗口服务人数
    public static int bNUM=0;//B类窗口服务人数
    public static int vNUM=0;//V类窗口服务人数
    static BlockingQueue<Customer> Queue = new ArrayBlockingQueue<Customer>(200);
    static BlockingQueue<Customer> Queue1 = new ArrayBlockingQueue<Customer>(200);

# 三、核心代码
## Customer、Task(都是Getter and Setter)
## Service
### 通过下标index值 找到具体业务(返回Service)
	public static Service getServiceByIndex(int index){
        for (Service s : values()){
            if( s.getIndex() == index){
                return s;
            }
        }
        return null;
    }
### 在maxTime与minTime之间随机生成一个时间成为本业务所耗费时间
	public static int needSeconds(Service service){
        return (int)(new Random().nextInt((int)service.maxTime)%(service.maxTime-service.minTime+1) + service.minTime);
    }
### 当确定enum的具体值时，自动生成maxTime与minTime
	    Service(int index){
        this.index = index;
        switch (index){
            case 1:
            case 2:
                this.minTime = 0.5 * 50 * benchMarkTime/Minute;
                this.maxTime = 1.5 * 50 * benchMarkTime/Minute;
                break;
            case 3:
                this.minTime = 1.2 * 50 * benchMarkTime/Minute;
                this.maxTime = 2.0 * 50 * benchMarkTime/Minute;
                break;
            case 4:
                this.minTime = 5 * 50 * benchMarkTime/Minute;
                this.maxTime = 8 * 50 * benchMarkTime/Minute;
                break;
            case 5:
                this.minTime = 1.5 * 50 * benchMarkTime/Minute;
                this.maxTime = 2.0 * 50 * benchMarkTime/Minute;
                break;
            case 6:
                this.minTime = 2.0 * 50 * benchMarkTime/Minute;
                this.maxTime = 3.0 * 50 * benchMarkTime/Minute;
                break;
            case 7:
                this.minTime = 3.0 * 50 * benchMarkTime/Minute;
                this.maxTime = 4.0 * 50 * benchMarkTime/Minute;
                break;
            case 8:
                this.minTime = 2.0 * 50 * benchMarkTime/Minute;
                this.maxTime = 4.0 * 50 * benchMarkTime/Minute;
                break;
        }
    }
## MathRandomTask(实现分配各业务的概率)
### Math.random()产生一个double型的随机数，判断一下。例如1出现的概率为%20，则介于0到0.20中间的返回1
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
## AWindows(BWindows / VWindows)三个类放在一起介绍
### 把顾客加入到该窗口的队列中
	public void addCustomerQueue(BlockingQueue<Customer> bQueue) {
        if (CustomerQueue == null){
            CustomerQueue = new ArrayList<BlockingQueue<Customer>>();
        }
        CustomerQueue.add(bQueue);
    }
### 从自己的一个或三个窗口队列中获取顾客，并将其从自己的窗口队列中删除
    protected Customer getCustomer() throws InterruptedException {
        if (CustomerQueue==null){
            return null;
        }
        if (CustomerQueue.get(0).isEmpty() || CustomerQueue.size() == 1) {// 当自己的客户队列空，或者本本窗口只有属于自己的任务
            return CustomerQueue.get(0).take();// 从对头取出客户，如果客户队列空，则阻塞线程
        }
	//        for (int i = 1; i < CustomerQueue.size(); i++) {
	//            if (!CustomerQueue.get(i).isEmpty()) {// 存在非空的客户队列，取出客户处理
	//                return CustomerQueue.get(i).remove();
	//            }
	//        }
        return null;
    }
### 多线程的重写 对从窗口队列中获取的顾客进行操作
 	@Override
    public void run(){
        BlockingQueue = CustomerQueue.get(0);
        super.run();
        while (true){
            Customer customer2 = null;
            try {
                synchronized (BlockingQueue) {
                    while ((customer2 = getCustomer()) == null) {
                        Thread.sleep(10);
                    }
                }
                customer2.setbWindows(this);
                customer2.getTask().setStartTime(Calendar.getInstance());//业务开始的时间
                sleep(customer2.getTask().getCostTime());//处理业务 所以线程暂停时间为业务所花时间
                customer2.getTask().setEndTime(Calendar.getInstance());//结束业务的时间
                customer2.setWindowsName(this.name);

                long birthTime=customer2.getTask().getBirthTime().getTimeInMillis()-calendar.getTimeInMilli();//顾客开始到窗口办理业务
                long endTime = customer2.getTask().getEndTime().getTimeInMillis()-calendar.getTimeInMillis();//顾客离开窗口
                customer2.setBirthTime(birthTime*Minute*60*1000/50+millis);
                customer2.setEndTime(endTime*Minute*60*1000/50+millis);
                aNUM++;//或者bNUM++。或者vNUM++。
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

## bank
### 统计表格的输出（要求第五点）
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
### 模拟一天的客户列表情况
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
### 银行开门
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
    }
### long转date
    public static String longToDate(long lo){
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sd.format(date);
    }
### 顾客初始化
	public Customer InitCustomer() {//vip概率为0.3

        Customer customer = new Customer();

        Task task = new Task();
        if (new Random().nextInt(100)<30)//vip概率为0.3由此确定
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

# 总结
## 整体思路
首先，我将整个银行的模拟从8：00-17：00 九个小时等比例缩放成几秒，跑完整个代码的同时记录下各个时间节点与时间段，例如三十分钟与100毫秒等价，通过以下的函数

	Calendar.getInstance()//获取时间点
	sleep()//处理业务，暂停时间即为业务所花费时间

来获取时间。整个银行模拟在几秒内跑完，再等比例放大到九个小时，实现模拟。
## 结果
###客户列表
![image](https://raw.githubusercontent.com/zhangjunze1/take_out_eat/master/1.png)
![image](https://raw.githubusercontent.com/zhangjunze1/take_out_eat/master/2.png)
###统计表格
![image](https://raw.githubusercontent.com/zhangjunze1/take_out_eat/master/3.png)

## 以上的要求除了6.2未完成，均已完成

