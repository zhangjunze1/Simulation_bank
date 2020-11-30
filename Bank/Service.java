package Bank;

import java.util.Random;

import static Bank.bank.Minute;

public enum Service {

    Deposit(1),DrawMoney(2),Forfeit(3),EBank(4),WaterEle(5),
    Fund(6),TransAccount(7),RepayLoan(8);

    public int index;
    public double minTime;
    public double maxTime;
    public static final double benchMarkTime = 10 ;//单位：分

    public int getIndex() {
        return index;
    }

    public static Service getServiceByIndex(int index){
        for (Service s : values()){
            if( s.getIndex() == index){
                return s;
            }
        }
        return null;
    }

    public static int needSeconds(Service service){
        return (int)(new Random().nextInt((int)service.maxTime)%(service.maxTime-service.minTime+1) + service.minTime);
    }

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
}
