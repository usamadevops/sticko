package sticko.app.Adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import sticko.app.R;

public class FollowingAdapter_tutorials extends RecyclerView.Adapter<FollowingAdapter_tutorials.HomeVH> {
    Context context;
    private int[] defaultImages = {R.drawable.avatar5, R.drawable.avatar4, R.drawable.avatar3, R.drawable.avatar2, R.drawable.avatar1};
    private String[] defaultNames = {"Léa", "Bessie Berry", "Gleb Kuznetsov", "Andrey Prokopenko", "Nick Herasimenko"};

    public FollowingAdapter_tutorials(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.following_tab_layout, parent, false);
        return new HomeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        holder.tv_user_name.setText(defaultNames[position]);
        Picasso.get().load(defaultImages[position]).into(holder.user_dp);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        public AppCompatButton btn_unfollow;
        public RelativeLayout rl_item;
        public TextView tv_user_name;
        public CircleImageView user_dp;

        public HomeVH(@NonNull View itemView) {
            super(itemView);
            btn_unfollow = itemView.findViewById(R.id.btn_unfollow);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            user_dp = itemView.findViewById(R.id.user_dp);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }

}
