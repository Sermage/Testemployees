package com.example.testemployees.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testemployees.R;
import com.example.testemployees.pojo.Employee;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private List<Employee> employees;

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.empoyee_item,parent,false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
    holder.textViewName.setText(employees.get(position).getFName());
    holder.textViewLastName.setText(employees.get(position).getLName());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        TextView textViewLastName;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=itemView.findViewById(R.id.textViewName);
            textViewLastName=itemView.findViewById(R.id.textViewLastName);
        }
    }
}
