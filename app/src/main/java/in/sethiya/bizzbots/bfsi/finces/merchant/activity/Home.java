package in.sethiya.bizzbots.bfsi.finces.merchant.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import in.sethiya.bizzbots.bfsi.finces.merchant.R;
import in.sethiya.bizzbots.bfsi.finces.merchant.activity.main.EnterAadhaarNoActivity;
import in.sethiya.bizzbots.bfsi.finces.merchant.databinding.ActivityHomeBinding;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.LocaleHelper;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.session.SessionHandler;
import in.sethiya.bizzbots.bfsi.finces.merchant.helper.session.User;

public class Home extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SessionHandler session;
    private final Context context = this;
    private final static String TAG = "Home";
    private String currentLanguage = "en", currentLang;
    private Locale myLocale;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        currentLanguage = getIntent().getStringExtra(currentLang);

        session = new SessionHandler(getApplicationContext());
        user = session.getUserDetails();

        if(session.isLoggedIn()){
            binding.navDrawerLayout.userName.setText(user.getName());
            binding.navDrawerLayout.userName.setText(user.getName());

            binding.toolbar.usernameLayout.setVisibility(View.VISIBLE);
            binding.toolbar.loginTextLayout.setVisibility(View.INVISIBLE);
            // Drawer
        //    binding.navDrawerLayout.loginBtn.setVisibility(View.INVISIBLE);
        }else{
            binding.toolbar.loginTextLayout.setVisibility(View.VISIBLE);
            binding.toolbar.usernameLayout.setVisibility(View.INVISIBLE);

            // Drawer
            binding.navDrawerLayout.userName.setVisibility(View.INVISIBLE);
        //    binding.navDrawerLayout.usernamePlaceHolder.setVisibility(View.INVISIBLE);
         //   binding.navDrawerLayout.loginBtn.setVisibility(View.VISIBLE);
        }

        loadHome();

        binding.toolbar.addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, EnterAadhaarNoActivity.class));
                finish();
            }
        });
    }

    private void loadHome() {
        ArrayList<Integer> dashboardImage = new ArrayList<>(Arrays.asList(
                R.drawable.img_4,
                R.drawable.img_2,
                R.drawable.img_8,
                R.drawable.img_7,
                R.drawable.img_3,
                R.drawable.img_4));

        ArrayList<String> dashboardName = new ArrayList<>(Arrays.asList(
                "Akila",
                "Santhiya",
                "Prasanth",
                "Santhosh",
                "Sangeetha",
                "Priya"
        ));

        ArrayList<String> dashboardAddress = new ArrayList<>(Arrays.asList(
                "Address 635b, 5th Main Road, Ramnagar South, Madipakkam, Madipakkam, Sholinganallur, Kanchipuram, Tamil Nadu, India - 600091",
                "Address 635b, 5th Main Road, Ramnagar South, Madipakkam, Madipakkam, Sholinganallur, Kanchipuram, Tamil Nadu, India - 600091",
                "Address 635b, 5th Main Road, Ramnagar South, Madipakkam, Madipakkam, Sholinganallur, Kanchipuram, Tamil Nadu, India - 600091",
                "Address 635b, 5th Main Road, Ramnagar South, Madipakkam, Madipakkam, Sholinganallur, Kanchipuram, Tamil Nadu, India - 600091",
                "Address 635b, 5th Main Road, Ramnagar South, Madipakkam, Madipakkam, Sholinganallur, Kanchipuram, Tamil Nadu, India - 600091",
                "Address 635b, 5th Main Road, Ramnagar South, Madipakkam, Madipakkam, Sholinganallur, Kanchipuram, Tamil Nadu, India - 600091"
        ));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        binding.toolbar.recyclerView.setLayoutManager(linearLayoutManager);
        binding.toolbar.recyclerView.setHasFixedSize(true);
        binding.toolbar.recyclerView.setItemViewCacheSize(20);
        Adapter adapter6 = new Adapter(context, dashboardImage, dashboardName, dashboardAddress);
        binding.toolbar.recyclerView.setAdapter(adapter6);
    }

    public void ClickMenu(View view){
        openDrawer(binding.drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(binding.drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        ArrayList<Integer> exploreImage;
        ArrayList<String> exploreName;
        ArrayList<String> exploreAddress;
        Context context;

        public Adapter(Context context, ArrayList<Integer> courseImg, ArrayList<String> courseName, ArrayList<String> mAddress) {
            this.context = context;
            this.exploreImage = courseImg;
            this.exploreName = courseName;
            this.exploreAddress = mAddress;
        }

        @NonNull
        @Override
        public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_dash_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            int res = exploreImage.get(position);
         //   holder.images.setImageResource(res);
            Glide
                    .with(context)
                    .load(res)
                    .apply(RequestOptions.circleCropTransform())
                    .placeholder(R.drawable.placeholder_person)
                    .into(holder.images);

            holder.text.setText(exploreName.get(position));

            holder.address.setText(exploreAddress.get(position));

            holder.layout.setOnClickListener(v -> {
                switch (position){

                }
            });
        }

        @Override
        public int getItemCount() {
            return exploreImage.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView images;
            TextView text, address;
            CardView layout;

            public ViewHolder(View view) {
                super(view);
                images = view.findViewById(R.id.iconImageView);
                text = view.findViewById(R.id.name_text);
                address = view.findViewById(R.id.address_text);
                layout = view.findViewById(R.id.layout);
            }
        }
    }
}