package ca.ualberta.cmput301f18t11.medicam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddDoctorNoteActivity extends AppCompatActivity {
    private EditText noteHeader;
    private  EditText noteComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor_note);
        noteHeader = findViewById(R.id.noteHeaderEditText);
        noteComment  = findViewById(R.id.noteCommentEditText);




    }

    public void passTextAndFinish(View view){
        String noteStr = noteHeader.getText().toString();
        String commentStr = noteComment.getText().toString();
        Toast.makeText(this,"Header : "+ noteStr +"\n" + "Comment: " + commentStr + "\n"+"Will be added",Toast.LENGTH_SHORT).show();
        finish();

    }
}
