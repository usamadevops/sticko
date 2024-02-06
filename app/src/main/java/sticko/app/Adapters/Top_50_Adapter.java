package sticko.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import sticko.app.Models.Top_50_model;
import sticko.app.R;
import sticko.app.UserHomeScreen.UserHomeScreen;

public class Top_50_Adapter extends RecyclerView.Adapter<Top_50_Adapter.Top_50_VH> {
    private Context context;
    private List<Top_50_model> top_50_list;
    private String PATH = "https://profile.sticko.fr/";


    public Top_50_Adapter(Context context, List<Top_50_model> top_50_list) {
        this.context = context;
        this.top_50_list = top_50_list;
    }

    @NonNull
    @NotNull
    @Override
    public Top_50_VH onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_50_layout, parent, false);
        return new Top_50_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Top_50_VH holder, int position) {
        String count = String.valueOf(position+1);
        holder.serial_no.setText(count);
        holder.btn_score.setText(top_50_list.get(position).getScore()+"  Sticks");
        holder.tv_user_name.setText(top_50_list.get(position).getUsername());
        String logoPath = PATH+top_50_list.get(position).getProfile_picture_path();
        Picasso.get().load(logoPath).placeholder(R.drawable.profile_icon).into(holder.user_dp);

        holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserHomeScreen.class);
                UserHomeScreen.userId = String.valueOf(Integer.parseInt(top_50_list.get(position).getId()));
                UserHomeScreen.following_user = false;
                UserHomeScreen.NAVIGATION_PATH = "TOP_50";
                context.startActivity(intent);
            }
        });

        holder.user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UserHomeScreen.class);
                UserHomeScreen.userId = String.valueOf(Integer.parseInt(top_50_list.get(position).getId()));
                UserHomeScreen.following_user = false;
                UserHomeScreen.NAVIGATION_PATH = "TOP_50";
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return top_50_list.size();
    }

    public static class Top_50_VH extends RecyclerView.ViewHolder {
        public TextView serial_no,tv_user_name;
        public CircleImageView user_dp;
        public AppCompatButton btn_score;
    public Top_50_VH(@NonNull @NotNull View itemView) {
        super(itemView);
        serial_no = itemView.findViewById(R.id.serial_no);
        tv_user_name = itemView.findViewById(R.id.tv_user_name);
        user_dp = itemView.findViewById(R.id.user_dp);
        btn_score = itemView.findViewById(R.id.btn_score);
    }
}
}
