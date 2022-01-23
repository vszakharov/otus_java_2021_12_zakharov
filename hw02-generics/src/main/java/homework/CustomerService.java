package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;

import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> map = new TreeMap<>(
            Comparator.comparingLong(Customer::getScores)
    );

    public Map.Entry<Customer, String> getSmallest() {
        return getEntryWithKeyCopy(map.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return getEntryWithKeyCopy(map.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> getEntryWithKeyCopy(Map.Entry<Customer, String> sourceEntry) {
        if (sourceEntry == null) {
            return null;
        }
        var customer = new Customer(sourceEntry.getKey());
        return new AbstractMap.SimpleImmutableEntry<>(customer, sourceEntry.getValue());
    }
}
