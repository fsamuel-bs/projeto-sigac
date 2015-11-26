package com.sigac.firefighter.view;

import android.widget.Button;
import com.sigac.firefighter.R;

public class ButtonGroup {
    private Button mSelectedButton;

    public ButtonGroup() {
        mSelectedButton = null;
    }

    public void selectButton(Button button) {
        if (mSelectedButton != null) {
            mSelectedButton.setBackgroundResource(R.drawable.screening_button_deselected);
        }
        mSelectedButton = button;
        mSelectedButton.setBackgroundResource(R.drawable.screening_button_selected);
    }

    public void deselect() {
        if (mSelectedButton != null) {
            mSelectedButton.setBackgroundResource(R.drawable.screening_button_deselected);
        }
        mSelectedButton = null;
    }
}
