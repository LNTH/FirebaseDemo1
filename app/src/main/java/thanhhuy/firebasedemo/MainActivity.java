package thanhhuy.firebasedemo;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import Class.Student;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.CompletionListener;
import com.google.android.gms.drive.internal.AddEventListenerRequest;
import com.google.android.gms.drive.query.internal.ComparisonFilter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    ArrayList<String> stList;
    ArrayAdapter adapter;
    Button btn,btnGet;
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("sinhvien");
    ChildEventListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        btn = (Button) findViewById(R.id.btn);
        btnGet = (Button)findViewById(R.id.btnGet);
        stList = new ArrayList<>();
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, stList);
        listView.setAdapter(adapter);
        new loadDuLieu().execute();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int i = random.nextInt();
                final String name = "Gia "+String.valueOf(i);
                myRef.push().setValue(new Student(name, "K19T2", "T123195"), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Toast.makeText(MainActivity.this, "Add thành công", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, stList.size()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    class loadDuLieu extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            listener = myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Student st = dataSnapshot.getValue(Student.class);
                    stList.add(st.getHoten());
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, stList.size()+"", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Toast.makeText(MainActivity.this, "dữ liệu mới đã được update", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                   adapter.notifyDataSetChanged();
                    Student st = dataSnapshot.getValue(Student.class);
                    stList.remove(st.getHoten());
                    Toast.makeText(MainActivity.this, stList.size()+"", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            return null;
        }
    }
}
