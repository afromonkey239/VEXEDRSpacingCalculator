package saketh.vexedrspacingcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button btnCalculate;
    Button btnReset;

    TextView txtCount;
    TextView inputLn;

    Spinner mySpinner;

    CheckBox nylon5,nylon375,nylon25,nylon125,teflon4,steel32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCalculate = (Button) findViewById(R.id.buttonCalculate);
        btnReset = (Button) findViewById(R.id.buttonReset);

        txtCount = (TextView) findViewById(R.id.textViewCount);
        inputLn = (TextView) findViewById(R.id.inputLength);

        mySpinner = (Spinner) findViewById(R.id.unitPick);

        ArrayAdapter<String> units = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.units) );

        units.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(units);

        nylon5 = (CheckBox) findViewById(R.id.nylon0_5check);
        nylon375 = (CheckBox) findViewById(R.id.nylon0_375check);
        nylon25 = (CheckBox) findViewById(R.id.nylon0_25check);
        nylon125 = (CheckBox) findViewById(R.id.nylon0_125check);
        teflon4 = (CheckBox) findViewById(R.id.teflon0_04check);
        steel32 = (CheckBox) findViewById(R.id.steel0_032check);

        final ArrayList<CheckBox> checked = new ArrayList<CheckBox>(Arrays.asList(nylon5,nylon375,nylon25,nylon125,teflon4,steel32));

        txtCount.setText("Nothing Inputted");

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String units = " " + mySpinner.getSelectedItem().toString();

                double userLength = 0;
                double rawInput = 0;
                double conversionFactor = 1;

                switch(units){
                    case " Inches" : conversionFactor = 1; break;
                    case " Centimeters" : conversionFactor = 1 / 2.54; break;
                    case  "Holes" : conversionFactor = 1 / 0.5; break;
                }

                boolean progress = false;

                if (inputLn.getText().toString().equals("")) {
                    userLength = 0;
                } else if (Double.parseDouble(inputLn.getText().toString()) * conversionFactor % 2 == 0) {
                    rawInput = Double.parseDouble(inputLn.getText().toString());
                    userLength = rawInput * conversionFactor;

                    String output = "Spacers for " + rawInput + units + "\n" + "\n";
                    output += "0.5 Nylon : " + (userLength * 2) + "\n";
                    output += "\n" + "Unable to fill: " + 0 + units;
                    txtCount.setText(output);

                    inputLn.setText("");
                } else {
                    rawInput = Double.parseDouble(inputLn.getText().toString());
                    userLength = rawInput * conversionFactor;
                    progress = true;
                }


                if (progress) {
                    double remaining = userLength;
                    DecimalFormat numberFormat = new DecimalFormat("#0.000");


                    String[] spacers = {"0.5 Nylon", "0.375 Nylon", "0.25 Nylon", "0.125 Nylon", "Teflon", "Steel Washer"};
                    double[] widths = {0.5, 0.375, 0.25, 0.125, 0.04, 0.032};

                    int[] spacerCount = {0, 0, 0, 0, 0, 0};


                    for (int i = 0; i < spacers.length; i++) {
                        int quantity = 1;

                        if(checked.get(i).isChecked() == true){
                            while (remaining >= widths[i]) {

                                //progress.setProgress(quantity);
                                spacerCount[i] = quantity++;
1
                                remaining -= widths[i];

                            }
                        }


                    }


                    String output = "Spacers for " + numberFormat.format(rawInput) + units + "\n" + "\n";

                    for (int i = 0; i < spacerCount.length; i++) {
                        if (spacerCount[i] > 0) {
                            output += spacers[i] + " : " + spacerCount[i] + "\n";
                        }
                    }

                    output += "\n" + "Unable to fill: " + numberFormat.format(remaining) + units;
                    txtCount.setText(output);

                    inputLn.setText("");
                }

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtCount.setText("Nothing Inputted");
            }
        });

    }
}
