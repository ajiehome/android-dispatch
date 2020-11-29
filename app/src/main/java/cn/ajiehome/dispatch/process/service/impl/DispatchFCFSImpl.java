package cn.ajiehome.dispatch.process.service.impl;

import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.Dispatch;
import cn.ajiehome.dispatch.utils.QueueUtils;

import java.util.Queue;

/**
 * @author Jie
 */
public class DispatchFCFSImpl implements Dispatch {

    @Override
    public Long transfer() {
        QueueUtils.allocationMemory(QueueUtils.distributionIndex);
        if (QueueUtils.runQueue.size()==0){
            if (!QueueUtils.runProcess(0)){
                return ++QueueUtils.systemRunTimeAll;
            }
        }
        PCB pcb = QueueUtils.runQueue.get(0);
        pcb.setUsedCPUTime(pcb.getUsedCPUTime()+1);
        if (pcb.getUsedCPUTime()>=pcb.getNeedTime()){
            QueueUtils.finishProcess();
        }
        return ++QueueUtils.systemRunTimeAll;
    }

    @Override
    public void blockProcess() {
        QueueUtils.blockProcess();
    }

    @Override
    public void runProcess(Integer index) {
        QueueUtils.runProcess(index);
    }

    @Override
    public void HangProcess(Integer position, Integer index) {
        QueueUtils.hangProcess(position,index);
    }

    @Override
    public void wakeProcess(Integer index) {
        QueueUtils.wakeProcess(index);
    }

    @Override
    public void activationProcess(Integer index) {
        QueueUtils.activationProcess(index);
    }
}
