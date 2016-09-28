package login.demo.stack.stack;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;




public class ApiActivity extends AppCompatActivity {

    String token ="",key="<<PLEASE PASTE YOUR KEY HERE>>";
    String url="https://api.stackexchange.com/2.2/me?site=stackoverflow&key="+key+"&access_token=";
    TextView tvName,tvLocation;
    ImageView dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        Log.i("token","token:"+token);
        new FetchData().execute();


    }

    private class FetchData extends AsyncTask<Void, Void, Void> {
        String jsonString;
        String name="",location="",image="";

        @Override
        protected Void doInBackground(Void... voids) {
            RequestHandler sh = new RequestHandler();
            jsonString = sh.getResponse(url+ token);
            Log.i("json", jsonString);
            if (jsonString != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonString);

                    JSONArray items = jsonObj.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject c = items.getJSONObject(i);

                        name = c.getString("display_name");
                        location = c.getString("location");
                        image = c.get("profile_image").toString();
                        Log.i("name",name+" "+location+" "+ image);

                    }
                } catch (final JSONException e) {

                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvName = (TextView) findViewById(R.id.textView);
            tvLocation=(TextView)findViewById(R.id.textView2);
            dp = (ImageView)findViewById(R.id.imageView);

            tvName.setText(name);
            tvLocation.setText(location);

            Picasso.with(ApiActivity.this)
                    .load(image)
                    .into(dp);



        }
    }
}
