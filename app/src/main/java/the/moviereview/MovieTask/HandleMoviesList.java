package the.moviereview.MovieTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import the.moviereview.R;

/**
 *
 *  This class gets JSON array and saves all data into arraylist and returns the arraylist
 *
 */


public class HandleMoviesList {

    private Context context;
    JSONArray JSON_MoviesList;

    public HandleMoviesList(JSONArray moviesList,Context context) {
        this.JSON_MoviesList = moviesList;
        this.context = context;
    }

    public ArrayList<MovieDetails> returnIndiviualMovieData()
    {
        ArrayList<MovieDetails> allMovieDetailsList = new ArrayList<>();

        int totalSizeOfJsonArray = JSON_MoviesList.length();
        Log.d("Handler","Size of JSON Array Before Work: "+totalSizeOfJsonArray);



        for(int i=0;i<totalSizeOfJsonArray;i++)
        {
            MovieDetails movieDetails = new MovieDetails(context);
            JSONObject JSON_MovieObject = null;
            try {
                JSON_MovieObject = (JSONObject) JSON_MoviesList.get(i);
                movieDetails.setMovieName(JSON_MovieObject.getString("title"));

                movieDetails.setMovieGenre(getAllGenresFrom_JSONarray_ToArrayList(JSON_MovieObject.getJSONArray("genre_ids")));
                movieDetails.setMovieID(JSON_MovieObject.getString("id"));
                movieDetails.setPictureURL(JSON_MovieObject.getString("poster_path"));
                movieDetails.setReleaseDate(JSON_MovieObject.getString("release_date"));
                movieDetails.setOverView(JSON_MovieObject.getString("overview"));
                allMovieDetailsList.add(movieDetails);
                Picasso.with(context).load(movieDetails.getPictureURL());
            } catch (JSONException e) {

            }
        }
        return allMovieDetailsList;
    }

    private ArrayList<String> getAllGenresFrom_JSONarray_ToArrayList(JSONArray genreJSON_array)
    {
        ArrayList<String> allGenres = new ArrayList<>();
        int size = genreJSON_array.length();

        for(int i=0;i<size;i++)
        {
            try {
                allGenres.add(String.valueOf(genreJSON_array.get(i)));
            } catch (JSONException e) {

            }
        }
        return allGenres;
    }

//
//    synchronized private void getMovieYoutubeLinkThroughMovieID(String movieID)
//    {
//        RequestQueue queue = Volley.newRequestQueue(context);
//        String url = context.getString(R.string.VIDEO_URL) +"/"+movieID+"/"
//                + "videos?api_key="+context.getString(R.string.APP_ID)+"&language=en-US";
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
//                {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                              String videoURL =  response.getJSONArray("results").getJSONObject(0).getString("key");
//                            openYoutubeLink(videoURL);
//
//                        } catch (JSONException e) {
//                            Toast.makeText(context, "Unable to get LINK\nPlease Try Later!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error)
//                    {
//                        Log.d("Handler","Error in 'getMovieYoutubeLinkThrougMovieID' function\n"+error.toString());
//                    }
//                });
//        // Add the request to the RequestQueue.
//        queue.add(jsObjRequest);
//    }

//    private void openYoutubeLink(String youtubeLink)
//    {
//        String completeYoutubeLink = "https://www.youtube.com/watch?v=" + youtubeLink;
//        Uri webpage = Uri.parse(completeYoutubeLink);
//        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
//        if (intent.resolveActivity(context.getPackageManager()) != null) {
//            context.startActivity(intent);
//        }
//
}


