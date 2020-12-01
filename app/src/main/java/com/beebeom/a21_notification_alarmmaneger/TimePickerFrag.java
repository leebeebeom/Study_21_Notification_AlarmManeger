package com.beebeom.a21_notification_alarmmaneger;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFrag extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private AlarmManager mAlarmManager;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //알람매니져 설정
        //set(int Type, long triggerAtTime, PendingIntent operation)
        //한번만 실행
        //이미 같은 펜딩인텐트가 등록되어 있을 경우 기존 알람은 취소.(시간 제설정)
        //triggerAtTime 을 현재 시간보다 과거로 설정할 경우 바로 실행.
        //
        //setRepeating(int Type, long triggerAtTime, long interval, PendingIntent operation)
        //일정 간격을 실행
        //cancel(PendingIntent)로 명시적으로 취소하기 전까지 반복
        //Type이 WAKEUP 종류가 아니면 기기는 꺠어나지 않고, 실행은 대기되고, 실행할 수 있는 상황이 되면 실행.
        //
        //setInexactRepeating(int Type, long triggerAtTime, long interval, PendingIntent operation)
        //대략적인 시간으로 알람 설정
        //예를 들어 정각에 알람을 실행해야 하지만 정확한 시간이 아니어도 될 때
        //나머지는 setRepeating 과 동일

        //int Type 종류
        //RTC_WAKEUP : 2번째 인자로 넘겨준 시간에 맞게 알림이 울리며 잠들어 있는 기기도 깨웁니다.
        //RTC : RTC_WAKEUP 과 유사하지만 해당 타입은 잠들어 있는 기기는 깨우지 않습니다.
        //ELAPSED_REALTIME_WAKEUP : 기기가 부팅된 시점으로부터 '2번째 인자로 받은 시간이 경과된 후'에 알람이 울리며 잠들어 있는 기기도 깨웁니다.
        //ELAPSED_REALTIME : ELAPSED_REALTIME_WAKEUP과 유사하지만 잠들어 있는 기기는 깨우지 않습니다.

        //long interval 종류
        //INTERVAL_ 등의 상수로 정의

        mAlarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        //현재 시간 얻기
        //인자를 HOUR 로 쓸경우 0-12시간으로 가져옴
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        //타임피커 다이얼로그 만들어주기.
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), this, hours, minutes, true);

        return timePickerDialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //인자로 들어온 시간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        //알람매니져 설정
        //그 뭐더라 어느 버전부터 어플이 마음대로 액티비티를 호출할 수 없게됨.
        Intent intent = new Intent(getContext(), NotiReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
