package courier.kishore.com.test.Declaration;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;

import courier.kishore.com.test.R;


public class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hh = calendar.get(Calendar.HOUR);
        int mm = calendar.get(Calendar.MINUTE);


        return new TimePickerDialog(getActivity(),this,mm,hh,false);
    }


    public void populateSetTime(int hour, int minute) {
        TextView tt = (TextView)getFragmentManager().findFragmentById(R.id.container).getView().findViewById(R.id.get_time);


        tt.setText(hour+":"+minute);
        //Toast.makeText(getActivity(), hour + " : " + minute + " " , Toast.LENGTH_LONG).show();
    }
    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        populateSetTime(hourOfDay,minute);
    }
}
