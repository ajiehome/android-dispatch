package cn.ajiehome.dispatch.adapters;

import android.content.Context;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.process.service.ActivationUpdateUI;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.ui.utils.BaseUiUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class HangQueueAdapter extends BaseAdapter {
    private Context context = null;
    private ActivationUpdateUI activationUpdateUI = null;

    public HangQueueAdapter(Context context, ActivationUpdateUI activationUpdateUI) {
        this.context = context;
        this.activationUpdateUI = activationUpdateUI;
    }

    @Override
    public int getCount() {
        return QueueUtils.hangQueue.size();
    }

    @Override
    public PCB getItem(int position) {
        return QueueUtils.hangQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PCB item = getItem(position);
        switch (item.getHangType()){
            case 0:
                item.setCauseBlock("就绪挂起");
                break;
            case 1:
                item.setCauseBlock("运行挂起");
                break;
            case 2:
                item.setCauseBlock("阻塞挂起");
                break;
        }
        View view = BaseUiUtils.initItemEntity(convertView, context, parent, item);
        ItemViewAll itemViewAll = (ItemViewAll) view.getTag();
        itemViewAll.getHangButton().setVisibility(View.INVISIBLE);
        itemViewAll.getOperatingButton().setText("激活");
        itemViewAll.getOperatingButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //激活
                activationUpdateUI.activationProcess(position);
            }
        });
        return view;
    }
}
