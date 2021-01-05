package com.example.kartm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Wheat extends AppCompatActivity {

    TextView title1,descp1;
    ImageView imageView1;
    Button add_to_cart1;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;
    String product_name="Wheat";
    TextView count;
    int c=0;
    ImageButton increment,decrement;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheat);
        title1=findViewById(R.id.wheat);
        imageView1=findViewById(R.id.imageview);
        descp1=findViewById(R.id.descp);
        add_to_cart1=findViewById(R.id.addtocart1);
        count=findViewById(R.id.count);
        increment=findViewById(R.id.increment);
        decrement=findViewById(R.id.decrement);
        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
        descp1.setText("Vitamins and minerals packed in wheat flour improve skin’s elasticity " +
                "and reduce wrinkles. Rich fiber content in wheat improves digestion and removes damaging " +
                "toxins from the body. Antioxidant ability of this innate ingredient protects your skin from harmful" +
                " UV rays and cancer causing agents.");
        add_to_cart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId=fAuth.getCurrentUser().getUid();
                DocumentReference documentReference=fstore.collection("product").document(userId);
                Map<String,Object> product=new HashMap<>();
                product.put("product",product_name);
                documentReference.set(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "onSuccess: Product Purchased "+userId);
                    }
                });
                startActivity(new Intent(getApplicationContext(),BuyingProductActivity.class));
            }
        });
        increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                count.setText(""+c);
            }
        });
        decrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c<=0){
                    count.setText("0");
                }
                else {
                    c--;
                    count.setText("" + c);
                }
            }
        });
    }
}