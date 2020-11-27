package cn.ajiehome.dispatch.process.service.impl;

import android.util.Log;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.Dispatch;
import cn.ajiehome.dispatch.utils.QueueUtils;

import java.util.IllegalFormatCodePointException;

/**
 * @author Jie
 */
public class DispatchRRImpl implements Dispatch {
    private static final String TAG = "DispatchRRImpl";

    private Long fragmentTime = 5L;
    private Long runTime = 0L;

    public DispatchRRImpl(Long fragmentTime) {
        this.fragmentTime = fragmentTime;
    }
    public DispatchRRImpl() {
    }

    @Override
    public Long transfer() {
        QueueUtils.allocationMemory(QueueUtils.distributionIndex);
        //无运行队列，分配一个
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
        if (runTime>=fragmentTime){
            QueueUtils.requeueRRProcess();
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

    public Long getFragmentTime() {
        return fragmentTime;
    }

    public void setFragmentTime(Long fragmentTime) {
        this.fragmentTime = fragmentTime;
    }
}
