package com.serpider.service.megastream.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.serpider.service.megastream.R;
import com.serpider.service.megastream.database.DatabaseClient;
import com.serpider.service.megastream.model.Favorites;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final Context context;
    private final FragmentActivity activity;
    private final List<Favorites> favoritesList;

    public FavoriteAdapter(Context context, FragmentActivity activity, List<Favorites> favoritesList) {
        this.context = context;
        this.activity = activity;
        this.favoritesList = favoritesList;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorites, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        Favorites favorites = favoritesList.get(position);
        holder.titleItem.setText(favorites.getTitle_item());
        holder.countryItem.setText(favorites.getCountry_item());
        holder.yearItem.setText(favorites.getYear_item());
        Picasso.get().load(favorites.getPoster_item()).into(holder.imgPoster);

        holder.itemView.setOnClickListener(view -> {
            String item_unique = favorites.getUnique_item();
            SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("DETAILS_ITEM", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("ID_ITEM", item_unique);
            editor.apply();
            Navigation.findNavController(view).navigate(R.id.action_favoritesFragment_to_detailsFragment);
        });

        /*Delete*/
        holder.btnDelete.setOnClickListener(view -> deleteItem(favorites.getUnique_item()));

    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView titleItem, countryItem, yearItem;
        private final ImageButton btnDelete;
        private final ImageView imgPoster;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleItem = itemView.findViewById(R.id.txtFavoritesTitle);
            countryItem = itemView.findViewById(R.id.txtFavoritesCountry);
            yearItem = itemView.findViewById(R.id.txtFavoritesYear);
            btnDelete = itemView.findViewById(R.id.btnFavoritesDelete);
            imgPoster = itemView.findViewById(R.id.imgFavoritesPoster);
        }

    }
    private void deleteItem(String unique) {
        class DeleteFavorites extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                Favorites favorites = new Favorites();
                DatabaseClient
                        .getInstance(activity).getAppDatabase()
                        .favoritesDao()
                        .deleteById(unique);

                return null;
            }
        }
        DeleteFavorites deleteFavorites = new DeleteFavorites();
        deleteFavorites.execute();

    }
}
