package ngovanquan_803656.datn.model;

/**
 * Created by ngoquan on 4/17/2016.
 */
public class NavDrawerItem {
    private String title;
    private int icon;
    private int count = 0;
    private boolean hasChild = false;
    public NavDrawerItem() {}
    public NavDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }
    public NavDrawerItem(String title, int icon, int count, boolean hasChild) {
        this.title = title;
        this.icon = icon;
        this.count = count;
        this.hasChild = hasChild;
    }

    public NavDrawerItem(String title, int count, boolean hasChild) {
        this.title = title;
        this.count = count;
        this.hasChild = hasChild;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }
}
