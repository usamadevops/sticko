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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.HomeVH> {
    public static List<Integer> followingusers;
    Activity context;
    List<Following_model> followers_list;
    private String PATH = "https://profile.sticko.fr/";
    private final String UNFOLLOW_URL = "https://profile.sticko.fr/api/v1/user/";
    private final String DELETE_URL = "https://profile.sticko.fr/api/v1/user/removeFollower";
    private final String FOLLOW_URL = "https://profile.sticko.fr/api/v1/user/";

    private Loader loader;
    private Session session;
    String username;
    private int id, followingUserID;
    private String registerToken;
    public String token, followingList;

    public FollowersAdapter(Activity context, List<Following_model> followers_list) {
        this.context = context;
        this.followers_list = followers_list;
        loader = new Loader(context);
        session = new Session(context);
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
        View view = LayoutInflater.from(context).inflate(R.layout.followers_tap_layout, parent, false);

        return new HomeVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeVH holder, int position) {
        int id = followers_list.get(position).getId();
        username = followers_list.get(position).getUsername();
        String bio = followers_list.get(position).getBio();
        holder.tv_user_name.setText(followers_list.get(position).getUsername());
        holder.tv_user_name.setText(followers_list.get(position).getUsername());
        String logoPath = PATH + followers_list.get(position).getProfile_picture_path();
        Log.i("path", PATH + followers_list.get(position).getProfile_picture_path());
        Picasso.get().load(logoPath).placeholder(R.drawable.profile_icon).into(holder.user_dp);
        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    deleteFromServer(position, followers_list.get(position).getUsername());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        if (followingusers !=null){
        for (int i = 0; i < followingusers.size(); i++) {

            if (id == followingusers.get(i)) {
                holder.tv_follow.setText("Following");

            }
        }
        }
//
        holder.tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_follow.getText().equals("Following")) {
                    try {
                        unfollowUser(holder, position, followers_list.get(position).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else
                    followUser(position, holder, followers_list.get(position).getId());

            }
        });
        holder.user_dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_follow.getText().equals("Following")){
                    UserHomeScreen.following_user = true;
                }
                else {
                    UserHomeScreen.following_user = false;
                }
                UserHomeScreen.NAVIGATION_PATH = "";
                Intent intent = new Intent(context, UserHomeScreen.class);
                UserHomeScreen.userId = String.valueOf(followers_list.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tv_follow.getText().equals("Following")){
                    UserHomeScreen.following_user = true;
                }
                else
                    UserHomeScreen.following_user = false;
                UserHomeScreen.NAVIGATION_PATH = "";
                Intent intent = new Intent(context, UserHomeScreen.class);
                UserHomeScreen.userId = String.valueOf(followers_list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    private void followUser(int position, HomeVH holder, int id) {
        StringRequest getRequest = new StringRequest(Request.Method.GET, FOLLOW_URL + id + "/follow",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        holder.tv_follow.setText("Following");
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
        return followers_list.size();
    }

    public static class HomeVH extends RecyclerView.ViewHolder {
        public AppCompatButton btn_remove;
        public RelativeLayout rl_item;
        public CircleImageView user_dp;
        public TextView tv_user_name, tv_follow;

        public HomeVH(@NonNull View itemView) {
            super(itemView);
            user_dp = itemView.findViewById(R.id.user_dp);
            btn_remove = itemView.findViewById(R.id.btn_remove);
            tv_user_name = itemView.findViewById(R.id.tv_user_name);
            tv_follow = itemView.findViewById(R.id.tv_follow);
            rl_item = itemView.findViewById(R.id.rl_item);
        }
    }

    private void deleteItem(int position, HomeVH holder) {
        followers_list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, followers_list.size());
        holder.itemView.setVisibility(View.GONE);

    }

    public void deleteFromServer(int position, String username) throws JSONException {
        loader.showLoader();
        JSONObject map = new JSONObject();
        map.put("username", username);
        JsonObjectRequest sr = new JsonObjectRequest(Request.Method.POST, DELETE_URL, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                followers_list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, followers_list.size());
                Log.i("respones_update", String.valueOf(response));
                loader.hideLoader();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                loader.setVisibility(View.GONE);
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
//                       

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + registerToken);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        sr.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(sr);

    }

    private void unfollowUser(HomeVH holder, int position, int id) throws JSONException {
        StringRequest getRequest = new StringRequest(Request.Method.GET, UNFOLLOW_URL + id + "/unfollow",
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        // response
//                        following_list.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, following_list.size());
//                        Log.d("unfollow", response);
                        holder.tv_follow.setText("Follow");
                        UserHomeScreen.following_user = false;
                        UserHomeScreen.NAVIGATION_PATH = "";
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
}
