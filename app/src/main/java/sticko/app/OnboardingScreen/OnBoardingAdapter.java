package sticko.app.OnboardingScreen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sticko.app.R;

public class OnBoardingAdapter extends RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder> {
    private List<OnBoardingItem> onBoardingItems;

    public OnBoardingAdapter(List<OnBoardingItem> onBoardingItems) {
        this.onBoardingItems = onBoardingItems;
    }

    @Override
    public OnBoardingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnBoardingViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_container_onboarding,parent,false)
        );
    }

    @Override
    public void onBindViewHolder( OnBoardingViewHolder holder, int position) {
        holder.setOnBoardingData(onBoardingItems.get(position));


    }

    @Override
    public int getItemCount() {
        return onBoardingItems.size();
    }

    class OnBoardingViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDescription;
        private ImageView imageOnboarding;

        public OnBoardingViewHolder(@NonNull View itemView) {
            super(itemView);
            imageOnboarding = itemView.findViewById(R.id.image_boarding);
        }
        void setOnBoardingData(OnBoardingItem onBoardingItem){
           // textTitle.setText(onBoardingItem.getTitle());
            //textDescription.setText(onBoardingItem.getDescription());
           imageOnboarding.setImageResource(onBoardingItem.getImage());

        }
    }
}
