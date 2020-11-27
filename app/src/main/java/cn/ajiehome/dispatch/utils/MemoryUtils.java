package cn.ajiehome.dispatch.utils;

import android.util.Log;
import cn.ajiehome.dispatch.memory.entity.FragmentMemory;

import java.util.*;

/**
 * @author Jie
 */
public class MemoryUtils {
    private static final String TAG = "BaseUtil";
    public static Integer[] MEMORY = new Integer[1024];
    public static Integer indexAll = -1;
    static {
        Arrays.fill(MEMORY, 0);
    }
    /**
     * 占用内存
     *
     * @param start      起始地址
     * @param memorySize 占用长度
     */
    public static void occupyMemory(Integer start, Integer memorySize) {
        for (int i = start; i < start + memorySize; i++) {
            MEMORY[i] = 1;
        }
        Log.e(TAG, "占用内存: " + Arrays.toString(MEMORY));
    }
    /**
     * 释放内存
     *
     * @param start      起始地址
     * @param memorySize 释放长度
     */
    public static void freedMemory(Integer start, Integer memorySize) {
        for (int i = start; i < start + memorySize; i++) {
            MEMORY[i] = 0;
        }
        Log.e(TAG, "释放内存: " + Arrays.toString(MEMORY));
    }
}
