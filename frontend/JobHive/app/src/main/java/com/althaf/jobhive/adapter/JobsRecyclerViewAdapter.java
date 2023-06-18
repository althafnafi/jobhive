package com.althaf.jobhive.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.althaf.jobhive.R;
import com.althaf.jobhive.model.Job;

import java.util.ArrayList;
import java.util.List;

public class JobsRecyclerViewAdapter extends RecyclerView.Adapter<JobsRecyclerViewAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList<Job> jobs;
    ArrayList<Job> jobsListFull;
    private final JobsRecyclerViewInterface jobsRecyclerViewInterface;


    public  JobsRecyclerViewAdapter(Context context, ArrayList<Job> jobs, JobsRecyclerViewInterface viewInterface) {
        this.context = context;
        this.jobs = jobs;
        this.jobsListFull = new ArrayList<>(jobs);
        this.jobsRecyclerViewInterface = viewInterface;
    }

    @NonNull
    @Override
    public JobsRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.jobs_row_recycler_view, parent, false);

        return new JobsRecyclerViewAdapter.MyViewHolder(view, jobsRecyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull JobsRecyclerViewAdapter.MyViewHolder holder, int position) {
        Log.d("DEBUG_DATA", this.jobs.get(position).toString());
        holder.companyName.setText(this.jobs.get(position).getCompanyName());
        holder.jobTitle.setText(this.jobs.get(position).getJobTitle());
        holder.jobDesc.setText(truncateString(this.jobs.get(position).getJobDesc(), 40));
        holder.avgSalary.setText("$" + this.jobs.get(position).getSalaryAvg() + "/yr");
        holder.jobLocation.setText(this.jobs.get(position).getCity());
        holder.lastUpdated.setText("Created " + this.jobs.get(position).getCreatedAtDiff() +" days ago");

    }

    @Override
    public int getItemCount() {
        return this.jobs.size();
    }

    public static String truncateString(String input, int maxSize) {
        return input.length() > maxSize ? (input.substring(0, maxSize) + "...") : input;
    }

    @Override
    public Filter getFilter() {
        return jobsFilter;
    }

    private Filter jobsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Job> filteredList  = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(jobsListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Job job : jobsListFull) {
                    if (job.getJobTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(job);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                jobs.clear();
                jobs.addAll((List) filterResults.values);
                notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView jobTitle, jobDesc, avgSalary, jobLocation, companyName, lastUpdated;

        public MyViewHolder(@NonNull View itemView, JobsRecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitle);
            jobDesc = itemView.findViewById(R.id.jobDescription);
            avgSalary = itemView.findViewById(R.id.avgSalary);
            jobLocation = itemView.findViewById(R.id.cityLocation);
            companyName = itemView.findViewById(R.id.companyName);
            lastUpdated = itemView.findViewById(R.id.lastUpdated);

            itemView.setOnClickListener(view -> {
                if (recyclerViewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
