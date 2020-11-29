package cn.ajiehome.dispatch.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.ui.utils.BaseUiUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class FinishQueueAdapter extends BaseAdapter {
    private Context context = null;

    public FinishQueueAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return QueueUtils.finishQueue.size();
    }

    @Override
    public PCB getItem(int position) {
        return QueueUtils.finishQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = BaseUiUtils.initItemEntity(convertView, context, parent, getItem(position));
        ItemViewAll itemViewAll = (ItemViewAll) view.getTag();
        itemViewAll.getHangButton().setVisibility(View.INVISIBLE);
        itemViewAll.getOperatingButton().setVisibility(View.INVISIBLE);
        return view;
    }
}
