package in.sethiya.bizzbots.bfsi.finces.merchant.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class AdapterHomePayments extends RecyclerView.Adapter<AdapterHomePayments.ViewHolder> {
    ArrayList<Integer> exploreImage;
    ArrayList<String> exploreName;
    Context context;

    public AdapterHomePayments(Context context, ArrayList<Integer> exploreImg, ArrayList<String> exploreName) {
        this.context = context;
        this.exploreImage = exploreImg;
        this.exploreName = exploreName;
    }

    @NonNull
    @Override
    public AdapterHomePayments.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_dash_item_payments, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHomePayments.ViewHolder holder, int position) {
        int res = exploreImage.get(position);
        holder.images.setImageResource(res);
        holder.text.setText(exploreName.get(position));

        holder.layout.setOnClickListener(v -> {
            switch (position){
                case 2:
                    IntentIntegrator intentIntegrator = new IntentIntegrator(LoginActivity.this);
                    intentIntegrator.setPrompt("Scan QR Code");
                    intentIntegrator.setOrientationLocked(false);
                    intentIntegrator.initiateScan();
                    break;
                /*
                case 0:
                    startActivity(new Intent(context, Activity.class));
                    break;

                case 1:
                    startActivity(new Intent(context, Activity.class));
                    break;

                case 2:
                    startActivity(new Intent(context, Activity.class));
                    break;

                case 3:
                    startActivity(new Intent(context, Activity.class));
                    break;

                case 4:
                    startActivity(new Intent(context, Activity.class));
                    break;

                case 5:
                    startActivity(new Intent(context, Activity.class));
                    break;

                case 6:
                    startActivity(new Intent(context, Activity.class));
                    break;

                 */
            }
        });
    }

    @Override
    public int getItemCount() {
        return exploreImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView images;
        TextView text;
        CardView layout;

        public ViewHolder(View view) {
            super(view);
            images = view.findViewById(R.id.iconImageView);
            text = view.findViewById(R.id.name_text);
            layout = view.findViewById(R.id.layout);
        }
    }
}