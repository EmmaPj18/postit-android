package com.emmapj18.postit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.emmapj18.postit.Listeners.MenuListener;

public class MenuFragment extends Fragment implements View.OnClickListener {

    private MenuListener mListener;

    public MenuFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MenuListener) mListener = (MenuListener) context;
        else throw new ClassCastException(context.toString() + " must implement MenuFragment.MenuFragmentListener");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.menu_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button buttonHome = view.findViewById(R.id.buttonHome);
        Button buttonPost = view.findViewById(R.id.buttonPost);
        Button buttonProfile = view.findViewById(R.id.buttonProfile);

        buttonHome.setOnClickListener(this);
        buttonPost.setOnClickListener(this);
        buttonProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonHome:
                mListener.onHomeButtonPressed();
                break;
            case R.id.buttonPost:
                mListener.onPostButtonPressed();
                break;
            case R.id.buttonProfile:
                mListener.onProfileButtonPressed();
                break;
        }
    }


}
