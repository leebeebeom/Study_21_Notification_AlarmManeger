package com.beebeom.a21_notification_alarmmaneger;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class NotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        //알림 만들기
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "알림");
        //클릭시 실행할 컴포넌트
        Intent intent1 = new Intent(context, MainActivity.class);
        //LAG_CANCEL_CURRENT : 이전에 생성한 PendingIntent는 취소하고 새로 만든다.
        //FLAG_UPDATE_CURRENT : 이미 생성된 PendingIntent가 존재하면 해당 Intent의 내용을 바꾼다.
        //FLAG_IMMUTABLE : 생성된 PendingIntent 는 수정 불가능하도록 한다.
        //FLAG_NO_CREATE : 생성된 PendingIntent 를 반환한다.(재사용 가능)
        //FLAG_ONE_SHOT : 해당 Flag로 생성한 PendingIntent 는 일회성이다.
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        //알림 제목
        builder.setContentTitle("알림 제목");
        //알림 내용
        builder.setContentText("알림 내용");
        //알림 아이콘
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 색
        builder.setColor(Color.GREEN);
        //진동 패턴
        long[] vibe = {0, 100, 200, 300, 400};
        builder.setVibrate(vibe);
        //알림 사운드
        //시스템 기본 알림음 가져오기
        Uri defaultRingtone = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE);
        builder.setSound(defaultRingtone);
        //터치시 알림 제거
        builder.setAutoCancel(true);

        //채널 만들기
        //이 채널에 들어오는 알림들은 이 채널의 설정이 적용됨.
        NotificationChannel channel = new NotificationChannel("알림", "알림", NotificationManager.IMPORTANCE_DEFAULT);
        //끝
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
        manager.notify(1, builder.build());
    }


}