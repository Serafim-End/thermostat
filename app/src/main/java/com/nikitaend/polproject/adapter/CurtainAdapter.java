package com.nikitaend.polproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikitaend.polproject.adapter.holder.CurtainHolder;
import com.nikitaend.polproject.R;

import java.util.ArrayList;

/**
 * adapter contains information about customizing every element in list
 * every element of CurtainHolder type  and card_curtain card screen
 * @author Endaltsev Nikita
 *         start at 12.05.15.
 */
public class CurtainAdapter extends ArrayAdapter<CurtainHolder> {
    
    public ArrayList<CurtainHolder> curtainHolders;
    public Context mContext;
    
    
    public CurtainAdapter(Context context, int resource, ArrayList<CurtainHolder> curtainHolders) {
        super(context, resource, curtainHolders);
        
        this.mContext = context;
        this.curtainHolders = curtainHolders;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        convertView = LayoutInflater.from(mContext).inflate(R.layout.card_curtain, parent, false);
        
        CurtainHolder item = getItem(position);
        
        ImageView dayImage = (ImageView) convertView.findViewById(R.id.curtain_day_image);
        dayImage.setImageDrawable(mContext.getDrawable(item.resourceOfImage));

        TextView dayTitle = (TextView) convertView.findViewById(R.id.curtain_day_title);
        dayTitle.setText(item.titleOfSection);
        
        return convertView;
    }
}
