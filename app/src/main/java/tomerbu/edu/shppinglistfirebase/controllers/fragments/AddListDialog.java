package tomerbu.edu.shppinglistfirebase.controllers.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tomerbu.edu.shppinglistfirebase.R;

public class AddListDialog extends DialogFragment implements View.OnClickListener {


    private EditText mEditText;
    private Button mButton;
    private OnSaveClicked listener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_list_dialog, container);
        mEditText = (EditText) view.findViewById(R.id.etListName);
        mButton = (Button) view.findViewById(R.id.btnSave);
        getDialog().setTitle("Hello");

        mButton.setOnClickListener(this);
        return view;
    }

    public AddListDialog setDelegate(OnSaveClicked onSaveClicked) {
        this.listener = onSaveClicked;
        return this;
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onSaveClicked(mEditText.getText().toString());
        }
        dismiss();
    }

    public interface OnSaveClicked {
        public void onSaveClicked(String title);
    }
}