package the.moviereview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import the.moviereview.MovieTask.CastDetails;
import the.moviereview.MovieTask.MovieDetails;

/**
 * Created by Abdullah on 9/19/2017.
 */

public class RecyclerData extends RecyclerView.Adapter<RecyclerData.viewHolder>{


    private ArrayList<MovieDetails> theMovieCompleteDetails;
    private Context mainActivityContext;

    @Override
    public long getItemId(final int position)
    {
        return position;
    }

    public RecyclerData(ArrayList<MovieDetails> movieCompleteDetails, Context context) {
        this.theMovieCompleteDetails = movieCompleteDetails;
        this.mainActivityContext = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_movies_list, parent , false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(final viewHolder holder, int position) {

        final MovieDetails tempMovieDetail = theMovieCompleteDetails.get(position);

        holder.genre.setText(tempMovieDetail.getMovieGenre());
        holder.releaseDate.setText(tempMovieDetail.getReleaseDate());
        holder.movieName.setText(tempMovieDetail.getMovieName());
        ArrayList<CastDetails> cast = tempMovieDetail.getCastDetails();
        String castAppendedData = "";
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openYoutubeAndShow(tempMovieDetail.getYoutubeURL());
                }
            });


        int getAllCasstUpto = 0;
        if(cast!=null) {
            if (cast.size() > 3) {
                getAllCasstUpto = 3;
            } else {
                getAllCasstUpto = cast.size();
            }
            for (int i = 0; i < getAllCasstUpto; i++) {
                castAppendedData += cast.get(i).getCharacterNameInMovie() + " (" + cast.get(i).getRealName() + " )";
                if (i + 1 < 3) {
                    castAppendedData += "\n";
                }
            }
            holder.cast.setText(castAppendedData);
        }
        else
        {
            holder.cast.setText("No Cast Info");

        }
        holder.progressBar.setVisibility(View.VISIBLE);
        if(!tempMovieDetail.getPictureURL().isEmpty()) {
            Picasso.with(mainActivityContext).load(tempMovieDetail.getPictureURL()).into(holder.poster, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    if (holder.progressBar != null) {
                        holder.progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onError() {
                    Picasso.with(mainActivityContext).load(R.mipmap.noimg).into(holder.poster);
                    holder.progressBar.setVisibility(View.GONE);
                }
            });
        }
        else
        {
            Log.d("new","URL is empty");
        }

    }

    @Override
    public int getItemCount() {
        return theMovieCompleteDetails.size();
    }



    private void openYoutubeAndShow(String url)
    {
        if(url.isEmpty() || url == null)
        {
            Toasty.info(mainActivityContext,"Trailer not Available!", Toast.LENGTH_SHORT, true).show();
        }
        else {
            String youtube_url = "https://www.youtube.com/watch?v=" + url;
            Uri webpage = Uri.parse(youtube_url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(mainActivityContext.getPackageManager()) != null) {
                mainActivityContext.startActivity(intent);
            }
        }
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView genre;
        TextView movieName;
        TextView cast;
        TextView releaseDate;
        ImageView poster;
        LottieAnimationView progressBar;
        ImageButton imageButton;

        public viewHolder(View itemView)
        {
            super(itemView);
            imageButton = itemView.findViewById(R.id.imageButton);
            genre = itemView.findViewById(R.id.genre);
            movieName = itemView.findViewById(R.id.movieName);
            cast = itemView.findViewById(R.id.cast);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            poster = itemView.findViewById(R.id.theImageView);
            progressBar = itemView.findViewById(R.id.progressBar);
            progressBar.setVisibility(View.GONE);

        }
    }
}
