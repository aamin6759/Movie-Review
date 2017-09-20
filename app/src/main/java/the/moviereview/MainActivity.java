package the.moviereview;

import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import the.moviereview.MovieTask.GetAllCrewData;
import the.moviereview.MovieTask.HandleMoviesList;
import the.moviereview.MovieTask.MovieDetails;


/*******
 *
 *  This class gets name of movie to search
 *  then it gets all movie details and sends JSONarray list to 'HandleMoviesList' to save data in array list
 *
 */

public class MainActivity extends AppCompatActivity {

    EditText movieSearchedNamed;
    Button button;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lottieAnimationView =  findViewById(R.id.lottieAnimation);
        lottieAnimationView.setVisibility(View.GONE);

        long maxHeapSize = Runtime.getRuntime().maxMemory();

        new Picasso.Builder(this)
                .memoryCache(new LruCache((int)maxHeapSize/3))
                .build();

        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.abc);
        movieSearchedNamed = findViewById(R.id.movieSearchedName);

//        String javaPracc = "My name is abdullah amin";
//        String[] split = javaPracc.split("\\s+");
//
//        Log.d("new",split.length+" ");
//        for(int i=0;i<split.length;i++)
//        {
//            Log.d("new",split[i]+".");
//        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isMovieNameValid = true;
                String movieSearched = movieSearchedNamed.getText().toString();
                lottieAnimationView.setVisibility(View.VISIBLE);
                if(movieSearched.isEmpty())
                {
                    isMovieNameValid = false;
                    Toasty.error(MainActivity.this,"Please enter movie name!", Toast.LENGTH_SHORT, true).show();
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(findViewById(R.id.movieSearchedName));
                }
//                else
//                {
//                    String numRegex   = ".*[0-9].*";
//                    Toast.makeText(MainActivity.this, "The mathcing of number is: "+movieSearched.matches(numRegex), Toast.LENGTH_SHORT).show();
//                    isMovieNameValid = false;
//                    YoYo.with(Techniques.Shake)
//                            .duration(700)
//                            .playOn(findViewById(R.id.movieSearchedName));
//                }

                if(isMovieNameValid){
                    lottieAnimationView.setVisibility(View.VISIBLE);
                    lottieAnimationView.playAnimation();
                    lottieAnimationView.loop(true);
                    get_JSONArray_OfAllMovieWithName(movieSearched);

                }
            }
        });
    }

    synchronized private void get_JSONArray_OfAllMovieWithName(final String movieName)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String abc = "https://api.themoviedb.org/3/search/movie?api_key=2dbab0902aa24b6e7fe12db65256f7db&query=Jack+Reacher";
//        String url = getString(R.string.MOVIE_URL) +"?query=" +movieName+ "&api_key=" + getString(R.string.APP_ID);
        String url = getString(R.string.MOVIE_URL) +"?api_key="+getString(R.string.APP_ID)+"&query=";
        String query = "";
        String[] movieNameParts = movieName.split("\\s+");
        int totalSize = movieNameParts.length;
        for(int i=0;i<totalSize;i++)
        {
            query += movieNameParts[i];
            if(i+1<totalSize)
            {
                query+='+';
            }
        }
        Log.d("new",url+query);


        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, (url+query), null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(Integer.parseInt(response.getString("total_results"))<=0)
                            {
                                Toast.makeText(MainActivity.this, "Sorry, No data for movie named "+movieName.toUpperCase()+" can be found", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Log.d("Handler",response.toString());
                                JSONArray movieJSONarray = response.getJSONArray("results");
                                extractAlldata(movieJSONarray);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // TODO: error
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                ;
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    private void extractAlldata(JSONArray movieJSONarray)
    {
        HandleMoviesList handleMoviesList = new HandleMoviesList(movieJSONarray, MainActivity.this);
        final ArrayList<MovieDetails> allSavedDataOfMovies = handleMoviesList.returnIndiviualMovieData();
        GetAllCrewData getAllCrewData = new GetAllCrewData(MainActivity.this);
        getAllCrewData.getDataForCrewAndSaveIn(allSavedDataOfMovies);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                for(int i=0;i<allSavedDataOfMovies.size();i++)
//                {
//                    allSavedDataOfMovies.get(i).printAllArrayListCaseDetailsData();
//
//                }
                lottieAnimationView.pauseAnimation();
                lottieAnimationView.setVisibility(View.GONE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new RecyclerData(allSavedDataOfMovies,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        },10000);


//        for(int i=0;i<allSavedDataOfMovies.size();i++)
//        {
//            String data = allSavedDataOfMovies.get(i).getMovieName()+"\n"+
//                    allSavedDataOfMovies.get(i).getPictureURL()+"\n"+
//                    allSavedDataOfMovies.get(i).getMovieGenre()+"\n"+
//                    allSavedDataOfMovies.get(i).getReleaseDate()+"\n"+
//                    allSavedDataOfMovies.get(i).getOverView()+"\n"+
//                    allSavedDataOfMovies.get(i).getMovieID();
//            Log.d("Handler",data);
//        }


    }

}
