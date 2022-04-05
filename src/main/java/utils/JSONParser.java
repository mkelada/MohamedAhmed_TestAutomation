package utils;

import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JSONParser {

    public JSONObject Parse_JSON_0(String json) {
        JSONObject obj = null;
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            System.out.println("failed to find " + json);
            e.printStackTrace();
        }
        return obj;
    }

    public Object Parse_JSON_1(String json, String Auto_in_obj) {
        Object Auto1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            Auto1 = obj.get(Auto_in_obj);
        } catch (JSONException e1) {
            System.out.println("failed to find " + Auto_in_obj);
        }
        return Auto1;
    }

    public JSONArray Parse_JSON_2(String json, String Arr_in_obj) {
        JSONArray arr = null;
        try {
            JSONObject obj = new JSONObject(json);
            arr = obj.getJSONArray(Arr_in_obj);
        } catch (JSONException e1) {
            System.out.println("failed to find " + Arr_in_obj);
            e1.printStackTrace();
        }
        return arr;
    }

    public String Parse_JSON_3(String json, String Arr_in_obj, int int_obj_in_arr) {
        String obj1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray(Arr_in_obj);

            obj1 = arr.getString(int_obj_in_arr);
        } catch (JSONException e1) {
            System.out.println("failed to find " + Arr_in_obj);
            e1.printStackTrace();
        }
        return obj1;
    }

    public Object Parse_JSON_4(String json, String Arr_in_obj, int int_obj_in_arr, String Auto_obj_in_arr) {
        Object obj1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray(Arr_in_obj);
            JSONObject obj_in_arr1 = arr.getJSONObject(int_obj_in_arr);
            obj1 = obj_in_arr1.get(Auto_obj_in_arr);
        } catch (JSONException e1) {
            System.out.println("failed to find " + Auto_obj_in_arr);
//            e1.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Failed to find an element");
        }
        return obj1;
    }

    public JSONArray Parse_JSON_5(String json, String Arr_in_obj, int int_obj_in_arr, String arr_in_obj_in_arr) {
        JSONArray obj1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray(Arr_in_obj);
            JSONObject obj_in_arr1 = arr.getJSONObject(int_obj_in_arr);
            JSONArray arr_in_obj_in_arr1 = obj_in_arr1.getJSONArray(arr_in_obj_in_arr);
            obj1 = arr_in_obj_in_arr1;

        } catch (JSONException e1) {
            System.out.println("failed to find " + arr_in_obj_in_arr);
            e1.printStackTrace();
        }
        return obj1;
    }

    public String Parse_JSON_6(String json, String Arr_in_obj, int int_obj_in_arr, String arr_in_obj_in_arr, int int_obj_in_arr2) {
        String obj1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray(Arr_in_obj);
            JSONObject obj_in_arr1 = arr.getJSONObject(int_obj_in_arr);
            JSONArray arr_in_obj_in_arr1 = obj_in_arr1.getJSONArray(arr_in_obj_in_arr);
            obj1 = arr_in_obj_in_arr1.getString(int_obj_in_arr2);

        } catch (JSONException e1) {
            System.out.println("failed to find " + arr_in_obj_in_arr);
            e1.printStackTrace();
        }
        return obj1;
    }

    public String Parse_JSON_7(String json, String Arr_in_obj, int int_obj_in_arr, String arr_in_obj_in_arr, int int_obj_in_arr2, String Auto_obj_in_arr2) {
        Object obj1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray(Arr_in_obj);
            JSONObject obj_in_arr1 = arr.getJSONObject(int_obj_in_arr);
            JSONArray arr_in_obj_in_arr1 = obj_in_arr1.getJSONArray(arr_in_obj_in_arr);
            JSONObject obj_in_arr2 = arr_in_obj_in_arr1.getJSONObject(int_obj_in_arr2);
            obj1 = obj_in_arr2.get(Auto_obj_in_arr2);

        } catch (JSONException e1) {
            System.out.println("failed to find " + Auto_obj_in_arr2);
            e1.printStackTrace();
        }
        return obj1.toString();
    }

    public Object Parse_JSON_8(String json, String Arr_in_obj, int int_obj_in_arr, String obj_in_arr, String Auto_obj_in_arr) {
        Object obj1 = null;
        try {
            JSONObject obj = new JSONObject(json);
            JSONArray arr = obj.getJSONArray(Arr_in_obj);
            JSONObject obj_in_arr1 = arr.getJSONObject(int_obj_in_arr);
            JSONObject obj2_in_arr = obj_in_arr1.getJSONObject(obj_in_arr);
            obj1 = obj2_in_arr.get(Auto_obj_in_arr);
        } catch (JSONException e1) {
            System.out.println("failed to find " + Auto_obj_in_arr);
            e1.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("Failed to find an element");
        }
        return obj1;
    }

    public Object Parse_JSON_9(String jsonStr, int order, String auto) {
        JSONArray jsonarray = new JSONArray(jsonStr);
        JSONObject jsonobject = jsonarray.getJSONObject(order);
        Object object = jsonobject.get(auto);
        return object;
    }

    public Object Parse_JSON_10(String json, String secObject, String auto) {
        JSONObject jsonObject = new JSONObject(json);
        Object object = jsonObject.getJSONObject(secObject).get(auto);
        return object;
    }

    public Object Parse_JSON_11(String json, String Arr_in_Obj, int index) {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray(Arr_in_Obj);
        Object object = arr.get(index);
        return object;
    }

    public JSONArray Parse_JSON_12(String json, String secObject, String Arr_in_Obj) {
        JSONObject jsonObject = new JSONObject(json);
        JSONArray object = jsonObject.getJSONObject(secObject).getJSONArray(Arr_in_Obj);
        return object;
    }

    public Object Parse_JSON_13(String json, String secObject, String Arr_in_Obj, int index) {
        JSONObject jsonObject = new JSONObject(json);
        Object object = jsonObject.getJSONObject(secObject).getJSONArray(Arr_in_Obj).get(index);
        return object;
    }

    public Object Parse_JSON_14(String json, String secObject, String Arr_in_Obj, int index, String Auto) {
        JSONObject jsonObject = new JSONObject(json);
        Object object = jsonObject.getJSONObject(secObject).getJSONArray(Arr_in_Obj).getJSONObject(index).get(Auto);

        return object;
    }

    public Object getObjectFromJSON(Response responseObject, String parentObject, String childObject, boolean Latest) {
        Object objectValue;
        List<Map<String, ?>> listOfObjects = responseObject.jsonPath().getList(parentObject);
        if (Latest)
            objectValue = listOfObjects.get(listOfObjects.size() - 1).get(childObject);
        else
            objectValue = listOfObjects.get(0).get(childObject);

        return objectValue;
    }

    public Object searchInJSONByObjectAndGetObject
            (Response responseObject, Object parentObject, Object searchByParameter, Object searchByValue, Object targetObject) {
        Object objectValue;
        int index = 0;
        List<Map<String, ?>> listOfObjects = responseObject.jsonPath().getList(parentObject.toString());
        for (int i = 0; i < listOfObjects.size(); i++) {
            objectValue = listOfObjects.get(i).get(searchByParameter);
            if (objectValue.equals(searchByValue)) {
                index = i;
                break;
            }
        }
        objectValue = listOfObjects.get(index).get(targetObject);
        return objectValue;
    }

    public JSONArray searchInJSONByObjectAndGetArray
            (Response responseObject, Object parentObject, Object searchByParameter, Object searchByValue, Object targetObject) {
        int index = 0;

        JSONArray objects = Parse_JSON_2(responseObject.asPrettyString(), parentObject.toString());
        for (int i = 0; i < objects.length(); i++) {
            Object searchParameter = Parse_JSON_4(responseObject.asPrettyString(), parentObject.toString(), i, searchByParameter.toString());
            if (searchParameter.equals(searchByValue)) {
                index = i;
                break;
            }
        }
        JSONArray targetArray = Parse_JSON_5(responseObject.asPrettyString(), parentObject.toString(), index, targetObject.toString());

        return targetArray;
    }

    public Object searchInJSONByObjectAndGetObjectInArrayInArray
            (Response responseObject, Object parentObject, Object searchByParameter, Object searchByValue, Object targetArray, int index, Object targetObject) {
        int order = 0;

        JSONArray objects = Parse_JSON_2(responseObject.asPrettyString(), parentObject.toString());
        for (int i = 0; i < objects.length(); i++) {
            Object searchParameter = Parse_JSON_4(responseObject.asPrettyString(), parentObject.toString(), i, searchByParameter.toString());
            if (searchParameter.equals(searchByValue)) {
                order = i;
                break;
            }
        }
        Object valueNeeded = Parse_JSON_7(responseObject.asPrettyString(), parentObject.toString(), order, targetArray.toString(), index, targetObject.toString());

        return valueNeeded;
    }

    public Object searchInJSONByObjectAndGetObjectInArray
            (Response responseObject, Object parentObject, Object searchByParameter, Object searchByValue, Object targetObject) {
        int order = 0;

        JSONArray objects = Parse_JSON_2(responseObject.asPrettyString(), parentObject.toString());
        for (int i = 0; i < objects.length(); i++) {
            Object searchParameter = Parse_JSON_4(responseObject.asPrettyString(), parentObject.toString(), i, searchByParameter.toString());
            if (searchParameter.equals(searchByValue)) {
                order = i;
                break;
            }
        }
        Object valueNeeded = Parse_JSON_4(responseObject.asPrettyString(), parentObject.toString(), order, targetObject.toString());

        return valueNeeded;
    }

    public String readJSONFile(String filePath) throws FileNotFoundException, JSONException {
        InputStream is = new FileInputStream(filePath);
        JSONTokener tok = new JSONTokener(is);
        return new JSONObject(tok).toString();
    }

}