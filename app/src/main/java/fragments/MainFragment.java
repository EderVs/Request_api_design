package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.esperando_la.first_material_design.R;

import async.EventfulAsyncTask;

/**
 * Created by eder on 12/02/2015.
 */
public class MainFragment extends Fragment
        implements EventfulAsyncTask.EventFulListener,
        View.OnClickListener
{
    public MainFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_actvity, container, false);
        Button btnAsync = (Button)rootView.findViewById(R.id.btn_asynctask);

        btnAsync.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onResponseSuccess() {
        //Que hacer cuando la peticion se haya completado
    }

    @Override
    public void onError() {
        //Que hacer en caso de error
    }

    @Override
    public void onClick(View viewClicked) {
        switch (viewClicked.getId())
        {
            case R.id.btn_asynctask:
                executeRequestByAsyncTask();
                break;
        }
    }

    private void executeRequestByAsyncTask() {
        new  EventfulAsyncTask(this).execute();
    }
}
