package ngovanquan_803656.datn.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import ngovanquan_803656.datn.R;
import ngovanquan_803656.datn.asynctask.qtht.GetInformationSystemAsyncTask;
import ngovanquan_803656.datn.utils.Constants;

/**
 * Created by NGOQUAN on 6/1/2016.
 */
public class GioiThieuFragment extends Fragment {
//    TextView txt_gioiThieu;
    WebView web_gioiThieu;
    public GioiThieuFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gioi_thieu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        txt_gioiThieu = (TextView) getView().findViewById(R.id.txt_gioiThieu);
        web_gioiThieu = (WebView) getView().findViewById(R.id.web_gioiThieu);
        try {
            String gioiThieu = new GetInformationSystemAsyncTask(getActivity(), Constants.FUNCTION_GET_GIOI_THIEU).execute().get();
//            txt_gioiThieu.setText(Html.fromHtml(gioiThieu));
            web_gioiThieu.loadDataWithBaseURL(null, gioiThieu, "text/html", "utf-8", null);
//            web_gioiThieu.loadData(gioiThieu, "text/html", "utf-8");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


}
