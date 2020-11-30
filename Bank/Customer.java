package Bank;

public class Customer {

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

    public long getBirthTime() {
        return birthTime;
    }

    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getWindowsName() {
        return WindowsName;
    }

    public void setWindowsName(String windowsName) {
        WindowsName = windowsName;
    }

    public AWindows getaWindows() {
        return aWindows;
    }

    public void setaWindows(AWindows aWindows) {
        this.aWindows = aWindows;
    }

    public BWindows getbWindows() {
        return bWindows;
    }

    public void setbWindows(BWindows bWindows) {
        this.bWindows = bWindows;
    }

    public VWindows getVipWindows() {
        return vipWindows;
    }

    public void setVipWindows(VWindows vipWindows) {
        this.vipWindows = vipWindows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public boolean isIfVIP() {
        return ifVIP;
    }

    public void setIfVIP(boolean ifVIP) {
        this.ifVIP = ifVIP;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

}
