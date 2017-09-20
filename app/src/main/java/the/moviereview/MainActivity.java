package the.moviereview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    Button button;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;


    public static int totalLinksSaved = 0;//this is for the thread to wait until the youtube links have been saved.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.abc);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_JSONArray_OfAllMovieWithName("Jack Reacher");
            }
        });
    }

    synchronized private void get_JSONArray_OfAllMovieWithName(final String movieName)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.MOVIE_URL) +"?query=" +movieName+ "&api_key=" + getString(R.string.APP_ID);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()
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
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
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
                for(int i=0;i<allSavedDataOfMovies.size();i++)
                {
                    allSavedDataOfMovies.get(i).printAllArrayListCaseDetailsData();

                }
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                adapter = new RecyclerData(allSavedDataOfMovies,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        },1000);


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
