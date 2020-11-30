package Bank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

import static Bank.bank.*;

public class BWindows extends Thread{

    public String name;

    protected ArrayList<BlockingQueue<Customer>> CustomerQueue = null;// 客户队列容器，每个窗口至少有一个客户队列

    public BWindows(String name) {
        this.name =name;
    }

    public void addCustomerQueue(BlockingQueue<Customer> bQueue) {
        if (CustomerQueue == null){
            CustomerQueue = new ArrayList<BlockingQueue<Customer>>();
        }
        CustomerQueue.add(bQueue);
    }

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

    private BlockingQueue<Customer> BlockingQueue;

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
                customer2.setWindowsName(this.name);//窗口名字

                long birthTime=customer2.getTask().getBirthTime().getTimeInMillis()-calendar.getTimeInMillis();//顾客开始到窗口办理业务
                long endTime = customer2.getTask().getEndTime().getTimeInMillis()-calendar.getTimeInMillis();//顾客离开窗口
                customer2.setBirthTime(birthTime*Minute*60*1000/50+millis);
                customer2.setEndTime(endTime*Minute*60*1000/50+millis);
                bNUM++;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
