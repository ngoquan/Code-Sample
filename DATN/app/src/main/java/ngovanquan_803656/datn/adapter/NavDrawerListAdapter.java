package ngovanquan_803656.datn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.model.NavDrawerItem;

/**
 * Created by ngoquan on 4/17/2016.
 */
public class NavDrawerListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<NavDrawerItem> listHeader;
    private HashMap<NavDrawerItem, List<NavDrawerItem>> listChild;

    public NavDrawerListAdapter(Context context, List<NavDrawerItem> listHeader, HashMap<NavDrawerItem, List<NavDrawerItem>> listChild) {
        this.context = context;
        this.listHeader = listHeader;
        this.listChild = listChild;
    }

    @Override
    public int getGroupCount() {
        return listHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (listHeader.get(groupPosition).isHasChild())
            return listChild.get(listHeader.get(groupPosition)).size();
        else
            return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (listHeader.get(groupPosition).isHasChild())
            return listChild.get(listHeader.get(groupPosition)).get(childPosition);
        else
            return null;
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
        NavDrawerItem navDrawerItem = (NavDrawerItem) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false);
        }
        RelativeLayout rl_drawer = (RelativeLayout) convertView.findViewById(R.id.rl_drawer);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
        ImageView img_expand = (ImageView) convertView.findViewById(R.id.img_expand);
        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_title);
        TextView txt_counter = (TextView) convertView.findViewById(R.id.txt_counter);
        if (!navDrawerItem.getTitle().equals("")) {
            img_icon.setImageResource(navDrawerItem.getIcon());
            txt_title.setText(navDrawerItem.getTitle());
            if (navDrawerItem.isHasChild()) {
                txt_counter.setText(navDrawerItem.getCount() + "");
                img_expand.setVisibility(View.VISIBLE);
            } else {
                txt_counter.setVisibility(View.GONE);
                img_expand.setVisibility(View.GONE);
            }
        } else {
            rl_drawer.setVisibility(View.GONE);
        }
        if (isExpanded)
            img_expand.setImageResource(R.drawable.ic_action_expand);
        else
            img_expand.setImageResource(R.drawable.ic_action_collapse);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        NavDrawerItem navDrawerItem = (NavDrawerItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.drawer_list_item, parent, false);
        }
        RelativeLayout rl_drawer = (RelativeLayout) convertView.findViewById(R.id.rl_drawer);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
        ImageView img_expand = (ImageView) convertView.findViewById(R.id.img_expand);
        TextView txt_title = (TextView) convertView.findViewById(R.id.txt_title);
        TextView txt_counter = (TextView) convertView.findViewById(R.id.txt_counter);
        if (!navDrawerItem.getTitle().equals("")) {
            txt_title.setText(navDrawerItem.getTitle());
            img_icon.setVisibility(View.INVISIBLE);
            img_expand.setVisibility(View.GONE);
            txt_counter.setVisibility(View.GONE);
        } else {
            rl_drawer.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
