package sticko.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sticko.app.R;

public class AddNewLinksAdapter extends RecyclerView.Adapter<AddNewLinksAdapter.HomeVH> {
    Context context;
    ArrayList<Integer> array_image = new ArrayList<Integer>();

    public AddNewLinksAdapter(Context context, List<Integer> array_image) {
        this.context = context;
        this.array_image = (ArrayList<Integer>) array_image;

        array_image.add(R.drawable.tiktok_icon);
        array_image.add(R.drawable.insta_icon);
        array_image.add(R.drawable.whatsapp_icon);
        array_image.add(R.drawable.facebook_icon);
        array_image.add(R.drawable.youtube_icon);
        array_image.add(R.drawable.snapchat_icon);
        array_image.add(R.drawable.tiktok_icon);
        array_image.add(R.drawable.insta_icon);
        array_image.add(R.drawable.snapchat_icon);
        array_image.add(R.drawable.tiktok_icon);
        array_image.add(R.drawable.insta_icon);

    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homescreen_layout, parent, false);

        return new HomeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        holder.iv_profile.setImageResource(array_image .get(position));
        holder.iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        public ImageView iv_profile;
        public HomeVH(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
        }
    }
}
