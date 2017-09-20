package the.moviereview;

import android.content.Context;
import android.graphics.Movie;
import android.media.Image;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import the.moviereview.MovieTask.CastDetails;
import the.moviereview.MovieTask.MovieDetails;

/**
 * Created by Abdullah on 9/19/2017.
 */

public class RecyclerData extends RecyclerView.Adapter<RecyclerData.viewHolder> {


    private ArrayList<MovieDetails> theMovieCompleteDetails;
    private Context mainActivityContext;

    @Override
    public long getItemId(final int position)
    {
        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mainActivityContext, "Position: "+position, Toast.LENGTH_LONG).show();
            }
        });
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
    public void onBindViewHolder(viewHolder holder, int position) {
        MovieDetails tempMovieDetail = theMovieCompleteDetails.get(position);

        holder.genre.setText(tempMovieDetail.getMovieGenre());
        holder.releaseDate.setText(tempMovieDetail.getReleaseDate());
        holder.movieName.setText(tempMovieDetail.getMovieName());
        ArrayList<CastDetails> cast = tempMovieDetail.getCastDetails();
        String castAppendedData = "";
        for(int i=0;i<3;i++)
        {
            castAppendedData += cast.get(i).getCharacterNameInMovie() + " ("+cast.get(i).getRealName()+" )";
            if(i+1<3)
            {
                castAppendedData+="\n";
            }
        }
        holder.cast.setText(castAppendedData);
        Log.d("Handler",castAppendedData);
        Picasso.with(mainActivityContext).load(tempMovieDetail.getPictureURL()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return theMovieCompleteDetails.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView genre;
        TextView movieName;
        TextView cast;
        TextView releaseDate;
        ImageView poster;

        public viewHolder(View itemView)
        {
            super(itemView);

            genre = itemView.findViewById(R.id.genre);
            movieName = itemView.findViewById(R.id.movieName);
            cast = itemView.findViewById(R.id.cast);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            poster = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            try
            {
//                Log.d("myTag", textViewHead.getText().toString());
            }
            catch (Exception e)
            {
                Log.d("myTag", e.toString());
            }
        }
    }
}
