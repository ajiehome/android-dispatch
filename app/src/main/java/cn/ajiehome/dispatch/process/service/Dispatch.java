package cn.ajiehome.dispatch.process.service;

/**
 * @author Jie
 */
public interface Dispatch {
    /**
     * 执行一次调度算法
     * @return 系统运行了多长时间
     */
    Long transfer();

    /**
     * 阻塞一个进程
     */
    void blockProcess();

    /**
     * 运行一个进程
     */
    void runProcess(Integer index);

    /**
     * 挂起
     * @param position 挂起的某个队列,0表示运行挂起，1表示就绪挂起，2表示阻塞挂起
     * @param index 挂起索引
     */
    void HangProcess(Integer position,Integer index);

    /**
     * 唤醒阻塞
     * @param index 索引
     */
    void wakeProcess(Integer index);

    /**
     * 激活挂起
     * @param index 索引
     */
    void activationProcess(Integer index);
}
