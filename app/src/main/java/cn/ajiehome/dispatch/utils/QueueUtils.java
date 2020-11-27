package cn.ajiehome.dispatch.utils;

import android.util.Log;
import cn.ajiehome.dispatch.memory.service.Fit;
import cn.ajiehome.dispatch.memory.service.impl.BestFit;
import cn.ajiehome.dispatch.memory.service.impl.FirstFit;
import cn.ajiehome.dispatch.memory.service.impl.NextFit;
import cn.ajiehome.dispatch.memory.service.impl.WorstFit;
import cn.ajiehome.dispatch.process.entity.PCB;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jie
 */
public class QueueUtils {
    public static final String TAG = "ProcessUtils";
    public static Integer processIndex = 0;//进程索引，多少个进程
    public static Integer distributionIndex = 0;//内存分配方式 0：首次适应算法，1：循环首次适应算法，2：最佳适应算法，3：最坏适应算法
    public static Long systemRunTimeAll = 0L;//系统运行总时间

    public static List<PCB> createQueue = new ArrayList<>();//创建队列
    public static List<PCB> readyQueue = new ArrayList<>();//就绪队列
    public static List<PCB> runQueue = new ArrayList<>();//运行队列
    public static List<PCB> blockQueue = new ArrayList<>();//阻塞队列
    public static List<PCB> finishQueue = new ArrayList<>();//完成队列
    public static List<PCB> hangQueue = new ArrayList<>();//挂起队列

    /**
     * 初始化进程
     *
     * @param position 进程个数
     */
    public static void initProcess(Integer position) {
        for (int i = 0; i < position; i++) {
            createQueue.add(BaseUtils.createPCB(i));
            processIndex++;
        }
    }

    /**
     * 为创建队列里面的进程分配内存，分配成功了就去就绪队列
     *
     * @param position 内存分配方式 0：首次适应算法，1：循环首次适应算法，2：最佳适应算法，3：最坏适应算法
     */
    public static void allocationMemory(Integer position) {
        Fit fit = null;
        switch (position) {
            case 0:
                fit = new FirstFit();
                break;
            case 1:
                fit = new NextFit();
                break;
            case 2:
                fit = new BestFit();
                break;
            case 3:
                fit = new WorstFit();
                break;
            default:
        }
        assert fit != null;
        int index = 0;
        int createQueueSize = createQueue.size();
        for (int i = 0; i < createQueueSize; i++) {
            PCB pcb = createQueue.get(index);
            if (pcb.getArrivalsTime() <= systemRunTimeAll) {
                Integer indexMemory = fit.distribution(pcb.getNeedMemorySize());
                if (indexMemory != -1) {
                    pcb.setStartIndexMemory(indexMemory);
                    readyQueue.add(pcb);
                    createQueue.remove(pcb);
                } else {
                    index++;
                }
            } else {
                index++;
            }
        }
    }

    /**
     * 添加一个进程
     */
    public static void addProcess() {
        createQueue.add(BaseUtils.createPCB(processIndex, systemRunTimeAll));
        allocationMemory(distributionIndex);
    }

    /**
     * 阻塞运行进程
     */
    public static void blockProcess() {
        PCB pcb = runQueue.get(0);
        blockQueue.add(pcb);
        runQueue.remove(pcb);
        runProcess(0);
    }

    /**
     * 运行一个进程
     *
     * @param index 就绪队列索引
     */
    public static Boolean runProcess(Integer index) {
        if (readyQueue.size() != 0) {
            PCB pcb = readyQueue.get(index);
            runQueue.add(pcb);
            readyQueue.remove(pcb);
            return true;
        }
        return false;
    }

    /**
     * 挂起
     *
     * @param position 挂起的某个队列,0表示运行挂起，1表示就绪挂起，2表示阻塞挂起
     * @param index    挂起索引
     */
    public static void hangProcess(Integer position, Integer index) {
        PCB pcb = null;
        switch (position) {
            case 0:
                pcb = runQueue.get(index);
                runQueue.remove(pcb);
                runProcess(0);
                break;
            case 1:
                pcb = readyQueue.get(index);
                readyQueue.remove(pcb);
                break;
            case 2:
                pcb = blockQueue.get(index);
                blockQueue.remove(pcb);
                break;
            default:
        }
        assert pcb != null;
        pcb.setHangType(position);
        hangQueue.add(pcb);
    }

    /**
     * 唤醒阻塞
     *
     * @param index 索引
     */
    public static void wakeProcess(Integer index) {
        PCB pcb = blockQueue.get(index);
        readyQueue.add(pcb);
        blockQueue.remove(pcb);
    }

    /**
     * 激活挂起
     *
     * @param index 索引
     */
    public static void activationProcess(Integer index) {
        PCB pcb = hangQueue.get(index);
        if (pcb.getHangType() == 2) {
            blockQueue.add(pcb);
        } else {
            readyQueue.add(pcb);
        }
        hangQueue.remove(pcb);
    }

    /**
     * 结束进程
     */
    public static void finishProcess() {
        PCB pcb = runQueue.get(0);
        MemoryUtils.freedMemory(pcb.getStartIndexMemory(), pcb.getNeedMemorySize());
        finishQueue.add(pcb);
        runQueue.remove(pcb);
    }

    /**
     * 碎片时间到达，切换进程
     */
    public static void requeueRRProcess(){
        PCB pcb = runQueue.get(0);
        readyQueue.add(pcb);
        runQueue.remove(pcb);
    }
}
