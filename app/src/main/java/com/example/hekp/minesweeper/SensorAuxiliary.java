package com.example.hekp.minesweeper;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.hekp.minesweeper.Activities.GameScreenActivity;

/**
 * Created by HEKP on 05-Jan-16.
 */
public class SensorAuxiliary implements SensorEventListener {
    private float _initX,_initY,_initZ;
    private float _thresholdX,_thresholdY,_thresholdZ;

    private SensorManager _sensorManager;
    private Sensor _accelerometer;
    private Context _context;
    private GameScreenActivity _manager;

    public SensorAuxiliary(GameScreenActivity manager,float thresholdX,float thresholdY,float thresholdZ){
        _manager = manager;
        _thresholdX = thresholdX;
        _thresholdY = thresholdY;
        _thresholdZ = thresholdZ;
        _initX = Float.MAX_EXPONENT;
        _initY = Float.MAX_EXPONENT;
        _initZ = Float.MAX_EXPONENT;
        _context = (Context)manager;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(_initX==Float.MAX_EXPONENT ||
           _initY==Float.MAX_EXPONENT ||
           _initZ==Float.MAX_EXPONENT){
            _initX = event.values[0];
            _initY = event.values[1];
            _initZ = event.values[2];
        }

        if(Math.abs(_initX - event.values[0])>=_thresholdX ||
           Math.abs(_initY - event.values[1])>=_thresholdY ||
           Math.abs(_initZ - event.values[2])>=_thresholdZ)
            this.notifyTresholdPass();
    }
    
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onCreate(){
        _sensorManager = (SensorManager) _context.getSystemService(Context.SENSOR_SERVICE);
        Log.d("sensor","got manager");
        if (_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            // success! we have an _accelerometer

            _accelerometer = _sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            _sensorManager.registerListener(this, _accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // fai! we dont have an _accelerometer!
        }
    }

    public void onResume(){
        if(_sensorManager!=null)
            _sensorManager.registerListener(this, _accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onPause(){
        if(_sensorManager!=null)
            _sensorManager.unregisterListener(this);
    }

    public void notifyTresholdPass(){

        _manager.notifyTresholdPass();
    }
}
