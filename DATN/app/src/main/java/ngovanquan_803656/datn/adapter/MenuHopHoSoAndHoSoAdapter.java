package ngovanquan_803656.datn.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.HoSo_ett;
import ngovanquan_803656.datn.model.HopHoSo_ett;

/**
 * Created by NGOQUAN on 5/27/2016.
 */
public class MenuHopHoSoAndHoSoAdapter extends BaseExpandableListAdapter {

    private Activity activity;
    private ArrayList<HopHoSo_ett> listHeader;
    private HashMap<HopHoSo_ett, ArrayList<HoSo_ett>> listChild;

    public MenuHopHoSoAndHoSoAdapter(Activity activity, ArrayList<HopHoSo_ett> listHeader, HashMap<HopHoSo_ett, ArrayList<HoSo_ett>> listChild) {
        this.activity = activity;
        this.listHeader = listHeader;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listChild.get(listHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listChild.get(listHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        HopHoSo_ett hopHoSo_ett = (HopHoSo_ett) getGroup(groupPosition);
        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item_hophs_hs, parent, false);
        ImageView img_hopHoSo = (ImageView) convertView.findViewById(R.id.img_hophoso);
        TextView txt_hopHoSo = (TextView) convertView.findViewById(R.id.txt_hophoso);
        txt_hopHoSo.setText("Hộp: " + hopHoSo_ett.getTenHop() + "(" + hopHoSo_ett.getSl_hhs() + ")");

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        HoSo_ett hoSo_ett = (HoSo_ett) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.list_item, parent, false);
        }
        ImageView img_hoSo = (ImageView) convertView.findViewById(R.id.img_item);
        TextView txt_hoSo = (TextView) convertView.findViewById(R.id.txt_hoso);
        txt_hoSo.setText("HS số: " + hoSo_ett.getHoSoSo() + "(" + hoSo_ett.getSl_hs() + ")");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
