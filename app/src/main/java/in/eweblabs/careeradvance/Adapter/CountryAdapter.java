package in.eweblabs.careeradvance.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import in.eweblabs.careeradvance.Entity.Country;
import in.eweblabs.careeradvance.R;
import in.eweblabs.careeradvance.SharedPreferences.PreHelper;
import in.eweblabs.careeradvance.StaticData.StaticConstant;

/**
 * Created by Akash.singh on 11/18/2015.
 */
public class CountryAdapter extends BaseAdapter {
    ArrayList<Object> object;
    LayoutInflater mInflater;
    Context context;
    public CountryAdapter(Context context,ArrayList<Object> object){
        this.object = object;
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return object.size();
    }

    @Override
    public Object getItem(int position) {
        return object.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_country_view, null);
            holder.country_item = (TextView) convertView.findViewById(R.id.country_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String CountryName = ((Country)getItem(position)).getCountryName();
        if(!TextUtils.isEmpty(PreHelper.getStoredString(context, StaticConstant.COUNTRY_CODE)))
        {
            if(PreHelper.getStoredString(context, StaticConstant.COUNTRY_CODE).equals(((Country)getItem(position)).getCountryCode()))
                holder.country_item.setText(CountryName+"✔");
            else
                holder.country_item.setText(CountryName);
        }
        else {
            if(((Country)getItem(position)).getCountryCode().equals(StaticConstant.BUSINESSCOUNTYCODE))
                holder.country_item.setText(CountryName+"✔");
            else
                holder.country_item.setText(CountryName);
        }

        return convertView;
    }

    class ViewHolder{
        TextView country_item;
    }




}
