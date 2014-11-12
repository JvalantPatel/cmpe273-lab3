package edu.sjsu.cmpe.cache.client;

import com.google.common.hash.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {

        List<String> servers = new ArrayList<String>();
        servers.add("http://localhost:3000");
        servers.add("http://localhost:3001");
        servers.add("http://localhost:3002");

        int bucketNo = 0;

        String[] values = {"a","b","c","d","e","f","g","h","i","j"};

        System.out.println("Starting Cache Client...");
        System.out.println("Performing PUT operation.....");

        //Distribute data on 3 servers

        for(int index=0 ;index<values.length;index++)
        {
            bucketNo = Hashing.consistentHash(Hashing.md5().hashString(""+index+""), servers.size());
            CacheServiceInterface cache = new DistributedCacheService(servers.get(bucketNo));

            System.out.println((index+1)+"--"+servers.get(bucketNo));

            cache.put(index+1,values[index]);
        }

        //getting data from all servers

        System.out.println("##########################################################");
        System.out.println("Getting data...");

        for(int index=0;index<values.length;index++)
        {
            bucketNo = Hashing.consistentHash(Hashing.md5().hashString(""+index+""), servers.size());
            CacheServiceInterface cache = new DistributedCacheService(servers.get(bucketNo));



            System.out.println((index+1)+"=>"+cache.get(index+1)+" from server "+servers.get(bucketNo));

            ///cache.get(index+1);

        }

        
        System.out.println("Existing Cache Client...");
    }

}
