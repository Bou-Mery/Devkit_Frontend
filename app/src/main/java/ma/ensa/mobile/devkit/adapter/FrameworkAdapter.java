package ma.ensa.mobile.devkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Ajoutez cette importation pour Glide

import java.util.List;

import ma.ensa.mobile.devkit.R;
import ma.ensa.mobile.devkit.beans.Framework;

public class FrameworkAdapter extends RecyclerView.Adapter<FrameworkAdapter.MyViewHolder> {

    private Context context;
    private List<Framework> frameworks;

    public FrameworkAdapter(Context context, List<Framework> frameworks) {
        this.context = context;
        this.frameworks = frameworks;
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
        Framework framework = frameworks.get(position);
        holder.id.setText(String.valueOf(framework.getId()));
        holder.name.setText(framework.getName());
        holder.dependencies.setText(framework.getDependencies());
        holder.domain.setText(framework.getDomain());

        // Utiliser Glide pour charger l'image à partir de l'URL
        Glide.with(context)
                .load(framework.getImage_path())
                .into(holder.image); // Ajouter l'image au ImageView
    }

    @Override
    public int getItemCount() {
        return frameworks.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, dependencies, domain;
        ImageView image; // Déclaration de l'ImageView

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.idtxt);
            name = itemView.findViewById(R.id.nametxt);
            dependencies = itemView.findViewById(R.id.depentxt);
            domain = itemView.findViewById(R.id.domaintxt);
            image = itemView.findViewById(R.id.frameworkImage); // Récupération de l'ImageView
        }
    }
}



















//package ma.ensa.mobile.devkit.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import org.w3c.dom.Text;
//
//import java.util.List;
//
//import ma.ensa.mobile.devkit.R;
//import ma.ensa.mobile.devkit.beans.Framework;
//
//public class FrameworkAdapter extends RecyclerView.Adapter<FrameworkAdapter.MyViewHolder> {
//
//    private Context context ;
//    private List<Framework> frameworks;
//
//    public FrameworkAdapter(Context context, List<Framework> frameworks) {
//        this.context = context;
//        this.frameworks = frameworks;
//    }
//
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater =LayoutInflater.from(context);
//        View view = layoutInflater.inflate(R.layout.framework_item , parent , false);
//
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        holder.id.setText(frameworks.get(position).getId()+"");
//        holder.name.setText(frameworks.get(position).getName()+"");
//        holder.dependencies.setText(frameworks.get(position).getDependencies()+"");
//        holder.domain.setText(frameworks.get(position).getDomain()+"");
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return frameworks.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//
//        TextView id, name ,  dependencies , domain ;
//
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            id = itemView.findViewById(R.id.idtxt);
//            name = itemView.findViewById(R.id.nametxt);
//            dependencies = itemView.findViewById(R.id.depentxt);
//            domain = itemView.findViewById(R.id.domaintxt);
//
//
//        }
//    }
//}
