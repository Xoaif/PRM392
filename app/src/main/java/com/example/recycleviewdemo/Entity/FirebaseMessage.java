package com.example.recycleviewdemo.Entity;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.recycleviewdemo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessage extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//        getFirebaseMessage(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
//
//    }
//    public void getFirebaseMessage(String title, String msg){
        boolean hasProductInCart = checkCartForProduct();
        // Build the notification based on the cart status
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Notification.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Notification Title")
                .setContentText(hasProductInCart ? "A product is in your cart!" : "Your cart is empty")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);



        // Display the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(0, builder.build());
    }

    private boolean checkCartForProduct() {
        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("Product Cart")
                .child("name"); // Replace "user_id" with the actual user ID

        final boolean[] hasProductInCart = {false};
        cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Cart exists, check if it contains a product
                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                        if (productSnapshot.getValue(Boolean.class)) {
                            hasProductInCart[0] = true;
                            break;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error, if any
            }
        });

        return hasProductInCart[0];
    }
}
