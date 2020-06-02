package com.example.testemployees;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.testemployees.adapters.EmployeeAdapter;
import com.example.testemployees.api.ApiFactory;
import com.example.testemployees.api.ApiService;
import com.example.testemployees.pojo.Employee;
import com.example.testemployees.pojo.EmployeeResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;



public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMain;
    private EmployeeAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewMain = findViewById(R.id.recyclerViewMain);
        adapter = new EmployeeAdapter();
        recyclerViewMain.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMain.setAdapter(adapter);
        adapter.setEmployees(new ArrayList<Employee>());
        ApiFactory apiFactory = ApiFactory.getInstance();
        ApiService apiService = apiFactory.getApiService();
        compositeDisposable=new CompositeDisposable();
        disposable= apiService.getEmployees()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EmployeeResponse>() {
                    @Override
                    public void accept(EmployeeResponse employeeResponse) throws Exception {
                        adapter.setEmployees(employeeResponse.getResponse());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);

    }

    @Override
    protected void onDestroy() {
        if(compositeDisposable!=null){
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}
