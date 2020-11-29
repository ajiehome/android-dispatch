package cn.ajiehome.dispatch.utils;

import android.annotation.SuppressLint;
import android.util.Log;
import cn.ajiehome.dispatch.memory.entity.FragmentMemory;
import cn.ajiehome.dispatch.memory.entity.MemoryDetailsUsed;
import cn.ajiehome.dispatch.memory.service.UpdateDialogUI;

import java.nio.channels.Pipe;
import java.util.*;

/**
 * @author Jie
 */
public class MemoryUtils {
    private static final String TAG = "BaseUtil";

    public static UpdateDialogUI updateDialogUI = null;
    public static Integer[] MEMORY = new Integer[1024];
    public static Integer indexAll = 0;
    public static Integer usedMemorySize = 0;
    @SuppressLint("UseSparseArrays")
    public static ArrayList<MemoryDetailsUsed> memoryUsedDetails = new ArrayList<>();
    static {
        Arrays.fill(MEMORY, 0);
    }
    /**
     * 占用内存
     *
     * @param start      起始地址
     * @param memorySize 占用长度
     */
    public static void occupyMemory(Integer start, Integer memorySize,Integer pid) {
        for (int i = start; i < start + memorySize; i++) {
            MEMORY[i] = 1;
        }
        usedMemorySize+=memorySize;
        memoryUsedDetails.add(new MemoryDetailsUsed(pid,start,start+memorySize));
        updateUI();
        Log.e(TAG, "占用内存: " + Arrays.toString(MEMORY));
    }
    /**
     * 释放内存
     *
     * @param start      起始地址
     * @param memorySize 释放长度
     */
    public static void freedMemory(Integer start, Integer memorySize,Integer pid) {
        for (int i = start; i < start + memorySize; i++) {
            MEMORY[i] = 0;
        }
        usedMemorySize-=memorySize;
        for (MemoryDetailsUsed memoryUsedDetail : memoryUsedDetails) {
            if (memoryUsedDetail.getPid().equals(pid)){
                memoryUsedDetails.remove(memoryUsedDetail);
                break;
            }
        }
        updateUI();
        Log.e(TAG, "释放内存: " + Arrays.toString(MEMORY));
    }

    public static Double occupyRate(){
        return (double) usedMemorySize / MEMORY.length;
    }

    public static void updateUI(){
        if (updateDialogUI!=null){
            updateDialogUI.updateUI();
        }
    }
}
