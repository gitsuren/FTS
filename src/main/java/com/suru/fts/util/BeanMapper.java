package com.suru.fts.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BeanMapper<T, S> {

	public abstract T mapToBean(S object);
	
    public List<T> mapToBean(Collection<? extends S> typeList) {
        if (null == typeList) {
            return null;
        }

        List<T> outputList = new ArrayList<T>(typeList.size());

        for (S s : typeList) {
            T t  = mapToBean(s);
            if (null != t) {
                outputList.add(t);
            }
        }

        return outputList;
    }
}
