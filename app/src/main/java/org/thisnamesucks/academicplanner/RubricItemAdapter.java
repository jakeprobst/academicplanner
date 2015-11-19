package org.thisnamesucks.academicplanner;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jake on 11/18/15.
 */
public class RubricItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<RubricItem> itemlist;


    public RubricItemAdapter(Context c, ArrayList<RubricItem> cl) {
        context = c;
        itemlist = cl;
    }

    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemrow = inflater.inflate(R.layout.rubric_item_layout, parent, false);

        TextView text;
        final RubricItem rubricitem = itemlist.get(pos);

        Spinner spinner = (Spinner) itemrow.findViewById(R.id.rubric_type_select);
        spinner.setAdapter(new ArrayAdapter<AssignmentType>(context, android.R.layout.simple_list_item_1, AssignmentType.values()));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rubricitem.type = AssignmentType.values()[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*{
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                rubricitem.type = AssignmentType.values()[position];
            }
        });*/

        spinner.setSelection(rubricitem.type.ordinal());
        EditText weight = (EditText) itemrow.findViewById(R.id.rubric_weight);
        weight.setText(Integer.toString(rubricitem.weight));
        weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    rubricitem.weight = Integer.parseInt(s.toString());
                }
                catch (NumberFormatException e) {
                    rubricitem.weight = 0;
                    // ok its not a number so ignore it!
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /*weight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                rubricitem.weight = Integer.parseInt(v.getText().toString());
                return true; // I have no idea what this is for?
            }
        });*/

        return itemrow;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public RubricItem getItem(int pos) {
        return itemlist.get(pos);
    }

    public void add(RubricItem item) {
        itemlist.add(item);
        notifyDataSetChanged();
    }
}
