package the.moviereview.MovieTask;

import android.content.Context;

import android.util.Log;
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

import the.moviereview.R;

/**
 * Created by Abdullah on 9/18/2017.
 */

public class GetAllCrewData {

    private Context context;

    public GetAllCrewData(Context context) {
        this.context = context;
    }

    public void getDataForCrewAndSaveIn(ArrayList<MovieDetails> moviesInfoWithNoCastInfo)
    {
        int totalSizeOfArrayList = moviesInfoWithNoCastInfo.size();
        for(int i=0;i<totalSizeOfArrayList;i++)
        {
            getCastInfoFrom_API_andSaveItInOriginalMoviesInfo(moviesInfoWithNoCastInfo.get(i).getMovieID(),
                                                               i,moviesInfoWithNoCastInfo );
        }
    }


    //https://api.themoviedb.org/3/movie/75780?api_key=2dbab0902aa24b6e7fe12db65256f7db&append_to_response=casts
    synchronized private void getCastInfoFrom_API_andSaveItInOriginalMoviesInfo(String movieID, final int CastInfoPositionToAddCrewInfo
            , final ArrayList<MovieDetails> addCastInfo)
    {
        String URL = context.getString(R.string.CREW_URL)+"/"+movieID+"?api_key="+
                context.getString(R.string.APP_ID)+"&append_to_response=videos,casts&language=en-US";


        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL, null, new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray castJSONArrayData = response.getJSONObject("casts").getJSONArray("cast");
                            String URL = response.getJSONObject("videos").getJSONArray("results").getJSONObject(0).getString("key");
                            addCastInfo.get(CastInfoPositionToAddCrewInfo).setYoutubeURL(URL);
                            setCastDataInMovieArrayListBy(castJSONArrayData,CastInfoPositionToAddCrewInfo,addCastInfo);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Handler","Error in 'getCastInfoFrom_API_andSaveItInOriginalMoviesInfo' function\n"+error.toString());
                    }
                });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);

    }

    private void setCastDataInMovieArrayListBy(JSONArray castJSONArrayData,int CastInfoPositionToAddCrewInfo,ArrayList<MovieDetails> addCastInfo)
    {

        ArrayList<CastDetails> getCastDetailsFromJSONaray = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            try {
                CastDetails castDetails = new CastDetails(context);
                castDetails.setCast_id(castJSONArrayData.getJSONObject(i).getString("cast_id"));
                castDetails.setCharacterNameInMovie(castJSONArrayData.getJSONObject(i).getString("character"));
                castDetails.setGender(castJSONArrayData.getJSONObject(i).getString("gender"));
                castDetails.setImageURL(context.getString(R.string.IMAGE_URL) +"/"+ castJSONArrayData.getJSONObject(i).getString("profile_path"));
                castDetails.setRealName(castJSONArrayData.getJSONObject(i).getString("name"));
                getCastDetailsFromJSONaray.add(castDetails);
            }
            catch (JSONException e) {
            }
        }
        addCastInfo.get(CastInfoPositionToAddCrewInfo).setCastDetails(getCastDetailsFromJSONaray);

    }

}
