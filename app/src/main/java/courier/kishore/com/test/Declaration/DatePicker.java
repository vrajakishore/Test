package courier.kishore.com.test.Declaration;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.TextView;

import courier.kishore.com.test.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog da = new DatePickerDialog(getActivity(), this, yy, mm, dd);
        Date newDate = calendar.getTime();
        da.getDatePicker().setMinDate(newDate.getTime());
        return da;
    }

    public void populateSetDate(int year, int month, int day) {

        TextView tv = (TextView)getFragmentManager().findFragmentById(R.id.container).getView().findViewById(R.id.get_date);
        Date d = new Date(year-1900, month-1, day);


        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormatter.format(d);
        //tv.setText(year+"-"+month+"-"+day);
        tv.setText(strDate);
        //Toast.makeText(getActivity(), year+"/"+month+"/"+day, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        populateSetDate(year, monthOfYear + 1, dayOfMonth);


    }



}
