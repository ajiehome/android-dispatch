package cn.ajiehome.dispatch.process.entity;

/**
 * @author Jie
 */
public class PCB {
    /**
     * 进程ID
     */
    private Integer processId;
    /**
     * 进程名字
     */
    private String processName;
    /**
     * 优先级别
     */
    private Integer priorityRank;
    /**
     * 到达时间
     */
    private Long arrivalsTime;
    /**
     * 需要时间
     */
    private Long needTime;
    /**
     * 已用CUP时间
     */
    private Long usedCPUTime = 0L;
    /**
     * 阻塞原因
     */
    private String causeBlock;
    /**
     * 挂起类型 0表示运行挂起，1表示就绪挂起，2表示阻塞挂起
     */
    private Integer hangType = -1;
    /**
     * 需要内存大小
     */
    private Integer needMemorySize;
    /**
     * 内存分配首地址
     */
    private Integer startIndexMemory = -1;
    /**
     * 所处队列等级，主要为了多级队列
     */
    private Integer processLevel = 1;

    public PCB(Integer processId, String processName, Integer priorityRank, Long arrivalsTime, Long needTime, Integer needMemorySize) {
        this.processId = processId;
        this.processName = processName;
        this.priorityRank = priorityRank;
        this.arrivalsTime = arrivalsTime;
        this.needTime = needTime;
        this.needMemorySize = needMemorySize;
    }

    public PCB(Integer processId, String processName, Integer priorityRank, Long arrivalsTime, Long needTime, Long usedCPUTime, String causeBlock, Integer needMemorySize, Integer startIndexMemory) {
        this.processId = processId;
        this.processName = processName;
        this.priorityRank = priorityRank;
        this.arrivalsTime = arrivalsTime;
        this.needTime = needTime;
        this.usedCPUTime = usedCPUTime;
        this.causeBlock = causeBlock;
        this.needMemorySize = needMemorySize;
        this.startIndexMemory = startIndexMemory;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getPriorityRank() {
        return priorityRank;
    }

    public void setPriorityRank(Integer priorityRank) {
        this.priorityRank = priorityRank;
    }

    public Long getArrivalsTime() {
        return arrivalsTime;
    }

    public void setArrivalsTime(Long arrivalsTime) {
        this.arrivalsTime = arrivalsTime;
    }

    public Long getNeedTime() {
        return needTime;
    }

    public void setNeedTime(Long needTime) {
        this.needTime = needTime;
    }

    public Long getUsedCPUTime() {
        return usedCPUTime;
    }

    public void setUsedCPUTime(Long usedCPUTime) {
        this.usedCPUTime = usedCPUTime;
    }

    public String getCauseBlock() {
        return causeBlock;
    }

    public void setCauseBlock(String causeBlock) {
        this.causeBlock = causeBlock;
    }

    public Integer getNeedMemorySize() {
        return needMemorySize;
    }

    public void setNeedMemorySize(Integer needMemorySize) {
        this.needMemorySize = needMemorySize;
    }

    public Integer getStartIndexMemory() {
        return startIndexMemory;
    }

    public void setStartIndexMemory(Integer startIndexMemory) {
        this.startIndexMemory = startIndexMemory;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "进程名称='" + processName + '\'' +
                ", 进程ID='" + processId + '\'' +
                ", 优先级别=" + priorityRank +
                ", 到达时间=" + arrivalsTime +
                ", 需要时间=" + needTime +
                ", 已使用CPU时间=" + usedCPUTime +
                ", 阻塞原因=" + causeBlock + '\'' +
                ", 内存首地址=" + startIndexMemory + '\'' +
                ", 需要内存大小=" + needMemorySize + '\'' +
                ", 所在队列=" + processLevel + '\'' +
                '}';
    }

    public Integer getHangType() {
        return hangType;
    }

    public void setHangType(Integer hangType) {
        this.hangType = hangType;
    }

    public Integer getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(Integer processLevel) {
        this.processLevel = processLevel;
    }
}
