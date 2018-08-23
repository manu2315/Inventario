package mx.com.manu.inventario.mainModule.view;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mx.com.manu.inventario.R;
import mx.com.manu.inventario.common.pojo.Product;
import mx.com.manu.inventario.mainModule.MainPresenter;
import mx.com.manu.inventario.mainModule.MainPresenterClass;
import mx.com.manu.inventario.mainModule.view.adapters.OnItemClickListener;
import mx.com.manu.inventario.mainModule.view.adapters.ProductAdapter;

public class MainActivity extends AppCompatActivity implements OnItemClickListener,MainView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.contentMain)
    ConstraintLayout contentMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    //Variables globales
    private MainPresenter mPresenter;
    private ProductAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configToolbar();
        configAdapter();
        configRecyclerView();

        mPresenter = new MainPresenterClass(this);
        mPresenter.onCreate();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    private void configToolbar() {
        setSupportActionBar(toolbar);
    }
    private void configAdapter(){
        mAdapter = new ProductAdapter(new ArrayList<Product>(),this);
    }
    private void configRecyclerView(){
        //configuracion para que no sea una lista sino cuadricula
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,
                getResources().getInteger(R.integer.main_columns));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    public void onAddClicked(){


    }
    /*
    * MainView
    * */
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void add(Product product) {
        mAdapter.add(product);
    }

    @Override
    public void update(Product product) {
        mAdapter.update(product);
    }

    @Override
    public void remove(Product product) {
        mAdapter.remove(product);
    }

    @Override
    public void removeFail() {
        Snackbar.make(contentMain,R.string.main_error_remove,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onShowError(int resMsg) {
        Snackbar.make(contentMain,resMsg,Snackbar.LENGTH_LONG).show();
    }

    //
    /*
    * OnItemClickListener
    * */
    @Override
    public void onItemClick(Product product) {

    }

    @Override
    public void onLongItemClick(Product product) {

    }
}
