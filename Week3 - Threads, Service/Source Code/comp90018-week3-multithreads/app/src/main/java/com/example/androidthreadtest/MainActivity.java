package com.example.androidthreadtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Modifier;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_TEXT_RUNNABLE = 1;
    public static final int UPDATE_TEXT_THREAD = 2;
    public static final int UPDATE_TEXT_FUTURETASK = 3;
    public static final int UPDATE_TEXT_HANDLER_SEND = 4;
    public static final int UPDATE_TEXT_HANDLER_RECEIVE = 5;

    private TextView text;
    private FutureTask<String> future;
    private HandlerThread exampleHandlerThread;
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;

    // Message Handler of main thread
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT_RUNNABLE: {
                    text.setText("Receive the message from other threads implements Runnable Interface!");
                    break;
                }
                case UPDATE_TEXT_THREAD: {
                    text.setText("Receive the message from other threads extends Thread!");
                    break;
                }
                case UPDATE_TEXT_FUTURETASK: {
                    text.setText((String) msg.obj);
                    break;
                }
                case UPDATE_TEXT_HANDLER_RECEIVE: {
                    text.setText("Receive the message from other threads extends HandlerThread!");
                    exampleHandlerThread.quit();
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        // register for receiving EventBus
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        Button crash = (Button) findViewById(R.id.crash_button);
        Button changeTextRunnable = (Button) findViewById(R.id.change_text_runnable);
        Button changeTextThread = (Button) findViewById(R.id.change_text_threads);
        Button changeTextFutureTask = (Button) findViewById(R.id.change_text_future_task);
        Button changeTextAsyncTask = (Button) findViewById(R.id.change_text_asynctask);
        Button changeTextHandlerThread = (Button) findViewById(R.id.change_text_handler_thread);
        Button changeTextIntentService = (Button) findViewById(R.id.change_text_intent_service);
        Button changeTextEventBus = (Button) findViewById(R.id.change_text_event_bus);

        crash.setOnClickListener(this);
        changeTextRunnable.setOnClickListener(this);
        changeTextThread.setOnClickListener(this);
        changeTextFutureTask.setOnClickListener(this);
        changeTextAsyncTask.setOnClickListener(this);
        changeTextHandlerThread.setOnClickListener(this);
        changeTextIntentService.setOnClickListener(this);
        changeTextEventBus.setOnClickListener(this);

        // To show the thread id
        Log.d("Future Tasks", "Thread Number: " + Thread.currentThread().getId());
        // To show the reference of TextView
        Log.d("Future Tasks", "TextView Object Reference: " + text);
    }

    @Override
    public void onStop() {
        // Unregister EventBus to avoid Android OOM (out-of-memory)
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // to show the result of operating UI in other thread;
            case R.id.crash_button: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Future Tasks", "Thread(Done method): " + Thread.currentThread().getId());
                        Log.d("Future Tasks", "TextView Object Reference(Done method): " + text);
                        text.setText("Crash Happens!");
                    }
                }).start();
                break;
            }
            // to show the implementation of runnable interface
            case R.id.change_text_runnable: {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Send the Message object towards UI threads
                        Message message = new Message();
                        message.what = UPDATE_TEXT_RUNNABLE;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            }
            // to show the implementation of extending Thread class
            case R.id.change_text_threads: {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Send the Message object towards UI threads
                        Message message = new Message();
                        message.what = UPDATE_TEXT_THREAD;
                        handler.sendMessage(message);
                    }
                }.start();
                break;
            }
            // to show the implementation of future task
            case R.id.change_text_future_task: {
                ExecutorService executor = new ScheduledThreadPoolExecutor(2);
                // Implement Callable for futureTask, detail explained in Tutorial slides
                future = new FutureTask<String>(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        Thread.sleep(2000);
                        return "Receive the message from other threads by FutureTask!";
                    }
                })
                //  Called when the worker thread finish their work (from isDone() of Future Class)
                {
                    @Override
                    protected void done() {
                        String textContent = null;
                        try {
                            textContent = future.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        Message message = new Message();
                        message.what = UPDATE_TEXT_FUTURETASK;
                        message.obj = textContent;
                        handler.sendMessage(message);
                    }
                };
                executor.execute(future);
                break;
            }
            // to show the implementation of asyncTask
            case R.id.change_text_asynctask: {
                new AsyncTask<Integer, Integer, String>() {
                    @Override
                    protected String doInBackground(Integer... integers) {
                        for (int i = 0; i < integers[0]; i++) {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            publishProgress(i);
                        }
                        return "Receive the message from other threads by AsyncTask!";
                    }

                    @Override
                    protected void onProgressUpdate(Integer... progress) {
                        text.setText("Progress: " + progress[0] * 10 + "%");
                        super.onProgressUpdate(progress);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        text.setText(result);
                    }
                }.execute(10);
                break;
            }
            // to show the implementation of handler thread
            case R.id.change_text_handler_thread: {
                exampleHandlerThread = new HandlerThread("example");
                exampleHandlerThread.start();

                Message msg = new Message();
                msg.what = UPDATE_TEXT_HANDLER_SEND;

                new Handler(exampleHandlerThread.getLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message message) {
                        switch (message.what) {
                            case UPDATE_TEXT_HANDLER_SEND: {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Message msg = new Message();
                                msg.what = UPDATE_TEXT_HANDLER_RECEIVE;
                                handler.sendMessage(msg);
                                break;
                            }
                        }
                        return false;
                    }
                }).sendMessage(msg);
                break;
            }
            // to show the implementation of intentService
            case R.id.change_text_intent_service: {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(ExampleIntentService.ACTION_EXAMPLE_END);
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
                broadcastReceiver = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String message = intent.getStringExtra(ExampleIntentService.RESULT_PARAM);
                        text.setText(message);
                        localBroadcastManager.unregisterReceiver(broadcastReceiver);
                    }
                };
                localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
                ExampleIntentService.startExample(this);
                break;
            }
            // to show the example of communication between threads and activity by EventBus
            case R.id.change_text_event_bus:{
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        EventBus.getDefault().post(new MessageEvent("Receive the message from other threads extends Thread through EventBus!"));
                    }
                }.start();
                break;
            }
            default:
                break;
        }
    }

    // The method to process when receiving MessageEvent
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        text.setText(event.message);
    }

}

