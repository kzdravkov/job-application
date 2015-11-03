package example.interviewapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    private static final BigInteger[] numbers = new BigInteger[100];
    private ArrayAdapter<BigInteger> numbersAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null) {
            initArray();
        }
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light, android.R.color.holo_green_light,
                android.R.color.holo_green_light);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                initArray();
                numbersAdapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            }
        });
        numbersAdapter = new ArrayAdapter<BigInteger>(this,
                android.R.layout.simple_list_item_1, numbers);
        ListView numbersList = (ListView) findViewById(R.id.listView);
        numbersList.setAdapter(numbersAdapter);

        // Pull to refresh is turned off if list is not scrolled to the top
        numbersList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }
            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0)
                    swipeRefresh.setEnabled(true);
                else
                    swipeRefresh.setEnabled(false);
            }
        });
    }


    private static BigInteger fib(int n) {
        BigInteger a = BigInteger.ZERO;
        BigInteger b = BigInteger.ONE;
        if (n <= 1)
            return BigInteger.valueOf(n);
        else {
            while (n-- > 1) {
                BigInteger x = a;
                a = b;
                b = x.add(a);
            }
            return b;
        }
    }

    private static void initArray() {
        for (int i = 0; i < 100; i++) {
            numbers[i] = BigInteger.valueOf(i);
        }
    }

    public void convertToFibonacci(View v) {
        for (int i = 0; i < 100; i++) {
            numbers[i] = fib(i);
            numbersAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle state){
        state.putBoolean("isExisting", true);
        super.onSaveInstanceState(state);
    }
}
