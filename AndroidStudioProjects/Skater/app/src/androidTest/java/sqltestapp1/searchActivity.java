package sqltestapp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static com.example.notandi.sqltestapp1.R.id.addLocation;

public class searchActivity extends AppCompatActivity {

    EditText LocationEt, InfoEt, ExtraInfoEt  /*, IsInsideEt */ ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spot1);
        LocationEt = (EditText)findViewById(addLocation);
        InfoEt = (EditText)findViewById(R.id.addInfo);
        ExtraInfoEt = (EditText)findViewById(R.id.addExtra);
        //IsInsideEt = (EditText)findViewById(R.id.isInside);
    }
    public void SubmitParkToDB(View view ){
        String newLocation = LocationEt.getText().toString();
        String newInfo = InfoEt.getText().toString();
        String newExtraInfo = ExtraInfoEt.getText().toString();

        String type = "Create";

        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type, newLocation, newInfo);
    }
}
