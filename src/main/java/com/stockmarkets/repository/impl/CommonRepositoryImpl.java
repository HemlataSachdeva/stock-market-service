package com.stockmarkets.repository.impl;


import com.stockmarkets.repository.CommonRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public abstract class CommonRepositoryImpl<K, V> implements CommonRepository<K, V> {

	protected abstract ConcurrentHashMap<K, V> getMap();
	
    @Override
    public long count() {
        return getMap().values().size();
    }

    @Override
    public V create(final K key , final V value) {
    	getMap().put(key, value);
        return getMap().get(value);
    }

    @Override
    public V load(final K key) {
        return getMap().get(key);
    }

    @Override
    public V update(final K key, final V value) {
        return create(key, value);
    }

    @Override
    public boolean delete(final K key) {
        if (!getMap().containsKey(key)) {
            return false;
        }
        getMap().remove(key);
        return Objects.isNull(getMap().get(key));
    }

    @Override
    public List<V> list() {
        return new ArrayList<>(getMap().values());
    }

    @Override
    public void drop() {
    	getMap().clear();
    }
}
