package cn.ajiehome.dispatch.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.BlockUpdateUI;
import cn.ajiehome.dispatch.process.service.HangUpdateUI;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.ui.utils.BaseUiUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class RunQueueAdapter extends BaseAdapter {
    private Context context = null;
    private HangUpdateUI hangUpdateUI = null;
    private BlockUpdateUI blockUpdateUI = null;

    public RunQueueAdapter(Context context, HangUpdateUI hangUpdateUI, BlockUpdateUI blockUpdateUI) {
        this.context = context;
        this.hangUpdateUI = hangUpdateUI;
        this.blockUpdateUI = blockUpdateUI;
    }

    @Override
    public int getCount() {
        return QueueUtils.runQueue.size();
    }

    @Override
    public PCB getItem(int position) {
        return QueueUtils.runQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PCB item = getItem(position);
        item.setCauseBlock("进程运行中");
        View view = BaseUiUtils.initItemEntity(convertView, context, parent, item);
        ItemViewAll itemViewAll = (ItemViewAll) view.getTag();
        itemViewAll.getHangButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //挂起
                hangUpdateUI.updateUI(position);
            }
        });
        itemViewAll.getOperatingButton().setText("阻塞");
        itemViewAll.getOperatingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //阻塞
                blockUpdateUI.blockProcess();
            }
        });
        return view;
    }
}
