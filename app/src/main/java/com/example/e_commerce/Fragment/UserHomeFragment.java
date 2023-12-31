package com.example.e_commerce.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.e_commerce.Activity.ProductActivity;
import com.example.e_commerce.Database.Database;
import com.example.e_commerce.Model.Product;
import com.example.e_commerce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeFragment extends Fragment {
    // Make a new arraylist
    // List view
    ListView user_list_products;
    ArrayList<Product> products = new ArrayList<>();
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    /*
      These constants are used as keys to identify the parameters that can be passed to
      the UserHomeFragment when creating a new instance of it using the newInstance factory method.
     */
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // Rename and change types and number of parameters
    //make new instance
    public static UserHomeFragment newInstance(String param1, String param2) {
        return UserHomeFragmentFactory.create(param1, param2);
    }
    //copy argument
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_home, container, false);

        user_list_products = v.findViewById(R.id.user_home_list_products);

        // TODO: get products from database and show it in listView

        Database dp = new Database(getContext());
        products = dp.get_products();
        UserHomeProductAdapter userHomeProductAdapter = new UserHomeProductAdapter(products);
        user_list_products.setAdapter(userHomeProductAdapter);
        user_list_products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity().getBaseContext(), ProductActivity.class);
                intent = new Intent(getActivity(), ProductActivity.class);

                intent.putExtra("id", products.get(i).getId());
                intent.putExtra("quantity", products.get(i).getQuantity());
                intent.putExtra("cat_id", products.get(i).getCat_id());
                intent.putExtra("sold", products.get(i).getSold());
                intent.putExtra("name", products.get(i).getName());
                intent.putExtra("price", products.get(i).getPrice());
                intent.putExtra("image", products.get(i).getImage());
                getActivity().startActivity(intent);
            }
        });

        return v;
    }

    // Adapter pattern
    // BaseAdapter is an abstract class provided by the Android framework
    // that you can use as a base for creating custom adapters in Android applications
    class UserHomeProductAdapter extends BaseAdapter {
        // Initialize ArrayList
        ArrayList<Product> products = new ArrayList<>();

        // Constructor
        public UserHomeProductAdapter(ArrayList<Product> products) {
            this.products = products;
        }

        // This method returns the number of items in the adapter, indicating the size of the data set.
        @Override
        public int getCount() {
            return products.size();
        }

        // This method returns the data item at the specified position in the data set.
        @Override
        public long getItemId(int i) {
            return i;
        }

        // This method returns a unique identifier for the item at the specified position.
        @Override
        public Object getItem(int i) {
            return products.get(i).getName();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        // Inflates the layout for each item, sets the data for each item, and returns the constructed view.
        @Override
        public View getView(int i, View view,ViewGroup viewGroup) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View item = layoutInflater.inflate(R.layout.user_home_product_item, null);

            ImageView product_image = (ImageView) item.findViewById(R.id.user_home_iv_product_image);
            TextView product_name = (TextView) item.findViewById(R.id.user_home_tv_product_name);
            TextView product_price = (TextView) item.findViewById(R.id.user_home_tv_product_price);
            product_name.setText(products.get(i).getName());
            product_price.setText(products.get(i).getPrice() + "");
            String url = products.get(i).getImage();
            // To load an image from a specified URL and display it into an ImageView
            Glide.with(getContext()).load(url).into(product_image);
            return item;
        }
    }
}
// Factory class
class UserHomeFragmentFactory {
    public static UserHomeFragment create(String param1, String param2) {
        UserHomeFragment fragment = new UserHomeFragment();
        Bundle args = new Bundle();
        args.putString(UserHomeFragment.ARG_PARAM1, param1);
        args.putString(UserHomeFragment.ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
}