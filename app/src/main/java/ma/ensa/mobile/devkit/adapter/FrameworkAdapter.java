package ma.ensa.mobile.devkit.adapter;

import android.content.Context;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.mobile.devkit.R;
import ma.ensa.mobile.devkit.beans.Framework;

public class FrameworkAdapter extends RecyclerView.Adapter<FrameworkAdapter.MyViewHolder> implements Filterable {

    private Context context;
    private List<Framework> frameworks;
    private List<Framework> frameworksFilter;
    private NewFilter mfilter;

    public FrameworkAdapter(Context context, List<Framework> frameworks) {
        this.context = context;
        this.frameworks = frameworks;
        this.frameworksFilter = new ArrayList<>(frameworks);
        mfilter = new NewFilter(this);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.framework_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Framework framework = frameworksFilter.get(position);
        holder.id.setText(String.valueOf(framework.getId()));
        holder.name.setText(framework.getName());
        holder.dependencies.setText(framework.getDependencies());
        holder.domain.setText(framework.getDomain());

        String base64Image = framework.getImage_path();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image.split(",")[1], Base64.DEFAULT);
            Glide.with(context)
                    .asBitmap()
                    .load(decodedString)
                    .into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.dotnet);
        }
    }

    @Override
    public int getItemCount() {
        return frameworksFilter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, dependencies, domain;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idtxt);
            name = itemView.findViewById(R.id.nametxt);
            dependencies = itemView.findViewById(R.id.depentxt);
            domain = itemView.findViewById(R.id.domaintxt);
            image = itemView.findViewById(R.id.imageRc);
        }
    }

    @Override
    public Filter getFilter() {
        return mfilter;
    }

    public class NewFilter extends Filter {
        private final RecyclerView.Adapter mAdapter;

        public NewFilter(RecyclerView.Adapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            frameworksFilter.clear();
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                frameworksFilter.addAll(frameworks);
            } else {
                final String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Framework framework : frameworks) {
                    if (framework.getName().toLowerCase().contains(filterPattern)) {
                        frameworksFilter.add(framework);
                    }
                }
            }
            results.values = frameworksFilter;
            results.count = frameworksFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            frameworksFilter = (List<Framework>) filterResults.values;
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
