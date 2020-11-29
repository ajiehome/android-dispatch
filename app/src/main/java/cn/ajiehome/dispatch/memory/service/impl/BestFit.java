package cn.ajiehome.dispatch.memory.service.impl;

import android.util.Log;
import cn.ajiehome.dispatch.memory.entity.FragmentMemory;
import cn.ajiehome.dispatch.memory.service.Fit;
import cn.ajiehome.dispatch.utils.MemoryUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Jie
 */
public class BestFit implements Fit {
    private static final String TAG = "BestFit";
    @Override
    public Integer distribution(Integer memorySize,Integer pid) {
        int index = -1;
        int count = 0;
        List<FragmentMemory> list = new ArrayList<>();//存储所有合适的碎片

        for (int i = 0; i < MemoryUtils.MEMORY.length; i++) {
            Integer memoryByte = MemoryUtils.MEMORY[i];
            if (memoryByte == 0) {
                if (index == -1) {
                    index = i;
                }
                count++;
            } else {
                if (count >= memorySize) {
                    list.add(new FragmentMemory(index, index + count));
                }
                index = -1;
                count = 0;
            }
        }
        Log.e(TAG, "distribution: "+index+"->"+count );
        if (index!=-1&&count>memorySize){
            list.add(new FragmentMemory(index, index + count));
        }
        Collections.sort(list, new Comparator<FragmentMemory>() {
            @Override
            public int compare(FragmentMemory o1, FragmentMemory o2) {
                return (o1.getEnd() - o1.getStart()) - (o2.getEnd() - o2.getStart());
            }
        });
        if (list.size() != 0) {
            MemoryUtils.occupyMemory(list.get(0).getStart(), memorySize,pid);
            index = list.get(0).getStart();
        } else {
            index = -1;
        }
        Log.e(TAG, "distribution: "+index );
        return index;
    }
}
