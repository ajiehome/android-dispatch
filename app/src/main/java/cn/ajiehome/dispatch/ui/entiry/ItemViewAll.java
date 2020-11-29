package cn.ajiehome.dispatch.ui.entiry;

import android.widget.Button;
import android.widget.TextView;

/**
 * @author Jie
 */
public class ItemViewAll {
    private TextView processName;
    private TextView pid;
    private TextView processRank;
    private TextView arrivalsTime;
    private TextView needTime;
    private TextView usedCPUTime;
    private TextView blockCause;
    private TextView startMemory;
    private TextView sizeMemory;
    private TextView processLevel;
    private Button hangButton;
    private Button operatingButton;

    public TextView getProcessName() {
        return processName;
    }

    public void setProcessName(TextView processName) {
        this.processName = processName;
    }

    public TextView getPid() {
        return pid;
    }

    public void setPid(TextView pid) {
        this.pid = pid;
    }

    public TextView getProcessRank() {
        return processRank;
    }

    public void setProcessRank(TextView processRank) {
        this.processRank = processRank;
    }

    public TextView getArrivalsTime() {
        return arrivalsTime;
    }

    public void setArrivalsTime(TextView arrivalsTime) {
        this.arrivalsTime = arrivalsTime;
    }

    public TextView getNeedTime() {
        return needTime;
    }

    public void setNeedTime(TextView needTime) {
        this.needTime = needTime;
    }

    public TextView getUsedCPUTime() {
        return usedCPUTime;
    }

    public void setUsedCPUTime(TextView usedCPUTime) {
        this.usedCPUTime = usedCPUTime;
    }

    public TextView getBlockCause() {
        return blockCause;
    }

    public void setBlockCause(TextView blockCause) {
        this.blockCause = blockCause;
    }

    public TextView getStartMemory() {
        return startMemory;
    }

    public void setStartMemory(TextView startMemory) {
        this.startMemory = startMemory;
    }

    public TextView getSizeMemory() {
        return sizeMemory;
    }

    public void setSizeMemory(TextView sizeMemory) {
        this.sizeMemory = sizeMemory;
    }

    public TextView getProcessLevel() {
        return processLevel;
    }

    public void setProcessLevel(TextView processLevel) {
        this.processLevel = processLevel;
    }

    public Button getHangButton() {
        return hangButton;
    }

    public void setHangButton(Button hangButton) {
        this.hangButton = hangButton;
    }

    public Button getOperatingButton() {
        return operatingButton;
    }

    public void setOperatingButton(Button operatingButton) {
        this.operatingButton = operatingButton;
    }
}
