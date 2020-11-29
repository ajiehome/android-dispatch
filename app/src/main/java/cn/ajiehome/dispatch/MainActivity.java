package cn.ajiehome.dispatch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ajiehome.dispatch.adapters.*;
import cn.ajiehome.dispatch.memory.service.UpdateDialogUI;
import cn.ajiehome.dispatch.process.service.*;
import cn.ajiehome.dispatch.process.service.impl.*;
import cn.ajiehome.dispatch.ui.view.ListViewForScrollView;
import cn.ajiehome.dispatch.utils.BaseUtils;
import cn.ajiehome.dispatch.utils.MemoryUtils;
import cn.ajiehome.dispatch.utils.QueueUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Timer timer = null;
    private ListViewForScrollView createQueue = null;
    private ListViewForScrollView readyQueue = null;
    private ListViewForScrollView runQueue = null;
    private ListViewForScrollView blockQueue = null;
    private ListViewForScrollView hangQueue = null;
    private ListViewForScrollView finishQueue = null;

    private Spinner spinner = null;
    private Spinner memorySpinner = null;
    private Button button = null;
    private Button memoryDetails = null;
    private TextView runTimeText = null;

    private CreateQueueAdapter createQueueAdapter = null;
    private ReadyQueueAdapter readyQueueAdapter = null;
    private RunQueueAdapter runQueueAdapter = null;
    private BlockQueueAdapter blockQueueAdapter = null;
    private HangQueueAdapter hangQueueAdapter = null;
    private FinishQueueAdapter finishQueueAdapter = null;

    private Dispatch dispatch = new DispatchFCFSImpl();

    private Float scale = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
        initEvent();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        scale =  metrics.density;
    }

    private void initEvent() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueueUtils.addProcess();
                updateUIAll();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Toast.makeText(MainActivity.this,"请选择算法",Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        dispatch = new DispatchFCFSImpl();
                        break;
                    case 2:
                        dispatch = new DispatchRRImpl();
                        break;
                    case 3:
                        dispatch = new DispatchHRRNImpl();
                        break;
                    case 4:
                        dispatch = new DispatchPriorityImpl();
                        break;
                    case 5:
                        dispatch = new DispatchMFQImpl();
                        break;
                    default:
                }
                if (position!=0){
                    showDialog();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        memoryDetails.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.details_memory_layout, null);
                TextView color = inflate.findViewById(R.id.memory_details_color);
                ViewGroup.LayoutParams layoutParams = color.getLayoutParams();
                layoutParams.width = (int) (MemoryUtils.occupyRate()*scale*450);
                color.setLayoutParams(layoutParams);
                TextView text = inflate.findViewById(R.id.memory_details_text);
                text.setText((int) (MemoryUtils.occupyRate()*100)+"%");
                ListView listView = inflate.findViewById(R.id.memory_details_list);
                MemoryDialogAdapter dialogAdapter = new MemoryDialogAdapter(MainActivity.this);
                listView.setAdapter(dialogAdapter);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(inflate);
                AlertDialog dialog = builder.show();
                MemoryUtils.updateDialogUI = new UpdateDialogUI() {
                    @Override
                    public void updateUI() {
                        runOnUiThread(new TimerTask() {
                            @Override
                            public void run() {
                                layoutParams.width = (int) (MemoryUtils.occupyRate()*scale*450);
                                color.setLayoutParams(layoutParams);
                                text.setText((int) (MemoryUtils.occupyRate()*100)+"%");
                                dialogAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                };
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        MemoryUtils.updateDialogUI = null;
                    }
                });
            }
        });
        memorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                QueueUtils.distributionIndex = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.process_count_dialog_layout, null);
        TextView title = inflate.findViewById(R.id.dialog_title);
        EditText edit = inflate.findViewById(R.id.dialog_edit);
        Button cancel = inflate.findViewById(R.id.dialog_cancel);
        Button ok = inflate.findViewById(R.id.dialog_ok);
        builder.setView(inflate);
        title.setText(spinner.getSelectedItem().toString());
        AlertDialog dialog = builder.show();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int processCount = Integer.parseInt(edit.getText().toString());
                if (processCount>20){
                    Toast.makeText(MainActivity.this,"请输入20以内的数",Toast.LENGTH_SHORT).show();
                }else {
                    timerStart(processCount);
                }
                dialog.dismiss();
            }
        });
    }

    private void timerStart(Integer processCount) {
        BaseUtils.clearStatic();
        QueueUtils.initProcess(processCount);
        if (timer!=null){
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Long transfer = dispatch.transfer();
                updateUIAll();
            }
        },1000,1000);
    }
    private void initAdapter() {
        createQueueAdapter = new CreateQueueAdapter(MainActivity.this);
        createQueue.setAdapter(createQueueAdapter);

        readyQueueAdapter = new ReadyQueueAdapter(MainActivity.this, new HangUpdateUI() {
            @Override
            public void updateUI(Integer index) {
                dispatch.HangProcess(1,index);
                updateUIAll();
            }
        }, new RunUpdateUI() {
            @Override
            public void runProcess(Integer index) {
                dispatch.runProcess(index);
                updateUIAll();
            }
        });
        readyQueue.setAdapter(readyQueueAdapter);

        runQueueAdapter = new RunQueueAdapter(MainActivity.this, new HangUpdateUI() {
            @Override
            public void updateUI(Integer index) {
                dispatch.HangProcess(0,index);
                updateUIAll();
            }
        }, new BlockUpdateUI() {
            @Override
            public void blockProcess() {
                dispatch.blockProcess();
                updateUIAll();
            }
        });
        runQueue.setAdapter(runQueueAdapter);
        blockQueueAdapter = new BlockQueueAdapter(MainActivity.this, new HangUpdateUI() {
            @Override
            public void updateUI(Integer index) {
                dispatch.HangProcess(2,index);
                updateUIAll();
            }
        }, new WakeUpdateUI() {
            @Override
            public void wakeProcess(Integer index) {
                dispatch.wakeProcess(index);
                updateUIAll();
            }
        });
        blockQueue.setAdapter(blockQueueAdapter);

        hangQueueAdapter = new HangQueueAdapter(MainActivity.this, new ActivationUpdateUI() {
            @Override
            public void activationProcess(Integer index) {
                dispatch.activationProcess(index);
                updateUIAll();
            }
        });
        hangQueue.setAdapter(hangQueueAdapter);
        finishQueueAdapter = new FinishQueueAdapter(MainActivity.this);
        finishQueue.setAdapter(finishQueueAdapter);
    }

    private void initView() {
        createQueue = findViewById(R.id.create_queue);
        readyQueue = findViewById(R.id.ready_queue);
        runQueue = findViewById(R.id.run_queue);
        blockQueue = findViewById(R.id.block_queue);
        hangQueue = findViewById(R.id.hang_queue);
        finishQueue = findViewById(R.id.finish_queue);

        spinner = findViewById(R.id.spinner_switch);
        button = findViewById(R.id.add_process_button);
        memorySpinner = findViewById(R.id.memory_switch);

        runTimeText = findViewById(R.id.system_run_time_all);
        memoryDetails = findViewById(R.id.show_memory_dialog);
    }

    private void updateUIAll(){
        runOnUiThread(new TimerTask() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                createQueueAdapter.notifyDataSetChanged();
                readyQueueAdapter.notifyDataSetChanged();
                runQueueAdapter.notifyDataSetChanged();
                blockQueueAdapter.notifyDataSetChanged();
                hangQueueAdapter.notifyDataSetChanged();
                finishQueueAdapter.notifyDataSetChanged();
                runTimeText.setText("系统运行时间："+QueueUtils.systemRunTimeAll.toString()+"S");
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer!=null){
            timer.cancel();
        }
        BaseUtils.clearStatic();
    }
}
