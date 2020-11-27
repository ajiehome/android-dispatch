package cn.ajiehome.dispatch.process.service.impl;

import android.util.Log;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.Dispatch;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class DispatchMFQImpl implements Dispatch {
    private static final String TAG = "DispatchMFQImpl";
    private Long fragmentTime = 5L;
    private Long runTime = 0L;


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
