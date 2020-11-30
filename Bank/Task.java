package Bank;


import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * 处理的业务
 */
public class Task {

    private SimpleDateFormat dateFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int costTime;// 业务需要消耗的时间
    private Calendar birthTime;// 业务生成时间
    private Calendar startTime;// 业务开始时间
    private Calendar endTime;// 业务结束时间
    private Integer TaskID;//业务序号


    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(SimpleDateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }

    public Calendar getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(Calendar birthTime) {
        this.birthTime = birthTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }

    public void setEndTime(Calendar endTime) {
        this.endTime = endTime;
    }

    public Integer getTaskID() {
        return TaskID;
    }

    public void setTaskID(Integer taskID) {
        TaskID = taskID;
    }
}
