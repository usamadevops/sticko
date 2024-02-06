package sticko.app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.google.android.material.textfield.TextInputLayout;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import sticko.app.Connections.Connections;
import sticko.app.Connections.InfoMessageActivity;
import sticko.app.Models.HomeModel;
import sticko.app.R;
import sticko.app.SvgDecoder.SvgDecoder;
import sticko.app.SvgDrawableTranscoder.SvgDrawableTranscoder;
import sticko.app.SvgSoftwareLayerSetter.SvgSoftwareLayerSetter;

public class ActiveLinksAdapter extends RecyclerView.Adapter<ActiveLinksAdapter.HomeVH> {
    private final String PATH = "https://profile.sticko.fr";
    public Context context;
    List<HomeModel> itemList;

    List<Integer> deleteSocialMedia_list = new ArrayList<Integer>();
    List<String> update_socialUsername_list = new ArrayList<String>();
    String visible;


    public ActiveLinksAdapter(Context context, List<HomeModel> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.active_links_tap_layout, parent, false);

        return new HomeVH(view,new MyCustomEditTextListener());
    }

    @Override
    public void onViewAttachedToWindow(@NonNull HomeVH holder) {
        ((HomeVH) holder).enableTextWatcher();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull HomeVH holder) {
        ((HomeVH) holder).disableTextWatcher();
    }


    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        HomeModel hv = itemList.get(position);

        holder.edt_social_name.setText(hv.getLink());
        holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());

        update_socialUsername_list.add(String.valueOf(hv));
        visible = hv.getVisible();
        if (visible.contains("1")) {
            holder.layout_new_pass.setEndIconDrawable(context.getDrawable(R.drawable.ic_baseline_remove_red_eye_24));
        } else {
            holder.layout_new_pass.setEndIconDrawable(context.getDrawable(R.drawable.ic_baseline_visibility_off_24));
        }

        holder.ib_delete.setOnClickListener(view -> {
            Connections.static_isUpdate = false;
            deleteSocialMedia_list.add(hv.getId());
            Connections.deleteList = deleteSocialMedia_list;
            Log.i("list", String.valueOf(deleteSocialMedia_list));
            deleteItem(position, holder);
        });

        holder.ib_info.setOnClickListener(view -> {
            Intent intent = new Intent(context, InfoMessageActivity.class);
            context.startActivity(intent);
        });

        holder.layout_new_pass.setEndIconOnClickListener(v -> {
            if (!hv.getVisible().contains("1")) {
                hv.setVisible("1");
                holder.layout_new_pass.setEndIconDrawable(context.getDrawable(R.drawable.ic_baseline_remove_red_eye_24));
            } else {
                holder.layout_new_pass.setEndIconDrawable(context.getDrawable(R.drawable.ic_baseline_visibility_off_24));
                hv.setVisible("0");
            }
        });

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
                .load(Uri.parse(PATH + hv.getLogo_path()))
                .into(holder.iv_profile);
        Log.i("listis", String.valueOf(itemList.get(position).getVisible()));

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public List<SocialMedia> update() {
        List<SocialMedia> list = new ArrayList<>();

        for (HomeModel obj : itemList) {
            SocialMedia m = new SocialMedia();
            m.setId(String.valueOf(obj.getId()));
            m.setSocial_media_id(Integer.parseInt(obj.getSocial_media_id()));
            m.setVisible(obj.getVisible().contains("1") ? true : false);
            m.setSocial_media_username(obj.getLink());
            list.add(m);
        }
        return list;
    }

    public class HomeVH extends RecyclerView.ViewHolder {
        public ImageButton ib_delete;
        public ImageButton ib_info;
        public EditText edt_social_name;
        public TextInputLayout layout_new_pass;
        public ImageView iv_profile;

        public MyCustomEditTextListener myCustomEditTextListener;

        public HomeVH(@NonNull View itemView,MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            ib_delete = itemView.findViewById(R.id.ib_delete);
            ib_info = itemView.findViewById(R.id.ib_info);
            edt_social_name = itemView.findViewById(R.id.edt_social_name);
            layout_new_pass = itemView.findViewById(R.id.layout_new_pass);
            iv_profile = itemView.findViewById(R.id.iv_profile);
            this.myCustomEditTextListener = myCustomEditTextListener;
        }

        void enableTextWatcher() {
            edt_social_name.addTextChangedListener(myCustomEditTextListener);
        }

        void disableTextWatcher() {
            edt_social_name.removeTextChangedListener(myCustomEditTextListener);
        }
    }

    private void deleteItem(int position, HomeVH holder) {
        itemList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, itemList.size());

        //holder.itemView.setVisibility(View.GONE);
    }

    public class SocialMedia {
        private String id;
        private String social_media_username;
        private int social_media_id;
        private boolean visible;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSocial_media_username() {
            return social_media_username;
        }

        public void setSocial_media_username(String social_media_username) {
            this.social_media_username = social_media_username;
        }

        public int getSocial_media_id() {
            return social_media_id;
        }

        public void setSocial_media_id(int social_media_id) {
            this.social_media_id = social_media_id;
        }

        public boolean isVisible() {
            return visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }


    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
           // Log.i("Update-Before",itemList.get(position).getLink());
            itemList.get(position).setLink( charSequence.toString());
           // Log.i("Update-After",itemList.get(position).getLink());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }
}
