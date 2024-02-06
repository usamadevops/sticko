package sticko.app.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import sticko.app.Loader;
import sticko.app.Models.Following_model;
import sticko.app.R;
import sticko.app.Session;
import sticko.app.UserHomeScreen.UserHomeScreen;
import sticko.app.Utils.UtilsForPopups;

public class FollowingAdapter extends RecyclerView.Adapter<FollowingAdapter.HomeVH> {
    private final  String UNFOLLOW_URL = "https://profile.sticko.fr/api/v1/user/";
    Activity context;
    List<Following_model> following_list ;
    private String PATH = "https://profile.sticko.fr/";
    private Session session;
    private Loader loader;
    String token , registerToken;
    


    public FollowingAdapter(Activity context, List<Following_model> following_list) {
        this.context = context;
        this.following_list = following_list;
        session = new Session(context);
        loader = new Loader(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences("Login_data",
                Context.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");

        if (session.getToken().equals("")) {
            registerToken = token;
            Log.i("tokenreg", registerToken);
        } else {
            registerToken = session.getToken();
            Log.i("token", registerToken);
        }
    }

    @NonNull
    @Override
    public HomeVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.following_tab_layout, parent, false);

        return new HomeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {

        holder.tv_user_name.setText(following_list.get(position).getUsername());
        String logoPath = PATH+following_list.get(position).getProfile_picture_path();
        Log.i("path", PATH+following_list.get(position).getProfile_picture_path());
        Picasso.get().load(logoPath).placeholder(R.drawable.profile_icon).into(holder.user_dp);
        holder.user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , UserHomeScreen.class);
                UserHomeScreen.userId = String.valueOf(following_list.get(position).getId());
                UserHomeScreen.following_user = true;
                UserHomeScreen.NAVIGATION_PATH = "";
                context.startActivity(intent);
            }
        });
        holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , UserHomeScreen.class);
                UserHomeScreen.userId = String.valueOf(following_list.get(position).getId());
                UserHomeScreen.following_user = true;
                UserHomeScreen.NAVIGATION_PATH = "";
                context.startActivity(intent);
            }
        });
        holder.btn_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    unfollowUser( position,following_list.get(position).getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void unfollowUser(int position, int  id) throws JSONException{
        StringRequest getRequest = new StringRequest(Request.Method.GET, UNFOLLOW_URL+id+"/unfollow",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
                        following_list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, following_list.size());
                        Log.d("unfollow", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        loader.hideLoader();
                        if (error instanceof NetworkError) {
                            UtilsForPopups.alertPopup(context, "Alert","NetworkError");
                        } else if (error instanceof ServerError) {
                            UtilsForPopups.alertPopup(context, "Alert","ServerError");
                        } else if (error instanceof AuthFailureError) {
                            UtilsForPopups.alertPopup(context, "Alert","AuthFailureError");
                        } else if (error instanceof ParseError) {
                            UtilsForPopups.alertPopup(context, "Alert","ParseError");
                        } else if (error instanceof NoConnectionError) {
                            UtilsForPopups.alertPopup(context, "Alert","NoConnectionError");
                        } else if (error instanceof TimeoutError) {
                            UtilsForPopups.alertPopup(context, "Alert","Oops. Timeout error!");

                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + registerToken);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        Volley.newRequestQueue(context).add(getRequest);


    }

    @Override
    public int getItemCount() {
        return following_list.size();
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
