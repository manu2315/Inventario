package mx.com.manu.inventario.mainModule.view.adapters;

import mx.com.manu.inventario.common.pojo.Product;

public interface OnItemClickListener {
    void onItemClick(Product product);
    void onLongItemClick(Product product);
}
