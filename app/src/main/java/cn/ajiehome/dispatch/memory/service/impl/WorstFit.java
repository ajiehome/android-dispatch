package cn.ajiehome.dispatch.memory.service.impl;

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
public class WorstFit implements Fit {
    @Override
    public Integer distribution(Integer memorySize) {
        int index = -1;
        int count = 0;
        List<FragmentMemory> list = new ArrayList<>();
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
        if (index!=0&&count>memorySize){
            list.add(new FragmentMemory(index, index + count));
        }
        Collections.sort(list, new Comparator<FragmentMemory>() {
            @Override
            public int compare(FragmentMemory o1, FragmentMemory o2) {
                return (o2.getEnd() - o2.getStart()) - (o1.getEnd() - o1.getStart());
            }
        });

        if (list.size() != 0) {
            MemoryUtils.occupyMemory(list.get(0).getStart(), memorySize);
            index = list.get(0).getStart();
        } else {
            index = -1;
        }
        return index;
    }
}
