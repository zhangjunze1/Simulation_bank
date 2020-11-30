package Bank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

import static Bank.bank.*;

public class AWindows extends Thread{

    public String name;

    protected ArrayList<BlockingQueue<Customer>> CustomerQueue = null;// 客户队列容器，每个窗口至少有一个客户队列

    public AWindows(String name) {
        this.name =name;
    }

    public void addCustomerQueue(BlockingQueue<Customer> Queue) {
        if (CustomerQueue == null){
            CustomerQueue = new ArrayList<BlockingQueue<Customer>>();
        }
        CustomerQueue.add(Queue);
    }

    protected Customer getCustomer() throws InterruptedException {
        if (CustomerQueue==null){
            return null;
        }
        // if (CustomerQueue.get(0).isEmpty()) {// 当属于自己的客户队列空
        // if (CustomerQueue.size() > 1) {// 存在其他的客户队列
        //
        // } else {// 不存在其他的客户队列
        //
        // }
        // } else {// 属于自己的客户队列非空
        // return CustomerQueue.get(0).take();
        // }
//        if (CustomerQueue.get(0).isEmpty() || CustomerQueue.size() == 1) {// 当自己的客户队列空，或者本本窗口只有属于自己的任务
//            return CustomerQueue.get(0).take();// 从对头取出客户，如果客户队列空，则阻塞线程
//        }
        for (int i = 0; i < CustomerQueue.size(); i++) {
            if (!CustomerQueue.get(i).isEmpty()) {// 存在非空的客户队列，取出客户处理
//                System.err.println("处理其他窗口业务");
                return CustomerQueue.get(i).take();
            }
        }
        return null;
    }

    private BlockingQueue<Customer> BlockingQueue;
    private BlockingQueue<Customer> BlockingQueue1;
    private BlockingQueue<Customer> BlockingQueue2;

    @Override
    public void run(){
        BlockingQueue = CustomerQueue.get(0);
        BlockingQueue1 = CustomerQueue.get(1);
        BlockingQueue2 = CustomerQueue.get(2);
        super.run();
        while (true){
            Customer customer1 = null;
            try {
                //锁住队列
                synchronized (BlockingQueue2) {
                    synchronized (BlockingQueue1) {
                        synchronized (BlockingQueue) {
                            while ((customer1 = this.getCustomer()) == null) {
                                Thread.sleep(10);
                            }
                        }
                    }
                }
                customer1.setaWindows(this);
                customer1.getTask().setStartTime(Calendar.getInstance());//业务开始的时间
                sleep(customer1.getTask().getCostTime());//处理业务 所以线程暂停时间为业务所花时间
                customer1.getTask().setEndTime(Calendar.getInstance());//结束业务的时间
                customer1.setWindowsName(this.name);

                long birthTime=customer1.getTask().getBirthTime().getTimeInMillis()-calendar.getTimeInMillis();
                long endTime = customer1.getTask().getEndTime().getTimeInMillis()-calendar.getTimeInMillis();
                customer1.setBirthTime(birthTime*Minute*60*1000/50+millis);
                customer1.setEndTime(endTime*Minute*60*1000/50+millis);
                aNUM++;

//                System.out.println(customer1.getName()+"于"+longToDate(customer1.getBirthTime())+"到达，在["+customer1.getWindowsName()
//                        +"]办理了业务序号["+customer1.getTask().getTaskID()+"]的"+customer1.getService()+"业务。" +"持续"+
//                        (customer1.getTask().getCostTime()*72/5)/60+"分"+(customer1.getTask().getCostTime()*72/5)%60+
//                        "秒，顾客离开时间为"+longToDate(customer1.getEndTime()));


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
