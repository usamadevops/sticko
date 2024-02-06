package sticko.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sticko.app.Models.HomeModel;
import sticko.app.R;
import sticko.app.SvgDecoder.SvgDecoder;
import sticko.app.SvgDrawableTranscoder.SvgDrawableTranscoder;
import sticko.app.SvgSoftwareLayerSetter.SvgSoftwareLayerSetter;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeVH> {
    public Context context;
    List<HomeModel> array_image;
    private final ArrayList<Boolean> selected = new ArrayList<Boolean>();

    public HomeAdapter() {
    }

    public HomeAdapter(Context context, List<HomeModel> array_image) {
        this.context = context;
        List<HomeModel> items = new ArrayList<>();
        for (HomeModel hm : array_image) {
            if(hm.getVisible().contains("1"))
                items.add(hm);
        }
        this.array_image = items;
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homescreen_layout, parent, false);

        return new HomeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {

        String visible = array_image.get(position).getVisible();
        if (visible.contains("1")) {
            String PATH = "https://profile.sticko.fr";
            GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder = Glide.with(context)
                    .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                    .from(Uri.class)
                    .as(SVG.class)
                    .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                    .sourceEncoder(new StreamEncoder())
                    .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                    .decoder(new SvgDecoder())
                    .listener(new SvgSoftwareLayerSetter<Uri>());

            requestBuilder.diskCacheStrategy(DiskCacheStrategy.NONE)
                    .load(Uri.parse(PATH+array_image.get(position).getLogo_path()))
                    .into(holder.iv_profile);
            holder.iv_profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = array_image.get(position).getPrefix() + "" + array_image.get(position).getLink();
                    if (array_image.get(position).getPrefix().contains("url")){
                        Uri uri = Uri.parse("https://"+link); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        context.startActivity(intent);
                    }else {
                    Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    context.startActivity(intent);
                }}
            });
        } else
            holder.rl_item.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return array_image.size();
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        public ImageView iv_profile;
        public RelativeLayout rl_item;

        public HomeVH(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            rl_item = itemView.findViewById(R.id.rl_item);

        }
    }
}
