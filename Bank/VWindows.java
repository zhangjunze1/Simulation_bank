package Bank;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.BlockingQueue;

import static Bank.bank.*;

public class VWindows extends Thread{

    public String name;

    protected ArrayList<BlockingQueue<Customer>> CustomerQueue = null;// 客户队列容器，每个窗口至少有一个客户队列

    public VWindows(String name) {
        this.name =name;
    }

    public void addCustomerQueue(BlockingQueue<Customer> vipQueue) {
        if (CustomerQueue == null){
            CustomerQueue = new ArrayList<BlockingQueue<Customer>>();
        }
        CustomerQueue.add(vipQueue);
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
            Customer customer3 = null;
            try {
                synchronized (BlockingQueue) {
                    while ((customer3 = getCustomer()) == null){
                        Thread.sleep(10);
                    }
                }
                customer3.setVipWindows(this);
                customer3.getTask().setStartTime(Calendar.getInstance());//业务开始的时间
                sleep(customer3.getTask().getCostTime());//处理业务 所以线程暂停时间为业务所花时间
                customer3.getTask().setEndTime(Calendar.getInstance());//结束业务的时间
                customer3.setWindowsName(this.name);

                long birthTime=customer3.getTask().getBirthTime().getTimeInMillis()-calendar.getTimeInMillis();
                long endTime = customer3.getTask().getEndTime().getTimeInMillis()-calendar.getTimeInMillis();
                customer3.setBirthTime(birthTime*Minute*60*1000/50+millis);
                customer3.setEndTime(endTime*Minute*60*1000/50+millis);
                vNUM++;
//                System.out.println(customer3.getName()+"于"+longToDate(customer3.getBirthTime())+"到达，在["+customer3.getWindowsName()
//                                +"]办理了业务序号["+customer3.getTask().getTaskID()+"]的"+customer3.getService()+"业务。" +"持续"+
//                        (customer3.getTask().getCostTime()*72/5)/60+"分"+(customer3.getTask().getCostTime()*72/5)%60+
//                        "秒，顾客离开时间为"+longToDate(customer3.getEndTime()));


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
