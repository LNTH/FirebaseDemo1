package thanhhuy.firebasedemo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import Class.Student;

public class SplashScreen extends AppCompatActivity {

     DatabaseReference ref = FirebaseDatabase.getInstance().getReference("sinhvien");
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        bundle = new Bundle();
        new loadSinhVien().execute();

    }

    class loadSinhVien extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            final ArrayList<String> stList = new ArrayList<>();
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Student st = ds.getValue(Student.class);
                        stList.add(st.getHoten());
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(SplashScreen.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            });
            return stList;
        }

        @Override
        protected void onPostExecute(ArrayList<String> aList) {
            super.onPostExecute(aList);
            final Intent mainActivityIntent = new Intent(SplashScreen.this, MainActivity.class);
            bundle.putStringArrayList("hotenArray",aList);
            mainActivityIntent.putExtras(bundle);
            CountDownTimer countDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    ref.onDisconnect();
                    startActivity(mainActivityIntent);
                }
            }.start();
        }
    }
}
