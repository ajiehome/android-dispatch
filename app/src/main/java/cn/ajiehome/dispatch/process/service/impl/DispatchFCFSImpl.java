package cn.ajiehome.dispatch.process.service.impl;

import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.Dispatch;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class DispatchFCFSImpl implements Dispatch {
    private static final String TAG = "DispatchFCFSImpl";

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

    }

    @Override
    public void runProcess(Integer index) {

    }

    @Override
    public void HangProcess(Integer position, Integer index) {

    }

    @Override
    public void wakeProcess(Integer index) {

    }

    @Override
    public void activationProcess(Integer index) {

    }
}
