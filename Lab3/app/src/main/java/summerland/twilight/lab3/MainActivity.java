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

    /*
        Used to update the final mark based off the marks for the grades
     */
    private void updateStandard()
    {
        double finalMark = assgn*15/100 + participation*15/100 + project*20/100 + quizzes*20/100 + exam*30/100;
        this.finalMark.setText(String.format("%.02f", finalMark));
    }

    private void updateExam()
    {
        examEditText.setText(String.format("%d", (int)exam));
    }

    /*
        Saves the state of the current entered marks
        @param outState the Bundle to save key value information in
     */
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
    /*
        Restores the information from the input
        @param inState  the Bundle with the key value information saved from
        @OnSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle inState)
    {
        super.onRestoreInstanceState(inState);
        assgn = inState.getDouble(ASSIGNMENTS);
        project = inState.getDouble(PROJECT);
        quizzes = inState.getDouble(QUIZZES);
        participation = inState.getDouble(PARTICIPATE);
        exam = inState.getDouble(EXAM);
        updateStandard();
    }
    /*
        Saves information when user updates the UI
     */
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
        ; //Do nothing
    }

    @Override
    public void afterTextChanged(Editable s)
    {
        ; //Do nothing
    }

    /*
        Restores the default value when the reset button is pressed
     */
    @Override
    public void onClick(View v)
    {
        participation = 0.0;
        assgn = 0.0;
        project = 0.0;
        quizzes = 0.0;
        assgnEditText.setText("");
        projectEditText.setText("");
        quizzesEditText.setText("");
        examEditText.setText("80");
        examSeek.setProgress(80);
        partEditText.setText("");

    }

    /*
        SeekBar class for updating the exam mark
     */
    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        /*
            updates the exam mark based on the seekBar
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            exam = seekBar.getProgress();
            updateExam();
            updateStandard();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            ; //Do nothing
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            ; //Do nothing
        }
    };
}
