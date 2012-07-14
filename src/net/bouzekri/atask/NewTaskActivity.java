package net.bouzekri.atask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NewTaskActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
    }
    
    public void cancelAction(View v) {
        finish();
    }
    
    public void saveAction(View v) {
        Toast.makeText(this, "Save Action.", Toast.LENGTH_SHORT).show();
    }
}
