package summerland.twilight.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "CourseGrades";
    private static final String ASSIGNMENTS = "ASSIGNMENTS";
    private static final String PARTICIPATE = "PARTICIPATION";
    private static final String PROJECT = "PROJECT";
    private static final String QUIZZES = "QUIZZES";
    private static final String EXAM = "EXAM";

    private double assgn;
    private EditText assgnEditText;

    private double participation;
    private EditText partEditText;

    private double project;
    private EditText projectEditText;

    private double quizzes;
    private EditText quizzesEditText;

    private double exam;
    private EditText examEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assgnEditText.findViewById()
        // agnEditTesxt.addtextChangedListener(this);
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int end, int i2)
    {
        ;
//        // if(assgnEditText.isFocused())
//        {
//            try
//            {
//
//            }
//            catch (NumberFormatException e)
//            {
//                assgnScore = 0.0
//            }
//        }
    }

}
