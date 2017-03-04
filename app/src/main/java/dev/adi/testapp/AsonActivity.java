package dev.adi.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.json.Ason;
import com.afollestad.json.AsonArray;

public class AsonActivity extends AppCompatActivity {

    private LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ason);
        getSupportActionBar().setTitle("ASON Test");
        getSupportActionBar().setHomeButtonEnabled(true);

        parentLayout = (LinearLayout) findViewById(R.id.parent_layout);

        Person person1 = new Person("Person 1", 21);
        Person person2 = new Person("Person 2", 25, person1);
        Person person3 = new Person("Person 3", 28, person2);

        addTextView("Single object", true);
        addTextView(Ason.serialize(person3).toString(4), false);

        addTextView("Simple list", true);
        int[] ids = { 1, 2, 3, 4 };
        AsonArray<Integer> intArray = Ason.serializeArray(ids);
        addTextView(intArray.toString(), false);

        addTextView("Simple list of Strings", true);
        String[] names = { "Adi", "Name 1", "Name 2", "Name 3" };
        AsonArray<Integer> stringArray = Ason.serializeArray(names);
        addTextView(stringArray.toString(2), false);

        addTextView("List of Person", true);
        AsonArray<Person> personArray = new AsonArray<Person>();
        personArray.add(person1);
        personArray.add(person2);
        personArray.add(person3);
        addTextView(personArray.toString(8), false);
    }

    public void addTextView(String text, boolean isHeading) {
        TextView textView = new TextView(this);
        textView.setText(text);
        if (isHeading) {
            textView.setTextAppearance(this, android.R.style.TextAppearance_Large);
        }

        parentLayout.addView(textView);
    }

    private class Person {
        String name;
        int age;
        Person partner;

        Person(String name, int age, Person partner) {
            this.name = name;
            this.age = age;
            this.partner = partner;
        }

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
    }
}
