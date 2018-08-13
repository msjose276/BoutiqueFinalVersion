package com.example.mateusjose.newchatos.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.mateusjose.newchatos.R;

public class AboutTheApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String applicationName= "Boutiques";

        String aboutTheApplication="In information technology, an application is a computer program designed to help people perform an activity. An application thus differs from an operating system (which runs a computer), a utility (which performs maintenance or general-purpose chores), and a programming tool (with which computer programs are created)[original research?]. Depending on the activity for which it was designed, an application can manipulate text, numbers, graphics, or a combination of these elements. Some application packages focus on a single task, such as word processing; others, called integrated software include several applications.[3]\n" +
                "\n" +
                "User-written software tailors systems to meet the user's specific needs. User-written software includes spreadsheet templates, word processor macros, scientific simulations, graphics and animation scripts. Even email filters are a kind of user software. Users create this software themselves and often overlook how important it is.\n" +
                "\n" +
                "The delineation between system software such as operating systems and application software is not exact, however, and is occasionally the object of controversy.[4] For example, one of the key questions in the United States v. Microsoft Corp. antitrust trial was whether Microsoft's Internet Explorer web browser was part of its Windows operating system or a separable piece of application software. As another example, the GNU/Linux naming controversy is, in part, due to disagreement about the relationship between the Linux kernel and the operating systems built over this kernel. In some types of embedded systems, the application software and the operating system software may be indistinguishable to the user, as in the case of software used to control a VCR, DVD player or microwave oven. The above definitions may exclude some applications that may exist on some computers in large organizations. For an alternative definition of an app: see Application Portfolio Management.";



        TextView tvTitle = (TextView)findViewById(R.id.tv_title);
        tvTitle.setText( applicationName);
        TextView tvAbout = (TextView)findViewById(R.id.tv_about);
        tvAbout.setText(aboutTheApplication);
    }

}
