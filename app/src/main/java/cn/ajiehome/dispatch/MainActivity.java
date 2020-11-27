package cn.ajiehome.dispatch;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ajiehome.dispatch.process.service.Dispatch;
import cn.ajiehome.dispatch.process.service.impl.DispatchFCFSImpl;
import cn.ajiehome.dispatch.process.service.impl.DispatchMFQImpl;
import cn.ajiehome.dispatch.process.service.impl.DispatchPriorityImpl;
import cn.ajiehome.dispatch.process.service.impl.DispatchRRImpl;
import cn.ajiehome.dispatch.utils.QueueUtils;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Long systemRunTimeAll = 0L;
    private Timer timer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = new Timer();

        QueueUtils.initProcess(5);
        Dispatch dispatch = new DispatchMFQImpl();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Long transfer = dispatch.transfer();
                if (QueueUtils.runQueue.size()!=0){
                    Log.e(TAG, "run: "+QueueUtils.runQueue.get(0) );
                }else {
                    Log.e(TAG, "run: "+transfer );
                }
            }
        },1000,1000);

    }

}
