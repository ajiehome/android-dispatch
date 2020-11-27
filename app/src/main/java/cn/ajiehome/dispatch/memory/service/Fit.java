package cn.ajiehome.dispatch.memory.service;

/**
 * @author Jie
 */
public interface Fit {
    /**
     * 分配内存
     *
     * @param memorySize 需要的内存大小
     * @return 分配给该进程内存的起始地址
     */
    Integer distribution(Integer memorySize);
}
