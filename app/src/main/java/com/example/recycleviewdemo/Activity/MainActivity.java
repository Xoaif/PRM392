package com.example.recycleviewdemo.Activity;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.recycleviewdemo.Entity.Product;
import com.example.recycleviewdemo.Adapter.ProductAdapter;
import com.example.recycleviewdemo.R;
import com.example.recycleviewdemo.Interface.IClickItemProductListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvData;
//    private ImageView imageView;

    private ProductAdapter productAdapter;
    private DatabaseReference mCartReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        imageView = findViewById(R.id.cartButton);
        rcvData = findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvData.setLayoutManager(linearLayoutManager);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get the device token
                    String token = task.getResult();
                    Log.d(TAG, "FCM registration token: " + token);

                    // Save the token to your backend server to send push notifications later
                });


        productAdapter = new ProductAdapter(getListProduct(), new IClickItemProductListener() {
            @Override
            public void onClickItemProduct(Product product) {
                onClickMoveToDetail(product);
            }
        });
        rcvData.setAdapter(productAdapter);
//        mCartReference = FirebaseDatabase.getInstance().getReference("Product Cart")
//                .child("name"); // Replace "user_id" with the actual user ID
//        checkCartForProduct();

    }

//    private void checkCartForProduct() {
//        mCartReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // Cart exists, check if it contains a product
//                    boolean hasProductInCart = false;
//
//                    for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
//                        // Assuming the cart structure is: "cart_id" -> "product_id" -> true
//                        if (productSnapshot.getValue(Boolean.class)) {
//                            hasProductInCart = true;
//                            break;
//                        }
//                    }
//
//                    // Handle the result
//                    if (hasProductInCart) {
//                        // Cart contains a product
//                        // TODO: Show the appropriate message or perform needed actions
//                        Log.d(TAG, "Cart contains a product");
//                    } else {
//                        // Cart is empty
//                        // TODO: Show the appropriate message or perform needed actions
//                        Log.d(TAG, "Cart is empty");
//                    }
//                } else {
//                    // Cart does not exist
//                    // TODO: Show the appropriate message or perform needed actions
//                    Log.d(TAG, "Cart does not exist");
//                }
//            }
//
//
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // An error occurred while reading the data
//                // TODO: Handle the error appropriately
//                Log.e(TAG, "Error reading cart data", databaseError.toException());
//            }
//        });
//    }



    private List<Product> getListProduct() {
        List<Product> list = new ArrayList<>();
        list.add(new Product(R.drawable.mh1, "MÀN HÌNH VSP V2704S (27 INCH/FHD/IPS/75HZ/5MS/PINK)", "2.599.000₫", "Thông số sản phẩm\n"+
                "Kích thước: 27 inch\n" +
                "Độ phân giải: FHD 1920 x 1080\n" +
                "Tấm nền: IPS\n" +
                "Tần số quét: 75Hz\n" +
                "Thời gian phản hồi: 5Ms\n" +
                "Độ sáng: 250 nits\n" +
                "Tỉ lệ tương phản: 1000:1\n" +
                "Cổng kết nối: HDMI x1 / VGA x1/ Audio out"
       ));
        list.add(new Product(R.drawable.mh2, "MÀN HÌNH HKC MG27S9Q PINK (27 INCH/QHD/IPS/144HZ/1MS/300", "5.499.000₫", "Thông số sản phẩm\n" +
                "LED | Panel IPS | 27\" | 2560 x 1440 | 16:9| 144hz\n" +
                "Độ sáng: 300 cd/m2 | 1000:1 | 16.7 triệu màu| DCI-P3 90%| Anti-glare\n" +
                "T/g đáp ứng: 5ms( GTG), 1ms (MPRT)\n" +
                "Kết nối: DP| HDMI 1.4x2\n" +
                "Góc Nhìn: 178°(H)/178°(V)\n" +
                "Công suất: 47.5W | 0.5W (nghỉ)\n" +
                "Trọng lượng 7.43kg | 693 x 480 x 242/103mm\n" +
                "Treo tường: NA\n" +
                "Phụ kiện: Adapter|DP cable"));
        list.add(new Product(R.drawable.mh3, "MÀN HÌNH GAMING ASUS TUF VG27AQ3A (27 INCH/QHD/IPS/180HZ/1MS/LOA", "7.089.000₫", "Thông số sản phẩm\n" +
                "Kích thước: 27 inch\n" +
                "Độ phân giải: WQHD 2560x1440\n" +
                "Tấm nền: IPS\n" +
                "Tần số quét: 180Hz\n" +
                "Thời gian phản hồi: 1ms\n" +
                "Tích hợp loa: 2x 2W\n" +
                "Độ sáng: 250 nits\n" +
                "VESA: 100x100mm\n" +
                "Tỉ lệ tương phản: 1000:1\n" +
                "Cổng kết nối: 2x HDMI, 1x DisplayPort"));
        list.add(new Product(R.drawable.mh4, "MÀN HÌNH ASUS PROART PA247CV (23.8INCH/FHD/IPS/75HZ/5MS/U", "5.599.000₫", "Thông số sản phẩm\n" +
                "Kích thước: 23.8 inch\n" +
                "Tấm nền: IPS\n" +
                "Độ phân giải: FHD (1920x1080)\n" +
                "Tốc độ làm mới: 75Hz\n" +
                "Thời gian đáp ứng: 5ms(GTG)\n" +
                "Cổng kết nối: HDMI(v1.4) x 1, DisplayPort 1.2 x 2, USB-C x 1\n" +
                "Phụ kiện: Cáp nguồn, Cáp DisplayPort"));
        list.add(new Product(R.drawable.mh5, "MÀN HÌNH GAMING LG 32GN600-B (31.5INCH/QHD/VA/165HZ)", "6.699.000₫", "Thông số sản phẩm\n" +
                "Màn hình QHD 31,5 inch (2560 x 1440)\n" +
                "Tốc độ làm mới 165Hz\n" +
                "1ms Motion Blur Reduction\n" +
                "sRGB 95% (Thông thường) & HDR10\n" +
                "Công nghệ AMD FreeSync™ Premium\n" +
                "Thiết kế hầu như không có viền\n" +
                "Phụ kiện: Nguồn, cáp HDMI"));

        return list;
    }

    private void onClickMoveToDetail(Product product){
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_product", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}