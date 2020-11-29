package cn.ajiehome.dispatch.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.ajiehome.dispatch.R;
import cn.ajiehome.dispatch.memory.entity.MemoryDetailsUsed;
import cn.ajiehome.dispatch.utils.MemoryUtils;

/**
 * @author Jie
 */
public class MemoryDialogAdapter extends BaseAdapter {
    private Context context = null;

    public MemoryDialogAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return MemoryUtils.memoryUsedDetails.size();
    }

    @Override
    public MemoryDetailsUsed getItem(int position) {
        return MemoryUtils.memoryUsedDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MemoryDetailsDialog memoryDetailsDialog = null;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.memory_item_layout,parent,false);
            memoryDetailsDialog = new MemoryDetailsDialog();
            memoryDetailsDialog.pid = convertView.findViewById(R.id.memory_dialog_pid);
            memoryDetailsDialog.startMemory = convertView.findViewById(R.id.memory_dialog_start);
            memoryDetailsDialog.endMemory = convertView.findViewById(R.id.memory_dialog_end);
            convertView.setTag(memoryDetailsDialog);
        }else {
            memoryDetailsDialog = (MemoryDetailsDialog) convertView.getTag();
        }
        MemoryDetailsUsed item = getItem(position);
        memoryDetailsDialog.pid.setText(item.getPid().toString());
        memoryDetailsDialog.startMemory.setText(item.getStart().toString());
        memoryDetailsDialog.endMemory.setText(item.getEnd().toString());
        return convertView;
    }
    private static class MemoryDetailsDialog{
        TextView pid;
        TextView startMemory;
        TextView endMemory;
    }
}
