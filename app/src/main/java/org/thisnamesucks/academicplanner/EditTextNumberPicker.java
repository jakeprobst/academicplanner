package org.thisnamesucks.academicplanner;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by jake on 12/9/15.
 */
// TODO: xml attr to set number of pickers
public class EditTextNumberPicker extends EditText {
    Context context;
    String pickerTitle;

    public EditTextNumberPicker(Context c) {
        super(c);
        context = c;
        init();
    }
    public EditTextNumberPicker(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.EditTextNumberPicker);
        for(int i = 0; i < a.length(); i++) {
            int attr = a.getIndex(i);
            switch(attr) {
                case R.styleable.EditTextNumberPicker_pickerText:
                    pickerTitle = a.getString(i);
                    break;
            }
        }
        a.recycle();
        init();
    }
    public EditTextNumberPicker(Context c, AttributeSet attrs, int style) {
        super(c, attrs, style);
        context = c;
        init();
    }

    private void init() {
        setFocusable(false);
        setKeyListener(null);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog(context);
                d.setContentView(R.layout.edit_number_picker);
                d.setTitle(pickerTitle);
                final NumberPicker picker1 = (NumberPicker) d.findViewById(R.id.dialog_number_picker1);
                picker1.setMinValue(0);
                picker1.setMaxValue(9);
                picker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                final NumberPicker picker2 = (NumberPicker) d.findViewById(R.id.dialog_number_picker2);
                picker2.setMinValue(0);
                picker2.setMaxValue(9);
                picker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
                final NumberPicker picker3 = (NumberPicker) d.findViewById(R.id.dialog_number_picker3);
                picker3.setMinValue(0);
                picker3.setMaxValue(9);
                picker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

                int number = Integer.parseInt(getText().toString());
                picker1.setValue(number/100);
                picker2.setValue((number/10)%10);
                picker3.setValue(number%10);

                d.show();
                d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        int num = picker1.getValue() * 100 +
                                  picker2.getValue() * 10  +
                                  picker3.getValue();
                        setText(Integer.toString(num));
                    }
                });
            }
        });
    }
}
