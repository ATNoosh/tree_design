package ir.atnoosh.treedesign.adapters;

public interface IListType {

    enum LIST_ITEM_TYPES {SIMPLE,LOADING,NO_ITEM,NO_INTERNET,ERROR}

    LIST_ITEM_TYPES getType(String str);
    void setType(LIST_ITEM_TYPES itemType);
}
