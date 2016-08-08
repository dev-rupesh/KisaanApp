package rsoni.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.util.List;

import rsoni.kisaanApp.R;
import rsoni.modal.State;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
    }

    private void initView(){



    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        List<State> states = null;
        try {
            states = State.getStateList(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(State state : states){
            System.out.println(state.state_name);
        }


    }

    @Override
    public void onClick(View view) {

    }
}
