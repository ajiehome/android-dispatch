package cn.ajiehome.dispatch.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.HangUpdateUI;
import cn.ajiehome.dispatch.process.service.WakeUpdateUI;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.ui.utils.BaseUiUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class BlockQueueAdapter extends BaseAdapter {
    private Context context = null;
    private HangUpdateUI hangUpdateUI = null;
    private WakeUpdateUI wakeUpdateUI = null;

    public BlockQueueAdapter(Context context, HangUpdateUI hangUpdateUI, WakeUpdateUI wakeUpdateUI) {
        this.context = context;
        this.hangUpdateUI = hangUpdateUI;
        this.wakeUpdateUI = wakeUpdateUI;
    }

    @Override
    public int getCount() {
        return QueueUtils.blockQueue.size();
    }

    @Override
    public PCB getItem(int position) {
        return QueueUtils.blockQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = BaseUiUtils.initItemEntity(convertView, context, parent, getItem(position));
        ItemViewAll itemViewAll = (ItemViewAll) view.getTag();
        itemViewAll.getHangButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //挂起
                hangUpdateUI.updateUI(position);
            }
        });
        itemViewAll.getOperatingButton().setText("唤醒");
        itemViewAll.getOperatingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //唤醒
                wakeUpdateUI.wakeProcess(position);
            }
        });
        return view;
    }
}
