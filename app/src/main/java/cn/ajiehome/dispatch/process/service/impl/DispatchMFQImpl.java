package cn.ajiehome.dispatch.process.service.impl;

import android.util.Log;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.Dispatch;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class DispatchMFQImpl implements Dispatch {
    private Long fragmentTime = 5L;
    private Long runTime = 0L;

    public DispatchMFQImpl(Long fragmentTime) {
        this.fragmentTime = fragmentTime;
    }
    public DispatchMFQImpl() {
    }

    @Override
    public Long transfer() {
        QueueUtils.allocationMemory(QueueUtils.distributionIndex);
        QueueUtils.sortMFQProcess();

        if (QueueUtils.runQueue.size()==0){
            if (!QueueUtils.runProcess(0)){
                return ++QueueUtils.systemRunTimeAll;
            }
        }
        //正常运行
        PCB pcb = QueueUtils.runQueue.get(0);
        runTime++;
        pcb.setUsedCPUTime(pcb.getUsedCPUTime()+1);
        //运行完毕
        if (pcb.getUsedCPUTime()>=pcb.getNeedTime()){
            QueueUtils.finishProcess();
            runTime=0L;
        }
        //重新排队
        if (runTime>=fragmentTime*pcb.getProcessLevel()){
            QueueUtils.requeueMFQProcess();
            runTime=0L;
        }
        return ++QueueUtils.systemRunTimeAll;
    }

    @Override
    public void blockProcess() {
        QueueUtils.blockProcess();
        runTime=0L;
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

    public Long getFragmentTime() {
        return fragmentTime;
    }

    public void setFragmentTime(Long fragmentTime) {
        this.fragmentTime = fragmentTime;
    }
}
