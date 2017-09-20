package the.moviereview.MovieTask;

import android.content.Context;

import java.util.ArrayList;

/**
 * This class is for containing all the details of a single crew member
 *
 * The reason for this class is that API does not support getting crew details and movie details in one response
 * so that's why this class is made
 *
 */



public class CastDetails {

    private Context context;
    private String cast_id;
    private String characterNameInMovie;
    private String realName;
    private String imageURL;
    private String gender;

    public CastDetails(Context context) {
        this.context = context;
        this.cast_id = "";
        this.characterNameInMovie = "";
        this.realName = "";
        this.imageURL = "";
        this.gender = "";
    }

    public String getCast_id() {
        return cast_id;
    }

    public void setCast_id(String cast_id) {
        this.cast_id = cast_id;
    }

    public String getCharacterNameInMovie() {
        return characterNameInMovie;
    }

    public void setCharacterNameInMovie(String characterNameInMovie) {
        this.characterNameInMovie = characterNameInMovie;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.contains("0"))
        {
            this.gender = "Not specified";
        }
        else if(gender.contains("1"))
        {
            this.gender = "Female";
        }
        else if(gender.contains("2"))
        {
            this.gender = "Male";
        }

    }

    public String GetAllData()
    {

        String allData =
        "Cast ID:                   "+ this.cast_id +"\n"+
        "Character Name In Movie:   "+ this.characterNameInMovie +"\n"+
        "Real Name:                 "+ this.realName+"\n"+
        "Image URL:                 "+ this.imageURL+"\n"+
        "Gender:                    "+ this.gender;
        return allData;

    }
    /*****************
     0 - Not specified
     1 - Female
     2 - Male
     ***************/
}
