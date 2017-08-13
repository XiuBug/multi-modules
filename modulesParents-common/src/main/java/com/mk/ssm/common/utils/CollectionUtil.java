package com.mk.ssm.common.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yankee on 2017/4/15.
 */
public class CollectionUtil {

    /**
     * List转Set
     * @param list
     * @return
     */
    public static Set<String> listToSet(List<String> list){
        Set<String> set = new HashSet<String>();
        for (String str:list){
            set.add(str);
        }
        return set;
    }
}
