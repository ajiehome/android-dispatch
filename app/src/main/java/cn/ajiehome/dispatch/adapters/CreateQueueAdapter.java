package cn.ajiehome.dispatch.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.ajiehome.dispatch.R;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.ui.utils.BaseUiUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class CreateQueueAdapter extends BaseAdapter {
    private Context context;

    public CreateQueueAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return QueueUtils.createQueue.size();
    }

    @Override
    public PCB getItem(int position) {
        return QueueUtils.createQueue.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PCB item = getItem(position);
        if (item.getArrivalsTime()<QueueUtils.systemRunTimeAll){
            item.setCauseBlock("内存待分配");
        }else {
            item.setCauseBlock("进程未到达");
        }

        View view = BaseUiUtils.initItemEntity(convertView, context, parent, item);
        ItemViewAll itemViewAll = (ItemViewAll) view.getTag();
        itemViewAll.getHangButton().setVisibility(View.INVISIBLE);
        itemViewAll.getOperatingButton().setVisibility(View.INVISIBLE);
        return view;
    }

}
