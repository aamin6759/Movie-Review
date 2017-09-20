package the.moviereview.MovieTask;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import the.moviereview.R;

/**
 * This class is for containing all the details of a single movie
 *
 * The reason for this class is that API does not support getting crew details and movie details in one response
 * so that's why this class is made
 */


public class MovieDetails {
    private String movieName;
    private String movieGenre;
    private String releaseDate;
    private String pictureURL;
    private String movieID;
    private String overView;
    private Context context;
    private ArrayList<CastDetails> castDetails;

    public MovieDetails(Context context) {
        this.context = context;
        this.castDetails = null;
        this.movieName = "";
        this.movieGenre = "";
        this.releaseDate = "";
        this.overView = "";
        this.movieID = "";
        this.pictureURL = context.getString(R.string.IMAGE_URL);
    }

    public String getOverView() {
        return overView;
    }

    public String getMovieID() {
        return movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setOverView(String overView) {

        this.overView = overView;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public void setMovieGenre(ArrayList<String> movieGenre) {
        int size = movieGenre.size();
        for(int i=0;i<size;i++)
        {
            String genreID = movieGenre.get(i);

            this.movieGenre += getGenreNameFrom_string_xml(context,genreID);

            if((i+1)<size)
            {
                this.movieGenre += ", ";
            }
        }
    }


    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL += pictureURL;
    }

    private String getGenreNameFrom_string_xml(Context context,String fullGenreID)//to get data from STRING.XML to get movie genre through string
    {
        String packageName = context.getPackageName();/*genre_10770*/
        int resId = context.getResources().getIdentifier(("genre_"+fullGenreID), "string", packageName);
        return context.getString(resId);
    }

    public ArrayList<CastDetails> getCastDetails() {
        return castDetails;
    }

    public void setCastDetails(ArrayList<CastDetails> castDetails) {
        this.castDetails = castDetails;
    }

    public void printAllArrayListCaseDetailsData(){
//    {
//        int totalSize = castDetails.size();
//        for(int i=0;i<totalSize;i++)
//        {
//            String temp = this.castDetails.get(i).GetAllData();
////            Log.d("Handler",temp);
//        }
    }
}
