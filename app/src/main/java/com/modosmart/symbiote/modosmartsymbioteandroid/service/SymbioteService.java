package com.modosmart.symbiote.modosmartsymbioteandroid.service;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.modosmart.symbiote.modosmartsymbioteandroid.network.NetworkResponseRequest;
import com.modosmart.symbiote.modosmartsymbioteandroid.network.VolleySingleton;
import com.modosmart.symbiote.modosmartsymbioteandroid.utils.ConstantsUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SymbioteService {
    private RequestQueue requestQueue;
    private ArrayList<INetworkService> listeners = new ArrayList<>();
    private final static String ALL_REQUESTS_TAG = "SYMBIOTE";

    public SymbioteService() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
    }

    public void getAllResources(final int requestNumber) {
        NetworkResponseRequest mBasicRequestTest = new NetworkResponseRequest(Request.Method.GET,
                ConstantsUtil.SYMBIOTE_URL + "/innkeeper/public_resources",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        // This is status code: response.statusCode
                        // This is string response: NetworkResponseRequest.parseToString(response)
                        try {
                            String response_string = NetworkResponseRequest.parseToString(response);
                            JSONArray reader = new JSONArray(response_string);
                            responseSuccess(reader, requestNumber);
                        } catch (JSONException e) {
                            responseFailure(requestNumber);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof NetworkError) {
                    responseNetworkError(requestNumber);
                } else if( error instanceof ServerError) {
                    responseServerError(requestNumber);
                } else if( error instanceof AuthFailureError) {
                    responseAuthFailureError(requestNumber);
                } else if( error instanceof ParseError) {
                    responseParseError(requestNumber);
                } else if( error instanceof TimeoutError) {
                    responseTimeoutError(requestNumber);
                } else {
                    responseFailure(requestNumber);
                }
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Cache-Control", "no-cache");
                return headers;
            }
        };
        // To fix error of volley sending request twice
        mBasicRequestTest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Maybe use this in future upgrade to trace requests with request code
        mBasicRequestTest.setTag(ALL_REQUESTS_TAG);
        requestQueue.add(mBasicRequestTest);
    }

    public void controlSwitchStatus(final int requestNumber, final boolean control,
                                    final boolean status, final String symbiote_id,
                                    final String token, final int position) {
        String url = ConstantsUtil.SYMBIOTE_URL + "/rap/Actuators(\'" + symbiote_id + "\')";

//        Map<String, String> params = new HashMap<>();
//        //String body = "{"on": " + status + ",\"control\": " + control + "}";
//        params.put("on", "" + status);
//        params.put("control", "" + control);
//        JSONObject obj = new JSONObject(params);
//        Log.d("DEBUG", obj.toString());

        JSONObject body = new JSONObject();
        try {
            int on_value = (status)? 1 : 0;
            int control_value = (control)? 1 : 0;
            JSONObject on_object = new JSONObject();
            on_object.put("on", on_value);
            JSONObject control_object = new JSONObject();
            control_object.put("control", control_value);
            JSONArray OnOffCapabililty = new JSONArray();
            OnOffCapabililty.put(on_object);
            OnOffCapabililty.put(control_object);
            body.put("OnOffCapabililty", OnOffCapabililty);
        } catch (JSONException e) {
        }

        JsonObjectRequest mBasicRequestTest = new JsonObjectRequest(Request.Method.PUT,
                url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            response.put("position", position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Log.d("DEBUG", response.toString());

                        responseSuccess(response, requestNumber);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if( error instanceof NetworkError) {
                    responseNetworkError(requestNumber);
                } else if( error instanceof ServerError) {
                    responseServerError(requestNumber);
                } else if( error instanceof AuthFailureError) {
                    responseAuthFailureError(requestNumber);
                } else if( error instanceof ParseError) {
                    responseParseError(requestNumber);
                } else if( error instanceof TimeoutError) {
                    responseTimeoutError(requestNumber);
                } else {
                    responseFailure(requestNumber);
                }
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Create Header
                Calendar calendar = Calendar.getInstance();
                long currentMillis =  calendar.getTimeInMillis();
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Cache-Control", "no-cache");
                headers.put("x-auth-size", "1");
                headers.put("x-auth-timestamp", "" + currentMillis);
                headers.put("x-auth-hash", "00000000000000000000");
                String token_header = "{\"token\":\"" + token + "\", \"authenticationChallenge\":\"\",\"clientCertificate\":\"\",\"clientCertificateSigningAAMCertificate\":\"\",\"foreignTokenIssuingAAMCertificate\":\"\"};";
                headers.put("x-auth-1", token_header);
                return headers;
            }
        };
        // To fix error of volley sending request twice
        mBasicRequestTest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Maybe use this in future upgrade to trace requests with request code
        mBasicRequestTest.setTag(ALL_REQUESTS_TAG);
        requestQueue.add(mBasicRequestTest);
    }

    public void getRoomSensorReadings(final int requestNumber, final String symbiote_id,
                                    final String token, final int position) {
        String url = ConstantsUtil.SYMBIOTE_URL + "/rap/Sensors(\'" + symbiote_id + "\')/Observations?$top=1";
        JsonArrayRequest mBasicRequestTest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject responseObject = new JSONObject();
                        try {
                            responseObject = response.getJSONObject(0);
                            responseObject.put("position", position);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        responseSuccess(responseObject, requestNumber);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("DEBUG", error.toString());
                if( error instanceof NetworkError) {
                    responseNetworkError(requestNumber);
                } else if( error instanceof ServerError) {
                    responseServerError(requestNumber);
                } else if( error instanceof AuthFailureError) {
                    responseAuthFailureError(requestNumber);
                } else if( error instanceof ParseError) {
                    responseParseError(requestNumber);
                } else if( error instanceof TimeoutError) {
                    responseTimeoutError(requestNumber);
                } else {
                    responseFailure(requestNumber);
                }
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Create Header
                Calendar calendar = Calendar.getInstance();
                long currentMillis =  calendar.getTimeInMillis();
                Map<String, String> headers = new HashMap<>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Cache-Control", "no-cache");
                headers.put("x-auth-size", "1");
                headers.put("x-auth-timestamp", "" + currentMillis);
                headers.put("x-auth-hash", "00000000000000000000");
                String token_header = "{\"token\":\"" + token + "\", \"authenticationChallenge\":\"\",\"clientCertificate\":\"\",\"clientCertificateSigningAAMCertificate\":\"\",\"foreignTokenIssuingAAMCertificate\":\"\"};";
                headers.put("x-auth-1", token_header);
                return headers;
            }
        };
        // To fix error of volley sending request twice
        mBasicRequestTest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Maybe use this in future upgrade to trace requests with request code
        mBasicRequestTest.setTag(ALL_REQUESTS_TAG);
        requestQueue.add(mBasicRequestTest);
    }

    public void addListener(INetworkService listener) {
        listeners.add(listener);
    }

    public void removeListener(INetworkService listener) {
        listeners.remove(listener);
    }

    private void responseSuccess(JSONObject response, int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseSuccess(response, requestNumber);
        }
    }

    private void responseSuccess(JSONArray response, int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseSuccess(response, requestNumber);
        }
    }

    private void responseFailure(JSONObject failure, int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseFailure(failure, requestNumber);
        }
    }

    private void responseFailure(int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseFailure(requestNumber);
        }
    }

    private void responseTimeoutError(int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseTimeoutError(requestNumber);
        }
    }

    private void responseNetworkError(int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseNetworkError(requestNumber);
        }
    }

    private void responseServerError(int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseServerError(requestNumber);
        }
    }

    private void responseParseError(int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.responseParseError(requestNumber);
        }
    }

    private void responseAuthFailureError(int requestNumber) {
        for (INetworkService listener : listeners) {
            listener.AuthFailureError(requestNumber);
        }
    }

    public void cancelAllRequests() {
        requestQueue.cancelAll(ALL_REQUESTS_TAG);
    }
}
