package com.eventviewer.eventviewerservice;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class EventReader {

	
	
	public static void main(String[] args) {
		
		JSONParser parser = new JSONParser();
		JSONArray a = null;
		try {
			a = (JSONArray) parser.parse(new FileReader("/Users/sgoel201/Desktop/input.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		for(int i = 0; i<a.length(); i++) {
			
			try {
				JSONObject person = a.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
/*		  for (Object o : a)
		  {
		    JSONObject person = (JSONObject) o;

		    String name = (String) person.get("name");
		    System.out.println(name);

		    String city = (String) person.get("city");
		    System.out.println(city);

		    String job = (String) person.get("job");
		    System.out.println(job);

		    JSONArray cars = (JSONArray) person.get("cars");

		    for (Object c : cars)
		    {
		      System.out.println(c+"");
		    }
		  }*/

	}

}
