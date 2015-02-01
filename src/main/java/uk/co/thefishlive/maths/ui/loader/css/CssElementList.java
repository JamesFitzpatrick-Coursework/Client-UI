package uk.co.thefishlive.maths.ui.loader.css;

import com.google.common.collect.AbstractIterator;

import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 */
public class CssElementList extends AbstractCollection<CssElement> {

    private Map<String, String> backing;

    public CssElementList() {
        backing = new HashMap<>();
    }

    public CssElementList(int size) {
        backing = new HashMap<>(size);
    }

    @Override
    public Iterator<CssElement> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public boolean add(CssElement cssElement) {
        backing.put(cssElement.getKey(), cssElement.getValue());
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return new CssElement(o.toString(), backing.get(o)) != null;
    }

    @Override
    public boolean contains(Object o) {
        return get((String) o).getValue() != null;
    }

    public CssElement get(String key) {
        return new CssElement(key, backing.get(key));
    }

    @Override
    public String toString() {
        return backing.toString();
    }
}
