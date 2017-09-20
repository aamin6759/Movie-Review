package the.moviereview.MovieTask;

import android.content.Context;
import android.support.v7.widget.SearchView;
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
    private String youtubeURL;

    private Context context;

    private ArrayList<CastDetails> castDetails;

    public MovieDetails(Context context) {
        this.context = context;
        this.youtubeURL = "";
        this.castDetails = null;
        this.movieName = "";
        this.movieGenre = "";
        this.releaseDate = "";
        this.overView = "";
        this.movieID = "";
        this.pictureURL = context.getString(R.string.IMAGE_URL);
    }

    public String getYoutubeURL() {
        return youtubeURL;
    }

    public void setYoutubeURL(String youtubeURL) {
        this.youtubeURL = youtubeURL;
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
        switch (fullGenreID)
        {
           default:
               return context.getString(R.string.genre_12);
        }
//            <string name="genre_28">Action</string>
//    <string name="genre_12">Adventure</string>
//    <string name="genre_16">Animation</string>
//    <string name="genre_35">Comedy</string>
//    <string name="genre_80">Crime</string>
//    <string name="genre_99">Documentary</string>
//    <string name="genre_18">Drama</string>
//    <string name="genre_10751">Family</string>
//    <string name="genre_14">Fantasy</string>
//    <string name="genre_36">History</string>
//    <string name="genre_27">Horror</string>
//    <string name="genre_10402">Music</string>
//    <string name="genre_9648">Mystery</string>
//    <string name="genre_10749">Romance</string>
//    <string name="genre_878">Science Fiction</string>
//    <string name="genre_10770">TV Movie</string>
//    <string name="genre_53">Thriller</string>
//    <string name="genre_10752">War</string>
//    <string name="genre_37">Western</string>
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
