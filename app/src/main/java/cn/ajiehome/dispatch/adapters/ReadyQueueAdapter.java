package cn.ajiehome.dispatch.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.HangUpdateUI;
import cn.ajiehome.dispatch.process.service.RunUpdateUI;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.ui.utils.BaseUiUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class ReadyQueueAdapter extends BaseAdapter {
    private Context context = null;
    private HangUpdateUI hangUpdateUI = null;
    private RunUpdateUI runUpdateUI = null;

    public ReadyQueueAdapter(Context context, HangUpdateUI hangUpdateUI, RunUpdateUI runUpdateUI) {
        this.context = context;
        this.hangUpdateUI = hangUpdateUI;
        this.runUpdateUI = runUpdateUI;
    }

    @Override
    public int getCount() {
        return QueueUtils.readyQueue.size();
    }

    @Override
    public PCB getItem(int position) {
        return QueueUtils.readyQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PCB item = getItem(position);
        item.setCauseBlock("CPU待分配");
        View view = BaseUiUtils.initItemEntity(convertView, context, parent, item);
        ItemViewAll itemViewAll = (ItemViewAll)view.getTag();
        itemViewAll.getHangButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //挂起回调
                hangUpdateUI.updateUI(position);
            }
        });
        itemViewAll.getOperatingButton().setText("运行");
        itemViewAll.getOperatingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //触发运行
                runUpdateUI.runProcess(position);
            }
        });
        return view;
    }
}
