package com.bls.imagesearching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import androidx.appcompat.widget.SearchView;

import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView Recycler_view;
    ArrayList<PhotoModel> photoModelArrayList = new ArrayList<>();
    PhotoAdapter photoAdapter;
    PhotoModel photoModel;
    int page_number = 0;
    String url = "https://api.imgur.com/3/gallery/search/"+page_number+"?q=rose";
    Boolean isScrolling = true;
    int current_item,total,scrolloutItems;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Recycler_view = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progress_bar);

        photoAdapter = new PhotoAdapter(photoModelArrayList,MainActivity.this);
        final LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        Recycler_view.setLayoutManager(layout);
        Recycler_view.setAdapter(photoAdapter);

        Recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
          @Override
          public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
              super.onScrollStateChanged(recyclerView, newState);
              if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                  isScrolling = true;
              }
          }
          @Override
          public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
              super.onScrolled(recyclerView, dx, dy);
              current_item = layout.getChildCount();
              total = layout.getItemCount();
              scrolloutItems = layout.findFirstVisibleItemPosition();
              if(isScrolling && (current_item + scrolloutItems == total)){
                  isScrolling = false;
                  fetchdata();
              }
          }
      });
        fetchdata();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.photo_search) {
            SearchView searchView = (SearchView) item.getActionView();
            searchView.setQueryHint("Type here to Search");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String search_item) {
                      String item = search_item.toLowerCase();
                      url = "https://api.imgur.com/3/gallery/search/"+page_number+"?q="+item;
                      photoModelArrayList.clear();
                      fetchdata();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
        }
         return super.onOptionsItemSelected(item);
    }

    private void fetchdata()
    {
        //   String url = "https://api.imgur.com/3/gallery/search/1?q=rose";
        //String url = "https://api.imgur.com/3/gallery/r/rose/"+page_number;
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,url,null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for(int i=0; i <jsonArray.length(); i++)
                            {
                                JSONObject json = jsonArray.getJSONObject(i);
                                String title = json.getString("title");
                                if(json.has("images")) {
                                    JSONArray jsonArray1 = json.getJSONArray("images");
                                    for (int j = 0; j < jsonArray1.length(); j++){
                                        photoModel = new PhotoModel(title,jsonArray1.getJSONObject(j).getString("link"),jsonArray1.getJSONObject(j).getString("id") );
                                        photoModelArrayList.add(photoModel);
                                    }
                                }
                                else{
                                    photoModel = new PhotoModel(title,json.getString("link"),json.getString("id"));
                                    photoModelArrayList.add(photoModel);
                                }
                            }
                            photoAdapter.notifyDataSetChanged();
                            page_number++;
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                        }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
        {
            //  API Authorization !
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String ,String> Header = new HashMap<>();
                Header.put("Authorization","Client-ID 137cda6b5008a7c");
                return Header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}