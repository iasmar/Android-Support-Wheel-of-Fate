package com.iasmar.rome.data.modules;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import static com.iasmar.rome.util.ObjectHelper.requireNonNull;

/**
 * Created by Asmar on 01/7/2018.
 * <p>
 * The main purpose of this Holder is to organize the list of {@link BaseModule] child.
 * Inaddition provide some functinality such get module by id and get module by index.
 *
 * @param <T> {@link BaseModule] child.
 * @author Asmar
 * @version 1
 * @see BaseModule
 * @since 0.1.0
 */

public class ModulesHolder<T extends BaseModule> {


    /**
     * Items that Hold the HashMap of Engineer
     * Use LinkedHashMap instead  HashMap  since it maintains insertion order.
     */
    private LinkedHashMap<String, T> items = new LinkedHashMap<>();


    /**
     * keys that Hold the Unique key int the LinkedHashMap.
     * <p>
     * Since there all maps dose not provide getValueByIndex.
     * We will create an arrayList that will save the id in the same index that the map will store it
     * so we could get the key from this arrayList then get the value from the map easily.
     */
    private ArrayList<String> keys = new ArrayList<>();

    /**
     * Add new item or replace.
     * <p>
     * the behavior is we store the id of the module in the keys arrayList
     * then we store the the module in the item map where the key is the id and value is the module.
     * if the item exist we don`t add it to the keys arrayList
     * and we will put the module in the map where it will be replaced.
     *
     * @param item the module that will be put in the value of the map.
     */
    public void addItem(T item) {
        item = requireNonNull(item, "item cannot be null");
        String id = item.getId();
        if (!keys.contains(id)) {
            keys.add(id);
        }

        // add the new item or replace it.
        items.put(id, item);
    }

    /**
     * Simply by get value by key from the map.
     *
     * @param id the module id.
     * @return the module.
     */
    public T getItemById(String id) {
        id = requireNonNull(id, "id cannot be null");
        return items.get(id);
    }

    /**
     * Get the value by the index.
     *
     * @param index the index of the module.
     * @return the module.
     */
    public T getItemByIndex(int index) {
        return items.get(keys.get(index));

    }

    /**
     * get the number of key-value mappings in this map.
     *
     * @return the size of key (How may items).
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Is there no items had been stored.
     *
     * @return {@code true} if this items is empty
     * ; {@code false} otherwise.
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
        keys.clear();
    }
}

