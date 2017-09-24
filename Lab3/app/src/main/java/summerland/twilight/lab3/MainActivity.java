package summerland.twilight.lab3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

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
    private SeekBar examSeek;

    private TextView finalMark;

    private Button resetButton;
    @Override
    /*
        onCreate Method call when Activity is created
        @param savedInstanceState saved bundle information
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assgnEditText = (EditText)findViewById(R.id.assignmentEdit);
        partEditText = (EditText)findViewById(R.id.participationEdit);
        projectEditText = (EditText)findViewById(R.id.projectEdit);
        quizzesEditText = (EditText)findViewById(R.id.quizzesEdit);
        examEditText= (EditText) findViewById(R.id.examEdit);
        examSeek = (SeekBar) findViewById(R.id.examSeekBar);
        resetButton = (Button) findViewById(R.id.resetButton);
        finalMark = (TextView) findViewById(R.id.finalTextView);

        assgnEditText.addTextChangedListener(this);
        partEditText.addTextChangedListener(this);
        projectEditText.addTextChangedListener(this);
        quizzesEditText.addTextChangedListener(this);
        examEditText.addTextChangedListener(this);

        examSeek.setOnSeekBarChangeListener(examSeekBarListener);
        examSeek.setProgress(80);
        resetButton.setOnClickListener(this);
    }

    private void updateStandard()
    {
        double finalMark = assgn*15/100 + participation*15/100 + project*20/100 + quizzes*20/100 + exam*30/100;
        this.finalMark.setText(String.format("%.02f", finalMark));
    }

    private void updateExam()
    {
        examEditText.setText(String.format("%d", (int)exam));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putDouble(ASSIGNMENTS, assgn);
        outState.putDouble(PROJECT, project);
        outState.putDouble(QUIZZES, quizzes);
        outState.putDouble(PARTICIPATE, participation);
        outState.putDouble(EXAM, exam);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState)
    {
        super.onRestoreInstanceState(inState);
        assgn = inState.getDouble(ASSIGNMENTS);
        project = inState.getDouble(PROJECT);
        quizzes = inState.getDouble(QUIZZES);
        participation = inState.getDouble(PARTICIPATE);
        exam = inState.getDouble(EXAM);
    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {
        if (assgnEditText.isFocused())
        {
            try
            {
                assgn = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e)
            {
                assgn = 0.0;
            }
        }
        else if (partEditText.isFocused())
        {
            try
            {
                participation = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e)
            {
                participation = 0.0;
            }
        }
        else if (projectEditText.isFocused())
        {
            try
            {
                project = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e)
            {
                project = 0.0;
            }
        }
        else if (quizzesEditText.isFocused())
        {
            try
            {
                quizzes = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e)
            {
                quizzes = 0.0;
            }
        }
        updateStandard();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    @Override
    public void onClick(View v)
    {
        assgnEditText.setText("");
        projectEditText.setText("");
        quizzesEditText.setText("");
        examEditText.setText("");
        examSeek.setProgress(80);
        partEditText.setText("");

    }

    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            exam = seekBar.getProgress();
            updateExam();
            updateStandard();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
