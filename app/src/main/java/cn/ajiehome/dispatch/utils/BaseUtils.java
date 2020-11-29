package cn.ajiehome.dispatch.utils;

import cn.ajiehome.dispatch.process.entity.PCB;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * @author Jie
 */
public class BaseUtils {
    private static final Random random = new Random();
    private static final Integer ARRIVAL_TIME_MAX = 20;//进程初始化到达的最大时间
    private static final Integer RANK_MAX = 10;//进程优先级最大
    private static final Integer NEED_TIME = 50;//需要最大时间
    private static final Integer FRAGMENT_MEMORY_MAX = 50;

    public static PCB createPCB(Integer position){
        return new PCB(position,"进程"+position,random.nextInt(RANK_MAX),(long)random.nextInt(ARRIVAL_TIME_MAX),(long)random.nextInt(NEED_TIME),50+random.nextInt(FRAGMENT_MEMORY_MAX));
    }

    public static PCB createPCB(Integer position,Long arrivalsTime){
        return new PCB(position,"进程"+position,random.nextInt(RANK_MAX),arrivalsTime,(long)random.nextInt(NEED_TIME),50+random.nextInt(FRAGMENT_MEMORY_MAX));
    }

    public static void clearStatic(){
        QueueUtils.createQueue.clear();
        QueueUtils.readyQueue.clear();
        QueueUtils.runQueue.clear();
        QueueUtils.blockQueue.clear();
        QueueUtils.hangQueue.clear();
        QueueUtils.finishQueue.clear();
        MemoryUtils.memoryUsedDetails.clear();
        QueueUtils.systemRunTimeAll = 0L;
        QueueUtils.processIndex = 0;
        MemoryUtils.indexAll = 0;
        MemoryUtils.usedMemorySize = 0;
        Arrays.fill(MemoryUtils.MEMORY,0);
    }
}
