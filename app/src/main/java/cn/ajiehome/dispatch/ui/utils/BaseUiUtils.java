package cn.ajiehome.dispatch.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cn.ajiehome.dispatch.R;
import cn.ajiehome.dispatch.process.entity.PCB;
import cn.ajiehome.dispatch.ui.entiry.ItemViewAll;
import cn.ajiehome.dispatch.utils.QueueUtils;

/**
 * @author Jie
 */
public class BaseUiUtils {
    @SuppressLint("SetTextI18n")
    public static View initItemEntity(View convertView, Context context, ViewGroup parent, PCB item) {
        ItemViewAll itemViewAll = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_item_layout, parent,false);
            itemViewAll = new ItemViewAll();
            itemViewAll.setProcessName(convertView.findViewById(R.id.item_name_process));
            itemViewAll.setPid(convertView.findViewById(R.id.item_pid_process));
            itemViewAll.setProcessRank(convertView.findViewById(R.id.item_rank_process));
            itemViewAll.setArrivalsTime(convertView.findViewById(R.id.item_arrivals_time));
            itemViewAll.setNeedTime(convertView.findViewById(R.id.item_need_time));
            itemViewAll.setUsedCPUTime(convertView.findViewById(R.id.item_used_cpu_time));
            itemViewAll.setBlockCause(convertView.findViewById(R.id.item_block_cause));
            itemViewAll.setStartMemory(convertView.findViewById(R.id.item_start_memory));
            itemViewAll.setSizeMemory(convertView.findViewById(R.id.item_size_memory));
            itemViewAll.setProcessLevel(convertView.findViewById(R.id.item_level_process));
            itemViewAll.setHangButton(convertView.findViewById(R.id.item_hang_button));
            itemViewAll.setOperatingButton(convertView.findViewById(R.id.item_operating_button));
            convertView.setTag(itemViewAll);
        } else {
            itemViewAll = (ItemViewAll) convertView.getTag();
        }
        itemViewAll.getProcessName().setText(item.getProcessName());
        itemViewAll.getPid().setText(item.getProcessId().toString());
        itemViewAll.getProcessRank().setText(item.getPriorityRank().toString());
        itemViewAll.getArrivalsTime().setText(item.getArrivalsTime().toString());
        itemViewAll.getNeedTime().setText(item.getNeedTime().toString());
        itemViewAll.getUsedCPUTime().setText(item.getUsedCPUTime().toString());
        itemViewAll.getBlockCause().setText(item.getCauseBlock());
        itemViewAll.getStartMemory().setText(item.getStartIndexMemory()==-1?"待分配":item.getStartIndexMemory().toString());
        itemViewAll.getSizeMemory().setText(item.getNeedMemorySize().toString());
        itemViewAll.getProcessLevel().setText(item.getProcessLevel().toString());
        itemViewAll.getHangButton().setText("挂起");
        itemViewAll.getOperatingButton().setText("等待");

        return convertView;
    }
}
