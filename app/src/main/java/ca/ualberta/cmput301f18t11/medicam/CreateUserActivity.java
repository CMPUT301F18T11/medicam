package ca.ualberta.cmput301f18t11.medicam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateUserActivity extends AppCompatActivity {
    private EditText enteredUserId;
    private EditText enteredPhoneNumber;
    private EditText enteredEmail;
    private EditText enteredAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);


        enteredUserId = findViewById(R.id.newUserUserIdEdit);
        enteredPhoneNumber = findViewById(R.id.newUserPhoneEdit);
        enteredEmail = findViewById(R.id.newUserEmialEdit);
        enteredAddress = findViewById(R.id.newUserAddressEdit);

        createUserAndSignIn();
    }



    public void createUserAndSignIn (){
        Button createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateUserActivity.this,CareProviderActivity.class);
                startActivity(intent);
            }
        });

    }
}
