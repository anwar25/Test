package in.eweblabs.careeradvance.Entity;

import android.database.Cursor;

import in.eweblabs.careeradvance.StaticData.DbConstraints;

/**
 * Created by Akash.Singh on 1/8/2016.
 */
public class RecentSearch extends DbConstraints{

    int id;
    String keyword;
    String location;
    public RecentSearch(){
    }
    public RecentSearch(Cursor cursor){
        setId(cursor.getInt(cursor.getColumnIndex(CA_ID)));
        setKeyword(cursor.getString(cursor.getColumnIndex(CA_KEYWORD)));
        setLocation(cursor.getString(cursor.getColumnIndex(CA_LOCATION)));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
