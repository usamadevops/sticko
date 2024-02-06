package sticko.app.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.List;

import sticko.app.Connections.AddLinksActivity;
import sticko.app.Models.ListOfConnections_model;
import sticko.app.R;
import sticko.app.SvgDecoder.SvgDecoder;
import sticko.app.SvgDrawableTranscoder.SvgDrawableTranscoder;
import sticko.app.SvgSoftwareLayerSetter.SvgSoftwareLayerSetter;
public class Connections_Adapter extends RecyclerView.Adapter<Connections_Adapter.HomeVH> {
    public Context context;
    List<ListOfConnections_model> array_image;
    private String PATH = "https://profile.sticko.fr";

    public Connections_Adapter(Context context, List<ListOfConnections_model> array_image) {
        this.context = context;
        this.array_image = array_image;

    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.homescreen_layout, parent, false);
        return new HomeVH(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        String prefix = array_image.get(position).getPrefix();
        int social_media_id = array_image.get(position).getId();
        String name = array_image.get(position).getName();
        String placeholder = array_image.get(position).getPlaceholder();
        String logoPath = PATH + array_image.get(position).getLogo_path();
        Log.i("path", PATH + array_image.get(position).getLogo_path());
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
                .load(Uri.parse(logoPath))
                .into(holder.iv_profile);
        // Picasso.get().load(logoPath).placeholder(R.drawable.whatsapp_icon).into(holder.iv_profile);
        //Glide.with(context).load(logoPath).into(holder.iv_profile);

        holder.iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLinksActivity.logoPath = logoPath;
                AddLinksActivity.name = name;
                AddLinksActivity.social_media_id = social_media_id;
                AddLinksActivity.placeholder =placeholder;
                Intent intent = new Intent((FragmentActivity) context, AddLinksActivity.class);
                context.startActivity(intent);
                ((FragmentActivity) context).finish();
//
            }
        });
    }

    @Override
    public int getItemCount() {
        return array_image.size();
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        public ImageView iv_profile;

        public HomeVH(@NonNull View itemView) {
            super(itemView);
            iv_profile = itemView.findViewById(R.id.iv_profile);
        }
    }
}
